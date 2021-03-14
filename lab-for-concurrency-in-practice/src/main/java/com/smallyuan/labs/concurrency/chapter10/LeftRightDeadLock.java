package com.smallyuan.labs.concurrency.chapter10;

public class LeftRightDeadLock {
    private final Object left = new Object();
    private final Object right = new Object();
    
    public void leftRight() {
        synchronized (left) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (right) {

            }
        }
    }
    
    public void rightLeft() {
        synchronized (right){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (left) {

            }
        }
    }

    public static void main(String[] args) {
        LeftRightDeadLock deadLock = new LeftRightDeadLock();
        Thread t1 = new Thread(() -> deadLock.leftRight());
        Thread t2= new Thread(() -> deadLock.rightLeft());
        t1.start();
        t2.start();
    }

}