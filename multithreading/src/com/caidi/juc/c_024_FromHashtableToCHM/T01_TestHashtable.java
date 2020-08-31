package com.caidi.juc.c_024_FromHashtableToCHM;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

public class T01_TestHashtable {

    static Hashtable<UUID, UUID> m = new Hashtable<>();

    static int count = Constants.COUNT;
    static UUID[] keys = new UUID[count];
    static UUID[] values = new UUID[count];
    static final int THREAD_COUNT = Constants.THREAD_COUNT;

    static {
        for (int i = 0; i < count; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

    static class MyThread extends Thread {
        int start;
        int gap = count/THREAD_COUNT;

        public MyThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for(int i=start; i<start+gap; i++) {
                m.put(keys[i], values[i]);
            }
        }
    }

    public static void main(String[] args) {
        // 写入
        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[THREAD_COUNT];

        for(int i=0; i<threads.length; i++) {
            threads[i] = new MyThread(i * (count/THREAD_COUNT));
        }

        for(Thread t : threads) {
            t.start();
        }

        for(Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);

        System.out.println(m.size());

        //--------------读取---------------------

        start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                for (int j = 0; j < 10000000; j++) {
                    m.get(keys[10]);
                }
            });
        }

        for(Thread t : threads) {
            t.start();
        }

        for(Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}

/**
 * Hashtable - HashMap
 * @date 17:08 2020/8/31
 * @param null
 * @return
 */
class Test_T01_TestHashtable {
    //
    static Hashtable hashtable = new Hashtable();
    //
    static String[] keys = new String[Constants.COUNT];
    static String[] vaues = new String[Constants.COUNT];

    // 生成好key和value
    static {
        for (int i=0;i<Constants.COUNT;i++) {
            keys[i] = UUID.randomUUID().toString();
            vaues[i] = UUID.randomUUID().toString();
        }
    }

    /**线程*/
    static class MyThread extends Thread implements Runnable {
        private int gap = Constants.COUNT/Constants.THREAD_COUNT;

        private int start = 0;

        MyThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            // 存入map
            for (int i = start;i<start + gap;i++) {
                hashtable.put(keys[i], vaues[i]);
            }
        }

    }

    /**读取和写入*/
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        MyThread[] threads = new MyThread[Constants.COUNT/Constants.THREAD_COUNT];
        // 写入
        for (int i = 0;i<threads.length;i++) {
            threads[i] = new MyThread(i * Constants.COUNT/Constants.THREAD_COUNT);
        }

        for (int i = 0;i<threads.length;i++) {
            threads[i].start();
        }

        // 所有线程执行完
        for (int i = 0;i<threads.length;i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(hashtable.size());
        System.out.println(endTime - startTime);

        // 读取
        for (int i = 0;i<10;i++) {
            //b
        }
    }


}
