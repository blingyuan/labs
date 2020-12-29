package com.smallyuan.labs.canal;

public abstract class AbstractKafkaTest {

    public static String topic = "example";
    public static Integer partition = null;
    public static String groupId = "g4";
    public static String servers = "slave1:6667";
    public static String zkServers = "slave1:2181";

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

}
