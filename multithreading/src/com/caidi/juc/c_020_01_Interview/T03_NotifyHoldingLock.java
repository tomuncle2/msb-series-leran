/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * 
 * 给lists添加volatile之后，t2能够接到通知，但是，t2线程的死循环很浪费cpu，如果不用死循环，该怎么做呢？
 * 
 * 这里使用wait和notify做到，wait会释放锁，而notify不会释放锁
 * 需要注意的是，运用这种方法，必须要保证t2先执行，也就是首先让t2监听才可以
 * 
 * 阅读下面的程序，并分析输出结果
 * 可以读到输出结果并不是size=5时t2退出，而是t1结束时t2才接收到通知而退出
 * 想想这是为什么？
 * @author mashibing
 */
package com.caidi.juc.c_020_01_Interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class T03_NotifyHoldingLock { //wait notify

	//添加volatile，使t2能够得到通知
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
				System.out.println("t2启动");
				if(c.size() != 5) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("t2 结束");
			}
			
		}, "t2").start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		new Thread(() -> {
			System.out.println("t1启动");
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
 * 加上synchronized，
 * 解决线程可见性问题。
 * 这里t2在t1加到size = 5 之前一直等着，size = 5 ,t1通知t2，醒来
 * 问题: t2醒来后，仍然获取不到锁，因为t1并没有释放锁。
 * */
class MyT03_NotifyHoldingLock {

	// 添加volatile，使t2能够得到通知
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

		// 没有检测到list size到5的时候就一直运行着
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

		// 往list add size到5的时候就一直运行着
		new Thread(() -> {
			synchronized (o) {
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + " add:" + i);
					myT03_notifyHoldingLock.add(new Object());
					if (5 == myT03_notifyHoldingLock.size()) {
						System.out.println(Thread.currentThread().getName() + " 唤醒t2 但是不释放锁");
						o.notify();

						try {
							System.out.println(Thread.currentThread().getName() + " 等待 释放锁");
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
