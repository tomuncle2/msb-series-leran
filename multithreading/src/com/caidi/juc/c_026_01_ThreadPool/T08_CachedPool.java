package com.caidi.juc.c_026_01_ThreadPool;
/**
 * 缓存线程池-线程池上限为Interger.maxValue()，核心线程数为零，来一个线程就加一个线程，
 * 如果当前线程没有执行完被过期回收，下一个任务来的时候还会被使用。
 *SynchronousQueue同时只会有一个线程，
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class T08_CachedPool {
//	public static void main(String[] args) throws InterruptedException {
//		ExecutorService service = Executors.newCachedThreadPool();
//		System.out.println(service);
//		for (int i = 0; i < 2; i++) {
//			service.execute(() -> {
//				try {
//					TimeUnit.MILLISECONDS.sleep(500);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				System.out.println(Thread.currentThread().getName());
//			});
//		}
//		System.out.println(service);
//
//		TimeUnit.SECONDS.sleep(80);
//
//		System.out.println(service);
//
//
//	}

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		//
		for (int i = 0;i<10;i++) {
			final int j = i;
			executorService.submit(() -> {
				System.out.println(Thread.currentThread().getName() + "task" + j);
			});
		}

		System.out.println(executorService);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(executorService);
	}
}
