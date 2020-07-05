/**
 * �����������⣺���Ա�����
 * ʵ��һ���������ṩ����������add��size
 * д�����̣߳��߳�1���10��Ԫ�ص������У��߳�2ʵ�ּ��Ԫ�صĸ�������������5��ʱ���߳�2������ʾ������
 * 
 * ��lists���volatile֮��t2�ܹ��ӵ�֪ͨ�����ǣ�t2�̵߳���ѭ�����˷�cpu�����������ѭ����
 * ���ң������if �� break֮�䱻����̴߳�ϣ��õ��Ľ��Ҳ����ȷ��
 * ����ô���أ�
 * @author mashibing
 */
package com.caidi.juc.c_020_01_Interview;


import java.util.*;
import java.util.concurrent.TimeUnit;


public class T02_WithVolatile {

	// ���volatile��ʹt2�ܹ��õ�֪ͨ
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
			System.out.println("t2 ����");
		}, "t2").start();
	}

}

/**
 *ʵ��һ���������ṩgetSize,add������ʵ��һ���߳����������Ԫ�أ���һ���̼߳�����������Ԫ�ظ���Ϊ���ʱ��ֹͣ�̡߳�
 *������⣺1.�̲߳��ɼ����� 2.add getSize�������̰߳�ȫ �п��������Ѿ��ӵ�5�ˣ�sizeֵ����40
 * ��������volatile���ε����������ͣ������߳�add�󣬺�ʱд��ȥ����֪�������Ի��������⡣
 * ����t2��ti�ӵ�size = 5 ֮ǰһֱѭ�������ţ���size = 5 ,t2ȥ��֪��ʱsize = 5,��������
 */
class MyT02_WithVolatile {
    // ʹt2�߳̿ɼ�
   // private volatile List<Object> list = new ArrayList<>();

	// ��list��Ϊ�̰߳�ȫ������
	private volatile List<Object> list = Collections.synchronizedList(new ArrayList<>());

    // ���Ԫ��
    public void add(Object element) {
            list.add(element);
    }

    // ��ȡ������С
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

                // ȥ��˯�߾��ܿ������������⣬�п�����˯�߶��ʱ��volatile���߳̿ɼ��ˣ�����д��ȥ�ˡ�
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