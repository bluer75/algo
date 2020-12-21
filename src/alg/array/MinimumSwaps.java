package alg.array;

/**
 * You are given an unordered array consisting of consecutive integers  [1, 2, 3, ..., n] without any duplicates. 
 * You are allowed to swap any two elements. You need to find the minimum number of swaps required to sort 
 * the array in ascending order.
 * 
 * Given array: [4 ,3, 1, 2], minimum number of swaps is 3.
 */
public class MinimumSwaps {
    static int minimumSwaps(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int swapCount = 0;
        // find out how many elements are out of order and and count direct swaps
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != i + 1) {
                swapCount++;
                if (arr[arr[i] - 1] == i + 1) {
                    // swapping would set both element in correct position
                    swapCount--;
                }
            }
        }
        return swapCount / 3 * 2 + swapCount % 3;
    }

    public static void main(String... args) {
        System.out.println(MinimumSwaps.minimumSwaps(new int[] { 1, 3, 5, 2, 4, 6, 7 }));
    }
}
