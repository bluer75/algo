package alg.array;

/**
 * Given an array which consists of non-negative integers and an integer m, split the array into m non-empty continuous
 * subarrays. Minimize the largest sum among these m subarrays.
 *
 * DP based solution tries all possible combinations - O(n^2 log m) time and O(nm) space complexity.
 * Binary search based solution searches for optimal value in a range [max value, sum of all values] by verifying if
 * given value is too small or too big and narrowing the range either on left or right side. It provides O(n log sum)
 * time and O(1) space complexity.
 */
public class SplitArrayMinSum {
    /**
     * Top-down with memoization.
     * O(n^2 log m)/O(nm).
     */
    public int splitArrayTopDown(int[] nums, int m) {
        int n = nums.length;
        int[][] memo = new int[n][m + 1]; // [p][k] min for split into k groups from position p
        int[] sums = new int[n + 1]; // prefix sum
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }
        return split(0, m, sums, memo);
    }

    private int split(int pos, int groups, int[] sums, int[][] memo) {
        if (memo[pos][groups] != 0) {
            return memo[pos][groups];
        }
        if (groups == 1) {
            // the last group
            return memo[pos][groups] = sums[sums.length - 1] - sums[pos];
        }
        int n = sums.length - pos - 1, minHere = Integer.MAX_VALUE;
        // try all possible splits
        for (int sumHere = 0, minSumFromHere = 0, size = 1; n - size >= groups - 1; size++) {
            sumHere = sums[pos + size] - sums[pos];
            // split recursively remaining groups
            minSumFromHere = split(pos + size, groups - 1, sums, memo);
            minHere = Math.min(minHere, Math.max(sumHere, minSumFromHere));
            if (sumHere >= minHere) {
                // better or equally good value found already
                break;
            }
        }
        return memo[pos][groups] = minHere;
    }

    /**
     * Bottom-up.
     * O(n^2 log m)/O(nm).
     */
    public int splitArrayBottomUp(int[] nums, int m) {
        int n = nums.length;
        int[][] dp = new int[n][m + 1]; // [p][k] min for split into k groups from position p
        int[] sums = new int[n + 1]; // prefix sum
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }
        // calculate incrementally all possible splits
        for (int groups = 1; groups <= m; groups++) {
            for (int pos = 0; pos < n; pos++) {
                if (groups == 1) {
                    dp[pos][groups] = sums[sums.length - 1] - sums[pos];
                    continue;
                }
                int length = n - pos, minHere = Integer.MAX_VALUE;
                for (int sumHere = 0, minSumFromHere = 0, size = 1; length - size >= groups - 1; size++) {
                    sumHere = sums[pos + size] - sums[pos]; // first group
                    minSumFromHere = dp[pos + size][groups - 1]; // min for remaining groups
                    minHere = Math.min(minHere, Math.max(sumHere, minSumFromHere));
                    if (sumHere >= minHere) {
                        // better or equally good value found already
                        break;
                    }
                }
                dp[pos][groups] = minHere;
            }
        }
        return dp[0][m];
    }

    /**
     * Binary search for correct value fro range max value and sum of all values.
     * O(n log sum)/O(1).
     */
    private int splitArrayBinarySearch(int[] nums, int g) {
        int l = 0, r = 0, m = 0, groups = 0, min = 0;
        for (int num : nums) {
            r += num;
            l = Math.max(l, num);
        }
        while (l < r) {
            m = l + (r - l) / 2;
            groups = split(nums, m);
            if (groups > g) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    private int split(int[] nums, int max) {
        int groups = 0, sum = 0;
        for (int num : nums) {
            if (sum + num > max) {
                sum = 0;
                groups++;
            }
            sum += num;
        }
        return groups + 1;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5};
        int m = 2;
        System.out.println(new SplitArrayMinSum().splitArrayTopDown(nums, m));
        System.out.println(new SplitArrayMinSum().splitArrayBottomUp(nums, m));
        System.out.println(new SplitArrayMinSum().splitArrayBinarySearch(nums, m));
    }
}
