package alg.sort;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {
    private int[] src;

    public MergeSort(int[] a) {
        this.src = a;
    }

    public void sort() {
        int[] a = new int[src.length];
        sort(a, 0, a.length - 1);
    }

    private void sort(int[] a, int lo, int hi) {
        if (lo == hi) {
            return;
        }
        int mid = (lo + hi) / 2;
        sort(a, lo, mid);
        sort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }

    private void merge(int[] a, int lo, int mid, int hi) {
        int l = lo, h = mid + 1, i = lo;
        // merge
        while (l <= mid && h <= hi) {
            if (src[l] < src[h]) {
                a[i++] = src[l++];
            } else {
                a[i++] = src[h++];
            }
        }
        // copy rest from left
        while (l <= mid) {
            a[i++] = src[l++];
        }
        // copy rest from right
        while (h <= hi) {
            a[i++] = src[h++];
        }
        // copy back to source array
        for (i = lo; i <= hi; i++) {
            src[i] = a[i];
        }
    }

    public static void main(String... strings) {
        int[] a = new Random().ints(20, 1, 100).toArray();
        System.out.println(Arrays.toString(a));
        new MergeSort(a).sort();
        System.out.println(Arrays.toString(a));
    }
}
