package com.yumodev.java.thread;


/**
 * 线程同步
 *
 */
public class ThreadSynchronize {

    public static void main(String[] args){
        action();
    }

    public static void action() {
//		    MuliteRunnable r = new MuliteRunnable(); 
//	        Thread ta = new Thread(r, "Thread-A"); 
//	        Thread tb = new Thread(r, "Thread-B"); 
//	        ta.start(); 
//	        tb.start(); 

        MyRunnableC rc = new MyRunnableC();
        Thread tc = new Thread(rc, "Thread_A");
        Thread td = new Thread(rc, "Thread_B");
        Thread te = new Thread(new Runnable() {
            public void run() {
                (new ClsC()).funCB(Thread.currentThread().getName());
            }
        }, "Thread_C");
        tc.start();
        td.start();
        te.start();
    }

    class ClsA {
        int x = 100;

        public synchronized int getX() {

            return x;

        }

        public synchronized int fixX(int y) {

            return x = x - y;
        }
    }


    class MuliteRunnable implements Runnable {
        Thread1 thread1 = new Thread1();
        private ClsB clsA = new ClsB();

        @Override
        public void run() {
            // public void run() {
            for (int i = 0; i < 3; i++) {
                this.fix(30);
                System.out.println(Thread.currentThread().getName() + " : 当前foo对象的x值= " + clsA.getX());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        public int fix(int y) {
            return clsA.fixX(y);
        }

    }

    static class ClsC {
        public synchronized void funCA(String strThreadName) {
            for (int i = 0; i < 5; i++) {
                System.out.println(strThreadName + " funCA" + i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        public void funCB(String strThreadName) {
            for (int i = 0; i < 5; i++) {
                System.out.println(strThreadName + " funC" + i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class MyRunnableC implements Runnable {

        private ClsC clsC = new ClsC();

        @Override
        public void run() {
            //synchronized(this)
            {

                System.out.println(Thread.currentThread().getName());
                clsC.funCA(Thread.currentThread().getName());

            }
        }

    }


    public void action1() {
        Account account = new Account();
        account.setBalance(1000);

        Thread customerThread = new Thread(new Customer(account));
        Thread bankThread = new Thread(new Bank(account));

        customerThread.start();
        bankThread.start();
        try {
            customerThread.join();
            bankThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("account:balance: " + account.getBalance());
    }

    //以账号的存取为例子描述Synchronized
    class Account {
        //金额
        int balance = 0;

        public void setBalance(int bal) {
            balance = bal;
        }

        public int getBalance() {
            return balance;
        }

        //存款
        public synchronized void addAmount(int balance) {
            this.balance += balance;
        }

        //取款
        public synchronized void subTractAmount(int balance) {
            this.balance -= balance;
        }
    }

    class Customer implements Runnable {

        private Account account = null;

        public Customer(Account account) {
            this.account = account;
        }

        @Override
        public void run() {
            for (int n = 0; n < 100; n++) {
                //取款
                account.subTractAmount(100);
            }
        }
    }

    class Bank implements Runnable {
        private Account account = null;

        public Bank(Account account) {
            this.account = account;
        }

        @Override
        public void run() {
            for (int n = 0; n < 100; n++) {
                //存款
                this.account.addAmount(100);
            }
        }
    }

    public class ClsB {
        int x = 100;

        public synchronized int getX() {
            return x;
        }

        public synchronized int fixX(int y) {
            return x = x - y;
        }
    }
}
