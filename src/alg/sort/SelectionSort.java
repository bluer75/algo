package alg.sort;

import java.util.Arrays;
import java.util.Random;

public class SelectionSort {
    public void sort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int min = i;
            // find minimum in unsorted part
            for (int j = i + 1; j < a.length; j++) { // i + 1 as we don't want to compare a[i] with a[i]
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            // swap minimum with first element in unsorted part
            swap(a, i, min);
        }
    }

    private static void swap(int[] a, int x, int y) {
        int t = a[x];
        a[x] = a[y];
        a[y] = t;
    }

    public static void main(String... strings) {
        int[] a = new Random().ints(20, 1, 100).toArray();
        System.out.println(Arrays.toString(a));
        new SelectionSort().sort(a);
        System.out.println(Arrays.toString(a));
    }
}
