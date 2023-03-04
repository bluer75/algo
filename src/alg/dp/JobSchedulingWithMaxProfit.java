package alg.dp;

import java.util.*;
import java.util.stream.IntStream;

/**
 * We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i], obtaining a profit of
 * profit[i]. Return the maximum profit you can take if no two jobs overlapping.
 * *
 * Optimal solution is based on DP and it takes O(nlogn) time and O(n) space where n is the number of intervals.
 * We need to process each start and stop event in sorted order. For start event we just keep store max profit at that
 * point and for end event we calculate profit as maximum of max profit so far (ignore current job) and profit from
 * current job plus max profit at the time it started.
 *
 * dp[i] = Math.max(maxProfitSoFar, profit[i] + dp[startTime[i]];
 *
 * https://leetcode.com/problems/maximum-profit-in-job-scheduling/
 */
public class JobSchedulingWithMaxProfit {

    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int max = 0;
        int n = profit.length;
        // sort by start time
        Queue<Integer> opening = new PriorityQueue<>(Comparator.comparingInt(i -> startTime[i]));
        // sort by end time
        Queue<Integer> closing = new PriorityQueue<>(Comparator.comparingInt(i -> endTime[i]));
        IntStream.range(0, n).forEach(opening::offer);
        IntStream.range(0, n).forEach(closing::offer);
        Map<Integer, Integer> dp = new HashMap<>();
        int id;
        // process all events
        while (!closing.isEmpty()) {
            // for equal start and stop, process stop before start
            while (!opening.isEmpty() && startTime[opening.peek()] < endTime[closing.peek()]) {
                // for start event set max profit so far
                id = opening.poll();
                dp.putIfAbsent(startTime[id], max);
            }
            // for end event calculate max profit here (take or ignore current job/profit))
            id = closing.poll();
            max = Math.max(max, profit[id] + dp.get(startTime[id]));
            dp.put(endTime[id], max);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] startTime = {1, 2, 3, 3};
        int[] endTime = {3, 4, 5, 6};
        int[] profit = {50, 10, 40, 70};
        System.out.println(new JobSchedulingWithMaxProfit().jobScheduling(startTime, endTime, profit));
    }
}
