package com.smallyuan.labs.canal;

import com.alibaba.otter.canal.client.kafka.KafkaCanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CanalKafkaClientExample {

    protected final static Logger logger  = LoggerFactory.getLogger(CanalKafkaClientExample.class);

    private KafkaCanalConnector connector;

    private static volatile boolean running = false;

    private Thread thread = null;

    private Thread.UncaughtExceptionHandler handler = (t,e)  -> logger.error("parse events has an error", e);

    public CanalKafkaClientExample(String zkServers, String servers, String topic, Integer partition, String groupId){
        connector = new KafkaCanalConnector(servers, topic, partition, groupId, null, false);
    }

    public static void main(String[] args) {
        final CanalKafkaClientExample kafkaClientExample = new CanalKafkaClientExample(AbstractKafkaTest.zkServers,
                AbstractKafkaTest.servers,
                AbstractKafkaTest.topic,
                AbstractKafkaTest.partition,
                AbstractKafkaTest.groupId);

        logger.info("## start the kafka consumer: {}-{}", AbstractKafkaTest.topic, AbstractKafkaTest.groupId);
        kafkaClientExample.start();
        logger.info("## the canal kafka consumer is running now ......");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                logger.info("## stop the kafka consumer");
                kafkaClientExample.stop();
            } catch (Throwable e) {
                logger.warn("##something goes wrong when stopping kafka consumer:", e);
            } finally {
                logger.info("## kafka consumer is down.");
            }
        }));
    }

    public void start() {
        Assert.notNull(connector,"connector is null");
        thread = new Thread(this::process);
        thread.setUncaughtExceptionHandler(handler);
        thread.start();
        running = true;
    }

    public void stop() {
        if (!running) {
            return;
        }
        running = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    private void process() {
        while (!running) {
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                // ignore
            }
        }

        while (running) {
            try {
                connector.connect();
                connector.subscribe();
                while (running) {
                    try {

                        List<Message> messages = connector.getListWithoutAck(100L, TimeUnit.MILLISECONDS);
                        if (messages == null) {
                            continue;
                        }
                        for (Message message : messages) {
                            long batchId = message.getId();
                            int size = message.getEntries().size();
                            if (batchId == -1 || size == 0) {

                            } else {
                                logger.info(message.toString());
                            }
                        }
                        connector.ack();
                    }catch (Exception e) {
                        logger.error(e.getMessage(),e);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
            connector.unsubscribe();
            connector.disconnect();
        }
    }

}
