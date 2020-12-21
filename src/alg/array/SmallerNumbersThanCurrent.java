package alg.array;

import java.util.Arrays;

/**
 * Given the array nums, for each nums[i] find out how many numbers in the array are smaller than it. 
 * Return the answer in an array.
 * 
 * Example 1:
 * Input: nums = [8,1,2,2,3]
 * Output: [4,0,1,1,3]
 * 
 * Example 2:
 * Input: nums = [6,5,4,8]
 * Output: [2,1,0,3]
 * 
 * Example 3:
 * Input: nums = [7,7,7,7]
 * Output: [0,0,0,0]
 * 
 * Constraints:
 * 2 <= nums.length <= 500
 * 0 <= nums[i] <= 100
 * 
 * Solution is base on counting sort and requires O(n) time and O(max) space.
 */
public class SmallerNumbersThanCurrent {
    public int[] count(int[] nums) {
        if (nums == null) {
            return null;
        }
        // find max
        int max = nums[0];
        for (int n : nums) {
            max = max > n ? max : n;
        }
        // count values
        int[] count = new int[max + 1];
        for (int n : nums) {
            count[n]++;
        }
        // count lower values
        int[] lower = new int[max + 1];
        for (int i = 1; i < count.length; i++) {
            lower[i] = lower[i - 1] + count[i - 1];
        }
        // fill results
        for (int i = 0; i < nums.length; i++) {
            nums[i] = lower[nums[i]];
        }
        return nums;
    }

    public static void main(String... args) {
        System.out.println(Arrays.toString(new SmallerNumbersThanCurrent().count(new int[] { 8, 1, 2, 2, 3 })));
    }
}
