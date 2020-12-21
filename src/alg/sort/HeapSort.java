package alg.sort;

import java.util.Arrays;
import java.util.Random;

public class HeapSort {
    private int last; // indicates last index of the heap

    public void sort(int[] a) {
        last = a.length - 1;
        build(a); // build heap from an array
        while (last > 0) {
            swap(a, 0, last--); // swap root with current last element
            heapify(a, 0); // restore max heap property
        }
    }

    private void heapify(int[] a, int i) {
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int largest = i;
        if (l <= last && a[l] > a[i]) {
            largest = l;
        }
        if (r <= last && a[r] > a[largest]) {
            largest = r;
        }
        if (largest != i) {
            swap(a, i, largest);
            heapify(a, largest);
        }
    }

    private void build(int[] a) {
        for (int i = last / 2; i >= 0; i--) {
            heapify(a, i);
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
        new HeapSort().sort(a);
        System.out.println(Arrays.toString(a));
    }
}
