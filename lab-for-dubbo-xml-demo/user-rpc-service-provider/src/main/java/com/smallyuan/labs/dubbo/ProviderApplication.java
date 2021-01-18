package com.smallyuan.labs.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:dubbo.xml")
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class,args);
    }

    public int searchInsert(int[] nums, int target) {
        int n = nums.length;
        int left = 0;
        int right = n-1;
        while (left <= right) {
            int middle = (left + right)/2;
            if (nums[middle] > target) {
               right = middle - 1;
            } else if (nums[middle] < target) {
                left = middle + 1;
            } else {
                return middle;
            }
        }
        return right + 1;
    }

    public int removeElement(int[] nums, int val) {
        int size = nums.length;
        for (int i = 0; i < size; i++) {
            if (nums[i] == val) { // 将后面的都往前移动一格
                for (int j = i + 1; j < size; j++) {
                    nums[j-1] = nums[j];
                }
                i--;
                size--;
            }
        }
        return size;
    }

    public int removeElementWithDoublePoint(int[] nums, int val) {
        int slowIndex = 0;
        for (int fastIndex = 0; fastIndex < nums.length; fastIndex ++) {
            if (nums[fastIndex] != val) {
                nums[slowIndex] = nums[fastIndex];
                slowIndex++;
            }
            fastIndex++;
        }
        return slowIndex;
    }

    class solution {
        /**
         * n*n 螺旋矩阵
         * @param n
         * @return
         */
        public int[][] generateMatrix(int n) {
            // 遵循左闭右开的原则
            // 四个方向循环
            int[][] res = new int[n][];
            int x = 0; // 起始点x
            int y = 0; // 起始点y
            int loop = n/2; // 要循环的次数
            int num = 1;    //  要填充的数值
            while (loop > 0) {
                // 右
                for (int j = y; j < n-y-1; j++) {
                    res[x][j] = num;
                    num++;
                }
                // 下
                for (int i = x ; i < n-x-1; i++ ) {
                    res[i][n-y-1] = num;
                    num++;
                }
                // 左
                for (int j = n-y-1; j > y + 1; j--) {
                    res[n-x-1][j] = num;
                    num++;
                }
                // 上
                for (int i = n-x-1; i > x + 1; i--) {
                    res[i][y] = num;
                    num++;
                }
                x++;
                y++;
            }
            // 中间
            if (n%2 == 1) {
                res[n/2][n/2] = num;
            }
            return res;
        }
    }

}
