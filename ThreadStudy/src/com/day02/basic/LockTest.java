package com.day02.basic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    public static void main(String[] args) {
        new LockTest().init();
    }

    private void init() {
        final output out = new output();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    out.print("lijilong");
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    out.print("zhangxiaoxiang");
                }
            }
        }).start();
    }

    class output {
        Lock lock = new ReentrantLock();

        public void print(String string) {
            int length = string.length();
            lock.lock();// 加锁
            //为了防止一下代码中抛出Exception或者发生Error后无法释放锁
            try {
                for (int i = 0; i < length; i++) {
                    System.out.print(string.charAt(i));
                }
                System.out.println();
            } finally {
                //无论如何最后都释放锁
                lock.unlock();
            }
        }
    }
}
