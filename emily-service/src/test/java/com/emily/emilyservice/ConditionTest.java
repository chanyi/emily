package com.emily.emilyservice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

    private volatile int i = 0;
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    public static void main(String[] args) {
        ConditionTest conditionTest = new ConditionTest();
        new Thread(() -> {
            conditionTest.A();
        }, "线程A").start();
        new Thread(() -> {
            conditionTest.B();
        }, "线程B").start();
        new Thread(() -> {
            conditionTest.C();
        }, "线程C").start();

        while (true) {
            //打印当前活跃线程数

        }
    }

    public void A() {
        while (true) {
            lock.lock();
            System.out.println("A线程拿到锁");
            try {
                while (i != 0) {
                    try {
                        conditionA.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "->A");
                try {
                    Thread.sleep(1000);
                    i = 1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //唤醒B
                conditionB.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void B() {
        while (true) {
            lock.lock();
            System.out.println("B线程拿到锁");
            try {
                while (i != 1) {
                    try {
                        conditionB.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "->B");
                try {
                    Thread.sleep(1000);
                    i = 2;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //唤醒C
                conditionC.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void C() {
        while (true) {
            lock.lock();
            System.out.println("C线程拿到锁");
            try {
                while (i != 2) {
                    try {
                        conditionC.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "->C");
                try {
                    Thread.sleep(1000);
                    i = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //唤醒A
                conditionA.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
