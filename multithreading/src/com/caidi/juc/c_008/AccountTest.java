package com.caidi.juc.c_008;

/**
 * @author: 蔡迪
 * @date: 21:24 2020/5/21
 * @description: 转账
 */
public class AccountTest {

    private int money = 100;

    public synchronized void set(int changeMoney) {
        this.money = money + changeMoney;
    }

    public int get() {
        return money;
    }

    public static void main(String[] args) {
        AccountTest accountTest = new AccountTest();
        /*new Thread(()->t.m1(), "t1").start();*/
        new Thread(()->{accountTest.set(50);},"").start();
    }
}