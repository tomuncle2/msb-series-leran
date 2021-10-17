package com.caidi.juc.c_thread_pool;

import java.util.concurrent.*;

/**
 * @author: 蔡迪
 * @date: 14:41 2021/10/17
 * @description: 自定义线程池
 */
public class ThreadPoolExecutorTest {

    /**自定义线程池参数
     * 最大核心线程数
     *
     * */
    static ThreadPoolExecutor poolExecutor1 = new  ThreadPoolExecutor(1,5,60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(4),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    static class Task1 implements Callable {

        @Override
        public Object call() throws Exception {
            return "tsak1 finsh";
        }
    }

    static class Task2 implements Runnable {
        @Override
        public void run() {
            System.out.println("tsak2 finsh");
        }
    }
    public static void main(String[] args) {
        Task1 task1 = new Task1();
        poolExecutor1.execute(new Task2());

        Future<String> future = poolExecutor1.submit(task1);
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
/**如果当前线程池中的线程数目小于corePoolSize，则每来一个任务，就会创建一个线程去执行这个任务；

 如果当前线程池中的线程数目>=corePoolSize，则每来一个任务，会尝试将其添加到任务等待队列当中，
 若添加成功，则该任务会等待空闲线程将其取出去执行；若添加失败（一般来说是任务缓存队列已满），
 则会尝试创建新的线程去执行这个任务；

 如果当前线程池中的线程数目达到maximumPoolSize，则会采取任务拒绝策略进行处理；

 如果线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，
 线程将被终止，直至线程池中的线程数目不大于corePoolSize；如果允许为核心池中的线程设置存活时间，
 那么核心池中的线程空闲时间超过keepAliveTime，线程也会被终止。*/