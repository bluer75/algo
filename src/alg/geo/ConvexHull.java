package alg.geo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * You are given an array trees where trees[i] = [xi, yi] represents the location of a tree in the garden.
 * Find the minimum length fence to enclose all trees.
 *
 * Return the coordinates of trees that are exactly located on the fence perimeter.
 *
 * Solution is based on Jarvis algorithm and takes O(nm) time, where n is number of trees and m is size of the fence.
 * Optimal solution is O(n log n)
 */
public class ConvexHull {
    public int[][] fence(int[][] trees) {
        int n = trees.length;
        if (n < 4) {
            return trees;
        }
        Set<Integer> hull = new HashSet<>();
        // find the leftmost point
        int l = 0;
        for (int i = 1; i < n; i++) {
            if (trees[i][0] < trees[l][0] || (trees[i][0] == trees[l][0] && trees[i][1] < trees[l][1])) {
                l = i;
            }
        }
        // keep moving until reach the start point again
        int p = l, q;
        do {
            // add current point to hull
            hull.add(p);
            // search for a point q such that orientation(p, q, x) creates right turn all points x
            // if any point i is more on the left than q, then replace q with i
            q = (p + 1) % n;
            for (int i = 0; i < n; i++) {
                if (orientation(trees[p], trees[i], trees[q]) < 0) {
                    // i is more on the left than q
                    q = i;
                }
            }
            // handle cooliner points with left most point q
            for (int i = 0; i < n; i++) {
                if (i != p && i != q && orientation(trees[p], trees[i], trees[q]) == 0 && inBetween(trees[p], trees[i],
                    trees[q])) {
                    hull.add(i);
                }
            }
            // q is the most left with respect to p
            p = q;
        } while (p != l);
        return hull.stream().map(i -> trees[i]).toArray(int[][]::new);
    }

    // coolinear -> 0, left turn < 0, right rurn > 0
    public static int orientation(int[] p, int[] q, int[] r) {
        return (q[1] - p[1]) * (r[0] - q[0]) - (q[0] - p[0]) * (r[1] - q[1]);
    }

    // i is between p and q
    public boolean inBetween(int[] p, int[] i, int[] q) {
        boolean a = i[0] >= p[0] && i[0] <= q[0] || i[0] <= p[0] && i[0] >= q[0];
        boolean b = i[1] >= p[1] && i[1] <= q[1] || i[1] <= p[1] && i[1] >= q[1];
        return a && b;
    }

    public static void main(String[] args) {
        System.out.println(
            Arrays.deepToString(new ConvexHull().fence(new int[][]{{1, 1}, {2, 2}, {2, 0}, {2, 4}, {3, 3}, {4, 2}})));
    }
}
