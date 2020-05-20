package com.caidi.juc.c_002;

/**
 * @author: 蔡迪
 * @date: 21:48 2020/5/20
 * @description:  锁this
 */
public class T {

    private  int count = 10;
    // 锁当前调用的对象
    public void add() {
        synchronized (this) {
            count++;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}