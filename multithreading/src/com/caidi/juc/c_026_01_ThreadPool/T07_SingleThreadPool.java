package com.caidi.juc.c_026_01_ThreadPool;
/**
 * 单例（单线程）线程池
 * 线程池有且只有一个线程存在
 */

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T07_SingleThreadPool {
//	public static void main(String[] args) {
//		ExecutorService service = Executors.newSingleThreadExecutor();
//		for(int i=0; i<5; i++) {
//			final int j = i;
//			service.execute(()->{
//
//				System.out.println(j + " " + Thread.currentThread().getName());
//			});
//		}
//
//	}

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		for (int i =0 ; i< 10; i++) {
			final int j = i;
			executorService.submit(()-> {
				System.out.println(Thread.currentThread().getName() + "task" + j);
			});
		}
	}
}


