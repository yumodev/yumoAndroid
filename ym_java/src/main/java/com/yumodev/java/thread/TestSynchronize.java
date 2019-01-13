package com.yumodev.java.thread;


import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumodev on 7/8/16.
 * 测试同步块
 */
public class TestSynchronize extends TestCase {

    private final String LOG_TAG = "TestSynchronize";

    public void testSyncFunction() {
        final TestSync testSync = new TestSync();
        List<Thread> lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            lists.add(new Thread(new Runnable() {
                @Override
                public void run() {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    testSync.add(1);
                    System.out.println(testSync.getCount() + "");
                }
            }));
        }

        for (int i = 0; i < lists.size(); i++) {
            lists.get(i).start();
        }
    }


    public class TestSync {
        int count = 0;

        public synchronized void add(int value) {
            System.out.println("add begin");
            //synchronized (this)
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count += value;
            }

            System.out.println("add end");
        }

        public int getCount() {
            return count;
        }
    }


    /**
     * 信息
     */
    class Info { // 定义信息类
        private String name = "name";//定义name属性，为了与下面set的name属性区别开
        private String content = "content";// 定义content属性，为了与下面set的content属性区别开
        private boolean flag = true;   // 设置标志位,初始时先生产

        public synchronized void set(String name, String content) {
            System.out.println("set:"+name+" content:"+content);
            while (!flag) {
                try {
                    super.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.setName(name);    // 设置名称
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.setContent(content);  // 设置内容
            flag = false; // 改变标志位，表示可以取走
            super.notify();
        }

        public synchronized void get() {
            System.out.println("get()");
            while (flag) {
                try {
                    super.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("get:"+this.getName() +
                    " --> " + this.getContent());
            flag = true;  // 改变标志位，表示可以生产
            super.notify();
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getName() {
            return this.name;
        }

        public String getContent() {
            return this.content;
        }
    }

    /**
     * 生产者
     */
    class Producer implements Runnable {
        private Info info = null;

        public Producer(Info info) {
            this.info = info;
        }

        public void run() {
            boolean flag = true;
            for (int i = 0; i < 10; i++) {
                if (flag) {
                    this.info.set("姓名--1", "内容--1");
                    flag = false;
                } else {
                    this.info.set("姓名--2", "内容--2");
                    flag = true;
                }
            }
        }
    }

    /**
     * 消费者
     */
    class Consumer implements Runnable {
        private Info info = null;

        public Consumer(Info info) {
            this.info = info;
        }

        public void run() {
            System.out.println("consumer:start");
            for (int i = 0; i < 10; i++) {
                this.info.get();
            }
        }
    }

    /**
     * 生产者和消费者问题。
     */
    public void testProducerConsumer() {
        Info info = new Info();
        Producer pro = new Producer(info);
        Consumer con = new Consumer(info); // 消费者
        new Thread(pro).start();

        //启动了生产者线程后，再启动消费者线程
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(con).start();
    }
}
