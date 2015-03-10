package com.day01.basic;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TraditionalTimerTest {
    public static void main(String[] args) throws Exception {
        
        class mytimertask extends TimerTask{
            @Override
            public void run() {
                System.out.println("bangbang duang");
                new Timer().schedule(new mytimertask(), 2000);
            }
        }
        /*
        //定时器
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("bangbang dunag");
                new Timer().schedule(this, 2000);//报错  需要多炸弹  因此需要将Timertask从匿名状态提出来
            }
        },2000);
        }, 10000,3000);  定时任务   10秒后开始执行 之后每隔三秒执行一次
        while(true){
            System.out.println(new Date().getSeconds());
            Thread.sleep(1000);
        }*/
        
    //连续定时器
    new Timer().schedule(new TimerTask() {
        @Override
        public void run() {
            System.out.println("bangbang dunag");
            new Timer().schedule(new mytimertask(),2000);
        }
    },2000);
    while(true){
        System.out.println(new Date().getSeconds());
        Thread.sleep(1000);
        }       
    }
}

