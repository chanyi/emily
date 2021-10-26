package com.emily.emilyservice.algorithm.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class ContainsDuplicate {
    public static void main(String[] args) {
//        int[] nums = {1,2,3,4,5};
//        System.out.println("数组："+ Arrays.toString(nums));
//        boolean result = new ContainsDuplicate().containsDuplicate2(nums);
//        System.out.println("不包含重复元素："+result);
//        System.out.println(Arrays.toString("anc".toCharArray()));
        System.out.println(Character.isLetterOrDigit('-'));
    }


    /**
     * 给定一个整数数组，判断是否存在重复元素。
     * 如果存在一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
     * 示例 1:
     * 输入: [1,2,3,1]
     * 输出: true
     * 示例 2:
     * 输入: [1,2,3,4]
     * 输出: false
     * 输入: [1,1,1,3,3,4,3,2,4,2]
     * 输出: true
     * =================================================================================================================
     * 两种解题思路
     * 1、先排序，在循环比较
     * 2、放入set集合的过程中，反悔了false，则说明有重复的值，直接返回false
     * =================================================================================================================
     * @param nums
     * @return
     */
    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if(nums[i-1] == nums[i]){
                return false;
            }
        }
        return true;
    }

    public boolean containsDuplicate2(int[] nums) {
        HashSet set = new HashSet();
        for (int i = 1; i < nums.length; i++) {
            if(!set.add(nums[i])){
                return false;
            }
        }
        return true;
    }
}
