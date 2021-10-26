package com.emily.emilyservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFutureTest {

    public static void main(String[] args) {
        test2();
    }


    public static void test(){
        CompletableFutureTest completableFutureTest = new CompletableFutureTest();
        //电脑核数
        System.out.println("电脑核数："+Runtime.getRuntime().availableProcessors());
        //创建一个自定义线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        //创建10个任务
        List<Dish> dishList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            dishList.add(new Dish(i+""));
        }
        //把任务存储起来
        List<CompletableFuture> completableFutureList = new ArrayList<>();
        for (Dish dish :dishList) {
            CompletableFuture completableFuture = CompletableFuture.runAsync(()->dish.make(),forkJoinPool);
            completableFutureList.add(completableFuture);
        }
        long startTime = System.currentTimeMillis();
        //执行任务列表
        CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[completableFutureList.size()])).join();
        System.out.println("任务执行完成...");
        System.out.println("耗时："+(System.currentTimeMillis()-startTime));
    }

    public static void test1(){

        long startTime = System.currentTimeMillis();
        //执行任务列表
        CompletableFuture[] completableFutures = IntStream.rangeClosed(1,10)//先生成1-10的列表
                .mapToObj(i->new Dish(i+""))//再生成和1-10相关的dish对象
                .map(dish -> CompletableFuture.runAsync(Dish::make))//再生成和dish相关的
                .toArray(size->new CompletableFuture[size]);

        CompletableFuture.allOf(completableFutures).join();
        System.out.println("任务执行完成...");
        System.out.println("耗时："+(System.currentTimeMillis()-startTime));
    }

    public static void test2(){
        IntStream.rangeClosed(1,10)
                .mapToObj(i->i+"aa")
                .map(dish->new Dish(Dish.getDishName()+"00"))
                .forEach(System.out::println);


    }

    static class Dish{

        private static String dishName;

        public Dish(String dishName) {
            this.dishName = dishName;
        }

        public static void make(){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" =make dish ="+dishName);
        }

        public static String getDishName() {
            return dishName;
        }

        public static void setDishName(String dishName) {
            Dish.dishName = dishName;
        }


    }
}

