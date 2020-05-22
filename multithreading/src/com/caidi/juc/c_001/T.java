package com.caidi.juc.c_001;

/**
 * @author: 蔡迪
 * @date: 20:59 2020/5/18
 * @description: 锁的概念，是对对象加锁。锁的必须是同一个对象 线程要拿到对象锁的锁,才能去操作同步的代码,方法
 */
public class T {

    private  int count = 10;
    /**锁的对象，对象有2位，来标记锁*/
    private Object o = new Object();
    /**想锁谁锁谁*/
    private String lock = new String("lock");

    public void add() {
        synchronized (o) {
            count++;
            System.out.println(Thread.currentThread().getName() + "count: " + count);
        }
    }

    public void sub() {
        synchronized (lock) {
            count--;
            System.out.println(Thread.currentThread().getName() + "count: " + count);
        }
    }

    public static void main(String[] args) {

    }

}