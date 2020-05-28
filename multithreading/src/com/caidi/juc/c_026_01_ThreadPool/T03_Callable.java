/**
 * 认识Callable，对Runnable进行了扩展
 * 对Callable的调用，可以有返回值
 */
package com.caidi.juc.c_026_01_ThreadPool;

import java.util.concurrent.*;

public class T03_Callable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> c = new Callable() {
            @Override
            public String call() throws Exception {
                return "Hello Callable";
            }
        };

        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(c); //异步

        System.out.println(future.get());//阻塞

        service.shutdown();
    }

}


class Test11{
    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int a = 8;
            int b = 78;
            int c = a + b;
            return c;
        }
    }
    public static void main(String[] args) {
        MyCallable myCallable = new MyCallable();
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 异步，开启一个线程去执行
        Future<Integer> future = executorService.submit(myCallable);
        // 返回结果处理 由于是异步的，所以想要获取(get)会阻塞,等在这里
        try {
            // 超时或者线程的问题会抛出异常
            Integer num = future.get(1, TimeUnit.SECONDS);
            System.out.println("future num:" + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        // 1、停止接收新的submit的任务；
        //2、已经提交的任务（包括正在跑的和队列中等待的）,会继续执行完成；
        //3、等到第2步完成后，才真正停止；
        executorService.shutdown();
    }
}

class Test2 {
    public static void main(String[] args) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int a = 8;
                int b = 78;
                int c = a * b;
                Thread.sleep(10000);
                return c;
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(callable);

        // 开启另一个没有返回值的线程去处理结果,主线程不用等待
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("callable返回值： " + future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //System.out.println();
        executorService.shutdown();
    }
}
