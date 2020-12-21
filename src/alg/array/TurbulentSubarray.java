package alg.array;

/**
 * A subarray A[i], A[i+1], ..., A[j] of A is said to be turbulent if and only if:
 * For i <= k < j, A[k] > A[k+1] when k is odd, and A[k] < A[k+1] when k is even;
 * OR, for i <= k < j, A[k] > A[k+1] when k is even, and A[k] < A[k+1] when k is odd.
 * That is, the subarray is turbulent if the comparison sign flips between each adjacent 
 * pair of elements in the subarray.
 * Return the length of a maximum size turbulent subarray of A.
 * 
 * Example 1:
 * Input: [9,4,2,10,7,8,8,1,9]
 * Output: 5
 * Explanation: (A[1] > A[2] < A[3] > A[4] < A[5])
 * 
 * Example 2:
 * Input: [100]
 * Output: 1
 */
public class TurbulentSubarray {
    /**
     * O(n) solution scans array from left to right and expand sliding sliding window (max indicates its size) as 
     * long as conditions are met. Otherwise it computes and checks the max and resets the window.
     */
    public int find(int[] a) {
        int maxSoFar = 0;
        int max = 0;
        for (int j = 0; j < a.length; j++) {
            // check last two elements
            if (max == 1 && a[j - 1] != a[j]) {
                // window has size 2 and two distinct elements
                max++;
                continue;
            }
            // check last three elements
            if (max > 1 && (((a[j - 2] > a[j - 1] && a[j - 1] < a[j]) || (a[j - 2] < a[j - 1] && a[j - 1] > a[j])))) {
                // window has at least 3 elements in correct sequence
                max++;
                continue;
            }
            // reset the window
            maxSoFar = Math.max(maxSoFar, max);
            // set new max - depends on current size of the window
            max = max > 1 ? 2 : 1;
        }
        return maxSoFar = Math.max(maxSoFar, max);
    }

    public static void main(String... args) {
        TurbulentSubarray ts = new TurbulentSubarray();
        System.out.println(ts.find(new int[] { 0, 8, 45, 88, 48, 68, 28, 55, 17, 24 }));
        System.out.println(ts.find(new int[] { 9, 4, 2, 10, 7, 8, 8, 1, 9 }));
        System.out.println(ts.find(new int[] { 37, 199, 60, 296, 257, 248, 115, 31, 273, 176 }));
        System.out.println(ts.find(new int[] { 1, 1, 1 }));
        System.out.println(ts.find(new int[] { 100 }));
    }
}
