package com.smallyuan.labs.concurrency;

import java.util.concurrent.*;

/**
 * 通过Future实现取消
 * @see Future#cancel
 */
public class CancelByFuture {

    private static final ExecutorService taskExec = new ThreadPoolExecutor(4,4,2000, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(200));

    public static void timedRun(Runnable r, long time,TimeUnit timeUnit) throws InterruptedException {
        Future<?> task = taskExec.submit(r);
        try {
            task.get(time,timeUnit);
        } catch (TimeoutException e) {
            // 如果是超时，那么直接在finally中进行cancel
        } catch (ExecutionException e) {
            // 如果在任务中抛出了异常，那么应该重新抛出该异常
            launderThrowable(e.getCause());
        } finally {
            // 如果任务已经结束，那么执行取消操作不会带来任何影响
            // 如果是正在运行中的，将会被中断
            task.cancel(true); // 知道不再需要结果，直接cancel
        }
    }

    /**
     * 强制将未检查的Throwable转换为RuntimeException
     * Error 抛出，RuntimeException 返回，
     * 其他未检查异常抛出 IllegalStateException（表示已在非法或不适当的时间调用了方法，即Java环境或
     * Java应用程序的状态不适合请求的操作）
     * @param t
     * @return
     */
    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not unchecked", t);
        }
    }
}
