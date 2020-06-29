package com.caidi.juc.c_020;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class T09_TestPhaser2 {
    static Random r = new Random();
    static MarriagePhaser phaser = new MarriagePhaser();


    static void milliSleep(int milli) {
        try {
            TimeUnit.MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        phaser.bulkRegister(7);

        for(int i=0; i<5; i++) {

            new Thread(new Person("p" + i)).start();
        }

        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();

    }



    static class MarriagePhaser extends Phaser {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {

            switch (phase) {
                case 0:
                    System.out.println("所有人到齐了！" + registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人吃完了！" + registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("所有人离开了！" + registeredParties);
                    System.out.println();
                    return false;
                case 3:
                    System.out.println("婚礼结束！新郎新娘抱抱！" + registeredParties);
                    return true;
                default:
                    return true;
            }
        }
    }


    static class Person implements Runnable {
        String name;

        public Person(String name) {
            this.name = name;
        }

        public void arrive() {

            milliSleep(r.nextInt(1000));
            System.out.printf("%s 到达现场！\n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void eat() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s 吃完!\n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void leave() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s 离开！\n", name);


            phaser.arriveAndAwaitAdvance();
        }

        private void hug() {
            if(name.equals("新郎") || name.equals("新娘")) {
                milliSleep(r.nextInt(1000));
                System.out.printf("%s 洞房！\n", name);
                phaser.arriveAndAwaitAdvance();
            } else {
                phaser.arriveAndDeregister();
                //phaser.register()
            }
        }

        @Override
        public void run() {
            arrive();


            eat();


            leave();


            hug();

        }
    }
}

/***
 * 分阶段的栅栏
 * 类   Phaser
 * 方法：onAdvance（）     bulkRegister（）：注册  arriveAndAwaitAdvance（）：前进  arriveAndDeregister（）：解除注册  register()：重新注册
 * 应用：达尔文遗传算法
 * @date 13:53 2020/6/29
 * @param null
 * @return
 */
class MyT09_TestPhaser2 {

    static MyPhaser myPhaser = new MyPhaser();

    /**继承Phaser，实现onAdvance()方法*/
    static class MyPhaser extends Phaser {

        // 返回true  所有阶段结束， 当到达当前阶段条件时，默认phase从0开始，方法被调用
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0 :
                    System.out.println("所有人到达婚礼现场 人数：" + registeredParties);return false;
                case 1 :
                    System.out.println("所有人吃完了 人数：" + registeredParties);return false;
                case 2 :
                    System.out.println("所有人离开了 人数：" + registeredParties);return false;
                case 3 :
                    System.out.println("新郎，新娘进洞房了 人数：" + registeredParties);return false;
                default: return true;
            }
            //return super.onAdvance(phase, registeredParties);
        }
    }


    /**person*/
    static class Person implements Runnable {

        private String name;

        public Person(String name) {
            this.name = name;
        }

        // 到达
        public void arrive() {
            System.out.println(name + " 到达了 ");
            myPhaser.arriveAndAwaitAdvance();
        }

        // 吃饭
        public void eat() {
            System.out.println(name + " 吃饭 ");
            myPhaser.arriveAndAwaitAdvance();
        }

        // 离开
        public void leave() {
            System.out.println(name + " 离开 ");
            myPhaser.arriveAndAwaitAdvance();
        }

        // 洞房
        public void hug() {
            if ("新郎".equals(name) || "新娘".equals(name)) {
                System.out.println(name + " 进洞房 ");
                myPhaser.arriveAndAwaitAdvance();
            } else {
                // 解除注册
                myPhaser.arriveAndDeregister();
            }
        }

        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[7];
        myPhaser.bulkRegister(7);
        for(int i =0; i<5; i++) {
            Person person = new Person("宾客" + i);
            threads[i] = new Thread(person);
        }
        Person personLang = new Person("新郎");
        Person personNiang = new Person("新娘");
        threads[5] = new Thread(personLang);
        threads[6] = new Thread(personNiang);


        Arrays.stream(threads).forEach((t)->{
            t.start();
        });
    }
}


