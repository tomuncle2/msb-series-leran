/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * 
 * 分析下面这个程序，能完成这个功能吗？
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
			System.out.println("t2 结束");
		}, "t2").start();
	}
}

/**
 *实现一个容器，提供getSize,add方法。实现一个线程往里面添加元素，另一个线程监听，当容器元素个数为五的时候停止线程。
 *会产生问题：1.线程不可见问题 2.add getSize方法非线程安全
 */
class MyT01_WithoutVolatile {

    private List<String> list = new ArrayList();

    // 添加元素
    public void add(String element) {
            list.add(element);
    }

    // 获取容器大小
    public int getSize() {
        return list.size();
    }

    /**线程的不可见性？加volatitle*/
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
