package com.day02.basic;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
    public static void main(String[] args) {
        final duXie xie = new duXie();
        for (int i = 0; i < 3; i++) {
            new Thread() {
                public void run() {
                    while (true) {
                        try {
                            xie.get();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
            new Thread() {
                public void run() {
                    while (true) {
                        try {
                            xie.put(new Random().nextInt(10000));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }
}

class duXie {
    private Object data = null;// 共享数据 只有一个线程能够写
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public void get() throws Exception {
        rwl.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()
                    + "be ready to read data");
            Thread.sleep(new Random().nextInt(3000));
            System.out.println(Thread.currentThread().getName()
                    + "has read data:" + data);
        } finally {
            rwl.readLock().unlock();
        }
    }

    public void put(Object data) throws Exception {
        rwl.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()
                    + "be ready to write data");
            Thread.sleep((long) (Math.random() * 1000));
            this.data = data;
            System.out.println(Thread.currentThread().getName()
                    + "has write data:" + data);
        } finally {
            rwl.writeLock().unlock();
        }
    }
}