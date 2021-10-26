package com.emily.emilyservice;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

    private Map<String,String> map = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public static void main(String[] args) {
        ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
        for (int i = 0; i < 5; i++) {
            final int temp =i;
            new Thread(()->{
                readWriteLockTest.set(temp+"",temp+"");
            },temp+"").start();
        }

        for (int i = 0; i < 5; i++) {
            final int temp =i;
            new Thread(()->{
                readWriteLockTest.get(temp+"");
            },temp+"").start();
        }
    }

    public void set(String key,String value){
        readWriteLock.writeLock().lock();
        try {
            System.out.println("写入"+key);
            map.put(key,value);
            System.out.println("写入"+key+"完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key){

        readWriteLock.readLock().lock();
        try {
            System.out.println("读取"+key);
            String value = map.get(key);
            System.out.println("读取"+key+"完成=="+value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
