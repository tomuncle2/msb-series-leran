/**
 * 认识Executor , 相当于将线程定义与执行分开了  执行器
 */
package com.caidi.juc.c_026_01_ThreadPool;

import java.util.concurrent.Executor;

public class T01_MyExecutor implements Executor{

	public static void main(String[] args) {
		new T01_MyExecutor().execute(()->System.out.println("hello executor"));
		new My_ThreadPool_1().execute(()->{
			System.out.println("dsdsds");
		});
	}

	@Override
	public void execute(Runnable command) {
		//new Thread(command).run();aaaa		command.run();
		command.run();
	}

}


class My_ThreadPool_1 implements Executor {

	// 相当于将线程定义与执行分开了
	@Override
	public void execute(Runnable command) {
		new Thread(command,"xxx").start();
		System.out.println("hello thread pool");
	}
}

