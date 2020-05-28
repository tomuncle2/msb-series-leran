package com.caidi.juc.c_003;


/**
 * synchronized关键字
 * 对某个对象加锁  修饰方法上
 * @author mashibing
 */
public class T {

    private int count = 10;
    // 等同于在方法的代码执行时要synchronized(this)
    public synchronized void add() {
        count++;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}



