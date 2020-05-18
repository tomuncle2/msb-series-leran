package com.caidi.juc.c000;

import com.caidi.juc.c001.T;

import java.util.concurrent.TimeUnit;

/**
 * @author: 蔡迪
 * @date: 21:38 2020/5/17
 * @description: 怎么创建线程
 */
public class T01_HowtoCreateThread {

    private  static class T1 extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("t1"  + i + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                    //Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private  static class T2 implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("t2" + i  + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                    //Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    创建线程方式
     */
    public static void main(String[] args) {
        T1 t1 = new T1();
        t1.start();

        T2 t2 = new T2();
        new Thread(t2).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类！" + Thread.currentThread().getName());
            }
        }).start();

        new Thread(()->{
            System.out.println("lambda！" + Thread.currentThread().getName());
        }).start();

        new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类！" + Thread.currentThread().getName());
            }
        };

        // 1.thread 2.runable 3.线程池

    }


}