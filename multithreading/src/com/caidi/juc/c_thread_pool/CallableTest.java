package com.caidi.juc.c_thread_pool;

import java.util.concurrent.Callable;

/**
 * @author: 蔡迪
 * @date: 14:35 2021/10/17
 * @description: 自定义callable实现类
 */
public class CallableTest implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "任务完成返回";
    }
}