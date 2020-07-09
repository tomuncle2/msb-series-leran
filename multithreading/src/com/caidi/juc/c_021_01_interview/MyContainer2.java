/**
 * 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * 
 * 使用wait和notify/notifyAll来实现
 * 
 * 使用Lock和Condition来实现
 * 对比两种方式，Condition的方式可以更加精确的指定哪些线程被唤醒
 * 
 * @author mashibing
 */
package com.caidi.juc.c_021_01_interview;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyContainer2<T> {
	final private LinkedList<T> lists = new LinkedList<>();
	final private int MAX = 10; //最多10个元素
	private int count = 0;
	
	private Lock lock = new ReentrantLock();
	private Condition producer = lock.newCondition();
	private Condition consumer = lock.newCondition();
	
	public void put(T t) {
		try {
			lock.lock();
			while(lists.size() == MAX) { //想想为什么用while而不是用if？
				producer.await();
			}
			
			lists.add(t);
			++count;
			consumer.signalAll(); //通知消费者线程进行消费
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public T get() {
		T t = null;
		try {
			lock.lock();
			while(lists.size() == 0) {
				consumer.await();
			}
			t = lists.removeFirst();
			count --;
			producer.signalAll(); //通知生产者进行生产
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return t;
	}
	
	public static void main(String[] args) {
		MyContainer2<String> c = new MyContainer2<>();
		//启动消费者线程
		for(int i=0; i<10; i++) {
			new Thread(()->{
				for(int j=0; j<5; j++) System.out.println(c.get());
			}, "c" + i).start();
		}
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//启动生产者线程
		for(int i=0; i<2; i++) {
			new Thread(()->{
				for(int j=0; j<25; j++) c.put(Thread.currentThread().getName() + " " + j);
			}, "p" + i).start();
		}
	}
}

/**
 * 用cas实现生产者-消费者模型
 */
class MyMyContainer2<T>{
	final private LinkedList<T> lists = new LinkedList<>();
	final private int MAX = 10; //最多10个元素
	private int count = 0;

	private Lock lock = new ReentrantLock();
	private Condition customer = lock.newCondition();
	private Condition product = lock.newCondition();

	public void put(T t) {
		// 手动加锁，异常不会打断ReentrantLock锁
		try {
			lock.lock();
			while (getCount() == MAX) {
				// 等待，并且让出锁
				try {
					customer.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			lists.add(t);
			count++;
			product.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public T get() {
		try {
			lock.lock();
			while (getCount() == 0) {
				// 等待，并且让出锁
				try {
					product.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			T t = lists.removeFirst();
			count--;
			customer.signalAll();
			return t;
		} finally {
			lock.unlock();
		}
	}

	public int getCount() {
		try {
			lock.lock();
			return count;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		MyMyContainer2<String> myMyContainer2 = new MyMyContainer2();
		// 生产者线程
		for (int i = 1;i < 2;i++) {
			new Thread(()->{
				// 每人生产10个馒头
				for (int j = 1;j < 10;j++) {
					myMyContainer2.put(j + "");
					System.out.println( Thread.currentThread().getName() + " put " + j);
				}
			},"p" + i).start();
		}

		// 消费者线程
		for (int i = 1;i < 10;i++) {
			new Thread(()->{
				// 每人吃2个馒头
				for (int j = 1;j < 2;j++) {
					System.out.println( Thread.currentThread().getName() + " get " + myMyContainer2.get());
				}
			},"c" + i).start();
		}
	}
}
