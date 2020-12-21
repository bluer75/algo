package alg.array;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A zero-indexed array A of length N contains all integers from 0 to N-1. 
 * Find and return the longest length of set S, where S[i] = {A[i], A[A[i]], A[A[A[i]]], ... } subjected to the rule below.
 * Suppose the first element in S starts with the selection of element A[i] of index = i, 
 * the next element in S should be A[A[i]], and then A[A[A[i]]]… 
 * By that analogy, we stop adding right before a duplicate element occurs in S.
 * 
 * Example:
 * 
 * Input: A = [5,4,0,3,1,6,2]
 * Output: 4
 * Explanation: 
 * A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.
 * 
 * One of the longest S[K]:
 * S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}
 *  
 * Note:
 * N is an integer within the range [1, 20,000].
 * The elements of A are all distinct.
 * Each element of A is an integer within the range [0, N-1].
 * 
 * Naive solution is to calculate recursively the length for each element in the array.
 * This takes O(n^2) time and O(n) space for stack.
 * 
 * Optimal solution uses just one pass where for each element it follows the sequence till it finds first
 * duplicate. Each sequence is either full cycle or prefix and cycle. For each element in prefix we can 
 * determine its final length and copy that value to corresponding slot. For each element in cycle we can 
 * set its final length as size of the cycle. When discovering each element we check whether it has been 
 * processed already (we know its final length) and we can reuse it to determine the length of current index.
 * This handles also cases where there are duplicates in input array.
 * This takes O(n) time and space. 
 * 
 * Further optimization.
 * Since all input elements are distinct, we can also observe that no two elements at indices i and j will 
 * lead to same index k. Also, because of the same reasoning, no element outside any cycle could lead 
 * to an element inside the cycle. This mean we don't have prefixes as described above just full cycles.
 * We don't need the values array, just visited array or reuse input array and store there -1 indicating 
 * item was part of previous cycle. For each cycle we calculate its length and compare with max so far.
 */
public class ArrayNesting {

    /**
     * Brute force solution. For each element find the length of nested array.
     */
    public int findMax(int[] nums) {

        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, getLength(nums, i, new HashSet<>()));
        }
        return max;
    }

    private int getLength(int[] a, int i, Set<Integer> visited) {

        if (visited.contains(i)) {
            return visited.size();
        }
        visited.add(i);
        return getLength(a, a[i], visited);
    }

    // optimal solution
    public int findMaxOptimal(int[] nums) {

        int max = 0;
        int[] values = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, getLength(nums, i, values));
        }
        return max;
    }

    private int getLength(int[] nums, int startIndex, int[] values) {

        if (values[startIndex] > 0) {
            // length already set
            return values[startIndex];
        }
        // element -> index on the path (zero based)
        Map<Integer, Integer> path = new LinkedHashMap<>();
        int currentIndex = startIndex;
        while (true) {
            if (values[currentIndex] != 0) {
                // length for currentIndex already set - use it to calculate the length for startIndex
                int lengthForStartIndex = values[currentIndex] + path.size();
                // set length for each element on the path using its index
                for (Map.Entry<Integer, Integer> entry : path.entrySet()) {
                    values[entry.getKey()] = lengthForStartIndex - entry.getValue();
                }
                return lengthForStartIndex;
            } else if (path.containsKey(currentIndex)) {
                // found duplicate/cycle
                int cycleStartIndex = path.get(currentIndex);
                int cycleSize = path.size() - cycleStartIndex;
                // length for startIndex is the length of the path
                int lengthForStartIndex = path.size();
                // set length for each element on the path using its index
                for (Map.Entry<Integer, Integer> entry : path.entrySet()) {
                    if (entry.getValue() < cycleStartIndex) {
                        // prefix - set length using length of startIndex and index of element
                        values[entry.getKey()] = lengthForStartIndex - entry.getValue();
                    } else {
                        // cycle - set length as size of the cycle
                        values[entry.getKey()] = cycleSize;
                    }
                }
                return lengthForStartIndex;
            } else {
                // add current item to the path and follow the link
                path.put(currentIndex, path.size());
                currentIndex = nums[currentIndex];
            }
        }
    }

    public int arrayNestingTest(int[] nums) {
        HashSet<Integer> hs = new HashSet<Integer>();
        int mc = -1, count = 0;

        for (int i = 0; i < nums.length; i++) {
            if (!hs.contains(i)) {
                int index = i;
                while (!hs.contains(index)) {
                    hs.add(index);
                    index = nums[index];
                    count++;
                }
                mc = Math.max(mc, count);
                count = 0;
            }
        }
        return mc;
    }

    public static void main(String... args) {

        int[] nums = { 5, 4, 0, 3, 1, 6, 2, 0 };
//        System.out.println(new ArrayNesting().findMax(nums));
//        System.out.println(new ArrayNesting().findMaxOptimal(nums));
        System.out.println(new ArrayNesting().arrayNestingTest(nums));
    }
}
