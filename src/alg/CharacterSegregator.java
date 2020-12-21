package alg;

import java.util.Arrays;

/**
 * Given an array characters 'R', 'G', and 'B', segregate the values of the array so that all the R's come first, the G's 
 * come second, and the B's come last. 
 * You can only swap elements of the array. Do this in linear time and in-place.
 */
public class CharacterSegregator {

    /**
     * Uses Lomuto partitioning algorithm to move all R's and then G's.
     * In worst case it requires two passes. 
     */
    public static char[] segregate(char[] chars) {
        int idx = 0;
        for (int i = 0; i < chars.length; i++) {
            // segregate R's
            if (chars[i] == 'R') {
                swap(chars, i, idx);
                idx++;
            }
        }
        for (int i = idx; i < chars.length; i++) {
            // segregate G's
            if (chars[i] == 'G') {
                swap(chars, i, idx);
                idx++;
            }
        }
        // B's are at the end now
        return chars;
    }

    /**
     * Uses 3-way partitioning - Dutch National Flag algorithm.
     * It is based on Lomuto's algorithm and requires one pass.
     * 'R' - low value
     * 'G' - middle value
     * 'B' - high value
     */
    public static char[] segregateDNF(char[] chars) {
        int low = 0; // index where next low value should go
        int high = chars.length - 1; // index where next high value should go
        int i = 0; // current index
        while (i <= high) {
            // segregate R's as in Lomuto's algorithm
            if (chars[i] == 'R') {
                swap(chars, i, low);
                low++;
                i++;
            } else {
                // move B's to the end of array
                if (chars[i] == 'B') {
                    swap(chars, i, high);
                    high--;
                } else {
                    // 'G' stays as it is
                    i++;
                }
            }
        }
        return chars;
    }

    private static void swap(char[] chars, int i, int j) {
        if (i != j) {
            char tmp = chars[i];
            chars[i] = chars[j];
            chars[j] = tmp;
        }
    }

    public static void main(String... args) {
        char[] chars = { 'G', 'R', 'B', 'B', 'R', 'R' };
        System.out.println(Arrays.toString(segregate(chars)));
        System.out.println(Arrays.toString(segregateDNF(chars)));
    }
}
