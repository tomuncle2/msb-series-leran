package com.caidi.juc.c_020;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class T07_TestCyclicBarrier {
    public static void main(String[] args) {
        //CyclicBarrier barrier = new CyclicBarrier(20);

        CyclicBarrier barrier = new CyclicBarrier(20, () -> System.out.println("����"));

        /*CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
            @Override
            public void run() {
                System.out.println("���ˣ�����");
            }
        });*/

        for(int i=0; i<100; i++) {

                new Thread(()->{
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }).start();

        }


    }
}

/**
 *  栅栏： 满人发车，循环使用
 * @date 11:12 2020/6/29
 * @param null
 * @return
 */
class MyT07_TestCyclicBarrier {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10,()->{
            // 满人后发车
            System.out.println(Thread.currentThread().getName() + " 发车....");
        });

        /** 这里相当于先到达的九个线程需要等待，第十个线程到达，栅栏推倒，发车。
         * 发车后，每个线程继续执行自己的逻辑，栅栏没有推倒之前，都等在那。
         */
        for(int i =0; i<100; i++) {
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName() + " 抵达栅栏，等待....");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " 推倒栅栏后，执行下一步....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}