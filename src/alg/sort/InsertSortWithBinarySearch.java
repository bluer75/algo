package alg.sort;

import java.util.Arrays;
import java.util.Random;

public class InsertSortWithBinarySearch {
    public void sort(int[] a) {
        for (int j = 1; j < a.length; j++) {
            int k = a[j];
            int i = bs(a, 0, j, k); // find place for current value in sorted
                                    // array
            if (i < -1) {
                // current value not found in sorted part but we have index
                // where is should go
            } else {
                // current value found in sorted array already
            }
        }
    }

    private static int bs(int[] a, int lo, int hi, int k) {
        if (lo > hi) {
            return Math.negateExact(lo) - 1; // position where k should be inserted -> (i * -1) - 1
        }
        int mid = (lo + hi) / 2;
        if (a[mid] < k) {
            return bs(a, mid + 1, hi, k);
        }
        if (a[mid] > k) {
            return bs(a, lo, mid - 1, k);
        }
        return mid;
    }

    public static void main(String... strings) {
        int[] a = new Random().ints(20, 1, 100).toArray();
        System.out.println(Arrays.toString(a));
        new InsertSortWithBinarySearch().sort(a);
        System.out.println(Arrays.toString(a));
    }
}
