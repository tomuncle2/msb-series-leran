package com.caidi.juc.c_020;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class T06_TestCountDownLatch {
    public static void main(String[] args) {
        //usingJoin();
        usingCountDownLatch();
    }

    private static void usingCountDownLatch() {
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);

        for(int i=0; i<threads.length; i++) {
            threads[i] = new Thread(()->{
                int result = 0;
                for(int j=0; j<10000; j++) result += j ;
                // 0就不减1了
                latch.countDown();
                System.out.println(Thread.currentThread().getName() + " 执行完一次countDown ....");
            });
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end latch");
    }

    private static void usingJoin() {
        Thread[] threads = new Thread[100];

        for(int i=0; i<threads.length; i++) {
            threads[i] = new Thread(()->{
                int result = 0;
                for(int j=0; j<10000; j++) result += j;
            });
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("end join");
    }
}

/**
 *倒数门栓
 * @date 16:10 2020/6/28
 * @param null
 * @return
 */
class MyT06_TestCountDownLatch {

    public static void useJoin() {
        Thread[] threads = new Thread[100];


        for (int i = 0; i<threads.length; i++) {
            threads[i] = new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " run....");
                // 归零为止
            });
        }

        for (int i = 0; i<threads.length; i++) {
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //  join 主线程在此等待
//        for (int i = 0; i<threads.length; i++) {
//            try {
//                threads[i].join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * 一般用户等待所有线程结束，也可以一个线程执行多次countDownLatch.countDown()， 其实就是记录线程执行--次数
     * @date 10:34 2020/6/29
     * @param
     * @return void
     */
    public static void useCountDownLatch() {
        Thread[] threads = new Thread[100];
        CountDownLatch countDownLatch = new CountDownLatch(threads.length);

        for (int i = 0; i<threads.length; i++) {
            threads[i] = new Thread(()->{
                System.out.println(Thread.currentThread().getName() + " start run....");
                // 归零为止
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName() + " 执行完一次countDown ....");
            });
        }

        for (int i = 0; i<threads.length; i++) {
            threads[i].start();
        }

        // 类似join 主线程在此等待,归零门栓打开 流程往下执行
        try {
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName() + " main run....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        useCountDownLatch();
        //useJoin();
    }

}
