/**
 * 线程池的概念
 * 固定线程数的线程池
 * nasa ，
 * 并行：将一个任务分成几个子任务（假设子任务间不存在依赖），一起执行
 * 并发：并行是并发的子集
 */
package com.caidi.juc.c_026_01_ThreadPool;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class T09_FixedThreadPool {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		long start = System.currentTimeMillis();
		getPrime(1, 200000);
		long end = System.currentTimeMillis();
		System.out.println(end - start);

		final int cpuCoreNum = 4;

		ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);

		MyTask t1 = new MyTask(1, 80000); //1-5 5-10 10-15 15-20
		MyTask t2 = new MyTask(80001, 130000);
		MyTask t3 = new MyTask(130001, 170000);
		MyTask t4 = new MyTask(170001, 200000);

		Future<List<Integer>> f1 = service.submit(t1);
		Future<List<Integer>> f2 = service.submit(t2);
		Future<List<Integer>> f3 = service.submit(t3);
		Future<List<Integer>> f4 = service.submit(t4);

		start = System.currentTimeMillis();
		f1.get();
		f2.get();
		f3.get();
		f4.get();
		end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	static class MyTask implements Callable<List<Integer>> {
		int startPos, endPos;

		MyTask(int s, int e) {
			this.startPos = s;
			this.endPos = e;
		}

		@Override
		public List<Integer> call() throws Exception {
			List<Integer> r = getPrime(startPos, endPos);
			return r;
		}

	}

	static boolean isPrime(int num) {
		for(int i=2; i<=num/2; i++) {
			if(num % i == 0) return false;
		}
		return true;
	}

	static List<Integer> getPrime(int start, int end) {
		List<Integer> results = new ArrayList<>();
		for(int i=start; i<=end; i++) {
			if(isPrime(i)) results.add(i);
		}

		return results;
	}


//	public static void main(String[] args) {
//		// 并行 并发
//		long start = System.currentTimeMillis();
//		List<Integer> result1 = getPrime(1, 200000);
//		long end = System.currentTimeMillis();
//		System.out.println("结果： " + result1.size() + " 耗时： "+ (end - start));
//
//		// 并行计算
//
//		ExecutorService executorService = Executors.newFixedThreadPool(4);
//
//		Mytask mytask1 = new Mytask(1,50000);
//		Mytask mytask2 = new Mytask(50001,100000);
//		Mytask mytask3 = new Mytask(100001,150000);
//		Mytask mytask4 = new Mytask(150001,200000);
//
//		Future<List<Integer>> future1 = executorService.submit(mytask1);
//		Future<List<Integer>> future2= executorService.submit(mytask2);
//		Future<List<Integer>> future3 = executorService.submit(mytask3);
//		Future<List<Integer>> future4 = executorService.submit(mytask4);
//		try {
//            long start1 = System.currentTimeMillis();
//			List<Integer> resultFuture1 = future1.get();
//			List<Integer> resultFuture2 = future2.get();
//			List<Integer> resultFuture3 = future3.get();
//			List<Integer> resultFuture4 = future4.get();
//			long end1 = System.currentTimeMillis();
//			long result2 = resultFuture1.size() + resultFuture2.size() +resultFuture3.size() +resultFuture4.size();
//					System.out.println("结果： " + result2 + " 耗时： "+ (end1 - start1));
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//
//	static class Mytask implements Callable<List<Integer>> {
//		private int start;
//		private int end;
//
//		public Mytask(int start, int end) {
//			this.start = start;
//			this.end = end;
//		}
//
//		@Override
//		public List<Integer> call() throws Exception {
//			return T09_FixedThreadPool.getPrime(start, end);
//		}
//	}
//
//	/**
//	 * 判断是否是质数
//	 */
//	static boolean isPrime(int number) {
//		if (number%2 ==0) {
//			return false;
//		}
//		return true;
//	}
//
//	static List<Integer> getPrime(int start, int end) {
//		List<Integer> result = new ArrayList<>();
//		for (int i = start ;i <=end; i++) {
//			if(isPrime(i)) {
//				result.add(i);
//			}
//		}
//		return result;
//	}
}
