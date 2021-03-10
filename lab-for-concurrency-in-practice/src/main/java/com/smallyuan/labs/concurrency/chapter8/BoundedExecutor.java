package com.smallyuan.labs.concurrency.chapter8;

import java.util.concurrent.*;

/**
 * 通过Semaphore来限制任务的提交速率
 * 使用一个无界队列（不能限制队列的大小和任务的到达率）
 * 设置信号量的上界为线程池的大小加可排队的任务的数量（信号量需要控制正在执行的和等待执行的任务数量）
 */
public class BoundedExecutor {
    private final Executor exec;

    private final Semaphore semaphore;

    public BoundedExecutor(Executor exec, Semaphore semaphore) {
        this.exec = exec;
        this.semaphore = semaphore;
    }

    public void submitTask(final Runnable r) throws InterruptedException {
        semaphore.acquire();
        try {
            exec.execute(() -> {
                try {
                    r.run();
                } finally {
                    semaphore.release();
                }
            });
        } catch (RejectedExecutionException e) {
            semaphore.release();
        }
    }
}
