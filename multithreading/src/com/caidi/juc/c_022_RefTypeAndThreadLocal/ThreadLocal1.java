/**
 * ThreadLocal线程局部变量
 * 
 * @author 马士兵
 */
package com.caidi.juc.c_022_RefTypeAndThreadLocal;

import java.util.concurrent.TimeUnit;

public class ThreadLocal1 {
	volatile static Person p = new Person();
	
	public static void main(String[] args) {
				
		new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(p.name);
		}).start();
		
		new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			p.name = "lisi";
		}).start();
	}
}

class Person {
	String name = "zhangsan";
}

class TestThreadLocal{

	private static Person person = new Person();

//	private static ThreadLocal<Person> threadLocal = new ThreadLocal<>();



	public static void main(String[] args) {
		Thread t1 = new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(person.name);
		});

		Thread t2 = new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			person.name = "caidi";
		});

		t1.start();
		t2.start();
	}
}
