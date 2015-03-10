package com.day01.basic;

public class traditonalSynchronized {
    public static void main(String[] args) {
        new traditonalSynchronized().init();
    }
    private void init(){
        final output out=new output();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    out.print("lijilong");
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    out.print("zhangxiaoxiang");
                }
            }
        }).start();
    }
    
    //以下三个方法能够同步  注意在几个方法同步时候用的锁一定要一样  不然就不能同步
    static class output{
        public void print(String string){
            int length=string.length();
            synchronized (this) 
//            synchronized (output.class)  与print3同步 
            {
                for(int i=0;i<length;i++){
                    System.out.print(string.charAt(i));
                }
                System.out.println();    
            }
        }
        public synchronized void print2(String string){
            int length=string.length();
                for(int i=0;i<length;i++){
                    System.out.print(string.charAt(i));
                }
                System.out.println();    
        }
        public static synchronized void print3(String string){
            int length=string.length();
                for(int i=0;i<length;i++){
                    System.out.print(string.charAt(i));
                }
                System.out.println();    
        }
    }
}
