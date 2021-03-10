package com.smallyuan.labs.concurrency.chapter8;

import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工厂
 * 创建了一个新的 CustomizeThread （自定义线程）
 */
public class CustomizeThreadFactory implements ThreadFactory {

    /**
     * 线程池的名字
     */
    private final String poolName;

    public CustomizeThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new CustomizeThread(r,poolName);
    }
}
