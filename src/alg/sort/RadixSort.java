package alg.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * QuickSort, MergeSort, HeapSort are comparison based sorting algorithms.
 * CountSort is not comparison based algorithm. It has the complexity of O(n+k), 
 * where k is the maximum element of the input array. 
 * So, if k is O(n) ,CountSort becomes linear sorting, which is better than comparison 
 * based sorting algorithms that have O(n log n) time complexity. 
 * The idea is to extend the CountSort algorithm to get a better time complexity when k goes O(n^2).
 * Algorithm:
 * For each digit i where i varies from the least significant digit to the most significant digit of a number
 * Sort input array using counting sort algorithm according to i-th digit.
 * We used count sort because it is a stable sort.
 * 
 * The complexity is O((n+b)*log (base b) (maxx)) where b is the base for representing numbers and maxx is the maximum 
 * element of the input array. This is clearly visible as we make (n+b) iterations logb(maxx) times (number 
 * of digits in the maximum element). If maxx <= n^c, then the complexity can be written as O(n*log b(n)). 
 * 
 * Example for input 10,21,17,34,44,11,654,123
 *  Based on the algorithm, we will sort the input array according to the one's digit (the least significant digit).
 * 0: 10 
 * 1: 21 11 
 * 2: 
 * 3: 123 
 * 4: 34 44 654 
 * 5: 
 * 6: 
 * 7: 17 
 * 8: 
 * 9: 
 * 
 * So, the array becomes 10,21,11,123,24,44,654,17
 * Now, we'll sort according to the ten's digit:
 * 0: 
 * 1: 10 11 17
 * 2: 21 123
 * 3: 34
 * 4: 44
 * 5: 654
 * 6: 
 * 7: 
 * 8: 
 * 9: 
 * 
 * Now, the array becomes : 10,11,17,21,123,34,44,654
 * Finally , we sort according to the hundred's digit (most significant digit):
 * 0: 010 011 017 021 034 044
 * 1: 123
 * 2: 
 * 3: 
 * 4: 
 * 5: 
 * 6: 654
 * 7: 
 * 8: 
 * 9: 
 * 
 * The array becomes : 10,11,17,21,34,44,123,654 which is sorted.
 */
public class RadixSort {
    public void sort(int[] a) {
        if (a == null) {
            return;
        }
        // find max value
        int max = -1;
        for (int v : a) {
            max = Math.max(max, v);
        }
        // execute counting sort for each position (1, 10, 100...) in max value
        int positionValue = 1;
        while (max > 0) {
            countingSort(a, positionValue);
            positionValue *= 10;
            max /= 10;
        }
    }

    // sort values based on given position
    private void countingSort(int[] a, int positionValue) {
        // counts occurrences based on given position
        int[] counts = new int[10]; // base 10
        for (int v : a) {
            counts[(v / positionValue) % 10]++;
        }
        // calculate elements before each value
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }
        // copy input array
        int[] b = new int[a.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = a[i];
        }
        // fill sorted values
        int digit = 0;
        for (int i = a.length - 1; i >= 0; i--) { // needs to go from back to be stable
            digit = (b[i] / positionValue) % 10;
            a[counts[digit] - 1] = b[i];
            counts[digit]--;
        }
    }

    public static void main(String... strings) {
        int[] a = new Random().ints(20, 1, 100).toArray();
        System.out.println(Arrays.toString(a));
        new RadixSort().sort(a);
        System.out.println(Arrays.toString(a));
    }
}
