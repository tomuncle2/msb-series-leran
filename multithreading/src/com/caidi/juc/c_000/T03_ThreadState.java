package com.caidi.juc.c_000;

import java.util.concurrent.TimeUnit;

/**
 * @author: 蔡迪
 * @date: 20:44 2020/5/18
 * @description: 线程状态  实例演示，从创建线程到线程执行，到等待状态的变化
 */
public class T03_ThreadState {

    /**
     * 创建线程
     */
    static class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println(this.getName() + " 状态： " + this.getState());
            for (int i = 0; i< 100; i++) {
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


    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        // 创建状态
        System.out.println(t1.getName() + " 状态： " + t1.getState());

        t1.start();

        try {
            // 让调用线程等待 子线程执行完，主线程才继续执行
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t1.getName() + " 状态： " + t1.getState());
    }
}