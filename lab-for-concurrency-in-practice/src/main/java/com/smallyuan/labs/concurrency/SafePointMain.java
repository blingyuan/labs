package com.smallyuan.labs.concurrency;

public class SafePointMain {
    public static void main(String[] args) {
        final SafePoint safePoint = new SafePoint(1,1);
        new Thread(() -> {
            safePoint.set(2,2);
            System.out.println(safePoint.toString());
        }).start();

        new Thread(() -> {
            SafePoint copyPoint = new SafePoint(safePoint);
            System.out.println("copy: " + copyPoint.toString() );
        }).start();
    }
}
