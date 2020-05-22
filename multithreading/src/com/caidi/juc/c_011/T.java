/**
 * 程序在执行过程中，如果出现异常，默认情况锁会被释放
 * 所以，在并发处理的过程中，有异常要多加小心，不然可能会发生不一致的情况。
 * 比如，在一个web app处理过程中，多个servlet线程共同访问同一个资源，这时如果异常处理不合适，
 * 在第一个线程中抛出异常，其他线程就会进入同步代码区，有可能会访问到异常产生时的数据。
 * 因此要非常小心的处理同步业务逻辑中的异常
 * @author mashibing
 */
package com.caidi.juc.c_011;



public class T {

	private int count = 0;

	public void m1() throws  InterruptedException {
		synchronized(this) {
			System.out.println(Thread.currentThread().getName() + " : start");
			while (true) {
				count++;
				System.out.println(Thread.currentThread().getName() + " : count = " + count);
				Thread.sleep(1000);
				if(count == 5) {
					throw new InterruptedException("程序异常！！");
				}
			}

		}
	}


	public static void main(String[] args) {
		T t = new T();
		new Thread(()-> {
			try {
				t.m1();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "t1").start();

		new Thread(()-> {
			try {
				t.m1();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "t2").start();
	}

	/**
	 * * t1 start
	 * * t1 count = 1
	 * * t1 count = 2
	 * * t1 count = 3
	 * * t1 count = 4
	 * * t1 count = 5
	 * * 程序异常
	 * * t2 start
	 * * t2 count = 6
	 * * t3 count = 7
	 * * t4 count = 8
	 * * t5 count = 9
	 * * t6 count = 10
	 * * .....
	 */

}


