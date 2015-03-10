package com.day01.basic;

import java.util.Random;

public class threadlocal3 {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                int data = new Random().nextInt();

                @Override
                public void run() {
                    System.out.println("put " + data + " into "
                            + Thread.currentThread().getName());
                    threadLocal.set(data);
                    MyThread.getInstance().setName("name"+data);
                    MyThread.getInstance().setAge(data);
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A {
        public void get() {
            int data = threadLocal.get();
            MyThread mydata=MyThread.getInstance();
            System.out.println("A thread get data from "
                    + Thread.currentThread().getName() + ":" + data);
            System.out.println("A thread get data from "
                    + Thread.currentThread().getName() + ":" + mydata.getName()+";"+mydata.getAge());
        }
    }

    static class B {
        public void get() {
            int data = threadLocal.get();
            MyThread mydata=MyThread.getInstance();
            System.out.println("B thread get data from "
                    + Thread.currentThread().getName() + ":" + data);
            System.out.println("A thread get data from "
                    + Thread.currentThread().getName() + ":" + mydata.getName()+";"+mydata.getAge());
        }
    }
    //MyThread的对象就是与当前线程绑定的对象
    static class MyThread{
        private MyThread(){}
        public static /*synchronized*/ MyThread getInstance(){
            MyThread instance=mydata.get();
            if(instance==null){
                instance=new MyThread();
                mydata.set(instance);
            }
            return instance;
        }
        private static ThreadLocal<MyThread> mydata=new ThreadLocal<threadlocal3.MyThread>();
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
