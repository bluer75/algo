package alg.array;

import java.util.Arrays;

/**
 * We have a list of points on the plane.  Find the K closest points to the origin (0, 0).
 * The distance between two points on a plane is the Euclidean distance.
 * You may return the answer in any order.
 *
 * Input: points = [[3,3],[5,-1],[-2,4]], K = 2
 * Output: [[3,3],[-2,4]] (or [[-2,4],[3,3]])
 *
 * Simple solution is based on sorting or min heap and takes O(n log n).
 * Quickselect on average takes O(n) and worst case is O(n^2).
 */
public class KClosestPoints {

    /**
     * Use min heap to extract K first elements.
     * This takes O(n log n). The same as using sorting.
     */
    public int[][] kClosestHeap(int[][] points, int K) {
        if (points == null || K > points.length) {
            return null;
        }
        int[][] res = new int[K][];
        for (int i = points.length / 2; i >= 0; i--) {
            heapify(points, i, points.length);
        }
        for (int i = 0; i < K; i++) {
            res[i] = points[0];
            swap(points, 0, points.length - i - 1);
            heapify(points, 0, points.length - i - 1);
        }
        return res;
    }

    private int distance(int[] p) {
        return p[0] * p[0] + p[1] * p[1];
    }

    private void heapify(int[][] points, int i, int s) {
        int left = i * 2 + 1;
        int right = i * 2 + 2;
        int min = i;
        if (left < s && distance(points[left]) < distance(points[min])) {
            min = left;
        }
        if (right < s && distance(points[right]) < distance(points[min])) {
            min = right;
        }
        if (min != i) {
            swap(points, min, i);
            heapify(points, min, s);
        }
    }

    private void swap(int[][] points, int i, int j) {
        int[] point = points[i];
        points[i] = points[j];
        points[j] = point;
    }

    /**
     * Better option is to  use partitioning that would organize array around random value.
     * We keep doing it as long as we reach K elements.
     * It is like Quickselect algorithm to find k-th element in unsorted array.
     * Average time would be O(n) and worst case O(n^2).
     */
    public int[][] kClosestSelect(int[][] points, int k) {
        if (points == null || k > points.length) {
            return null;
        }
        int lo = 0, hi = points.length - 1, p = 0;
        while (true) {
            p = partition(points, lo, hi);
            if (p + 1 < k) {
                lo = p + 1;
            } else if (p + 1 > k) {
                hi = p - 1;
            } else {
                break;
            }
        }
        int[][] res = new int[k][];
        for (int i = 0; i < k; i++) {
            res[i] = points[i];
        }
        return res;
    }

    private int partition(int[][] points, int lo, int hi) {
        int d = distance(points[hi]);
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (distance(points[j]) <= d) {
                swap(points, j, i++);
            }
        }
        swap(points, i, hi);
        return i;
    }

    public static void main(String... args) {
//        int[][] points = { { 3, 3 }, { 5, -1 }, { -2, 4 } };
        int[][] points = {{1, 3}, {-2, 2}};
        int k = points.length - 1;
        System.out.println(Arrays.deepToString(new KClosestPoints().kClosestHeap(points, k)));
        System.out.println(Arrays.deepToString(new KClosestPoints().kClosestSelect(points, k)));
    }
}
