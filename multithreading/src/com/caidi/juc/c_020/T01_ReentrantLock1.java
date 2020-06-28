/**
 * reentrantlock�������synchronized
 * ����������m1����this,ֻ��m1ִ����ϵ�ʱ��,m2����ִ��
 * �����Ǹ�ϰsynchronized��ԭʼ������
 * @author mashibing
 */
package com.caidi.juc.c_020;

import java.util.concurrent.TimeUnit;

public class T01_ReentrantLock1 {
	synchronized void m1() {
		for(int i=0; i<10; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(i);
			if(i == 2) m2();
		}

	}

	synchronized void m2() {
		System.out.println("m2 ...");
	}

	public static void main(String[] args) {
		T01_ReentrantLock1 rl = new T01_ReentrantLock1();
		new Thread(rl::m1).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//new Thread(rl::m2).start();
	}

}

/**
 * 复习synchronized 的可重入性，锁的互斥性，排他性
 * @date 10:04 2020/6/28
 * @param null
 * @return
 */
class MyT01_ReentrantLock1 {

	synchronized void m1() {
		for (int i = 0; i<10; i++) {
			System.out.println(Thread.currentThread().getName() +   " : i = "+ i + " m1 run....");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// synchronized是可重入锁
			if (i == 2) {
				m2();
			}
		}
	}

	/**
	 * synchronized为互斥锁,其修饰了两个方法，当不同线程都去获取锁的时候表现出互斥性，如果同一线程，则是可重入的。
	 * 锁是相对于同一个对象的，这里为MyT01_ReentrantLock1类的对象
	 * @date 10:27 2020/6/28
	 * @param null
	 * @return
	 */
	synchronized void m2() {
		for (int i = 0; i<2; i++) {
			System.out.println(Thread.currentThread().getName() +   " : i = "+ i + " m2 run....");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		MyT01_ReentrantLock1 myT01_reentrantLock1 = new MyT01_ReentrantLock1();

		// 可重入
//		new Thread(()-> {
//			myT01_reentrantLock1.m1();
//		}).start();

		// 互斥性
		new Thread(()-> {
			myT01_reentrantLock1.m2();
		}).start();

		// 互斥性
		new Thread(()-> {
			myT01_reentrantLock1.m2();
		}).start();
	}
}
