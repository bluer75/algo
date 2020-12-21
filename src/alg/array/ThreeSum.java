package alg.array;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? 
 * Find all unique triplets in the array which gives the sum of zero.
 * The solution set must not contain duplicate triplets.
 * 
 * Example: nums = [-1, 0, 1, 2, -1, -4],
 * 
 * A solution set is:
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 * 
 * We sort input array first and for each element we find TwoSum within remaining elements.
 * Time complexity is O(n^2).
 */

public class ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        List<List<Integer>> triplets = new LinkedList<>();
        // has to be length - 2 to run TwoSum for last element
        for (int first = 0; first < nums.length - 2; first++) {
            // 0, 0, 0 is allowed otherwise there should be no duplicates
            if (first == 0 || (first > 0 && nums[first] != nums[first - 1])) {
                // use TwoSum to find missing values
                // as the array is sorted we can check remaining values from both sides
                int second = first + 1; // next element after first
                int third = nums.length - 1; // last element
                int wantedSum = 0 - nums[first]; // value we need to find
                int currentSum = 0;
                while (second < third) {
                    currentSum = nums[second] + nums[third];
                    if (currentSum == wantedSum) {
                        // we found new triplet
                        triplets.add(Arrays.asList(nums[first], nums[second], nums[third]));
                        // skip duplicate values on both sides
                        while (second < third && nums[second] == nums[second + 1]) {
                            second++;
                        }
                        while (second < third && nums[third] == nums[third - 1]) {
                            third--;
                        }
                        second++;
                        third--;
                    } else if (currentSum < wantedSum) {
                        // current sum is too low
                        second++;
                    } else {
                        // current sum is too high
                        third--;
                    }
                }
            }
        }
        return triplets;
    }

    public static void main(String... args) {
        int[] nums = { 0, 0, 0 };
        System.out.println(new ThreeSum().threeSum(nums));
    }
}
