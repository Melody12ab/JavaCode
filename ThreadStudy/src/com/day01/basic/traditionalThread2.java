package com.day01.basic;

//题目   子线程运行10次  主线程运行50次   如此重复50次
public class traditionalThread2 {
    public static void main(String[] args) throws Exception {
        final Bussiness bussiness = new Bussiness();
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 1; i < 51; i++) {
                    try {
                        bussiness.sub(i);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        for (int i = 1; i < 51; i++) {
            bussiness.main(i);
        }
    }
}


//将需要同步的有关功单独封装成类   高内聚  将同步的方法放在了一个类里面  这个类就天生同步了
//要用到共同数据（同步锁）或共同算法的若干个方法应该归在同一个类上面，供内聚和健壮性
class Bussiness {
    private boolean bShoould=true;
    public synchronized void sub(final int i) throws Exception {
        if(!bShoould){//这里的if可以换成while  更好  防止伪唤醒
            this.wait();
        }
        for (int j = 1; j < 11; j++) {
            System.out.println("sub thread :" + j + "   loop of :" + i);
        }
        bShoould=false;
        this.notify();//唤醒一个正在等待的线程；
    }

    public synchronized void main(int i) throws Exception {
        if(bShoould){
            this.wait();
        }
        for (int j = 1; j < 51; j++) {
            System.out.println("main thread :" + j + "   loop of :" + i);
        }
        bShoould=true;
        this.notify();
    }
}
