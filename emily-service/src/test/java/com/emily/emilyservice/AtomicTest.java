package com.emily.emilyservice;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.stream.LongStream;

public class AtomicTest {

    public static void main(String[] args) {
        AtomicStampedReference<Integer> atomicReference = new AtomicStampedReference<>(1,1);
        new Thread(()->{
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicReference.compareAndSet(1,2,
                    atomicReference.getStamp(),atomicReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"--atomicReference.getReference()--"+atomicReference.getReference());
            System.out.println(Thread.currentThread().getName()+"--atomicReference.getStamp()--"+atomicReference.getStamp());
        }).start();

        new Thread(()->{
            int stamp = atomicReference.getStamp();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(2, 3,
                    stamp, stamp + 1));

            System.out.println(Thread.currentThread().getName()+"--atomicReference.getReference()--"+atomicReference.getReference());
            System.out.println(Thread.currentThread().getName()+"--stamp--"+stamp);
        }).start();
    }
}
