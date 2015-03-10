package com.day02.basic;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService threadpool = Executors.newSingleThreadExecutor();
        Future<String> future = threadpool.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "hello!";
            }
        });
        System.out.println("等待结果");
        System.out.println("结果为："+future.get());
        
        
        
        //CompletionService用于提交一组Callable去执行  然后通过take方法返回最先执行完的任务的Future对象  并可以调用其get方法得到返回值
        ExecutorService threadpool2=Executors.newFixedThreadPool(10);
        CompletionService<Integer> completionService=new ExecutorCompletionService<Integer>(threadpool2);
        for(int i=1;i<11;i++){
            final int task=i;
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(new Random().nextInt(5000));
                    return task;
                }
            });
        }
        for(int i=0;i<10;i++){
            System.out.println(completionService.take().get());
        }
    }
}
