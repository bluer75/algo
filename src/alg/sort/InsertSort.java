package alg.sort;

import java.util.Arrays;
import java.util.Random;

public class InsertSort {
    public void sort(int[] a) {
        for (int j = 1; j < a.length; j++) {
            int k = a[j]; // remember current unsorted value
            int i = j - 1;
            while (i >= 0 && a[i] > k) { // check sorted values starting form right side
                a[i + 1] = a[i]; // and shift greater to the right
                i--;
            }
            a[i + 1] = k; // put unsorted value in right place
        }
    }

    public static void main(String... strings) {
        int[] a = new Random().ints(20, 1, 100).toArray();
        System.out.println(Arrays.toString(a));
        new InsertSort().sort(a);
        System.out.println(Arrays.toString(a));
    }
}
