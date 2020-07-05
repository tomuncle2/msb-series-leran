/**
 * �����������⣺���Ա�����
 * ʵ��һ���������ṩ����������add��size
 * д�����̣߳��߳�1���10��Ԫ�ص������У��߳�2ʵ�ּ��Ԫ�صĸ�������������5��ʱ���߳�2������ʾ������
 * 
 * ��lists���volatile֮��t2�ܹ��ӵ�֪ͨ�����ǣ�t2�̵߳���ѭ�����˷�cpu�����������ѭ��������ô���أ�
 * 
 * ����ʹ��wait��notify������wait���ͷ�������notify�����ͷ���
 * ��Ҫע����ǣ��������ַ���������Ҫ��֤t2��ִ�У�Ҳ����������t2�����ſ���
 * 
 * �Ķ�����ĳ��򣬲�����������
 * ���Զ���������������size=5ʱt2�˳�������t1����ʱt2�Ž��յ�֪ͨ���˳�
 * ��������Ϊʲô��
 * @author mashibing
 */
package com.caidi.juc.c_020_01_Interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class T03_NotifyHoldingLock { //wait notify

	//���volatile��ʹt2�ܹ��õ�֪ͨ
	volatile List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}
	
	public static void main(String[] args) {
		T03_NotifyHoldingLock c = new T03_NotifyHoldingLock();
		
		final Object lock = new Object();
		
		new Thread(() -> {
			synchronized(lock) {
				System.out.println("t2����");
				if(c.size() != 5) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("t2 ����");
			}
			
		}, "t2").start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		new Thread(() -> {
			System.out.println("t1����");
			synchronized(lock) {
				for(int i=0; i<10; i++) {
					c.add(new Object());
					System.out.println("add " + i);
					
					if(c.size() == 5) {
						lock.notify();
					}
					
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "t1").start();

	}
}

/**
 * ����synchronized��
 * ����߳̿ɼ������⡣
 * ����t2��t1�ӵ�size = 5 ֮ǰһֱ���ţ�size = 5 ,t1֪ͨt2������
 * ����: t2��������Ȼ��ȡ����������Ϊt1��û���ͷ�����
 * */
class MyT03_NotifyHoldingLock {

	// ���volatile��ʹt2�ܹ��õ�֪ͨ
	private volatile List<Object> lists =new ArrayList<>();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	public static void main(String[] args) {

		MyT03_NotifyHoldingLock myT03_notifyHoldingLock = new MyT03_NotifyHoldingLock();

		Object o = new Object();

		// û�м�⵽list size��5��ʱ���һֱ������
		new Thread(() -> {
			synchronized (o) {
				//
				if (myT03_notifyHoldingLock.size() != 5) {
					try {
						System.out.println(Thread.currentThread().getName() + " t2 wait");
						o.wait(10000000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}, "t2").start();

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// ��list add size��5��ʱ���һֱ������
		new Thread(() -> {
			synchronized (o) {
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + " add:" + i);
					myT03_notifyHoldingLock.add(new Object());
					if (5 == myT03_notifyHoldingLock.size()) {
						System.out.println(Thread.currentThread().getName() + " ����t2 ���ǲ��ͷ���");
						o.notify();

						try {
							System.out.println(Thread.currentThread().getName() + " �ȴ� �ͷ���");
							o.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, "t1").start();

	}

}
