package com.caidi.juc.c_sync;

/**
 * @author: 蔡迪
 * @date: 11:40 2021/10/15
 * @description: 仓库
 */
public class Warehouse {

    // 产品数量上线
    static int count = 50;

    static int max = 100;


    // 生产方法
    public synchronized void add() {
        if (count < max) {
            count++;
            System.out.println("生产者生产：" + Thread.currentThread().getName()
            + "生产后：" + count);
            try {
                // 睡眠1s
                Thread.sleep(100);
                // 唤醒所以此监视对象的阻塞线程
                this.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                // 放弃锁,等待
                this.wait();
            } catch (InterruptedException e) {
                // 没优获取到锁放弃
                e.printStackTrace();
            }
        }
    }

    // 消费方法
    public synchronized void sub() {
        if (count > 0) {
            count--;
            System.out.println("消费者消费：" + Thread.currentThread().getName()
                    + "消费后前：" + count);
            try {
                // 睡眠1s 没有放弃锁
                Thread.sleep(100);
                // 唤醒所以此监视对象的阻塞线程
                this.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                // 放弃锁
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}