package alg.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array of positive integers, consider all binary trees such that:
 * Each node has either 0 or 2 children;
 * The values of input correspond to the values of each leaf in an in-order traversal of the tree.
 * The value of each non-leaf node is equal to the product of the largest leaf value in 
 * its left and right subtree respectively.
 * Among all possible binary trees considered, return the smallest possible sum of the values of each non-leaf node.  
 * It is guaranteed this sum fits into a 32-bit integer.
 * 
 * Example: [6,2,4]
 * Output: 32
 * There are two possible trees. The first has non-leaf node sum 36, and the second has non-leaf node sum 32.
 * 
 *     24            24
 *    /  \          /  \
 *   12   4        6    8
 *  /  \               / \
 * 6    2             2   4
 * 
 */
public class MinimumCostTree {
    public int find(int[] arr) {
        return find(arr, 0, new ArrayList<>(), Integer.MAX_VALUE);
    }

    private int find(int[] arr, int i, List<List<Integer>> res, int min) {
        int n = arr.length - i;
        if (n == 0) {
            int value = calculate(res);
            System.out.println(res + " " + value);
            return Math.min(value, min);
        }
        if (n == 1) {
            List<Integer> one = Arrays.asList(arr[i]);
            res.add(one);
            min = find(arr, i + 1, res, min);
            res.remove(one);
        } else if (n == 2) {
            List<Integer> two = Arrays.asList(arr[i], arr[i + 1]);
            res.add(two);
            min = find(arr, i + 2, res, min);
            res.remove(two);
        } else {
            List<Integer> one = Arrays.asList(arr[i]);
            res.add(one);
            List<Integer> two = Arrays.asList(arr[i + 1], arr[i + 2]);
            res.add(two);
            min = find(arr, i + 3, res, min);
            res.remove(one);
            res.remove(two);
            two = Arrays.asList(arr[i], arr[i + 1]);
            res.add(two);
            min = find(arr, i + 2, res, min);
            res.remove(two);
        }
        return min;
    }

    private int calculate(List<List<Integer>> res) {
        int sum = 0;
        Node p = null;
        for (List<Integer> leafs : res) {
            Node n = new Node();
            if (leafs.size() == 1) {
                n.value = 0;
                n.max = leafs.get(0);
            } else {
                n.value = leafs.get(0) * leafs.get(1);
                n.max = Math.max(leafs.get(0), leafs.get(1));
                sum += n.value;
            }
            if (p == null) {
                p = n;
            } else {
                p.value = p.max * n.max;
                p.max = Math.max(p.max, n.max);
                sum += p.value;
            }
        }
        return sum;
    }

    private static class Node {
        int max;
        int value;
    }

    public static void main(String... args) {
        System.out.println(new MinimumCostTree().find(new int[] { 1, 2, 3, 4 }));
    }
}
