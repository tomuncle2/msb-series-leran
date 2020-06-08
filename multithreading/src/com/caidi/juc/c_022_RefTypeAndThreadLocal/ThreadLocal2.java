/**
 * ThreadLocal线程局部变量
 *
 * ThreadLocal是使用空间换时间，synchronized是使用时间换空间
 * 比如在hibernate中session就存在与ThreadLocal中，避免synchronized的使用
 *
 * 运行下面的程序，理解ThreadLocal
 * 
 * @author 马士兵
 */
package com.caidi.juc.c_022_RefTypeAndThreadLocal;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ThreadLocal2 {
	//volatile static Person p = new Person();
	static ThreadLocal<Person> tl = new ThreadLocal<>();
	
	public static void main(String[] args) {

		new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(tl.get());
		}).start();
		
		new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tl.set(new Person());
		}).start();
	}
	
	static class Person {
		String name = "zhangsan";
		// threadLocals包可见的
//		Thread thread = new Thread();
//		thread.threadLocals = null;

	}


}


class TestThreadLocal2 {
	static ThreadLocal<Person> threadLocal = new ThreadLocal<>();

	static class Person {
		String name = "zhangsan";
		// threadLocals包可见的
//		Thread thread = new Thread();
//		thread.threadLocals = null;
	}

	public static void main(String[] args) {
		System.out.println("start");
		Thread t1 = new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 只能取出当前线程的变量信息
			Person p = threadLocal.get();
			System.out.println(p);
		});

		Thread t2 = new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Person p = new Person();
			p.name = "caidi";
			// 将value放入当前线程维护的一个map里面,key为当前线程对象的引用.
			threadLocal.set(p);
			while (true) {

			}
		});

		t1.start();
		t2.start();
		try {
			System.out.println("t1" + t1.getState());
			System.out.println("t2" + t2.getState());
//			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


