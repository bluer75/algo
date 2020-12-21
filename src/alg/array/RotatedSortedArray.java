package alg.array;

/**
 * Search in Rotated Sorted Array
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
 * You have to find given value in the array, return its index, otherwise return -1.
 * You may assume no duplicate exists in the array.
 * Your algorithm's runtime complexity must be in the order of O(log n).
 * Example:
 * Input: [4,5,6,7,0,1,2], target = 0 ->Output: 4
 */
public class RotatedSortedArray {

    /**
     * It uses binary search to find where is beginning of the array and then checks in which part it needs
     * to search for given value. It uses then binary search in that part of the array.
     * Complexity is determined by binary search ->O(log n).
     */
    public int search(int[] a, int i) {
        if (a == null || a.length == 0) {
            return -1;
        }
        int k = findk(a, 0, a.length - 1);
        if (k == 0) {
            // not shifted, search in whole array
            return find(a, 0, a.length - 1, i);
        }
        if (i >= a[0]) {
            // search in left part
            return find(a, 0, k - 1, i);
        } else {
            // search in right part
            return find(a, k, a.length - 1, i);
        }
    }

    private int find(int[] a, int l, int r, int i) {

        if (l > r) {
            return -1;
        }
        int m = l + (r - l) / 2;
        if (i > a[m]) {
            return find(a, m + 1, r, i);
        } else if (i < a[m]) {
            return find(a, l, m - 1, i);
        }
        return m;
    }

    private int findk(int[] a, int l, int r) {

        if (l == r) {
            // base case one element left
            return l;
        }
        if (r - l == 1) {
            // base case two elements left
            return a[l] < a[r] ? l : r;
        }
        // calculate middle keep searching
        int m = l + (r - l) / 2;
        if (a[l] > a[m]) {
            return findk(a, l, m);
        } else if (a[m] > a[r]) {
            return findk(a, m, r);
        }
        // not shifted
        return 0;
    }

    public static void main(String... args) {
        RotatedSortedArray rsa = new RotatedSortedArray();
        int[] a = { 4, 5, 6, 7, 0, 1, 2 };
        System.out.println(rsa.search(a, 0));
    }
}