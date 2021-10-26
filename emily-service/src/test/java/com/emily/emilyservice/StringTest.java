package com.emily.emilyservice;

public class StringTest {
    public static void main(String[] args) {
        test();
    }

    public static void test(){
        String s1 = "a";
        String s2 = new String("a");
        String s3 = s2;
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s2 == s3);
        System.out.println("+----------------+");
        System.out.println(s1.equals(s2));
        System.out.println(s1.equals(s3));
        System.out.println(s3.equals(s2));

        final int[] arr = {1,2,3};
        arr[2]=3;



    }
}
