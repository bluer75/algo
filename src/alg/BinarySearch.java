package alg;

import java.util.Arrays;
import java.util.stream.IntStream;

public class BinarySearch {
	public int search(int[] a, int x) {
		int lo = 0;
		int hi = a.length - 1;
		int mid = 0;
		while (lo < hi) {
			mid = lo + (hi - lo) / 2;
			if (a[mid] < x) {
				// search in right side
				lo = mid + 1;
			} else if (a[mid] > x) {
				// search in left side
				hi = mid - 1;
			} else {
				// found
				return mid;
			}
		}
		// return negated index where it would fit
		return -1 * lo;

	}

	public int searchFirst(int[] a, int x) {
		int lo = 0;
		int hi = a.length - 1;
		int mid = 0;
		while (lo < hi) {
			mid = lo + (hi - lo) / 2;
			if (a[mid] < x) {
				// search in right side
				lo = mid + 1;
			} else {
				// search in left side
				hi = mid;
			}
		}
		return lo;
	}

	public int searchLast(int[] a, int x) {
		int lo = 0;
		int hi = a.length - 1;
		int mid = 0;
		while (lo < hi) {
			mid = lo + (hi - lo + 1) / 2;
			if (a[mid] > x) {
				// search in right side
				hi = mid - 1;
			} else {
				// search in left side
				lo = mid;
			}
		}
		return hi;
	}

	public static void main(String... strings) {
		int[] a = { 1, 2, 2, 2, 3 };
		System.out.println(Arrays.toString(a));
		// System.out.println(new BinarySearch().search(a, 1));
		// System.out.println(new BinarySearch().search(a, 2));
		System.out.println(new BinarySearch().search(a, 2));
		System.out.println(new BinarySearch().searchFirst(a, 0));
		System.out.println(new BinarySearch().searchLast(a, 0));
	}
}
