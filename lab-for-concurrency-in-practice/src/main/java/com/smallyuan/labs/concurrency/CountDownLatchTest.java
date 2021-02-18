package com.smallyuan.labs.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * 统计多个线程所消耗的时间
 */
public class CountDownLatchTest {
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(() -> {
                try {
                    startGate.await(); // 这里在等着 startGate
                    try {
                        task.run();
                    } finally {
                        endGate.countDown(); // 每执行完一个任务，将endGate里的计数器-1，
                    }
                } catch (InterruptedException ignored) {
                }
            });
            t.start();
        }
        long start = System.nanoTime();
        startGate.countDown();  // 在这里 startGate 才开始，在这之前，所有的线程都在等着
        endGate.await();    // 在这里等着所有线程结束 endGate计数器减为0
        return System.nanoTime() - start;
    }
}
