package com.caidi.juc.c_004;

/**
 * synchronized关键字
 * 对某个对象加锁,  对静态方法做同步，同步方法和代码块 ,  m和mm方法等效，锁的是T类的Class对象（类对象）
 * 问：一个CLASS，loder到内存，这个类对象是单例的吗？
 * 答：一般是，在同一个classloder的空间，它一定是，如果是不同的classloder,是可以把不同的Class加载到不同的内存空间的，
 * 不同加载器就不是，不同的类加载器是不可以互相访问的，要是能互相访问他就是单例，不能访问也就不需要考虑了。
 * @author caidi
 */

public class T {

    private static int count = 10;
    // 这里等同于synchronized(T.class)
    public  synchronized static void m() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void mm() {
        // synchronized(this) 是错误的，因为静态方法数据是加载在方法区的，一般通过类名来访问
        synchronized (T.class) {
            count--;
        }
//        synchronized (new T()) {
//            count--;
//        }
    }

    public static void main(String[] args) {
        for (int i = 0;i < 5;i++) {
            MyThread t1 = new MyThread();
            t1.start();
//            try {
//                t1.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        }
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " start ");
            m();
        }
    }
}