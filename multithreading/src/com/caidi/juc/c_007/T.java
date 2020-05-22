/**
 * 同步和非同步方法是否可以同时调用？
 * 可以
 * @author mashibing
 */

package com.caidi.juc.c_007;


import java.util.concurrent.TimeUnit;

public class T {

	public synchronized void m1() {
		System.out.println("m1 start");
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("m1 end");
	}

	public /*synchronized*/ void m2() {
		System.out.println("m2 start");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("m2 end");
	}

	public static void main(String[] args) {
		T t1 = new T();
//		Thread thread01 = new Thread(()->{
//			t1.m1();
//		},"t1");
//		Thread thread02 = new Thread(()->{
//			t1.m2();
//		},"t2");
//		thread01.start();
//		thread02.start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				t1.m1();
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				t1.m2();
			}
		}).start();
	}
}
