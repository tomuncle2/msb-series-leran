package com.caidi.juc.c_thread_pool;

import java.util.concurrent.*;

/**
 * @author: 蔡迪
 * @date: 11:02 2021/10/17
 * @description: 线程池执行器接口
 */
public class ExecutorTest {

    public static void main(String[] args) {
        // 执行器顶级接口 Executor
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 异步  多个线程带来的任务（其实就是一个个callable,runnable对象）
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello word";
            }
        });

        Object o = null;
        try {
            // 主线程阻塞获取（类比join）
            o = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(o);


    }
}