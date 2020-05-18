package com.caidi.juc.c000;

/**
 * @author: 蔡迪
 * @date: 20:42 2020/5/18
 * @description: sleep/yeild
 */
public class T02_SleepAndYeild {

    // s
    static void testWait() {

    }

    // 进入线程等待状态
    static void testSleep() {
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " A ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 让出一下下
    static void testYeild() {
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " A ");
                //
                Thread.yield();
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " B ");
                Thread.yield();
            }
        }).start();
    }

    static void testJoin() {
        Thread t1 = new Thread(()-> {
            for (int i = 0; i < 100; i++) {
                System.out.println("joinA" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(()-> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("joinB" + i);
            }
        });
    }

    public static void main(String[] args) {
        testSleep();
        testYeild();
        testJoin();
    }

}