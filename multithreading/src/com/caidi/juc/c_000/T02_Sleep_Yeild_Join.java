package com.caidi.juc.c_000;

/**
 * @author: 蔡迪
 * @date: 20:42 2020/5/18
 * @description: sleep/yeild   让出线程资源进行
 */
public  class T02_Sleep_Yeild_Join {

    // s
    static void testWait() {

    }

    /**
     * 进入线程等待状态
     */
    static void testSleep() {
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " A" + i);
                try {
                    Thread.sleep(1000);
                    //TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 让出一下下.就进入cpu等待队列里面，Ready状态
     * */
    static void testYeild() {
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " A" + i);
                // cpu运行速度过快 如果不限制一下 大概率能看到交替执行的效果，这样能看到cpu执行的“痕迹”（时间片的）
                if ( i % 10 == 0) {
                    Thread.yield();
                }
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " B"+ i);
                if ( i % 10 == 0) {
                    Thread.yield();
                }
            }
        }).start();
    }

    /**
     * 将t2加入t1线程，t2必须等到t1执行完了后，才能执行（之前处于Waiting状态）
     * t1.join方法是使调用当前方法的线程(calling thread) Waiting状态，然后直到t1执行完，此线程再继续。
     */
    static void testJoin() {
        Thread t1 = new Thread(()-> {
            for (int i = 0; i < 100; i++) {
                System.out.println("joinA" + i);
                try {
                    // 为了减缓cpu执行速度，使结果更明显
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(()-> {
            try {
                // 让调用线程等待 子线程执行完，主线程才继续执行
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 100; i++) {
                System.out.println("joinB" + i);
                try {
                    // 为了减缓cpu执行速度，使结果更明显
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        //testSleep();
        //testYeild();
        // 执行顺序 main t1  t2
        //testJoin();
        TTT ttt = new TTT();
        Thread thread = new Thread(ttt);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CCC ccc = new CCC();
        Thread thread1 = new Thread(ccc);
        thread1.start();

        try {
            ccc.notify();
            thread1.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread.yield();


        System.out.println("finsh");
    }


    static class TTT implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("线程TTT");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class CCC implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                if (i == 3) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("线程CCC");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}