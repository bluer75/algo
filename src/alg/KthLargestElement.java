package alg;

/**
 * Find the k-th largest element in an unsorted array. 
 * 
 * Example:
 * Input: [3, 2, 3, 1, 2, 4, 5, 5, 6] and k = 4
 * Output: 4
 * 
 * Quick select provides result in O(n) with a worst case O(n^2). Space complexity is O(1).
 */
public class KthLargestElement {

    public int findKthLargest(int[] nums, int k) {
        int lo = 0;
        int hi = nums.length - 1;
        int pos = nums.length - k;
        while (true) {
            int p = partition(nums, lo, hi);
            if (p == pos) {
                return nums[p];
            }
            if (p < pos) {
                lo = p + 1;
            } else {
                hi = p - 1;
            }
        }
    }

    private int partition(int[] a, int lo, int hi) {
        int i = lo;
        int p = a[hi];
        for (int j = lo; j < hi; j++) {
            if (a[j] <= p) {
                swap(a, j, i++);
            }
        }
        swap(a, hi, i);
        return i;
    }

    private void swap(int[] a, int i, int j) {
        if (i != j) {
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public static void main(String... args) {
        System.out.println(new KthLargestElement().findKthLargest(new int[] { 3, 2, 3, 1, 2, 4, 5, 5, 6 }, 4));
    }
}
