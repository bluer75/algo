package alg.interval;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a collection of intervals, merge all overlapping intervals.
 * Example:
 * 
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
 * 
 * Intervals are sorted based on start value and processed one by one - we calculate max value for open/close.
 * This takes O(n log n), where n i number of intervals.
 */
public class MergeIntervals {
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][];
        }
        List<int[]> res = new LinkedList<>();
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int minOpen = intervals[0][0];
        int maxClose = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= maxClose) {
                maxClose = Math.max(maxClose, intervals[i][1]);
            } else {
                res.add(new int[] { minOpen, maxClose });
                minOpen = intervals[i][0];
                maxClose = intervals[i][1];
            }
        }
        res.add(new int[] { minOpen, maxClose });
        return res.toArray(new int[res.size()][]);
    }

    public static void main(String... args) {
        System.out.println(Arrays
                .deepToString(new MergeIntervals().merge(new int[][] { { 1, 3 }, { 2, 6 }, { 8, 10 }, { 15, 18 } })));
    }
}
