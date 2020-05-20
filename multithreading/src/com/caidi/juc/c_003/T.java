package com.caidi.juc.c_003;

/**
 * @author: 蔡迪
 * @date: 21:51 2020/5/20
 * @description: 锁方法
 */
public class T {

    private int count = 10;
    // 等同于在方法的代码执行时要synchronized(this)
    public synchronized add() {
        count++;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}



