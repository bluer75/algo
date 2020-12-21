package alg.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * Counting sort is a linear time sorting algorithm that sort in O(n+k) time when elements are in range from 1 to k.
 */
public class CountingSort {

    /**
     * Simple implementation assuming all we care about are just numbers in input array
     */
    public void sortSimple(int[] a) {
        if (a == null) {
            return;
        }
        // find max value -> O(n)
        int size = -1;
        for (int v : a) {
            size = Math.max(size, v);
        }
        // counts occurrences -> O(n)
        int[] counts = new int[size + 1]; // 0..size
        for (int v : a) {
            counts[v]++;
        }
        // fill sorted values using occurrences
        int k = 0;
        for (int i = 0; i < counts.length; i++) {
            while (counts[i] > 0) {
                a[k] = i;
                k++;
                counts[i]--;
            }
        }
    }

    /**
     * Generic solution applicable when numbers represent real non-numeric objects.
     * There is one extra pass through array where we calculate how many elements is before each element.
     * Then this information is used to populate values from original array to output array.
     * This requires copy of input array. 
     */
    public void sortGeneric(int[] a) {
        if (a == null) {
            return;
        }
        // find max value -> O(n)
        int size = -1;
        for (int v : a) {
            size = Math.max(size, v);
        }
        // counts occurrences -> O(n)
        int[] counts = new int[size + 1]; // 0..size
        for (int v : a) {
            counts[v]++;
        }
        // calculate elements before each value
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }
        // copy input array
        int[] b = new int[a.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = a[i];
        }
        // fill sorted values
        for (int i = a.length - 1; i >= 0; i--) {
            a[counts[b[i]] - 1] = b[i];
            counts[b[i]]--;
        }
    }

    public static void main(String... strings) {
        int[] a = new Random().ints(20, 1, 100).toArray();
        int[] aa = Arrays.copyOf(a, a.length);
        System.out.println(Arrays.toString(a));
        new CountingSort().sortSimple(a);
        System.out.println(Arrays.toString(a));
        new CountingSort().sortGeneric(aa);
        System.out.println(Arrays.toString(aa));
    }
}
