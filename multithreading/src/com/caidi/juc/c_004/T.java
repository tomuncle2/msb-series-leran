package com.caidi.juc.c_004;

/**
 * synchronized关键字
 * 对某个对象加锁,  对静态方法加锁 ,  m和mm方法等效，锁的是T类的Class对象（类对象）
 * @author caidi
 */

public class T {

    private static int count = 10;

    public  static void m() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void mm() {
         count --;
    }

    public static void main(String[] args) {
        for (int i = 0;i < 5;i++) {
            MyThread t1 = new MyThread();
            t1.start();
            System.out.println(Thread.currentThread().getName() + " start ");
        }
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            m();
        }
    }
}