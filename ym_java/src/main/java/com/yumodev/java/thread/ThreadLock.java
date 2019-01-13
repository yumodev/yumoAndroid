/**
 * ThreadLock.java
 * yumodev
 * 2015-3-11
 */
package com.yumodev.java.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * yumo
 */
public class ThreadLock {

    public static void main(String[] args){

    }
    /**
     * Lock 的测试类
     */
    public ThreadLock() {
        Lock lock = new ReentrantLock();
        Account account = new Account();
        account.setLock(lock);
        account.setBalance(100);

        Thread customerThread = new Thread(new Customer(account));
        Thread bankThread = new Thread(new Bank(account));

        customerThread.start();
        bankThread.start();
//		try {
//			customerThread.join();
//			bankThread.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}


        System.out.println("account:balance: " + account.getBalance());
    }

    //以账号的存取为例子描述Synchronized
    class Account {
        //金额
        int balance = 0;

        private Lock lock = null;

        public void setBalance(int bal) {
            balance = bal;
        }

        public int getBalance() {
            return balance;
        }

        public void setLock(Lock lock) {
            this.lock = lock;
        }

        //存款
        public void addAmount(int balance) {
            try {
                System.out.println("begin lock addAmount: " + this.balance);
                lock.lock();
                this.balance += balance;
                System.out.println("lock addAmount: " + this.balance);
            } finally {
                lock.unlock();
                System.out.println("end lock addAmount: ");
            }
        }

        //取款
        public void subTractAmount(int balance) {
            try {
                System.out.println("begin lock subTractAmount: " + this.balance);
                lock.lock();
                this.balance -= balance;
                System.out.println("subTractAmount: " + this.balance);
            } finally {
                lock.unlock();
                System.out.println("end lock subTractAmount: ");
            }
        }
    }

    class Customer implements Runnable {

        private Account account = null;

        public Customer(Account account) {
            this.account = account;
        }

        @Override
        public void run() {
            for (int n = 0; n < 5; n++) {
                //取款
                System.out.println(Thread.currentThread().getName() + " customer subtract begin");
                account.subTractAmount(100);
                System.out.println(Thread.currentThread().getName() + " customer subtract end");
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
            for (int n = 0; n < 5; n++) {
                //存款
                System.out.println(Thread.currentThread().getName() + " bank add begin");
                this.account.addAmount(100);
                System.out.println(Thread.currentThread().getName() + " bak add end");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
