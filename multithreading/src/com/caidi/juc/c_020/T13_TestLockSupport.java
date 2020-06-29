package com.caidi.juc.c_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class T13_TestLockSupport {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if(i == 5) {
                    LockSupport.park();
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();

        //LockSupport.unpark(t);

        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after 8 senconds!");

        LockSupport.unpark(t);

    }
}

/** 停车， 车开走
 * LockSupport.park()  LockSupport.unpark()
 * 相比其他cas锁，不用加在某把锁上，直接就可以停车，可以直接唤醒某个线程， 而且不一定先停车，才能unpark(),
 * 先unpark() 后park() 会停不了车。
 */
class MyT13_TestLockSupport{
    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            for(int i=0; i<10; i++) {
                // sout
                System.out.println(Thread.currentThread().getName() + " " + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i==4) {
                    // 停车
                    LockSupport.park();
                }
            }
        });

        //
        thread.start();
        // 加在这里会听停不了车
        //LockSupport.unpark(thread);
        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 车开走
        LockSupport.unpark(thread);

    }
}