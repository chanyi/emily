package com.emily.emilyservice;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CountDownLatchTest {
    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 1; i < 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"doing ...");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("all thread done!");
    }

}
