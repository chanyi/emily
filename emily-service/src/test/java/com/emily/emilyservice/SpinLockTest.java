package com.emily.emilyservice;

import com.mysql.cj.jdbc.MysqlDataSource;

public class SpinLockTest {

    public static void main(String[] args) {
        MySpinLock lock = new MySpinLock();
        new Thread(()->{
            lock.lock();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"A").start();
        new Thread(()->{
            lock.lock();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"B").start();
    }
}
