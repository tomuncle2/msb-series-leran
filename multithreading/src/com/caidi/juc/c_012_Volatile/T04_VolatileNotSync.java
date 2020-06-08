/**
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
 * 运行下面的程序，并分析结果
 * @author mashibing
 */
package com.caidi.juc.c_012_Volatile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class T04_VolatileNotSync {
	volatile int count = 0;
	void m() {
		for(int i=0; i<10000; i++) count++;
	}
	
	public static void main(String[] args) {
		T04_VolatileNotSync t = new T04_VolatileNotSync();
		
		List<Thread> threads = new ArrayList<Thread>();
		
		for(int i=0; i<10; i++) {
			threads.add(new Thread(t::m, "thread-"+i));
		}
		
		threads.forEach((o)->o.start());
		
		threads.forEach((o)->{
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
 * 测试只用volatile 可以解决成员变量的线程安全问题吗？
 */
class TestVolatileNotSync {
	private  volatile int count = 0;

	public void add() {
		for (int i=0;i<10000;i++) {
			count++;
		}
	}

	public static void main(String[] args) {
		TestVolatileNotSync testVolatileNotSync = new TestVolatileNotSync();
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


