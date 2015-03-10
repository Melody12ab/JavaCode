package com.day01.basic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ThreadScopeShareData {
    private static int data;
    public static Map<Thread, Integer> ThreadData = new HashMap<Thread, Integer>();

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                int data = new Random().nextInt();

                @Override
                public void run() {
                    System.out.println("put "
                            + Thread.currentThread().getName() + "  :" + data);
                    ThreadData.put(Thread.currentThread(), data);
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A {
        public void get() {
            int data = ThreadData.get(Thread.currentThread());
            System.out.println("A get data from "
                    + Thread.currentThread().getName() + data);
        }
    }

    static class B {
        public void get() {
            int data = ThreadData.get(Thread.currentThread());
            System.out.println("B get data from "
                    + Thread.currentThread().getName() + data);
        }
    }
}
