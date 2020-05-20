package com.caidi.juc.c_003;

/**
 * @author: 蔡迪
 * @date: 21:51 2020/5/20
 * @description:
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
