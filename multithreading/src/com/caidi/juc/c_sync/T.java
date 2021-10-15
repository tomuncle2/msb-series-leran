package com.caidi.juc.c_sync;

/**
 * @author: 蔡迪
 * @date: 11:03 2021/10/15
 * @description: 线程同步
 */
public class T {

    int money = 100;

    public synchronized void add() {
        money++;
    }

    public void sub() {
        money--;
    }

    public synchronized static void subb() {
        System.out.println("");
    }

    //
    static class C {

    }

    static class B {

    }
}