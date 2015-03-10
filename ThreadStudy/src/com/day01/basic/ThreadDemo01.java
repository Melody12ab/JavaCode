package com.day01.basic;

public class ThreadDemo01 {
    public static void main(String[] args) {
        //1 override the run method
        Thread thread=new Thread(){
            public void run() {
                while(true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread1:1"+Thread.currentThread().getName());
                    System.out.println("thread1:2"+this.getName());
                }
            }
        };
        thread.start();
        
        
        //2  runable not null
        Thread thread2=new Thread(new Runnable() {
            public void run() {
                while(true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("thread2"+Thread.currentThread().getName());
                }
            }
        });
        thread2.start();
    }
}
