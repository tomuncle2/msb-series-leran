/**
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁.
 * 也就是说synchronized获得的锁是可重入的，否者产生死锁。
 * @author caidi
 */
package com.caidi.juc.c_009;



public class T {
	int count = 100;
	synchronized void m1() {
		System.out.println("m1 count :" + count);
		this.count = this.count + 10 ;
		m2();
		System.out.println("m1 count :" + count);
	}

	synchronized void m2() {
		System.out.println("m2 count :" + count);
		this.count = this.count - 20 ;
		System.out.println("m2 count :" + count);
	}

	public static void main(String[] args) {
		T t = new T();
//		t.m1();
		new Thread(()-> {
			t.m1();
		}).start();


	}
}
