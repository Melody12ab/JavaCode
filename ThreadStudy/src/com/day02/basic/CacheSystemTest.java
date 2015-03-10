package com.day02.basic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 缓存系统
 * 
 * @author melody
 */

public class CacheSystemTest {
    private Map<String, Object> cache = new HashMap<String, Object>();

    public static void main(String[] args) {

    }

    ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public Object getObj(String key) {
        rwl.readLock().lock();
        Object obj = null;
        try {
            obj = cache.get(key);
            if (obj == null) {
                rwl.readLock().unlock();// 当没有数据的时候 准备写数据 放读锁 加谢锁
                rwl.writeLock().lock();
                try { // 为避免异常或者错误
                    if (obj == null) {
                        obj = "aaa";// 实际这个时候应该是去数据库中找
                    }
                } finally {
                    rwl.writeLock().unlock(); // 最后都要释放谢锁
                }
                rwl.readLock().lock(); // 重新上读锁
            }
        } finally {
            rwl.readLock().unlock(); // 释放锁
        }
        return obj;
    }
}
