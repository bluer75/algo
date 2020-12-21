package alg;

/**
 * Given an array of integers, return a new array such that each element at index i of the new array is 
 * the product of all the numbers in the original array except the one at i.
 * The easiest way is to calculate the product for all and when filling output table divide it by i each time. 
 */
import java.util.Arrays;

public class ArrayProduct {
    static int[] transform(int[] a) {
        int[] output = new int[a.length];
        transform(a, 0, output);
        return output;
    }

    /**
     * Executes in O(n) - two passes.
     * Going from 0 to length -1 and calculating product for all preceding elements.
     * Going from length - 1 to 0 and calculating product of elements laying ahead.
     * It returns product of all visited elements.     
     */
    private static int transform(int[] input, int i, int[] output) {
        if (i == input.length) {
            return 1;
        } else {
            if (i == 0) {
                output[i] = 1;
            } else {
                // set the product for each element based on product of previous elements
                output[i] = output[i - 1] * input[i - 1];
            }
            int res = transform(input, i + 1, output);
            // on the way back use product for the elements ahead
            output[i] *= res;
            return input[i] * res;
        }
    }

    public static void main(String... args) {
        int[] input = new int[] { 1, 2, 3, 4, 5 };
        // expected output [120, 60, 40, 30, 24]
        int[] output = transform(input);
        System.out.println(Arrays.toString(output));
    }
}
