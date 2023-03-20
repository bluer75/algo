package alg.interval;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Given a collection of intervals, find the minimum number of intervals you need to remove to 
 * make the rest of the intervals non-overlapping.
 * 
 * Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.
 * 
 * Example:
 * 
 * Input: [[1,2],[2,3],[3,4],[1,3]]
 * Output: 1
 * Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
 * 
 * Intervals are sorted based on end value and processed one by one - we count how many intervals overlap.
 * This is greedy approach, While iterating intervals, we select end value for each pair.
 * if there is no overlapping between the previous interval and the current interval, there is no need to remove any interval. 
 * We just set new based on current one. In case there is an overlap end is not changed.
 * 
 * This takes O(n log n), where n i number of intervals.
 */
public class MakeNonOverlappingIntervals {
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length < 2) {
            return 0;
        }
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
        int count = intervals.length;
        int end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= end) {
                // no overlap - set end of current as new end
                end = intervals[i][1];
                count--;
            }
        }
        return count - 1;
    }

    public static void main(String... args) {
        System.out.println(new MakeNonOverlappingIntervals()
                .eraseOverlapIntervals(new int[][] { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 3 } }));
    }
}
