package alg.fb;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Above-Average Subarrays
 * You are given an array A containing N integers. Your task is to find all subarrays whose average 
 * sum is greater than the average sum of the remaining array elements. 
 * You must return the start and end index of each subarray in sorted order.
 * A subarray that starts at position L1 and ends at position R1 comes before a subarray that starts at L2 and ends at R2 
 * if L1 < L2, or if L1 = L2 and R1 ≤ R2.
 * Note that we'll define the average sum of an empty array to be 0, and we'll define the indices of the array 
 * (for the purpose of output) to be 1 through N. A subarray that contains a single element will have L1 = R1.
 * Input
 * 1 ≤ N ≤ 2,000
 * 1 ≤ A[i] ≤ 1,000,000
 * Output
 * A Subarray is an object with two integer fields, left and right, defining the range that a given subarray covers. Return a list of all above-average subarrays sorted as explained above.
 * Example 1
 * A = [3, 4, 2]
 * output = [[1, 2], [1, 3], [2, 2]]
 * The above-average subarrays are [3, 4], [3, 4, 2], and [4].
 */
public class AboveSubarraysAvg {

    private static class Subarray {
        int start;
        int end;

        Subarray(int s, int e) {
            start = s;
            end = e;
        }

        @Override
        public String toString() {
            return "[start=" + start + ", end=" + end + "]";
        }
    }

    public Subarray[] aboveAverageSubarrays(int[] A) {
        List<Subarray> res = new LinkedList<>();
        int n = A.length;
        int[] left = new int[n + 1];
        int[] right = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            left[i] = left[i - 1] + A[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            right[i] = right[i + 1] + A[i];
        }
        float avg = 0;
        float rest = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j++) {
                avg = (float)(left[j] - left[i - 1]) / (float)(j - i + 1);
                rest = (float)(left[i - 1] + right[j]) / (float)(i + n - j - 1);
                if (avg > rest || Float.isNaN(rest)) {
                    res.add(new Subarray(i, j));
                }
            }
        }
        return res.stream().toArray(Subarray[]::new);
    }

    public static void main(String... args) {
        AboveSubarraysAvg a = new AboveSubarraysAvg();
        System.out.println(Arrays.toString(a.aboveAverageSubarrays(new int[] { 3, 4, 2 })));
    }
}
