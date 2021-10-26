package com.emily.emilyservice.algorithm.array;

import java.util.Arrays;

public class Rotate {
    public static void main(String[] args) {
//        int[] nums = {1,2,3,4,5,6,7};
//        int k= 2;
//        int[] nums = {1,2};
//        int k= 3;
        int[] nums = {1,2,3,4,5,6};
        int k= 11;
        System.out.println("数组："+Arrays.toString(nums)+"旋转："+k+"步后变为：");
//        new Rotate().reverse(nums,0,4);
        new Rotate().rotate(nums,k);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 给定一个数组，将数组中的元素向右移动k个位置，其中k是非负数。
     * 进阶：
     *
     * 尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
     * 你可以使用空间复杂度为O(1) 的原地算法解决这个问题吗？
     * =================================================================================================================
     * 示例 1:
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右旋转 1 步: [7,1,2,3,4,5,6]
     * 向右旋转 2 步: [6,7,1,2,3,4,5]
     * 向右旋转 3 步: [5,6,7,1,2,3,4]
     * =================================================================================================================
     * 实现思路是反转整个数组，然后再反转前面k位和反转后k位，即可
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        if(nums.length<k){
            k=k%nums.length;
        }
        //全部反转
        reverse(nums,0,nums.length-1);
        //分别反转前面k位和剩下的数组
        reverse(nums,0,k-1);
        reverse(nums,k,nums.length-1);
    }
    //反转数组的
    public void reverse(int[] nums,int start,int end){

        if(nums.length==0 ||nums.length==1 ||nums.length<end || start>=end){
            return;
        }

        for (int i=0;i<(end-start)/2+1;i++){
            int temp =0;
            temp = nums[start+i];
            nums[start+i]=nums[end-i];
            nums[end-i] =temp;
        }
    }
}
