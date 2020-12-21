package alg;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Given a stream of numbers, print out the median of the list so far on each new element.
 * Median of an even-numbered list is the average of the two middle numbers.
 */
public class MedianFinder {
    /**
     * Uses max and min heap and maintains their sizes so they are either the same or they differ at most by 1.
     * Each one is storing half of elements and median can be always calculated based on top value(s).
     */
    public static double[] find(int[] ints) {
        double[] res = new double[ints.length];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.naturalOrder());
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        // add first value to maxHeap - it's the first median
        maxHeap.add(ints[0]);
        res[0] = ints[0];
        for (int i = 1; i < ints.length; i++) {
            if (maxHeap.peek() >= ints[i]) {
                // i-th element goes to max heap
                maxHeap.add(ints[i]);
            } else {
                // i-th element goes to min heap
                minHeap.add(ints[i]);
            }
            // check if heaps (sizes) are balanced after adding new element
            int diff = maxHeap.size() - minHeap.size();
            if (diff == 1) {
                // uneven number of elements - take top value from maxHeap
                res[i] = maxHeap.peek();
            } else if (diff == -1) {
                // uneven number of elements - take top value from minHeap
                res[i] = minHeap.peek();
            } else {
                if (diff == 2) {
                    // transfer top element from max to min heap
                    minHeap.add(maxHeap.remove());
                } else if (diff == -2) {
                    // transfer top element from min to max heap
                    maxHeap.add(minHeap.remove());
                }
                // even number of elements - take average from top values on both heaps
                res[i] = (maxHeap.peek() + minHeap.peek()) / 2d;
            }
        }
        return res;
    }

    public static void main(String... args) {
        int[] ints = { 2, 1, 5, 7, 2, 0, 5 };
        System.out.println(Arrays.toString(find(ints)));
    }
}
