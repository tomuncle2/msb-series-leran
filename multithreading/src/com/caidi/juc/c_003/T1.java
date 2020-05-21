package com.caidi.juc.c_003;

/**
 * synchronized关键字
 * 对某个对象加锁  加锁方法和非加锁方法可以同时存在，非加锁方法可以随意访问
 * @author mashibing
 */
public class T1 {

    private int count = 10;

    public synchronized void m() { //等同于在方法的代码执行时要synchronized(this)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public void n() { //访问这个方法的时候不需要上锁
        count++;
    }
}
