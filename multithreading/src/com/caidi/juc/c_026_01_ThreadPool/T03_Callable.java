/**
 * ��ʶCallable����Runnable��������չ
 * ��Callable�ĵ��ã������з���ֵ
 */
package com.caidi.juc.c_026_01_ThreadPool;

import java.util.concurrent.*;

public class T03_Callable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> c = new Callable() {
            @Override
            public String call() throws Exception {
                return "Hello Callable";
            }
        };

        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(c); //�첽

        System.out.println(future.get());//����

        service.shutdown();
    }

}


class Test11{
    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int a = 8;
            int b = 78;
            int c = a + b;
            return c;
        }
    }
    public static void main(String[] args) {
        MyCallable myCallable = new MyCallable();
        ExecutorService executorService = Executors.newCachedThreadPool();
        // �첽������һ���߳�ȥִ��
        Future<Integer> future = executorService.submit(myCallable);
        // ���ؽ������ �������첽�ģ�������Ҫ��ȡ(get)������,��������
        try {
            // ��ʱ�����̵߳�������׳��쳣
            Integer num = future.get(1, TimeUnit.SECONDS);
            System.out.println("future num:" + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        // 1��ֹͣ�����µ�submit������
        //2���Ѿ��ύ�����񣨰��������ܵĺͶ����еȴ��ģ�,�����ִ����ɣ�
        //3���ȵ���2����ɺ󣬲�����ֹͣ��
        executorService.shutdown();
    }
}

class Test2 {
    public static void main(String[] args) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int a = 8;
                int b = 78;
                int c = a * b;
                Thread.sleep(10000);
                return c;
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(callable);

        // ������һ��û�з���ֵ���߳�ȥ������,���̲߳��õȴ�
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("callable����ֵ�� " + future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //System.out.println();
        executorService.shutdown();
    }
}
