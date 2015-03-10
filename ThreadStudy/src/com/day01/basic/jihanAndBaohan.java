package com.day01.basic;

public class jihanAndBaohan {
 // 单例类的饱汉模式 无论什么时候都返回一个实例 是不是第一次都返回同一个实例
    private jihanAndBaohan() {}

    public static jihanAndBaohan getInstance() {
        return instance;
    }

    private static jihanAndBaohan instance = new jihanAndBaohan();

}

class jiHan {
    //饥汉模式   但对象为空时候创建  否则之间返回实例  
    //但是在多线程的环境下就可能第一个对象正在创建时候  还没赋值就切换到其他线程  造成资源浪费
    //因此在多线程环境下需要对其方法进行同步化
    private jiHan() {}
    public static synchronized jiHan getInstance() {
        if (instance == null) {
            instance=new jiHan();
        }
        return instance;
    }

    private static jiHan instance = null;
}
