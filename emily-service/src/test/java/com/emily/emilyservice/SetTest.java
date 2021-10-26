package com.emily.emilyservice;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class SetTest {
    public static void main(String[] args) {
//        WeakHashMap weakHashMap = new WeakHashMap();
//        Person lilei = new Person();
//        weakHashMap.put(lilei, new PersonValue());
//        System.out.println(weakHashMap.get(lilei));
//        lilei = null;
//        System.gc();
//        System.out.println(weakHashMap.get(lilei));//弱对象在指定的对象置空后，不能获取到对应的value值
//
//        HashMap<Person,PersonValue> hashMap  =new HashMap();
//        hashMap.put(lilei,new PersonValue());
//        System.out.println(hashMap.get(lilei));
//        System.gc();
//        System.out.println(hashMap.get(lilei));//强应用还是能获取到对应的value值



    }


}

final class Person {

}

class PersonValue {
    private Date date;

    public PersonValue() {
        this.date = new Date();
    }

    @Override
    public String toString() {
        return "PersonValue{" +
                "date=" + date +
                '}';
    }
}
