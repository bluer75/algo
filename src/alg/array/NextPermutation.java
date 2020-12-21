package alg.array;

import java.util.Arrays;

/**
 * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
 * If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
 * The replacement must be in-place and use only constant extra memory.
 * Examples:
 * 1,2,3 -> 1,3,2 -> 2,1,3 -> 2,3,1 -> 3,1,2 -> 3,2,1 -> 1,2,3
 * 
 * Solution requires one pass and processes array from right to left.
 * There is always non-decreasing suffix at the end (or just last element). It's the point where it changes from  
 * non-decreasing to non-increasing sequence and that is the point where we need to do the change.
 * Suffix needs to be reversed and selected element needs to be swapped with first greater element in suffix.
 * 
 * For 2, 1, 4, 3 
 * non-decreasing suffix is 4, 3
 * changing point is pair: 1 , 4
 * reverse suffix: 2, 1, 3, 4
 * swap 1 with first greater element: 2, 3, 1, 4
 *  
 * It takes O(n) time and O(1) space.
 */
public class NextPermutation {
    public void nextPermutation(int[] nums) {
        int i = nums.length - 1;
        while (i > 0 && nums[i - 1] >= nums[i]) {
            i--;
        }
        if (i > 0) {
            reverse(nums, i);
            int j = i - 1;
            while (i < nums.length && nums[j] >= nums[i]) {
                i++;
            }
            swap(nums, i, j);
        } else {
            reverse(nums, 0);
        }
    }

    private void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private void reverse(int[] a, int from) {
        int l = from;
        int r = a.length - 1;
        while (l < r) {
            swap(a, l++, r--);
        }
    }

    public static void main(String... args) {
        int[] nums = new int[] { 1, 2, 3 };
        System.out.println(Arrays.toString(nums));
        new NextPermutation().nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
        new NextPermutation().nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
        new NextPermutation().nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
        new NextPermutation().nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
        new NextPermutation().nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
        new NextPermutation().nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
    }
}
