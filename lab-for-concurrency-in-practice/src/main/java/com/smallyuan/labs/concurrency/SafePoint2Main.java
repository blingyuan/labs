package com.smallyuan.labs.concurrency;

public class SafePoint2Main {
    public static void main(String[] args) {
        final SafePoint2 safePoint = new SafePoint2(1,1);
        new Thread(() -> {
            safePoint.set(2,2);
            System.out.println(safePoint.toString());
        }).start();

        new Thread(() -> {
            SafePoint2 copyPoint = new SafePoint2(safePoint);
            System.out.println("copy: " + copyPoint.toString() );
        }).start();
    }
}
