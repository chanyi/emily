package com.emily.emilyservice.algorithm.array;

import java.util.Arrays;

public class MaxProfit {
    public static void main(String[] args) {
//        int[] prices = {7,1,5,3,6,4};
//        int[] prices = {7,6,4,3,1};
//        int[] prices = {1,2,3,4,5};
//        int[] prices = {2,4,1};
//        int[] prices = {3,3,5,0,0,3,1,4};
        int[] prices = {2,1,2,0,1};
        int total = new MaxProfit().maxProfit(prices);
        System.out.println("股票："+Arrays.toString(prices)+"的最大收益是："+total);
    }


    /**
     * 买卖股票的最佳时机 II
     * 给定一个数组 prices ，其中prices[i] 是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）
     *==================================================================================================================
     * 示例 1:
     * 输入: prices = [7,1,5,3,6,4]
     * 输出: 7
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     * 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
     * ==================================================================================================================
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices){
        if(prices.length==0){
            return 0;
        }
        int total = 0;
        int part = 0;
        int left =0;
        for (int right=1; right<prices.length;right++){
            if(prices[left]>prices[right]){
                left++;
                total+=part;
                part=0;
                left=right;
            }
            else{
                if(prices[right]-prices[left] > part){
                    part= prices[right]-prices[left];
                    if(right == prices.length-1){
                        total+=part;
                    }
                }else{
                    total+=part;
                    part=0;
                    left=right;
                }

            }

        }
        return total;
    }
}
