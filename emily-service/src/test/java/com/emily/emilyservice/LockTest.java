package com.emily.emilyservice;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    private static int i = 0;

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
//        LockTest lockTest = new LockTest();
//
//        //设定两个消费者，两个生产者
//        new Thread(() -> { lockTest.product(); }, "生产者1").start();
//        new Thread(() -> { lockTest.product(); }, "生产者2").start();
//        new Thread(() -> { lockTest.consumer(); }, "消费者1").start();
//        new Thread(() -> { lockTest.consumer(); }, "消费者2").start();


        testReentrantLock1();
    }

    public static void testReentrantLock1(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("第1次获取锁，这个锁是：" + lock);

                    int index = 1;
                    while (true) {
                        try {
                            lock.lock();
                            System.out.println("第" + (++index) + "次获取锁，这个锁是：" + lock);

                            try {
                                Thread.sleep(new Random().nextInt(200));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (index == 10) {
                                break;
                            }
                        } finally {
                            lock.unlock();
                        }

                    }

                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("第1次获取锁，这个锁是：" + lock);

                    int index = 1;
                    while (true) {
                        try {
                            lock.lock();
                            System.out.println("第" + (++index) + "次获取锁，这个锁是：" + lock);

                            try {
                                Thread.sleep(new Random().nextInt(200));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (index == 10) {
                                break;
                            }
                        } finally {
                            lock.unlock();
                        }

                    }

                } finally {
                    lock.unlock();
                }
            }
        }).start();

    }
    public static void testReentrantLock() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    System.out.println(Thread.currentThread().getName()+"第1次获取锁，这个锁是：" + this);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int index = 1;
                    while (true) {
                        synchronized (this) {
                            System.out.println(Thread.currentThread().getName()+"第" + (++index) + "次获取锁，这个锁是：" + this);
                        }
                        if (index == 10) {
                            break;
                        }
                    }
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    System.out.println(Thread.currentThread().getName()+"第1次获取锁，这个锁是：" + this);
                    int index = 1;
                    while (true) {
                        synchronized (this) {
                            System.out.println(Thread.currentThread().getName()+"第" + (++index) + "次获取锁，这个锁是：" + this);
                        }
                        if (index == 10) {
                            break;
                        }
                    }
                }
            }
        }).start();
    }


    public synchronized void product() {
        while (true) {
            while (i >= 10) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("当前物品数量" + i + ",不足10个，" + Thread.currentThread().getName() + "开始生产...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            this.notifyAll();
        }
    }
    public synchronized void consumer() {
        while (true) {
            while (i < 1) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("当前物品数量" + i + ",大于0个，" + Thread.currentThread().getName() + "开始消费...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i--;
            this.notifyAll();
        }
    }
}
