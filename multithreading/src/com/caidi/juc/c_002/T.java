package com.caidi.juc.c_002;

/**
 * synchronized关键字
 * 对某个对象加锁  修饰代码块,用this
 * @author mashibing
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