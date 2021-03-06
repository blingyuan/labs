package com.smallyuan.labs.concurrency;

import java.util.Objects;

public class SafePoint {

    private int x,y;

    public SafePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private SafePoint(int[] a) {
        this(a[0],a[1]);
    }

    public SafePoint(SafePoint p) {
        this(p.get());
    }

    public synchronized int[] get() {
        return new int[] {x, y};
    }

    public synchronized void set(int x, int y) {
        this.x = x;
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.y = y;
    }

    @Override
    public String toString() {
        return "SafePoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
