package com.emily.emilyservice.algorithm.string;

import java.security.acl.LastOwnerException;
import java.util.Arrays;

public class LongestCommonPrefix {

    public static void main(String[] args) {
        System.out.println("fl".indexOf("l"));
        String[] strs= {"flower","flow","flight"};
        System.out.println(Arrays.toString(strs));
        System.out.println("结果="+new LongestCommonPrefix().longestCommonPrefix(strs));
    }

    /**
     * 求最长公共前缀
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        //边界条件判断
        if (strs == null || strs.length == 0)
            return "";
        //默认第一个字符串是他们的公共前缀
        String pre = strs[0];
        int i = 1;
        while (i < strs.length) {
            //不断的截取
            while (strs[i].indexOf(pre) != 0)
                pre = pre.substring(0, pre.length() - 1);
            i++;
        }
        return pre;
    }

}
