package com.caidi.juc.c_sync;

/**
 * @author: 蔡迪
 * @date: 15:52 2021/10/15
 * @description: 线程变量数据隔离
 */
public class ThreadLocalTest {
    // 线程变量隔离map  key:当前线程的引用 value:变量
    private static ThreadLocal<String> local = new ThreadLocal();

    public static void main(String[] args) {
        String threadVar = "thread-";
        // 数据隔离
        for (int i = 0; i <3 ; i++) {
            new Thread(()->{
                // run方法
                System.out.println(threadVar);
                // 处理变量
                String deal = threadVar+" 线程名:"+Thread.currentThread().getName();
                local.set(deal);

                System.out.println("获取线程变量: "+local.get());
            }).start();
        }

    }

}