package alg.array;

/**
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * Find the minimum element.
 * You may assume no duplicate exists in the array.
 * Example:
 * Input: [3,4,5,1,2]
 * Output: 1
 */
public class RotatedSortedArrayMin {

    /**
     * Use binary search to find beginning of the array.
     * Compare mid with low and high value and follow part where the array should start.
     * Complexity is determined by binary search -> O(log n).
     */
    public int findMin(int[] nums) {
        if (nums == null || nums[0] < nums[nums.length - 1]) {
            // not rotated
            return nums[0];
        }
        int lo = 0;
        int hi = nums.length - 1;
        int mid = 0;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            if (nums[lo] > nums[mid]) {
                lo += 1;
                hi = mid;
            } else if (nums[mid] > nums[hi]) {
                lo = mid + 1;
            } else {
                // special case where we don't know which side we need to check
                // either check both sides (recursively) or reduce higher bound by one
                hi--;
            }
        }
        return nums[lo];
    }

    public static void main(String... args) {
        RotatedSortedArrayMin rsa = new RotatedSortedArrayMin();
        int[] a = { 3, 3, 1, 3 };
        System.out.println(rsa.findMin(a));
    }
}