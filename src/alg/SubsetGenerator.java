package alg;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Generates all subsets for given set.
 */
public class SubsetGenerator {

    static void generate(int[] elems) {
        generate(elems, elems.length - 1, new LinkedList<Integer>());
    }

    /**
     * For each element there are two choices - it is selected or not.
     * i indicates which element of input array we want to process
     */
    private static void generate(int[] elems, int i, Deque<Integer> subset) {
        if (i < 0) {
            // base case - nothing left in input array
            System.out.println(subset);
        } else {
            // choose next element from input set and add it to subset
            subset.push(elems[i]);
            // explore case with element added to subset
            generate(elems, i - 1, subset);
            // un-choose element
            subset.pop();
            // explore case with element not added to subset
            generate(elems, i - 1, subset);
        }
    }

    /**
     * Generate subset iteratively.
     */
    static void generateIterative(int[] elems) {
        List<List<Integer>> subsets = new LinkedList<>();
        subsets.add(new LinkedList<>()); // empty set
        for (int elem : elems) {
            List<List<Integer>> sets = new LinkedList<>();
            for (List<Integer> subset : subsets) { // create new subsets by adding next element to existing subsets
                List<Integer> set = new LinkedList<>(subset);
                set.add(elem);
                sets.add(set); // add new subsets to existing subsets
            }
            subsets.addAll(sets);
        }
        System.out.println(subsets);
    }

    /**
     * Generates iteratively all subsets.
     * All possible subsets of n elements are like all possible binary numbers of size n bits.
     * For n = 2 -> 2^2 -> 4 - 00, 01, 10, 11
     * 1 means elements are included in subset, 0 they are excluded.
     */
    static void generateBinary(int[] elements) {
        int numOfSubsets = 1 << elements.length;
        // iterate through values from 0 to 2^n - 1
        for (int v = 0; v < numOfSubsets; v++) {
            LinkedList<Integer> subset = new LinkedList<>();
            for (int i = 0; i < elements.length; i++) {
                // if bit at index i is set in v add element[i] to subset
                if ((v & (1 << i)) > 0) {
                    subset.add(elements[i]);
                }
            }
            System.out.println(subset);
        }
    }

    public static void main(String... args) {
        int[] elems = new int[]{1, 2, 3};
        System.out.println("recursive...");
        generate(elems);
        System.out.println("iterative...");
        generateIterative(elems);
        System.out.println("binary...");
        generateBinary(elems);

    }
}
