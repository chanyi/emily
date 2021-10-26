package com.emily.emilyservice;

import java.util.concurrent.atomic.AtomicReference;

public class MySpinLock {

    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"在尝试获取锁...");
        while(!atomicReference.compareAndSet(null,thread)){
        }
        System.out.println(Thread.currentThread().getName()+"拿到了锁！");
    }

    public void unlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"解锁完成！");
    }
}
