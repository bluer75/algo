package alg.array;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an integer array nums and an integer k, return the maximum length of a subarray that sums to k.
 * If there isn't one, return 0 instead.
 *
 * Example:
 * For nums = [1,-1,5,-2,3], k = 3,
 * Output: 4, subarray [1, -1, 5, -2] sums to 3 and is the longest.
 *
 * Solution is based on prefix sum and map storing min index for given sum. For each index we calculate sum here and
 * check if there is sum - k in the map.
 *
 * It requires O(n) time and space.
 */
public class MaxSubArrayWithSumK {
    public int maxSubArrayLen(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // to cover the case when given sum is k
        int max = 0;
        for (int sum = 0, i = 0; i < nums.length; i++) {
            sum += nums[i];
            max = Math.max(max, i - map.getOrDefault(sum - k, i));
            map.putIfAbsent(sum, i);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new MaxSubArrayWithSumK().maxSubArrayLen(new int[]{1, -1, 5, -2, 3}, 3));
    }
}
