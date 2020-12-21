package alg.array;

import java.util.HashMap;
import java.util.Map;

/**
 * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
 * Example 1
 * Input: [[1,1],[2,2],[3,3]]
 * Output: 3
 * ^
 * |
 * |        o
 * |     o
 * |  o
 * +------------->
 * 0 1 2 3 4
 * 
 * Example 2:
 * Input: [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
 * Output: 4
 * ^
 * |
 * |  o
 * |     o        o
 * |        o
 * |  o        o
 * +------------------->
 * 0 1 2 3 4 5 6
 * 
 * For each point p we calculate the slope of the line going from p to any other point q.
 * Equal values of slope mean points are on the same line.
 * 
 * This requires O(n^2) time and O(n) space.
 */
public class MaxPointsOnLine {

    public int maxPoints(int[][] points) {
        if (points.length <= 2) {
            return points.length;
        }
        Map<String, Integer> slopes;
        int max = 0;
        for (int p = 0; p < points.length - 1; p++) {
            slopes = new HashMap<>();
            int samePoints = 0;
            int currentMax = 0;
            for (int q = p + 1; q < points.length; q++) {
                if (samePoint(points, p, q)) {
                    samePoints++;
                } else {
                    slopes.merge(slope(points, p, q), 1, Integer::sum);
                }
            }
            currentMax = slopes.values().stream().max(Integer::compare).orElse(0);
            max = Math.max(max, currentMax + samePoints + 1); // plus one because first entry/samePoint is for two points
        }
        return max;
    }

    private boolean samePoint(int[][] points, int p, int q) {
        return points[p][0] == points[q][0] && points[p][1] == points[q][1];
    }

    private String slope(int[][] points, int p, int q) {
        long deltax = points[p][0] - points[q][0];
        long deltay = points[p][1] - points[q][1];
        if (deltax == 0) {
            // vertical line
            return "NaN";
        }
        if (deltay == 0) {
            // horizontal line
            return "0";
        }
        long gcd = gcd(deltax, deltay);
        return deltay / gcd + "/" + deltax / gcd;
    }

    private long gcd(long a, long b) {
        return a == 0 ? b : gcd(b % a, a);
    }

    public static void main(String... args) {
        //int[][] points = { { 1, 1 }, { 3, 2 }, { 5, 3 }, { 4, 1 }, { 2, 3 }, { 1, 4 } };
        //int[][] points = { { 1, 1 }, { 1, 1 }, { 2, 3 } };
        int[][] points = { { 2, 3 }, { 3, 3 }, { -5, 3 } };
        System.out.println(new MaxPointsOnLine().maxPoints(points));
    }
}