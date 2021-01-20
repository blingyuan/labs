package com.smallyuan.labs.leetcode.string;


class Solution {

//    344. 反转字符串(简单)
    public void reverseString(char[] s) {
        if (s == null || s.length == 1) return;
        for (int i = 0; i < s.length/2; i++) {
            if (s[i] == s[s.length-1-i]) {
                continue;
            } else {
                char tmp = s[i];
                s[i] = s[s.length-i-1];
                s[s.length-i-1] = tmp;
            }
        }
    }

//    541. 反转字符串 II(简单)
    public String reverseStr(String s, int k) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i += (2*k)) {
            if (i + k < s.length()) {
                reverse(chars,i,i+k-1);
            } else {
                reverse(chars,i,s.length()-1);
            }
        }
        return new String(chars);
    }

    private void reverse(char[] s, int left, int right) {
        System.out.printf("left[%d]:%c; right[%d]:%c\n",left,s[left],right,s[right]);
        for (; left < right; ++left, --right) {
            char tmp = s[left];
            s[left] = s[right];
            s[right] = tmp;
        }
    }

    // 剑指 Offer 05. 替换空格(简单)

    // c++ 才可以用下面的方法，因为c++的String类型是可变的
    // 统计空格个数，将数组扩充到替换完后的长度
    // 采用双指针法，从后往前填充，left指向旧长度末尾，right指向新长度末尾
    // java，直接StringBuilder搞起
    public String replaceSpace(String s) {
        if (s == null || s.length() == 0) return null;
        StringBuilder builder = new StringBuilder();
        for (Character c : s.toCharArray()) {
            if (c == ' ') {
                builder.append("%20");
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }


    // 151. 翻转字符串里的单词 (中等)
    public String reverseWords(String s) {
        // 直接使用api的写法
//        s = s.trim();
//        List<String> wordList = Arrays.asList(s.split("\\s+"));
//        Collections.reverse(wordList);
//        return String.join(" ",wordList);

        // 自己编写的步骤
        // 1. 去除多余的空格
        StringBuilder builder = trimSpace(s);
        System.out.println("去除空格："+builder.toString());
        // 2. 将整个字符串翻转
        reverse(builder,0,builder.length()-1);
        System.out.println("整个翻转："+builder.toString());
        // 3. 将每个单词翻转
        reverseEachWord(builder);

        return builder.toString();
    }

    /**
     * 去除多余的空格
     * @param s
     * @return
     */
    private StringBuilder trimSpace(String s) {
        int left = 0, right = s.length() - 1;
        // 去除开头空格
        while (left <= right && s.charAt(left) == ' ') {
            left++;
        }
        // 去除尾巴空格
        while (left <= right && s.charAt(right) == ' ') {
            right--;
        }
        // 去除中间多余空格
        StringBuilder builder = new StringBuilder();
        while (left <= right) {
            char c = s.charAt(left);
            if (c != ' ') {
                builder.append(c);
            } else if (builder.charAt(builder.length()-1) != ' ') {
                builder.append(c);
            }
            left++;
        }
        return builder;
    }

    /**
     * 翻转字符串
     * @param builder
     * @param left
     * @param right
     */
    private void reverse(StringBuilder builder, int left, int right) {
        System.out.printf("left[%d]:%c; right[%d]:%c\n",left,builder.charAt(left),right,builder.charAt(right));
        while (left < right) {
            char tmp = builder.charAt(left);
            builder.setCharAt(left,builder.charAt(right));
            builder.setCharAt(right,tmp);
            left++;
            right--;
        }
    }

    /**
     * 翻转每个单词
     * @param builder
     */
    private void reverseEachWord(StringBuilder builder) {
        int left = 0, right = 0, n = builder.length();
        while (left < n) {
            while (right < n && builder.charAt(right) != ' '){
                right++;
            }
            reverse(builder,left,right - 1);
            left = right + 1;
            right++;
        }
    }

//    剑指 Offer 58 - II. 左旋转字符串(简单)
    public String reverseLeftWords(String s, int n) {
        StringBuilder builder = new StringBuilder();
        for (int i = n; i < s.length(); i++) {
            builder.append(s.charAt(i));
        }
        for (int i = 0; i < n; i++) {
            builder.append(s.charAt(i));
        }
        return builder.toString();
    }
}