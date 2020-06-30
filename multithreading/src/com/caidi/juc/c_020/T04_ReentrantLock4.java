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
 *
 * ʹ��ReentrantLock�����Ե���lockInterruptibly���������Զ��߳�interrupt����������Ӧ��
 * ��һ���̵߳ȴ����Ĺ����У����Ա����
 *
 * @author mashibing
 */
package com.caidi.juc.c_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T04_ReentrantLock4 {

	public static void main(String[] args) {
		Lock lock = new ReentrantLock();


		Thread t1 = new Thread(()->{
			try {
				lock.lock();
				System.out.println("t1 start");
				TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
				System.out.println("t1 end");
			} catch (InterruptedException e) {
				System.out.println("interrupted!");
			} finally {
				lock.unlock();
			}
		});
		t1.start();

		Thread t2 = new Thread(()->{
			try {
				//lock.lock();
				lock.lockInterruptibly(); //���Զ�interrupt()����������Ӧ
				System.out.println("t2 start");
				TimeUnit.SECONDS.sleep(5);
				System.out.println("t2 end");
			} catch (InterruptedException e) {
				System.out.println("interrupted!");
			} finally {
				lock.unlock();
			}
		});
		t2.start();

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.interrupt(); //����߳�2�ĵȴ�

	}
}

class MyT04_ReentrantLock4 {

	//
	public static void main(String[] args) {
		//
		Lock lock = new ReentrantLock();
		Thread t1 = new Thread(()->{
			try {
				lock.lockInterruptibly();
				System.out.println("t1 start");
				TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
				System.out.println("t1 end");
			} catch (InterruptedException e) {
				System.out.println("interrupted!");
			} finally {
				lock.unlock();
			}
		});
		t1.start();
		//

		Lock lock2 = new ReentrantLock();
		Thread t2 = new Thread(()->{
			try {
				lock2.lock();
				System.out.println("t2 start");
				TimeUnit.SECONDS.sleep(1);
				System.out.println("t2 end");
			} catch (InterruptedException e) {
				System.out.println("interrupted!");
			} finally {
				lock2.unlock();
			}
		});
		t2.start();

		// mian线程打断t1 t2的等待， t1流程可被打断,同时释放锁   t2执行流程不会被打断，lock()获得锁，直到unlock()才会释放锁
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t1.interrupt();

		t2.interrupt();
	}
}
