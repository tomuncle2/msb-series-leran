package com.caidi.juc.c_026_01_ThreadPool;
/**
 * 自定义线程池 nb
 */

import java.io.IOException;
import java.util.concurrent.*;

public class T05_00_HelloThreadPool {

    static class Task implements Runnable {
        private int i;

        public Task(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " Task " + i);
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task{" +
                    "i=" + i +
                    '}';
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4,
                60, TimeUnit.SECONDS,
                // 任务等待队列
                new ArrayBlockingQueue<Runnable>(4),
                // 自定义线程工厂，给线程起名字（便于排查问题线程）
                Executors.defaultThreadFactory(),
                // 拒绝策略
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 8; i++) {
            tpe.execute(new Task(i));
        }

        // 任务等待队列的任务详情 打印每个任务（runnable实现）。
        System.out.println(tpe.getQueue());

        // 临界任务
//        tpe.execute(new Task(100));
//
        // 任务等待队列的任务详情
        System.out.println(tpe.getQueue());
//
        tpe.shutdown();

    }
}

class TestExecutors{

     //
    private static class Task implements Runnable{
        private int num;

        public Task(int num) {
            this.num = num;
        }
        /**
         *执行任务
         */
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " task-" + num);
            // 阻塞当前线程
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

         @Override
         public String toString() {
             return "Task{i=" + num + "}";
         }
     }
    public static void main(String[] args) {
        /**
         * 自定义线程池
         * 参数详解，
          */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, 4,
                30 ,TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        //System.out.println("");
        for (int i = 0 ; i < 8 ; i++) {
            Task task = new Task(i);
            threadPoolExecutor.execute(task);
        }

        // 任务等待队列的任务详情 打印每个任务（runnable实现）。
        System.out.println(threadPoolExecutor.getQueue());

        // 加入临界任务
        Task task = new Task(8);
        threadPoolExecutor.execute(task);

        // 任务等待队列的任务详情 打印每个任务（runnable实现）。
        System.out.println(threadPoolExecutor.getQueue());
    }
}
