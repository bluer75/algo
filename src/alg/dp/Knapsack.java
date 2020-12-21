package alg.dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * For given set of elements with size and value, 
 * find maximum value subset of elements with sum of sizes not greater than given limit.
 */
public class Knapsack {

    /**
     * Naive brute-force implementation.
     * Generate all possible subsets of elements, select ones with total size below the limit and 
     * find the one with max value.
     * Running time - O(2^n).
     */
    static int[] find(int[] sizes, int[] values, int limit) {
        int[][] subsets = generateSubsets(sizes);
        // create Map size -> value
        Map<Integer, Integer> sizeToValue = getSizeToValueMap(sizes, values);
        int max = 0;
        int[] maxSubset = new int[0];
        for (int[] subset : subsets) {
            // check limit
            if (Arrays.stream(subset).sum() <= limit) {
                // find max value subset
                int value = getValue(subset, sizeToValue);
                if (value > max) {
                    maxSubset = subset;
                    max = value;
                }
            }
        }
        return maxSubset;
    }

    private static Map<Integer, Integer> getSizeToValueMap(int[] sizes, int[] values) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < sizes.length; i++) {
            map.put(sizes[i], values[i]);
        }
        return map;
    }

    private static int getValue(int[] subset, Map<Integer, Integer> sizeToValue) {
        int value = 0;
        for (int v : subset) {
            value += sizeToValue.get(v);
        }
        return value;
    }

    /**
     * Generates iteratively all subsets.
     * All possible subsets of n elements are like all possible binary numbers of size n bits.
     * For n = 2 -> 2^2 -> 4 - 00, 01, 10, 11
     */
    private static int[][] generateSubsets(int[] elements) {
        int numOfSubsets = 1 << elements.length;
        int[][] result = new int[numOfSubsets][];
        // iterate through values from 0 to 2^n - 1
        for (int v = 0; v < numOfSubsets; v++) {
            LinkedList<Integer> subset = new LinkedList<>();
            for (int i = 0; i < elements.length; i++) {
                // if bit at index i is set in v add element[i] to subset
                if ((v & (1 << i)) > 0) {
                    subset.add(elements[i]);
                }
            }
            result[v] = subset.stream().mapToInt(i -> i).toArray();
        }
        return result;
    }

    /**
     * DP - naive recursive implementation.
     * There are two cases for every element:
     * - it is included in the optimal subset
     * - it is not included in the optimal set
     * The maximum value can be found as maximum of:
     * - element is included -  value of that element plus maximum value obtained by n-1 elements and limit 
     * minus size of current element
     * - element is excluded -  maximum value obtained by n-1 elements and limit
     * The same subproblems are evaluated many times.
     * Running time - O(2^n).
     */
    static int findDP(int sizes[], int[] values, int limit) {
        // start from last element and given limit
        return findDP(sizes, values, limit, sizes.length - 1);
    }

    private static int findDP(int[] sizes, int[] values, int limit, int i) {
        if (i < 0 || limit == 0) {
            // base case - no elements left or whole limit is used
            return 0;
        }
        // if current size is over limit the only option is to exclude current element
        if (sizes[i] > limit) {
            return findDP(sizes, values, limit, i - 1);
        }
        // maximum of two options: included and excluded
        return Math.max(values[i] + findDP(sizes, values, limit - sizes[i], i - 1),
                findDP(sizes, values, limit, i - 1));
    }

    /**
     * DP bottom-up approach.
     * It optimizes the fact that naive recursive approach evaluates several times the same 
     * combination of i (index of element) and limit left.
     * It builds res table where res[i][l] stores result for computing max value for element 
     * at index i and limit l (in recursive approach).
     * Time complexity is reduced to O(n * limit). It is not polynomial-time (polynomial in length of input). 
     */
    static int findDPBottomUp(int sizes[], int[] values, int limit) {
        int[][] res = new int[sizes.length + 1][limit + 1]; // initialized with 0, size + 1 and limit + 1 due to the
                                                            // fact we use i - 1 and l - 1 when filling the table
        for (int i = 1; i <= sizes.length; i++) {
            for (int l = 1; l <= limit; l++) {
                // if given size is over limit the only option is to exclude current element
                if (sizes[i - 1] > l) {
                    res[i][l] = res[i - 1][l];
                } else
                // maximum of two options: included and excluded
                {
                    res[i][l] = Math.max(values[i - 1] + res[i - 1][l - sizes[i - 1]], res[i - 1][l]);
                }
            }
        }
        return res[sizes.length][limit];
    }

    public static void main(String... args) {
        int values[] = new int[] { 60, 100, 120 };
        int sizes[] = new int[] { 10, 20, 30 };
        int limit = 50;

        System.out.println(Arrays.toString(find(sizes, values, limit)));
        System.out.println(findDP(sizes, values, limit));
        System.out.println(findDPBottomUp(sizes, values, limit));
    }
}
