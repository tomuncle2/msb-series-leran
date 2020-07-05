package com.caidi.juc.c_020;

import java.util.concurrent.Exchanger;

public class T12_TestExchanger {

    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(()->{
            String s = "T1";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);

        }, "t1").start();


        new Thread(()->{
            String s = "T2";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);

        }, "t2").start();


    }
}

/**
 * 两个线程交换便变量，两个游戏账号交换装备  一个线程等着另一个线程
 */
class MyT12_TestExchanger{

    static Exchanger<String> exchanger = new Exchanger<>();
    public static void main(String[] args) {
        new Thread(()->{
            String s = "sss级宝刀";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" t1 s: " + s);
        }).start();

        new Thread(()->{
            String s = "橙装护甲";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" t2 s: " + s);
        }).start();
    }
}
