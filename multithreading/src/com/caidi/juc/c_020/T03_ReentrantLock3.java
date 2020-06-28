/**
 * reentrantlock�������synchronized
 * ����m1����this,ֻ��m1ִ����ϵ�ʱ��,m2����ִ��
 * �����Ǹ�ϰsynchronized��ԭʼ������
 *
 * ʹ��reentrantlock�������ͬ���Ĺ���
 * ��Ҫע����ǣ�����Ҫ����Ҫ����Ҫ�ֶ��ͷ�������Ҫ������˵���飩
 * ʹ��syn�����Ļ���������쳣��jvm���Զ��ͷ���������lock�����ֶ��ͷ�������˾�����finally�н��������ͷ�
 *
 * ʹ��reentrantlock���Խ��С�����������tryLock�������޷�������������ָ��ʱ�����޷��������߳̿��Ծ����Ƿ�����ȴ�
 * @author mashibing
 */
package com.caidi.juc.c_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T03_ReentrantLock3 {
	Lock lock = new ReentrantLock();

	void m1() {
		try {
			lock.lock();
			for (int i = 0; i < 3; i++) {
				TimeUnit.SECONDS.sleep(1);

				System.out.println(i);
				System.out.println(Thread.currentThread().getName() + " status: " + Thread.currentThread().getState());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * ʹ��tryLock���г�������������������񣬷�����������ִ��
	 * ���Ը���tryLock�ķ���ֵ���ж��Ƿ�����
	 * Ҳ����ָ��tryLock��ʱ�䣬����tryLock(time)�׳��쳣������Ҫע��unclock�Ĵ�������ŵ�finally��
	 */
	void m2() {
		/*
		boolean locked = lock.tryLock();
		System.out.println("m2 ..." + locked);
		if(locked) lock.unlock();
		*/

		boolean locked = false;

		try {
			System.out.println(Thread.currentThread().getName() + " status: " + Thread.currentThread().getState());
			locked = lock.tryLock(5, TimeUnit.SECONDS);
			System.out.println("m2 ..." + locked);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(locked) lock.unlock();
		}

	}

	public static void main(String[] args) {
		T03_ReentrantLock3 rl = new T03_ReentrantLock3();
		new Thread(rl::m1).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(rl::m2).start();
	}
}

/**
 * tryLock的使用。
 * @date 11:31 2020/6/28
 * @param
 * @return
 */
class MyT03_ReentrantLock3 {
	// 可重入锁
	private Lock lock = new ReentrantLock();

	public void m1 () {
		try {
			lock.lock();
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName() + " : i = " + i + " m1 run....");
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
		} finally {
			lock.unlock();
		}
	}

	/**
	 *  ReentrantLock为互斥锁，当不同线程都去获取锁的时候表现出互斥性，如果同一线程，则是可重入的。
	 * 	 锁是相对于同一个对象的，这里为Lock对象
	 * @date 10:27 2020/6/28
	 * @param null
	 * @return
	 */
	public void m2() {

		boolean b = false;
		try {
			// 尝试获取锁,并返回结果
			b = lock.tryLock(11,TimeUnit.SECONDS);
			System.out.println(Thread.currentThread().getName() + " isLocked :" + b);
			if (b) {
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + " : i = " + i + " m2 run....");
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				if (b) {
					lock.unlock();
				}
			}
	}

	public static void main(String[] args) {
		MyT03_ReentrantLock3 myT03_reentrantLock3 = new MyT03_ReentrantLock3();

		// 重入性
		new Thread(()->{
			myT03_reentrantLock3.m1();
		}).start();

		// 互斥性
//		new Thread(()->{
//			myT03_reentrantLock3.m2();
//		}).start();
//
//		new Thread(()->{
//			myT03_reentrantLock3.m2();
//		}).start();
	}

}
