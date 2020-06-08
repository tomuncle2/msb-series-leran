/**
 * volatile �ؼ��֣�ʹһ�������ڶ���̼߳�ɼ�
 * A B�̶߳��õ�һ��������javaĬ����A�߳��б���һ��copy���������B�߳��޸��˸ñ�������A�߳�δ��֪��
 * ʹ��volatile�ؼ��֣����������̶߳�������������޸�ֵ
 * 
 * ������Ĵ����У�running�Ǵ����ڶ��ڴ��t������
 * ���߳�t1��ʼ���е�ʱ�򣬻��runningֵ���ڴ��ж���t1�̵߳Ĺ������������й�����ֱ��ʹ�����copy��������ÿ�ζ�ȥ
 * ��ȡ���ڴ棬�����������߳��޸�running��ֵ֮��t1�̸߳�֪���������Բ���ֹͣ����
 * 
 * ʹ��volatile������ǿ�������̶߳�ȥ���ڴ��ж�ȡrunning��ֵ
 * 
 * �����Ķ���ƪ���½��и���������
 * http://www.cnblogs.com/nexiyi/p/java_memory_model_and_thread.html
 * 
 * volatile�����ܱ�֤����̹߳�ͬ�޸�running����ʱ�������Ĳ�һ�����⣬Ҳ����˵volatile�������synchronized
 * @author mashibing
 */
package com.caidi.juc.c_012_Volatile;


import java.util.concurrent.TimeUnit;

public class T01_HelloVolatile {
	volatile boolean running = true; //�Ա�һ������volatile������£������������н��������
	void m() {
		System.out.println("m start");
		while(running) {
		}
		System.out.println("m end!");
	}
	
	public static void main(String[] args) {
		T01_HelloVolatile t = new T01_HelloVolatile();
		
		new Thread(t::m, "t1").start();

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t.running = false;
		System.out.println(t.running);
	}
	
}

/**
 * ���߳��޸Ķ����Ա�������൱���̼߳乫�������������߳��޷���ʱ��֪��
 *
 */
class  Test{
	// �̼߳乲�����
	 volatile boolean b = true;

	public void m1() {
		System.out.println("start--");
		while (b) {

		}
		System.out.println("end--");
	}

	public static void main(String[] args) {
		//
		Test test = new Test();

		new Thread(()->{
			test.m1();
		}).start();

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		test.b = false;
		System.out.println(test.b);
	}
}

class Task1 {
	public void m1(boolean b) {
		System.out.println("start--");
		while (b) {

		}
		System.out.println("end--");
	}
}



