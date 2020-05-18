package com.caidi.juc.c000;

import java.util.concurrent.TimeUnit;

/**
 * @author: 蔡迪
 * @date: 21:36 2020/5/17
 * @description: 线程概念
 */
public class TestThread01 {
    /*
        开启线程
     */
    public static void main(String[] args) {
        Thread myThread = new TestThread01.MyThread();
        myThread.start();
        //相当于没有开启线程 直接执行
        //myThread.run();
        for (int i = 0; i < 10; i++) {
            System.out.println("main" + i);
            try {
                TimeUnit.SECONDS.sleep(1);
                //Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private  static class MyThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("MyThread" + i);
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