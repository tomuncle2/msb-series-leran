/**
 * 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * 
 * 使用wait和notify/notifyAll来实现
 * 
 * @author mashibing
 */
package com.caidi.juc.c_021_01_interview;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class MyContainer1<T> {
	final private LinkedList<T> lists = new LinkedList<>();
	final private int MAX = 10; //最多10个元素
	private int count = 0;
	
	
	public synchronized void put(T t) {
		while(lists.size() == MAX) { //想想为什么用while而不是用if？
			try {
				this.wait(); //effective java
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		lists.add(t);
		++count;
		this.notifyAll(); //通知消费者线程进行消费
	}
	
	public synchronized T get() {
		T t = null;
		while(lists.size() == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		t = lists.removeFirst();
		count --;
		this.notifyAll(); //通知生产者进行生产
		return t;
	}
	
	public static void main(String[] args) {
		MyContainer1<String> c = new MyContainer1<>();
		// 启动消费者线程
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
		
		// 启动生产者线程
		for(int i=0; i<2; i++) {
			new Thread(()->{
				for(int j=0; j<25; j++) c.put(Thread.currentThread().getName() + " " + j);
			}, "p" + i).start();
		}
	}
}

/**
 * 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * 一个篮子，2个人往里面放馒头，10个人往里面拿馒头。
 * */
class MyMyContainer1<T> {
	// 容器
	private  LinkedList<T> linkedList = new LinkedList<>();
	// 当前容器中元素的个数
	private int count;
	// 容器最大元素个数
	private final int MAX = 20;

	// get
	public synchronized T get() {
		// 元素个数等于0 就等待 不能用if 当其被唤醒后，必须再判断一次count大小，否则可能为零还去拿,还会报错
		while (getCount()  == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// 元素个数大于0 就取一个元素出来 下面的操作必须同步完成，否则有线程安全问题。
		T t = linkedList.removeFirst();
		count --;

		// 通知生产者生产
		this.notifyAll();
		return t;
	}

	// put
	public synchronized void set(T t) {
		// 元素个数等于MAX 就等待 不能用if 当其被唤醒后，必须再判断一次count大小，否则可能为最大值了还去加
		while (getCount() == MAX) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// 元素个数小于0 就放一个元素出来 下面的操作必须同步完成，否则有线程安全问题。
		linkedList.add(t);
		count++;
		// 通知消费者消费 也可能唤醒生产者
		this.notifyAll();
	}

	// getCount
	public synchronized int getCount() {
		return count;
	}

	public static void main(String[] args) {
		MyMyContainer1<String> myMyContainer1 = new MyMyContainer1();
		// 生产者
		for (int i = 0;i<2;i++) {
			new Thread(new product(myMyContainer1),"producter " + i).start();
		}
		// 消费者现场
		for (int i = 0;i<10;i++) {
			new Thread(new customer(myMyContainer1),"customer " + i).start();
		}
	}
}

class product implements Runnable{

	// 将容器对象传进来，锁在容器对象上
	private MyMyContainer1 o;
	public product(MyMyContainer1 o) {
		this.o = o;
	}

	@Override
	public void run() {
		for (int i = 0;i<10;i++) {
			o.set(i);
			System.out.println(Thread.currentThread().getName() + " set " + i);
		}
	}
}

class customer implements Runnable{

	// 将容器对象传进来，锁在容器对象上
	private MyMyContainer1 o;
	public customer(MyMyContainer1 o) {
		this.o = o;
	}

	@Override
	public void run() {
		//while (true) {
		for (int i = 0; i < 2; i++) {

			System.out.println(Thread.currentThread().getName() + " get " + o.get());
		}
		//}
	}
}
