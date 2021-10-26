package com.emily.emilyservice;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CallableTest {
    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask(new CallableTest().new MyThread());
        new Thread(futureTask,"A").start();
        new Thread(futureTask,"B").start();
        String result = "";
        try {
            result = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("线程执行返回结果："+result);;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(futureTask,"C").start();
        try {
            result = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("线程C执行返回结果："+result);;
    }
    public class MyThread implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName()+"在执行call()");
            return "A";
        }
    }
}
