package com.emily.emilyservice.algorithm.array;

import java.util.Arrays;

public class RemoveDuplicates {

    public static void main(String[] args) {
        int[] nums = {0, 0, 1, 1,1,1,1, 1, 2,3, 4, 4, 4, 5, 5};
        int newLen = new RemoveDuplicates().removeDuplicates(nums);
        System.out.println("数组长度：" + newLen);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 原地删除数组内重复的元素，要求O(1)复杂度
     * <p>
     * ==================================================================================================================
     * 示例：
     * 输入：nums = [1,1,2]
     * 输出：2, nums = [1,2]
     * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素
     * ==================================================================================================================
     *
     * @param nums
     * @return 返回去重后数组的长度
     */
    public int removeDuplicates(int[] nums){

            //边界条件判断
            if (nums == null || nums.length == 0)
                return 0;
            int left = 0;
            for (int right = 1; right < nums.length; right++)
                // 如果左指针和右指针指向的值一样，说明有重复的， 这个时候，左指针不动，右指针继续往右移。如果他俩
                // 指向的值不一样就把右指针指向的值往前挪
                if (nums[left] != nums[right])
                    nums[++left] = nums[right];
            return ++left;

    }
}
