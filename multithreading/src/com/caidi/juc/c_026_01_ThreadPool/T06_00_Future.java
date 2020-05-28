/**
 * ��ʶfuture
 * �첽
 */
package com.caidi.juc.c_026_01_ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class T06_00_Future {
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// Future��runable�Ľ�� �������Լ���ʼ����Ҳ�ܽ��ܷ��ؽ��
		FutureTask<Integer> task = new FutureTask<>(()->{
			TimeUnit.MILLISECONDS.sleep(500);
			return 1000;
		}); //new Callable () { Integer call();}
		
		new Thread(task).start();
		
		System.out.println(task.get()); //����


	}
}

class Test{
	public static void main(String[]args){
		FutureTask<Integer> futureTask = new FutureTask(new Callable() {
			@Override
			public Object call() throws Exception {
				return 55;
			}
		});

		new Thread(futureTask, "cxcx").start();

		// ����
		try {
			System.out.println(futureTask.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
