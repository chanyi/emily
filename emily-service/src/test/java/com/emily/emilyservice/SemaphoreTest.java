package com.emily.emilyservice;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) {
        //设定三个证书
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i < 9; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"在抢证书中...");
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"抢到证书");
                    Thread.sleep(2000);
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName()+"释放证书");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
