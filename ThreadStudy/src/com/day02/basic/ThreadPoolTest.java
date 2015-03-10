package com.day02.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        //下面是缓存线程池   会根据任务数量的不同从而创建不同数量的线程来执行任务
        ExecutorService threadPool2 = Executors.newCachedThreadPool();
        //下面这个是单线程池   只有一个线程  如果线程死了就在弄一个出来继续干活 保证有一个线程在干活
        //创建单一线程池就可以  实现线程死掉后重新启动
        ExecutorService threadPool3 = Executors.newCachedThreadPool();
        for(int j=1;j<11;j++){
            final int task=j;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=1;i<11;i++){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName()+"is looping of "+i+" for task of "+task);
                    }
                }
            });
        }
//        threadPool.shutdown();执行完成后就结束
        
        //以下为定时器线程池
        Executors.newScheduledThreadPool(2).scheduleAtFixedRate(new Runnable() {
            
            @Override
            public void run() {
                System.out.println("bomming!");
            }
        }, 6, 2, TimeUnit.SECONDS);
    }
}
