package com.caidi.juc.c_sync;

/**
 * @author: 蔡迪
 * @date: 11:32 2021/10/15
 * @description: 消费者
 */
public class Customer implements Runnable{

    private Warehouse warehouse;

    @Override
    public void run() {
        // 消费
        while (true) {
            warehouse.sub();
        }
    }

    public Customer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}