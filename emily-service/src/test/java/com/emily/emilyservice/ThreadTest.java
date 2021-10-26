package com.emily.emilyservice;

import java.util.concurrent.*;

public class ThreadTest {

    public static void main(String[] args) {
        Thread thread = Thread.currentThread();
        thread.getState();
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.remove();

        ExecutorService executorService1 = Executors.newCachedThreadPool();
        ExecutorService executorService2 = Executors.newFixedThreadPool(10);
        ExecutorService executorService3 = Executors.newSingleThreadExecutor();

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(10);
        BlockingQueue<String> blockingQueue2 = new SynchronousQueue();


    }
}
