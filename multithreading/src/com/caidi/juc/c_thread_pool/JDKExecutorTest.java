package com.caidi.juc.c_thread_pool;

import java.util.concurrent.*;

/**
 * @author: 蔡迪
 * @date: 14:39 2021/10/17
 * @description: jdk自带线程池
 */
public class JDKExecutorTest {

    public static void main(String[] args) {

        ExecutorService executorService1 = Executors.newFixedThreadPool(10);

        ExecutorService executorService2 = Executors.newCachedThreadPool();

        ExecutorService executorService3 = Executors.newSingleThreadExecutor();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

        Future future = executorService1.submit(()->{
            System.out.println("fd");
        });
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}