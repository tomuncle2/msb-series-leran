/**
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁.
 * 也就是说synchronized获得的锁是可重入的
 * 这里是继承中有可能发生的情形，子类调用父类的同步方法
 * @author mashibing
 */
package com.caidi.juc.c_010;


public class T {

	public static void main(String[] args) {
		TT tt = new TT();
		new Thread(()->{
			tt.set(5);
		}).start();
	}
}

class T1 {
	private int i = 0;

	public synchronized void set(int number) {
		this.i = i + number;
		System.out.println("father-T1: " + i);
	}

}

class TT extends T1 {

	private int i = 0;

	@Override
	public synchronized void set(int number) {
		this.i = i + number;
		super.set(10);
		System.out.println("son-TT: " + i);
	}
}
