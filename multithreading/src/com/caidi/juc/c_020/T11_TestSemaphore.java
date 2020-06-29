package com.caidi.juc.c_020;
/**
 * 信号量  允许一个线程同时执行
 */
import java.util.concurrent.Semaphore;

public class T11_TestSemaphore {
    public static void main(String[] args) {
        //Semaphore s = new Semaphore(2);
        Semaphore s = new Semaphore(2, true);
        //允许一个线程同时执行
        //Semaphore s = new Semaphore(1);

        new Thread(()->{
            try {
                // 获取到
                s.acquire();

                System.out.println("T1 running...");
                Thread.sleep(200);
                System.out.println("T1 running...");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放
                s.release();
            }
        }).start();

        new Thread(()->{
            try {
                s.acquire();

                System.out.println("T2 running...");
                Thread.sleep(200);
                System.out.println("T2 running...");

                s.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
/**
 * 限流 一个收费站 有几个车道 同时有几辆车通过（ 同时有几个线程可以执行完-等待线程执行完，才计数减1）
 */
class MyT11_TestSemaphore {
    public static void main(String[] args) {
        //Semaphore semaphore = new Semaphore(2);

        // 公平/非公平锁
        Semaphore semaphore = new Semaphore(1, false);

        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("T1 running...");
                Thread.sleep(1000);
                System.out.println("T1 running...");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("T2 running...");
                Thread.sleep(200);
                System.out.println("T2 running...");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
