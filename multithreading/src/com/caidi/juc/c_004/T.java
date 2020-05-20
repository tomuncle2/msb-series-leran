package com.caidi.juc.c_004;

/**
 * @author: 蔡迪
 * @date: 21:51 2020/5/20
 * @description:
 */
/**
 * synchronized关键字
 * 对某个对象加锁
 * @author mashibing
 */



public class T {

    private static int count = 10;

    public synchronized static void m() { //这里等同于synchronized(FineCoarseLock.class)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void mm() {
        synchronized(T.class) { //考虑一下这里写synchronized(this)是否可以？
            count --;
        }
    }

}