package com.smallyuan.labs.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;

import java.net.InetSocketAddress;
import java.util.List;

public class SimpleCanalClientExample {
    public static void main(String[] args) {
        CanalConnector canalConnector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),11111),"example","root","bieo5512");
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            canalConnector.connect();
            canalConnector.subscribe(".*\\..*");
            canalConnector.rollback();
            int totalEmptyCount = 120;
            while (emptyCount < totalEmptyCount) {
                // 获得指定数量的数据
                Message message = canalConnector.getWithoutAck(batchSize);
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count:" + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                } else {
                    emptyCount = 0;
                    printEntry(message.getEntries());
                }
                canalConnector.ack(batchId);
            }
            System.out.println("empty too many times, exit");
        } finally {
            canalConnector.disconnect();
        }

    }

    private static void printEntry(List<CanalEntry.Entry> entrys) {

        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            CanalEntry.RowChange rowChange = null;

            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error,data:" + entry.toString());
            }
        }
    }
}
