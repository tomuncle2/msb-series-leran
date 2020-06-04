package com.caidi.juc.c_026_01_ThreadPool;
/***
 * 执行定时任务的线程池,
 * 最大线程数为Integer.value()
 */

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class T10_ScheduledPool {
//	public static void main(String[] args) {
//		ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
//		service.scheduleAtFixedRate(()->{
//			try {
//				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			System.out.println(Thread.currentThread().getName());
//		}, 0, 500, TimeUnit.MILLISECONDS);
//
//	}

	public static void main(String[] args) {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
		scheduledExecutorService.scheduleAtFixedRate(()->{
					System.out.println(Thread.currentThread().getName());
		},
		 0,
		 10,
		TimeUnit.SECONDS);
	}
}
