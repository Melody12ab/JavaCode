package com.day01.basic;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SwitchTimer {
    static int count;
    public static void main(String[] args) throws Exception {
        class mytimertask extends TimerTask {
            @Override
            public void run() {
                count=(count+1)%2;
                System.out.println("bangbang duang");
                new Timer().schedule(new mytimertask(), 2000+2000*count);
            }
        }
        // 交替定时器
        // 方法一是在mytimertask中维护一个
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("bangbang dunag");
                new Timer().schedule(new mytimertask(), 2000);
            }
        }, 2000);
        while (true) {
            System.out.println(new Date().getSeconds());
            Thread.sleep(1000);
        }
    }
}
