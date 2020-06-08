/**
 * 对比上一个程序，可以用synchronized解决，synchronized可以保证可见性和原子性，volatile只能保证可见性
 * @author mashibing
 */
package com.caidi.juc.c_012_Volatile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class T05_VolatileVsSync {
	/*volatile*/ int count = 0;

	synchronized void m() { 
		for (int i = 0; i < 10000; i++)
			count++;
	}

	public static void main(String[] args) {
		T05_VolatileVsSync t = new T05_VolatileVsSync();

		List<Thread> threads = new ArrayList<Thread>();

		for (int i = 0; i < 10; i++) {
			threads.add(new Thread(t::m, "thread-" + i));
		}

		threads.forEach((o) -> o.start());

		threads.forEach((o) -> {
			try {
				o.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		System.out.println(t.count);

	}

}

/**
* 对比上一个程序，可以用synchronized解决，synchronized可以保证可见性和原子性，volatile只能保证可见性
*/
class TestVolatileSync {
	private  volatile int count = 0;

	public synchronized void add() {
		for (int i=0;i<10000;i++) {
			count++;
		}
	}

	public static void main(String[] args) {
		TestVolatileSync testVolatileNotSync = new TestVolatileSync();
		// 线程集合
		List<Thread> threads = new ArrayList<>();
		for (int i=0;i<10;i++) {
			Thread t = new Thread(()-> {
				testVolatileNotSync.add();
			},"t" + i);
			threads.add(t);
			t.start();
		}

//		threads.forEach((thread -> {
//			try {
//				thread.join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}));

//		for (Thread thread : threads) {
//			try {
//				thread.join();
//				System.out.println(Thread.currentThread().getName()  + testVolatileNotSync.count);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}

		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(testVolatileNotSync.count);
	}
}