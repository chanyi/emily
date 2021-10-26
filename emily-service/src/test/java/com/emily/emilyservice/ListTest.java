package com.emily.emilyservice;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringBootTest
public class ListTest {
    public static void main(String[] args) {

    }

    @Test
    public void testArrayList() throws InterruptedException {
        List list = Collections.synchronizedList(Lists.newArrayList());
        for (int j = 0; j < 100; j++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        list.add(i + "");
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        Thread.sleep(1000);
//        synchronized (list) {
//            for (int i = 0; i < list.size(); i++) {
//                System.out.println("第" + (i + 1) + "个元素为：" + list.get(i));
//            }
//        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println("第" + (i + 1) + "个元素为：" + list.get(i));
        }
    }

    @Test
    public void testLinkedList(){
        List<String> list = new LinkedList<>();

    }

}
