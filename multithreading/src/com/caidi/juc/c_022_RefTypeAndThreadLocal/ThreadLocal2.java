/**
 * ThreadLocal�ֲ߳̾�����
 *
 * ThreadLocal��ʹ�ÿռ任ʱ�䣬synchronized��ʹ��ʱ�任�ռ�
 * ������hibernate��session�ʹ�����ThreadLocal�У�����synchronized��ʹ��
 *
 * ��������ĳ������ThreadLocal
 * 
 * @author ��ʿ��
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
		// threadLocals���ɼ���
//		Thread thread = new Thread();
//		thread.threadLocals = null;

	}


}


class TestThreadLocal2 {
	static ThreadLocal<Person> threadLocal = new ThreadLocal<>();

	static class Person {
		String name = "zhangsan";
		// threadLocals���ɼ���
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
			// ֻ��ȡ����ǰ�̵߳ı�����Ϣ
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
			// ��value���뵱ǰ�߳�ά����һ��map����,keyΪ��ǰ�̶߳��������.
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


