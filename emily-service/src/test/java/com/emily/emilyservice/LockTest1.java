package com.emily.emilyservice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest1 {

    private static volatile int i = 0;

    public static void main(String[] args) {
        LockTest1 lockTest = new LockTest1();
        Lock lock = new ReentrantLock();
        //设定两个消费者，两个生产者
        new Thread(() -> {
            lockTest.product(lock);
        }, "生产者1").start();
        new Thread(() -> {
            lockTest.product(lock);
        }, "生产者2").start();
        new Thread(() -> {
            lockTest.consumer(lock);
        }, "消费者1").start();
        new Thread(() -> {
            lockTest.consumer(lock);
        }, "消费者2").start();
    }

    public void product(Lock lock) {

        while (true) {
            lock.lock();
            if (i < 10) {
                System.out.println("当前物品数量" + i + ",不足10个，" + Thread.currentThread().getName() + "开始生产...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
                i++;
            }
            lock.unlock();
        }
    }

    public void consumer(Lock lock) {
        while (true) {
            lock.lock();
            if (i > 0) {
                System.out.println("当前物品数量" + i + ",大于0个，" + Thread.currentThread().getName() + "开始消费...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i--;
            }
            lock.unlock();
        }
    }
}
