/**
 * reentrantlock�������synchronized
 * ����m1����this,ֻ��m1ִ����ϵ�ʱ��,m2����ִ��
 * �����Ǹ�ϰsynchronized��ԭʼ������
 *
 * ʹ��reentrantlock�������ͬ���Ĺ���
 * ��Ҫע����ǣ�����Ҫ����Ҫ����Ҫ�ֶ��ͷ�������Ҫ������˵���飩
 * ʹ��syn�����Ļ���������쳣��jvm���Զ��ͷ���������lock�����ֶ��ͷ�������˾�����finally�н��������ͷ�
 * @author mashibing
 */
package com.caidi.juc.c_020;

import com.caidi.juc.c_021_02_AQS.MLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T02_ReentrantLock2 {
	Lock lock = new ReentrantLock();

	void m1() {
		try {
			lock.lock(); //synchronized(this)
			for (int i = 0; i < 10; i++) {
				TimeUnit.SECONDS.sleep(1);

				System.out.println(i);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	void m2() {
		try {
			lock.lock();
			System.out.println("m2 ...");
		} finally {
			lock.unlock();
		}

	}

	public static void main(String[] args) {
		T02_ReentrantLock2 rl = new T02_ReentrantLock2();
		new Thread(rl::m1).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(rl::m2).start();
	}
}

class MyT02_ReentrantLock2Test {
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
	 * ReentrantLock为互斥锁，当不同线程都去获取锁的时候表现出互斥性，如果同一线程，则是可重入的。
	 * 锁是相对于同一个对象的，这里为Lock对象
	 * @date 10:27 2020/6/28
	 * @param null
	 * @return
	 */
	public void m2() {
	 	try {
			// 与synchronized(this)作用类似
			lock.lock();
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName() + " : i = " + i + " m2 run....");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
	 		lock.unlock();
		}
	}



	//
	public static void main(String[] args) {
		MyT02_ReentrantLock2 myT02_reentrantLock2 = new MyT02_ReentrantLock2();
		// 可重入
//		new Thread(()-> {
//			myT02_reentrantLock2.m1();
//		}).start();

		// 互斥性
		Lock lock = new ReentrantLock();
		new Thread(()-> {
			myT02_reentrantLock2.m2(lock);
		}).start();

		// 互斥性
		new Thread(()-> {
			myT02_reentrantLock2.m2(lock);
		}).start();
	}
}


class MyT02_ReentrantLock2 {
	// 可重入锁   锁在Lock对象上 不是在MyT02_ReentrantLock2对象上
//	private Lock lock = new ReentrantLock();
//
//	public void m1 () {
//		try {
//			lock.lock();
//			for (int i = 0; i < 10; i++) {
//				System.out.println(Thread.currentThread().getName() + " : i = " + i + " m1 run....");
//				try {
//					TimeUnit.SECONDS.sleep(1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				// synchronized是可重入锁
//				if (i == 2) {
//					m2();
//				}
//			}
//		} finally {
//			lock.unlock();
//		}
//	}
//
//	/**
//	 * synchronized为互斥锁,其修饰了两个方法，当不同线程都去获取锁的时候表现出互斥性，如果同一线程，则是可重入的。
//	 * 锁是想对于同一个对象的，成员变量Lock和对象是一一对应的
//	 * @date 10:27 2020/6/28
//	 * @param null
//	 * @return
//	 */
//	public void m2() {
//	 	try {
//			// 与synchronized(this)作用类似
//			lock.lock();
//			for (int i = 0; i < 10; i++) {
//				System.out.println(Thread.currentThread().getName() + " : i = " + i + " m2 run....");
//				try {
//					TimeUnit.SECONDS.sleep(1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		} finally {
//	 		lock.unlock();
//		}
//	}


	public void m2(Lock lock) {
	 //lock必须为类成员变量
//		Lock lock = new ReentrantLock();
		try {
			// 与synchronized(this)作用类似
			lock.lock();
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName() + " : i = " + i + " m2 run....");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			lock.unlock();
		}
	}

	//
	public static void main(String[] args) {
		MyT02_ReentrantLock2 myT02_reentrantLock2 = new MyT02_ReentrantLock2();
		MyT02_ReentrantLock2 myT02_reentrantLock3 = new MyT02_ReentrantLock2();
		// 可重入
//		new Thread(()-> {
//			myT02_reentrantLock2.m1();
//		}).start();

		// 互斥性  锁在Lock对象上
		Lock lock = new ReentrantLock();
		new Thread(()-> {
			myT02_reentrantLock2.m2(lock);
		}).start();

		// 互斥性
		new Thread(()-> {
			myT02_reentrantLock3.m2(lock);
		}).start();
	}
}

