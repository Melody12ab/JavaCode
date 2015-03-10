package com.day01.basic;

import java.util.Random;

public class Threadlocaltest2 {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
    private static ThreadLocal<MyThread> mydatathread = new ThreadLocal<MyThread>();
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                int data = new Random().nextInt();

                @Override
                public void run() {
                    System.out.println("put " + data + " into "
                            + Thread.currentThread().getName());
                    threadLocal.set(data);
                    MyThread mythread = new MyThread();
                    MyThread mydata=mythread;
                    mydata.setName("name"+data);
                    mydata.setAge(data);
                    mydatathread.set(mydata);
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
            MyThread mydata=mydatathread.get();
            System.out.println("A thread get data from "
                    + Thread.currentThread().getName() + ":" + mydata.getName()+";"+mydata.getAge());
        }
    }

    static class B {
        public void get() {
            int data = threadLocal.get();
            System.out.println("B thread get data from "
                    + Thread.currentThread().getName() + ":" + data);
            MyThread mydata=mydatathread.get();
            System.out.println("A thread get data from "
                    + Thread.currentThread().getName() + ":" + mydata.getName()+";"+mydata.getAge());
        }
    }
    static class MyThread{
        private String name;
        private int age;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
    }
}
