package com.caidi.juc.c_sync;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 蔡迪
 * @date: 11:32 2021/10/15
 * @description: 生产者-消费者
 */
public class TestSync {

    public static void main(String[] args) {
        // 新建仓库（锁对象）
        Warehouse warehouse = new Warehouse();


        // 生产者
        List<Thread> listP = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Product product = new Product(warehouse);
            Thread thread = new Thread(product);
            listP.add(thread);
        }

        // 消费者
        List<Thread> listC = new ArrayList<>();
        for (int i = 0; i <3 ; i++) {
            Customer customer = new Customer(warehouse);
            Thread thread = new Thread(customer);
            listC.add(thread);
        }

        for (Thread thread : listP) {
            thread.start();
        }

        for (Thread thread : listC) {
            thread.start();
        }
    }

}