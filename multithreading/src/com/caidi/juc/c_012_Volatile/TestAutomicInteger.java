package com.caidi.juc.c_012_Volatile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: 蔡迪
 * @date: 15:57 2020/6/8
 * @description: juc包下基本类型线程安全类
 */
public class TestAutomicInteger {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public  void add() {
        for (int i=0;i<10000;i++) {
            atomicInteger.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        TestAutomicInteger testAutomicInteger = new TestAutomicInteger();
        // 线程集合
        List<Thread> threads = new ArrayList<>();
        for (int i=0;i<10;i++) {
            Thread t = new Thread(()-> {
                testAutomicInteger.add();
            },"t" + i);
            threads.add(t);
            t.start();
        }

//		threads.forEach((thread -> {
//			try {
//				thread.join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}));

//		for (Thread thread : threads) {
//			try {
//				thread.join();
//				System.out.println(Thread.currentThread().getName()  + testVolatileNotSync.count);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(testAutomicInteger.atomicInteger.get());
    }
}