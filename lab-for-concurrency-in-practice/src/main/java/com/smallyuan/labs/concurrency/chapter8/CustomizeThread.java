package com.smallyuan.labs.concurrency.chapter8;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 自定义线程
 * 1. 为线程指定名字
 * 2. 设置自定义 UncaughtExceptionHandler 向Logger中写入信息
 * 3. 维护统计信息（包括有多少线程被创建和销毁）
 * 4. 在线程被创建或终止时把调试信息写入日志
 */
public class CustomizeThread extends Thread {

    private static final String DEFAULT_NAME = "CustomizeThread";

    private static volatile boolean debugLifecycle = false;
    /** 标记创建的线程数量 */
    private static final AtomicInteger created = new AtomicInteger();
    /** 标记存活的线程数量 */
    private static final AtomicInteger alive = new AtomicInteger();

    private static final Logger log = Logger.getAnonymousLogger();

    public CustomizeThread(Runnable r) {
        this(r, DEFAULT_NAME);
    }

    public CustomizeThread(Runnable r, String name){
        super(r,name + "-" + created.incrementAndGet());
        setDefaultUncaughtExceptionHandler((t,e) -> log.log(Level.SEVERE,"UNCAUGHT in Thread " + t.getName(),e));
    }

    public void run() {
        boolean debug = debugLifecycle;
        if (debug) log.log(Level.FINE,"Created " + getName());
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            if (debug) log.log(Level.FINE,"Exiting " + getName());
        }
    }

    public static int getThreadsCreated() {return created.get();}

    public static int getThreadsAlive() {return alive.get();}

    public static boolean getDebug() {return debugLifecycle;}

    public static void setDebug(boolean b) {debugLifecycle = b;}
}
