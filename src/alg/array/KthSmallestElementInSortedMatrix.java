package alg.array;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Given a n x n matrix where each of the rows and columns are sorted in ascending order, 
 * find the k-th smallest element in the matrix.
 * Note that it is the k-th smallest element in the sorted order, not the k-th distinct element.
 * 
 * Example:
 * 
 * matrix = [
 *    [ 1,  5,  9],
 *    [10, 11, 13],
 *    [12, 13, 15]
 * ],
 * k = 8,
 * 
 * return 13.
 * 
 * Solution is based on min heap. We add first column and follow smallest elements in columns from there.
 * It takes O(x k log x) time, where x = min(k, n)
 */
public class KthSmallestElementInSortedMatrix {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        Queue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        int maxRow = Math.min(n, k);
        for (int row = 0; row < maxRow; row++) {
            minHeap.offer(new int[] { row, 0, matrix[row][0] });
        }
        int[] data = null;
        while (k-- > 0) {
            data = minHeap.poll();
            if (data[1] < n - 1) {
                minHeap.offer(new int[] { data[0], data[1] + 1, matrix[data[0]][data[1] + 1] });
            }
        }
        return data[2];
    }

    /**
     * Attempt to solve it using diagonals - incorrect. 
     * Find diagonal where k-th element is and process only elements from that line.
     * Incorrect - wrong assumption that all elements in given diagonal are greater than all 
     * elements from previous diagonal.
     *  
     * [ 1,  3,  5]
     * [ 6,  7, 12]
     * [11, 14, 14]
     * k = 3
     * Output: 6 - min from diagonal 6, 3
     * Expected: 5
     */
    public int kthSmallestIncorrect(int[][] matrix, int k) {
        int n = matrix.length;
        Queue<Integer> minHeap = new PriorityQueue<>();
        int m = n * (n + 1) / 2;
        int pos = 0;
        if (k <= m) {
            // upper or middle
            int count = 0;
            int i = 0;
            while (count < k) {
                i++;
                count += i;
            }
            count -= i;
            for (int col = 0, row = i - 1; row >= 0; row--, col++) {
                minHeap.offer(matrix[row][col]);
            }
            pos = count + 1;
        } else {
            // lower
            int count = m;
            int i = n;
            while (count < k) {
                i--;
                count += i;
            }
            count -= i;
            for (int col = n - i, row = n - 1; col < n; row--, col++) {
                minHeap.offer(matrix[row][col]);
            }
            pos = count + 1;
        }
        while (pos < k) {
            minHeap.poll();
            pos++;
        }
        return minHeap.peek();
    }

    public static void main(String... args) {
        System.out.println(new KthSmallestElementInSortedMatrix()
                .kthSmallest(new int[][] { { 1, 3, 5 }, { 6, 7, 12 }, { 11, 14, 14 } }, 3));
    }
}
