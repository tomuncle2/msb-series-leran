/**
 * �����������⣺���Ա�����
 * ʵ��һ���������ṩ����������add��size
 * д�����̣߳��߳�1���10��Ԫ�ص������У��߳�2ʵ�ּ��Ԫ�صĸ�������������5��ʱ���߳�2������ʾ������
 * 
 * �����������������������������
 * @author mashibing
 */
package com.caidi.juc.c_020_01_Interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class T01_WithoutVolatile {

	List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}
	
	public static void main(String[] args) {
		T01_WithoutVolatile c = new T01_WithoutVolatile();

		new Thread(() -> {
			for(int i=0; i<10; i++) {
				c.add(new Object());
				System.out.println("add " + i);
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t1").start();
		
		new Thread(() -> {
			while(true) {
				if(c.size() == 5) {
					break;
				}
			}
			System.out.println("t2 ����");
		}, "t2").start();
	}
}

/**
 *ʵ��һ���������ṩgetSize,add������ʵ��һ���߳����������Ԫ�أ���һ���̼߳�����������Ԫ�ظ���Ϊ���ʱ��ֹͣ�̡߳�
 *��������⣺1.�̲߳��ɼ����� 2.add getSize�������̰߳�ȫ
 */
class MyT01_WithoutVolatile {

    private List<String> list = new ArrayList();

    // ���Ԫ��
    public void add(String element) {
            list.add(element);
    }

    // ��ȡ������С
    public int getSize() {
        return list.size();
    }

    /**�̵߳Ĳ��ɼ��ԣ���volatitle*/
    public static void main(String[] args) {

        MyT01_WithoutVolatile myT01_withoutVolatile = new MyT01_WithoutVolatile();

        new Thread(()->{
            for (int i =0;i<10;i++) {
                myT01_withoutVolatile.add("i: " + i);
				System.out.println(Thread.currentThread().getName() + " add  i:" + i);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
        }).start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "  start... ");
           while(true) {
               if (myT01_withoutVolatile.getSize() == 5) {
                   break;
               }
            }
            System.out.println(Thread.currentThread().getName() + "  end ");
        }).start();
    }

}
