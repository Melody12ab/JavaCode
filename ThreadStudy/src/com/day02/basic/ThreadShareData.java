package com.day02.basic;

//两个线程对i进行加，两个减
public class ThreadShareData {
    private int count = 100;

    public static void main(String[] args) {
        ThreadShareData data=new ThreadShareData();
        myRunnable1 plu=data.new myRunnable1();
        myRunnable2 des=data.new myRunnable2();
        for(int i=0;i<2;i++){
            new Thread(plu).start();
            new Thread(des).start();
        }
    }
    private void inc(){
        count++;
        System.out.println(Thread.currentThread().getName()+"count++:"+count);
    }
    private void des(){
        count--;
        System.out.println(Thread.currentThread().getName()+"count++:"+count);
    }

    class myRunnable1 implements Runnable {

        @Override
        public synchronized void run() {
            for (int i = 0; i < 10; i++) {
               inc();
            }
        }

    }

    class myRunnable2 implements Runnable {

        @Override
        public synchronized void run() {
            for (int i = 0; i < 10; i++) {
                des();
            }
        }

    }
}
