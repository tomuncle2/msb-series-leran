package com.caidi.juc.c_000;

import java.util.concurrent.TimeUnit;

/**
 * @author: 蔡迪
 * @date: 20:35 2020/5/20
 * @description: join
 */
public class T04_Join {

    /**
     * 创建线程
     */
    static class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println(this.getName() + " 状态： " + this.getState());
            for (int i = 0; i< 10; i++) {
                System.out.println("A" + i);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //
        }
    }

    /**
     * 创建线程
     */
    static class MyThreadTwo extends Thread {

        @Override
        public void run() {
            System.out.println(this.getName() + " 状态： " + this.getState());
            for (int i = 0; i< 10; i++) {
                System.out.println("B" + i);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //
        }
    }

    public static void main(String[] args) {
        MyThread t1 = new  MyThread();
        MyThreadTwo t2 = new  MyThreadTwo();
        t1.start();
        t2.start();
        System.out.println("main: start ");
        try {
            t1.join();
            //t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main: end ");
    }

}