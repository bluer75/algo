package alg.array;

import java.util.Arrays;

/**
 * Given an array of integers sorted in ascending order, find the starting and ending position of a given target value.
 * Your algorithm's runtime complexity must be in the order of O(log n).
 * If the target is not found in the array, return [-1, -1].
 * 
 * Example 1:
 * Input: [5,7,7,8,8,10], target = 8
 * Output: [3,4]
 * 
 * Example 2:
 * Input: [5,7,7,8,8,10], target = 6
 * Output: [-1,-1]
 * 
 * Solution uses modified binary search for finding first left and last right occurrence of target.
 */
public class FindRange {
    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[] { -1, -1 };
        }
        int start = bsl(nums, target);
        int end = bsr(nums, target);
        return new int[] { start, end };
    }

    private int bsl(int[] nums, int t) {
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        while (left < right) {
            mid = left + (right - left) / 2; // take lower bound
            if (nums[mid] < t) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left] == t ? left : -1;
    }

    private int bsr(int[] nums, int t) {
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        while (left < right) {
            mid = left + (right - left + 1) / 2; // take upper bound
            if (nums[mid] > t) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }
        return nums[right] == t ? right : -1;
    }

    public static void main(String... args) {
        System.out.println(Arrays.toString(new FindRange().searchRange(new int[] { 5, 7, 7, 8, 8, 10 }, 8)));
    }
}
