package com.caidi.juc.c_020;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class T10_TestReadWriteLock {
    static Lock lock = new ReentrantLock();
    private static int value;

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    public static void read(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("read over!");
            //模拟读取操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void write(Lock lock, int v) {
        try {
            lock.lock();
            Thread.sleep(1000);
            value = v;
            System.out.println("write over!");
            //模拟写操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }





    public static void main(String[] args) {
        //Runnable readR = ()-> read(lock);
        Runnable readR = ()-> read(readLock);

        //Runnable writeR = ()->write(lock, new Random().nextInt());
        Runnable writeR = ()->write(writeLock, new Random().nextInt());

        for(int i=0; i<18; i++) new Thread(readR).start();
        for(int i=0; i<2; i++) new Thread(writeR).start();


    }
}

/**
 * 读写锁,
 * @date 15:54 2020/6/29
 * @param null
 * @return
 */
class MyT10_TestReadWriteLock {
    private static Random random = new Random();
    private static Lock lock = new ReentrantLock(false);

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    private static int value;

    public static void sleep(int num, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void read(Lock lock){
        //
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " read value:" + value);
            sleep(1, TimeUnit.SECONDS);
        } finally {
            lock.unlock();
        }
    }

    public static void write(Lock lock, int random){
        try {
            lock.lock();
            int before = value;
            value = random;
            System.out.println(Thread.currentThread().getName() + " write before value:" + before + "  after value:" + value);
            sleep(1, TimeUnit.SECONDS);
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
//        // 普通排它锁 花费时间较长，全部为互斥锁
//        Runnable runnableWrite = ()->{
//            int raddom = random.nextInt(100);
//            write(lock, raddom);
//        };
//
//        Runnable runnableRead = ()->{
//            read(lock);
//        };

        // 加入读写锁提高效率
        Runnable runnableWrite = ()->{
            int raddom = random.nextInt(100);
            write(writeLock, raddom);
        };

        Runnable runnableRead = ()->{
            read(readLock);
        };

        for (int i =0;i<18;i++) {
            new Thread(runnableRead).start();
        }

        for (int i =0;i<2;i++) {
            new Thread(runnableWrite).start();
        }
    }
}