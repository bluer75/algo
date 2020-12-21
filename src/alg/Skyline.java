package alg;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city 
 * when viewed from a distance. Now suppose you are given the locations and height of all the buildings, 
 * write a program to output the skyline formed by these buildings collectively.
 * 
 * The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi], 
 * where Li and Ri are the x coordinates of the left and right edge of the ith building, respectively, 
 * and Hi is its height. It is guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. 
 * You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.
 * For instance, the dimensions of all buildings in are recorded as: [ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ].
 * The output is a list of "key points" in the format of [ [x1,y1], [x2, y2], [x3, y3], ... ] that 
 * uniquely defines a skyline. A key point is the left endpoint of a horizontal line segment. 
 * Note that the last key point, where the rightmost building ends, is merely used to mark 
 * the termination of the skyline, and always has zero height. $
 * Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.
 * For instance, the skyline from example above should be represented as:[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].
 * 
 * There must be no consecutive horizontal lines of equal height in the output skyline. 
 * For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; the three lines of 
 * height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]
 */
public class Skyline {

    private static final class Point {
        int x, y;
        boolean open;

        Point(int x, int y, boolean open) {
            this.x = x;
            this.y = y;
            this.open = open;
        }

        @Override
        public String toString() {
            return "[" + x + "," + y + "," + open + "]";
        }
    }

    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> res = new LinkedList<>();
        // converts buildings to list of points indicating start and end of the building
        List<Point> points = Arrays.stream(buildings).flatMap(
                values -> Stream.of(new Point(values[0], values[2], true), new Point(values[1], values[2], false)))
                .sorted(Comparator.comparingInt(p -> p.x)).collect(Collectors.toList());
        // use max heap to store active buildings (heights)
        PriorityQueue<Integer> heights = new PriorityQueue<>(Comparator.reverseOrder());
        Point p = null;
        Point next = null;
        boolean ignore = false;
        for (int i = 0; i < points.size(); i++) {
            if (ignore) {
                ignore = false;
                continue;
            }
            p = points.get(i);
            if (i < points.size() - 1) {
                next = points.get(i + 1);
                // check if this and next point should be ignored/merged as they create a line
                if (p.x == next.x && p.y == next.y && !p.open && next.open) {
                    ignore = true;
                    continue;
                }
                // check if this point should be ignored as next one is higher
                if (p.x == next.x && p.y < next.y && p.open && next.open) {
                    continue;
                }
            } else {
                next = null;
            }
            if (p.open) {
                // start of the building - check if it is hidden
                if (heights.isEmpty() || p.y > heights.peek()) {
                    res.add(Arrays.asList(p.x, p.y));
                }
                heights.add(p.y);
            } else {
                // end of the building
                heights.remove(p.y);
                if (heights.isEmpty()) {
                    // no active buildings and there is no adjacent point
                    if (next == null || (next != null && next.x != p.x)) {
                        res.add(Arrays.asList(p.x, 0));
                    }
                } else if (p.y > heights.peek()) {
                    // it's not hidden and there is no adjacent point
                    if (next == null || (next != null && next.x != p.x)) {
                        res.add(Arrays.asList(p.x, heights.peek()));
                    }
                }
            }
        }
        return res;
    }

    public static void main(String... args) {
        Skyline s = new Skyline();
//        int[][] buildings = { { 2, 9, 10 }, { 3, 7, 15 }, { 5, 12, 12 }, { 15, 20, 10 }, { 19, 24, 8 } };
//        int[][] buildings = { { 0, 2, 3 }, { 2, 5, 3 } };
//        int[][] buildings = { { 2, 9, 10 }, { 9, 12, 15 } };
        int[][] buildings = { { 1, 2, 1 }, { 1, 2, 2 }, { 1, 2, 3 } };
        System.out.println(s.getSkyline(buildings));
    }
}
