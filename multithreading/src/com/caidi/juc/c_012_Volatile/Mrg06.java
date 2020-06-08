package com.caidi.juc.c_012_Volatile;

/**
 * @author: 蔡迪
 * @date: 10:05 2020/6/8
 * @description: 指令重排序-单例模式-double-check,来保障线程安全性，但是否可以不加volatile?
 * 理论上要加上，因为new instance过程中，可能出现指令重排序。导致取到的对象的值是未赋值完成的。
 * 对象创建底层指令步骤：1.申请内存空间，变量赋予默认值 2.给变量赋值。3.将对象堆空间地址赋给栈引用
 * 实际情况，在超高并发情况下，才有可能出现下面的情况。一般比较难观察出问题。
 */

public class Mrg06 {

    private int a = 5;
    private static volatile Mrg06 instance = null;
    private Mrg06() {
    }

    public int getA() {
        return a;
    }

    // 获取唯一实例对象
    public static Mrg06 getInstance() {
        // double check
        if (instance == null) {
            synchronized (Mrg06.class) {
                if (instance == null) {
                    instance = new Mrg06();
                }
                    return instance;
            }
        } else {
            return instance;
        }
    }

    public static void main(String[] args) {
        // 1000000并发要是不加锁,就会出现线程安全问题
        for (int i = 0; i < 10000;i++) {
            Mrg06 mrg06 = Mrg06.getInstance();
            System.out.println(mrg06.hashCode());
            //System.out.println(mrg06.getA());
        }
    }
}