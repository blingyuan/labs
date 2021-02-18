package com.smallyuan.labs.concurrency;

public class SafePoint2 {

    private int x,y;

    public SafePoint2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private SafePoint2(int[] a) {
        this(a[0],a[1]);
    }

    public SafePoint2(SafePoint2 p) {
        this(p.x,p.y);
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
        return "SafePoint2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
