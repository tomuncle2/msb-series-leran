package com.caidi.juc.c_000;

import java.util.concurrent.TimeUnit;

/**
 * @author: 蔡迪
 * @date: 20:35 2020/5/20
 * @description: join  可以让线程顺序执行，join的作用是让calling线程等待，直到被调用的子线程执行完，主线程才恢复runable状态
 * 多线程编程是为了提高cpu的利用率，榨干性能。
 * join实现：想象在不考虑性能瓶颈的情况下，相同时间内：1.同时打开qq，浏览器，微信 2.先打开qq，然后浏览器，最后微信。
 * 若三者都是同一任务，比如打开qq，能看到打开速度加快（本质是开启了多个线程，提高了cpu的时间片命中率）
 * 多任务会使每个任务被分配的时间片变短
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


    /**
     * 创建线程
     */
    static class MyThreadThree extends Thread {

        @Override
        public void run() {
            MyThreadFour t4 = new MyThreadFour();
            t4.start();
            try {
                t4.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.getName() + " 状态： " + this.getState());
            for (int i = 0; i< 10; i++) {
                System.out.println("C" + i);
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
    static class MyThreadFour extends Thread {

        @Override
        public void run() {
            System.out.println(this.getName() + " 状态： " + this.getState());
            for (int i = 0; i< 10; i++) {
                System.out.println("D" + i);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //
        }
    }

    // 或者应该t3里面应该是t4.join,

    public static void main(String[] args) {
//        MyThread t1 = new  MyThread();
//        MyThreadTwo t2 = new  MyThreadTwo();
//        System.out.println("main: start ");
//        try {
//            t1.start();
//            t1.join();
//
//            t2.start();
//            t2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("main: end ");


        // 嵌套join 执行顺序线程
        System.out.println("main: start ");
        MyThreadThree t3 = new MyThreadThree();
        t3.start();
        try {
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main: end ");
    }

}