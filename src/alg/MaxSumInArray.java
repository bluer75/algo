package alg;

/**
 * Find largest sum of non-adjacent numbers in an array.
 */
public class MaxSumInArray {
    static int find(int[] a) {
        int incl = a[0];
        int excl = 0;
        for (int i = 1; i < a.length; i++) {
            int tmp = Math.max(incl, excl);
            incl = excl + a[i]; // sum if we include current element
            excl = tmp; // sum if we exclude current value - max of previous incl/excl
        }
        return incl < excl ? excl : incl;
    }

    public static void main(String... args) {
        System.out.println(find(new int[] { 5, 1, 1, 5 }));
        System.out.println(find(new int[] { 2, 4, 6, 2, 5 }));
    }
}
