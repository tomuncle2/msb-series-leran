package com.caidi.juc.c_001;

import java.util.concurrent.TimeUnit;

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


    class CC {
        int count = 100;
        public synchronized void add() {
            count ++;
            System.out.println("eat food");
        }

        public synchronized void sum() {
            count --;
            System.out.println("eat food");
        }
    }

    static class AA extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("aa"  + i + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                    //Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class BB extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("bb"  + i + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                    //Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}