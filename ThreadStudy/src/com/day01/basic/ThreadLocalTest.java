package com.day01.basic;

import java.util.Random;

public class ThreadLocalTest {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
    private static int data;

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                int data = new Random().nextInt();

                @Override
                public void run() {
                    System.out.println("put " + data + " into "
                            + Thread.currentThread().getName());
                    threadLocal.set(data);
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A {
        public void get() {
            int data = threadLocal.get();
            System.out.println("A thread get data from "
                    + Thread.currentThread().getName() + ":" + data);
        }
    }

    static class B {
        public void get() {
            int data = threadLocal.get();
            System.out.println("B thread get data from "
                    + Thread.currentThread().getName() + ":" + data);
        }
    }
}
