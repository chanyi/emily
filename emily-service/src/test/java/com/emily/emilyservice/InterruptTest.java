package com.emily.emilyservice;

public class InterruptTest {
    //在线程sleep前中断线程
    public static void main(String[] args) {
        Thread.currentThread().interrupt();
        try {
            System.out.println("主线程开始执行");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("线程被中断");
        }
        System.out.println("线程继续执行");

    }
}
