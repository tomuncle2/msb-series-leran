package com.caidi.juc.c_sync;

/**
 * @author: 蔡迪
 * @date: 11:31 2021/10/15
 * @description: 生产者
 */

public class Product implements Runnable {
    private Warehouse warehouse;
    @Override
    public void run() {
        // 生产
        while (true) {
            warehouse.add();
        }
    }

    public Product(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}