package com.smallyuan.labs.elasticJob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 作业开发
 */
public class SimpleJobDemo implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        switch (shardingContext.getShardingItem()) {
            case 0:
                System.out.println("ShardingItem0...");
                break;
            case 1:
                System.out.println("ShardingItem1...");
                break;
            case 2:
                System.out.println("ShardingItem2...");
                break;
            default:
                System.out.println("default...");
        }
    }
}
