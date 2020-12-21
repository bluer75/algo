package alg.array;

/**
 * A peak element is an element that is greater than its neighbors.
 * Given an input array nums, where nums[i] ≠ nums[i+1], find a peak element and return its index.
 * The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.
 * You may imagine that nums[-1] = nums[n] = -∞.
 * 
 * Input: nums = [1,2,3,1]
 * Output: 2
 * 
 * Solution is based on binary search and runs in O(log n).
 * Pay attention to edge cases where there is just one or two elements.
 */
public class PeakFinder {

    public int findPeakElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        return find(nums, 0, nums.length - 1);
    }

    private int find(int[] nums, int lo, int hi) {
        // handle just one element
        if (lo == hi) {
            return lo;
        }
        int m = lo + (hi - lo) / 2;
        // this check needs to be done first
        // m + 1 is safe here as we know we have more than one element and m will be evaluated to 0 if
        // there are just 2 elements (it is rounded down)
        if (nums[m] < nums[m + 1]) {
            return find(nums, m + 1, hi);
        }
        // needs to be m, not m - 1, in case m is a peak
        return find(nums, lo, m);
    }

    public static void main(String... args) {
        int[] a = { 1, 2, 1, 3, 5, 6, 4 };
        System.out.println(new PeakFinder().findPeakElement(a));
    }
}
