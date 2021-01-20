package com.smallyuan.labs.leetcode.string;

/**
 * 获取前后缀数组
 */
public class KMP {
    /**
     * 求next数组（最长相等前后缀数组）
     * 1. 初始化
     * 2. 处理前后缀不相等的情况
     * 3. 处理前后缀相等的情况
     * 4. 更新next数组
     * 参数 1， next数组， 2，模式串 S
     * 变量 1. i：后缀末尾位置 j：前缀末尾位置/字串最长相等前后缀
     */
    void getNext(int[] next,String s) {
        int j = 0;
        next[0] = 0;
        for (int i = 1; i < s.length(); i++) {
            // 前后缀不相等的情况
            while (s.charAt(i) != s.charAt(j) && j > 0) {
                j = next[j - 1];
            }
            // 前后缀相等的情况
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
            // 更新next数组
            next[i] = j;
        }
    }

//    28. 实现 strStr()
    public int strStr(String haystack, String needle) {
        return 0;
    }
}
