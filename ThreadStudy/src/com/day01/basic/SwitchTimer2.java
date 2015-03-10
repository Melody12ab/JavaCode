package com.day01.basic;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SwitchTimer2 {
    public static void main(String[] args) throws InterruptedException {
        //交换定时器
        // 方法二是弄两颗炸弹
        new Timer().schedule(new mytimertaska(), 2000);
        while(true){
            System.out.println(new Date().getSeconds());
            Thread.sleep(1000);
        }
    }
}
class mytimertaska extends TimerTask{

    @Override
    public void run() {
        System.out.println("bang duang A");
        new Timer().schedule(new mytimertaskb(), 2000);
    }
    
}
class mytimertaskb extends TimerTask{

    @Override
    public void run() {
        // TODO Auto-generated method stub
       System.out.println("bang duang B");
       new Timer().schedule(new mytimertaska(), 4000);
    }
    
}
