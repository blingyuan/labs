package com.smallyuan.labs.dubbo;

import java.util.*;

public class AlgorithmTest {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        [-4,-1,-1,0,1,2]
        solution.threeSum(new int[]{-1,0,1,2,-1,-4});
    }

    static class Solution {
        public void forLoop(int n) {
            // 遵循左闭右闭的原则
            // 四个方向循环
            int[][] res = new int[n][n];
            int up = 0, down = n - 1, right = n - 1, left = 0;
            int num = 1;    //  要填充的数值
            while (num <= n * n) {
                // 左 到 右
                for (int i = left; i <= right; i++) {
                    res[up][i] = num++;
                }
                up++;
                // 上 到 下
                for (int i = up; i <= down; i++) {
                    res[i][right] = num++;
                }
                right--;
                // 右 到 左
                for (int i = right; i >= left; i--) {
                    res[down][i] = num++;
                }
                down--;
                // 下 到 上
                for (int i = down; i >= up; i--) {
                    res[i][left] = num++;
                }
                left++;
            }

            for (int i = 0; i < n; i++) {
                System.out.print("[");
                for (int j = 0; j < n; j++) {
                    System.out.print(" " + res[i][j] + " ,");
                }
                System.out.print("]");
                System.out.println("");
            }
        }

        public boolean isAnagram(String s, String t) {
            if (s.length() != t.length()) {
                return false;
            }
            Map<Character,Integer> table = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                table.put(ch,table.getOrDefault(ch,0)+1);
            }
            for (int i = 0; i < t.length(); i++) {
                char ch = t.charAt(i);
                table.put(ch,table.getOrDefault(ch,0)-1);
                if (table.get(ch) < 0) {
                    return false;
                }
            }
            return true;
        }

        // 两个数组交集
        public int[] intersection(int[] nums1, int[] nums2) {

            Set<Integer> result = new HashSet<>();
            Set<Integer> arrayOne = new HashSet<>();
            for (int i = 0; i < nums1.length; i++) {
                arrayOne.add(nums1[i]);
            }

            for (int i = 0; i < nums2.length; i++) {
                if (arrayOne.contains(nums2[i])) {
                    result.add(nums2[i]);
                }
            }
            return result.stream().mapToInt(Integer::intValue).toArray();
        }

        // 快乐树 202
        public boolean isHappy(int n) {
            int slow = n;
            int fast = getNext(n);
            while (fast != 1 && slow != fast) {
                slow = getNext(slow);
                fast = getNext(getNext(fast));
            }
            return fast == 1 ? true : false;
        }

        private int getNext(int n) {
            int sum = 0;
            while (n != 0) {
                sum += (n % 10) * (n % 10);
                n = n / 10;
            }
            return sum;
        }

        // 两数之和
        public int[] twoSum(int[] nums, int target) {
            Map<Integer,Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                if (map.containsKey(target-nums[i])) {
                    return new int[]{map.get(target-nums[i]),i};
                }
                map.put(nums[i],i);
            }
            return new int[0];
        }

        // 四数之和
        public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
            Map<Integer,Integer> map = new HashMap<>();
            for (int a : A) {
                for (int b : B) {
                    map.put(a+b,map.getOrDefault(a+b,0)+1);
                }
            }
            int count = 0;
            for (int c : C) {
                for (int d : D) {
                    if (map.containsKey(0-c-d)) {
                        count += map.get(0-c-d);
                    }
                }
            }
            return count;
        }

        // 383. 赎金信
        public boolean canConstruct(String ransomNote, String magazine) {
            int[] table = new int[26];
            for (int i = 0; i < magazine.length(); i++) {
                table[magazine.charAt(i) - 'a']++;
            }
            for (int i = 0; i < ransomNote.length(); i++) {
                table[ransomNote.charAt(i) - 'a']--;
                if (table[ransomNote.charAt(i) - 'a'] < 0) {
                    return false;
                }
            }
            return true;
        }

        // 15. 三数之和
        // 双指针法 固定指针 i 和两个移动指针 left + right， 保证 num[i] < num[left] < num[right]，两个指针往里走
        // [0, 0, 0, 0, 0, ..., 0, 0, 0] 最极端的情况
        public List<List<Integer>> threeSum(int[] nums) {
            if (nums.length < 3) {
                return Collections.EMPTY_LIST;
            }
            Arrays.sort(nums);
            List<List<Integer>> result = new ArrayList<>();
            for (int i = 0; i < nums.length-2; i++) {
                // num[i]的值重复也要跳过 [-4,-1,-1,0,1,2]
                while (i > 0 && i < nums.length-2 && nums[i-1] == nums[i]) {
                    i++;
                }
                int left = i + 1;
                int right = nums.length - 1;
                System.out.println("left:"+left+", right:"+right);
                while (left < right) {
                    System.out.printf("nums[%d] + nums[%d] + nums[%d] = %d + %d + %d = %d\n",i,left,right,nums[i],nums[left],nums[right],nums[i] + nums[left] + nums[right]);
                    if (nums[i] + nums[left] + nums[right] == 0) {
                        result.add(Arrays.asList(nums[i],nums[left],nums[right]));
                        // 去重
                        while (right > left && nums[right] == nums[right - 1]) right--;
                        while (right > left && nums[left] == nums[left + 1]) left++;
                        right--;
                        left++;
                    } else if (nums[i] + nums[left] + nums[right] < 0) {
                        left++;
                    } else {
                        right--;
                    }
                    System.out.println("left:"+left+", right:"+right);
                }
            }
            return result;
        }

        // 18. 四数之和
        public List<List<Integer>> fourSum(int[] nums, int target) {
            return null;
        }
    }

}
