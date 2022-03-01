package alg.interval;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Given a data stream input of non-negative integers a1, a2, ..., an, summarize the numbers seen so far as a list of
 * disjoint intervals.
 * Example for sequence: 3,5,2,6,3,4 there is one interval [2,6] covering all values
 *
 * Solution is based on TreeMap implementation. Tree uses min value of interval as the key and value is the interval.
 * For each value we find floor and ceiling value and use them to either:
 * - ignore the value if it is already covered by existing interval
 * - create new interval if value is new
 * - update existing interval if value extends it from left or right side
 * - merge two existing intervals if value fills the gap between them
 */
public class SummaryRanges {
    private TreeMap<Integer, int[]> tree;
    // used to deal with null values for floor and ceiling
    private int[] MIN_RANGE = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
    private int[] MAX_RANGE = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};

    public SummaryRanges() {
        tree = new TreeMap<>();
    }

    public void addNum(int val) {
        int[] floor = getFloor(val);
        int[] ceiling = getCeiling(val);
        if (floor[1] >= val || ceiling[0] == val) { // value already exists
            return;
        }
        if (floor[1] == val - 1 && ceiling[0] == val + 1) { // merge intervals
            floor[1] = ceiling[1];
            tree.remove(ceiling[0]);
            return;
        }
        if (floor[1] == val - 1) { // update existing interval with new maximum
            floor[1] = val;
            return;
        }
        if (ceiling[0] == val + 1) { // replace existing interval with new minimum
            tree.remove(ceiling[0]);
            tree.put(val, new int[]{val, ceiling[1]});
            return;
        }
        tree.put(val, new int[]{val, val}); // add new interval
    }

    public int[][] getIntervals() {
        return tree.values().toArray(new int[tree.size()][2]);
    }

    private int[] getFloor(int val) {
        Map.Entry<Integer, int[]> entry = tree.floorEntry(val);
        return entry == null ? MIN_RANGE : entry.getValue();
    }

    private int[] getCeiling(int val) {
        Map.Entry<Integer, int[]> entry = tree.ceilingEntry(val);
        return entry == null ? MAX_RANGE : entry.getValue();
    }

    public static void main(String[] args) {
        SummaryRanges sr = new SummaryRanges();
        sr.addNum(3); // creates new interval [3,3]
        sr.addNum(5); // creates new interval [5,5]
        sr.addNum(2); // changes [3,3] to [2,3]
        sr.addNum(6); // changes [5,5] to [5,6]
        sr.addNum(3); // ignored
        System.out.println(Arrays.deepToString(sr.getIntervals()));
        sr.addNum(4); // merges [2,3] and [5,6] to [2,6]
        System.out.println(Arrays.deepToString(sr.getIntervals()));
    }
}
