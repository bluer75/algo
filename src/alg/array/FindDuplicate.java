package alg.array;

/**
 * Given an array of integers containing n + 1 integers, where each integer is in the range [1, n] inclusive, there is
 * one repeated number, return this repeated number.
 *
 * Naive solution would be to sort the array any find first duplicate - O(n log n).
 * Some other solution with O(n log n) time use binary search and bits counting.
 * Optimal solution requires O(n) time and uses either:
 * - marking seen values in input array
 * - cycle finding
 * - following number -> index
 */
public class FindDuplicate {
    /**
     * Uses number -> index mapping and marks visited indices/numbers as negative.
     * Time: O(n).
     * Space: O(1) changes input array (can be restored).
     */
    private int findDuplicateNegativeMarking(int[] nums) {
        for (int v = 0, i = 0; i < nums.length; i++) {
            v = Math.abs(nums[i]);
            if (nums[v] < 0) {
                return v;
            }
            nums[v] = -nums[v];
        }
        return -1;
    }

    /**
     * Follows number -> index mapping and finds a cycle.
     * Time: O(n).
     * Space: O(1).
     */
    private int findDuplicateCycleDetection(int[] nums) {
        int slow = 0;
        int fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    /**
     * Uses index 0 as unmapped and places values into correct index following number -> index mapping
     * Time: O(n).
     * Space: O(1) changes input array (cannot be restored).
     */
    private int findDuplicateSwapValues(int[] nums) {
        int next = 0;
        while (nums[0] != nums[nums[0]]) {
            next = nums[nums[0]];
            nums[nums[0]] = nums[0];
            nums[0] = next;
        }
        return nums[0];
    }

    /**
     * Uses binary search to find minimum value such as the number of values less than or equal to it is greater than
     * that value.
     * Time: O(n log n).
     * Space : O(1).
     */
    private int findDuplicateBinarySearch(int[] nums) {
        int l = 1, r = nums.length - 1, m = 0, count = 0;
        while (l < r) {
            m = l + (r - l) / 2;
            count = 0;
            for (int num : nums) {
                if (num <= m) {
                    count++;
                }
            }
            if (count <= m) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    /**
     * Counts bits in input array and compares it with bits in array with 1..n values.
     * Time: O(n log n).
     * Space: O(1).
     */
    private int findDuplicateBitsCounting(int[] nums) {
        int[] bits = new int[31];
        for (int num : nums) {
            for (int p = 0; p < 32 && num > 0; p++, num >>= 1) {
                bits[p] += num & 0b1;
            }
        }
        for (int i = 1; i < nums.length; i++) {
            for (int v = i, p = 0; p < 32 && v > 0; p++, v >>= 1) {
                bits[p] -= v & 0b1;
            }
        }
        int duplicate = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] > 0) {
                duplicate |= 1 << i;
            }
        }
        return duplicate;
    }

    public static void main(String[] args) {
        FindDuplicate fd = new FindDuplicate();
        System.out.println(fd.findDuplicateNegativeMarking(new int[]{3, 1, 3, 4, 2}));
        System.out.println(fd.findDuplicateCycleDetection(new int[]{3, 1, 3, 4, 2}));
        System.out.println(fd.findDuplicateSwapValues(new int[]{3, 1, 3, 4, 2}));
        System.out.println(fd.findDuplicateBinarySearch(new int[]{3, 1, 3, 4, 2}));
        System.out.println(fd.findDuplicateBitsCounting(new int[]{3, 1, 3, 4, 2}));
    }
}
