package com.emily.emilyservice;

import com.jayway.jsonpath.internal.function.numeric.Sum;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.LongBinaryOperator;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ForkJoinTest {

    public static void main(String[] args) {
        test1();
    }

    public static void test(){
        long sum = LongStream.rangeClosed(1,1_0000_0000).parallel().reduce(0, Long::sum);
        System.out.println(sum);
    }

    public static void test1(){
        long start = 1;
        long end = 1_0000_0000;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MyForkJoinTask myForkJoinTask = new MyForkJoinTask(start,end);
        forkJoinPool.submit(myForkJoinTask);
        long result = 0;
        try {
            result = myForkJoinTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
