package alg.sort;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {

    private enum PARTITIONING {
        HOARE {
            @Override
            int partition(int[] a, int lo, int hi) {
                int l = lo - 1;
                int h = hi;
                int pivot = a[hi]; // pivot is at the end
                while (l < h) {
                    while (a[++l] < pivot)
                        ;
                    while (--h > lo && a[h] >= pivot)
                        ;
                    if (l < h) {
                        swap(a, l, h);
                    }
                }
                swap(a, l, hi); // place pivot in final position
                return l;
            }
        },

        LOMUTO {
            @Override
            int partition(int[] a, int lo, int hi) {
                int i = lo;
                int pivot = a[hi]; // pivot is at the end
                for (int j = lo; j < hi; j++) {
                    if (a[j] <= pivot) {
                        swap(a, i++, j);
                    }
                }
                swap(a, i, hi); // place pivot in final position
                return i;
            }
        };

        abstract int partition(int[] a, int lo, int hi);
    }

    private PARTITIONING p = PARTITIONING.HOARE; // default partitioning strategy

    public void sort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    private void sort(int[] a, int lo, int hi) {
        if (lo > hi) {
            return;
        }
        int ppos = p.partition(a, lo, hi); // index of pivot
        sort(a, lo, ppos - 1); // left side
        sort(a, ppos + 1, hi); // right side
    }

    private static void swap(int[] a, int x, int y) {
        int t = a[x];
        a[x] = a[y];
        a[y] = t;
    }

    public static void main(String... strings) {
        int[] a = new Random().ints(20, 1, 100).toArray();
        System.out.println(Arrays.toString(a));
        new QuickSort().sort(a);
        System.out.println(Arrays.toString(a));
    }
}
