/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * 
 * 给lists添加volatile之后，t2能够接到通知，但是，t2线程的死循环很浪费cpu，如果不用死循环，
 * 而且，如果在if 和 break之间被别的线程打断，得到的结果也不精确，
 * 该怎么做呢？
 * @author mashibing
 */
package com.caidi.juc.c_020_01_Interview;


import java.util.*;
import java.util.concurrent.TimeUnit;


public class T02_WithVolatile {

	// 添加volatile，使t2能够得到通知
	volatile List lists = new LinkedList();
	//volatile List lists = Collections.synchronizedList(new LinkedList<>());

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	public static void main(String[] args) {

		T02_WithVolatile c = new T02_WithVolatile();
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
 *解决问题：1.线程不可见问题 2.add getSize方法非线程安全 有可能容器已经加到5了，size值还是40
 * 但是由于volatile修饰的是引用类型，本地线程add后，何时写回去还不知道。所以还是有问题。
 * 这里t2在ti加到size = 5 之前一直循环运行着，到size = 5 ,t2去感知何时size = 5,结束运行
 */
class MyT02_WithVolatile {
    // 使t2线程可见
   // private volatile List<Object> list = new ArrayList<>();

	// 将list变为线程安全的容器
	private volatile List<Object> list = Collections.synchronizedList(new ArrayList<>());

    // 添加元素
    public void add(Object element) {
            list.add(element);
    }

    // 获取容器大小
    public int getSize() {
        return list.size();
    }


    public static void main(String[] args) {
        MyT02_WithVolatile myT02_withVolatile = new MyT02_WithVolatile();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "  start ");
            for (int i =0;i<10;i++) {
                myT02_withVolatile.add(new Object());
                System.out.println(Thread.currentThread().getName() + " add " + i);

                // 去掉睡眠就能看到还是有问题，有可能在睡眠额度时候volatile让线程可见了，数据写回去了。
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        },"t1").start();

        new Thread(()->{
            while (true) {
                if (5 == myT02_withVolatile.getSize()) {
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + "  end ");
        },"t2").start();


		System.out.println();
    }

}