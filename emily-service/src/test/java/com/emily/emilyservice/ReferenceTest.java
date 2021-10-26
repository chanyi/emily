package com.emily.emilyservice;

import javax.sound.midi.Soundbank;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class ReferenceTest {

    public static void main(String[] args) {
//        new ReferenceTest().strongRef();
//        new ReferenceTest().softRef();
        new ReferenceTest().phantomRef();
    }

    //强引用就是常用的引用
    public void strongRef(){
        //强引用
        Object obj1 =new Object();
        Object obj2 = obj1;
        obj1=null;
        System.gc();
        System.out.println(obj2);
    }

    //软引用就是在内存够用的时候不会回收，在内存不够用的时候，会回收
    //测试的过程中使用命令号设置内存参数：-Xms5m -Xmx5m -XX:+PrintGCDetails
    public void softRef(){
        Object obj1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(obj1);
        obj1=null;
        System.gc();
        System.out.println(obj1);
        System.out.println(softReference.get());

        //设置内存不够情况，创建一个大对象
        try {
            Byte[] bytes = new Byte[30*1024*1024];
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            System.out.println("内存不够用的情况==");
            System.out.println(softReference.get());
        }


        //软引用使用的场景（一次性加载很多图片到缓存中）
        Map<String,SoftReference<BitSet>> map= new HashMap<String,SoftReference<BitSet>>();
    }

    public void phantomRef(){
        Object o =new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(o,referenceQueue);
        System.out.println(o);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
        System.out.println("====");
        o=null;
        System.gc();
        try {
            Thread.sleep(500);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(o);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
    }
}

