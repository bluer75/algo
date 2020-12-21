package alg.array;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * You are given an integer array nums and you have to return a new counts array. 
 * The counts array has the property where counts[i] is the number of smaller elements 
 * to the right of nums[i].
 * 
 * Example:
 * 
 * Input: [5,2,6,1]
 * Output: [2,1,1,0] 
 * 
 * Solution is checking elements from the right and inserts them is BST so we can count when inserting 
 * how many smaller numbers are already there.
 * 
 * Time complexity is O(n log n). Space complexity is O(n).
 */
public class SmallerNumbersToTheRight {
    public List<Integer> countSmaller(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Collections.emptyList();
        }
        LinkedList<Integer> result = new LinkedList<>();
        result.add(0);
        BstNode root = new BstNode(nums[nums.length - 1]);
        for (int i = nums.length - 2; i >= 0; i--) {
            result.addFirst(root.insert(nums[i]));
        }
        return result;
    }

    private class BstNode {
        private int count = 1; // counts duplicates
        private int leftCount = 0; // left side size
        private int val;
        private BstNode left, right;

        BstNode(int val) {
            this.val = val;
        }

        int insert(int v) {
            BstNode n = this;
            int smaller = 0;
            while (n != null) {
                if (v < n.val) {
                    n.leftCount++; // smaller element is inserted on left side
                    if (n.left == null) {
                        n.left = new BstNode(v);
                        return smaller;
                    } else {
                        n = n.left;
                    }
                } else if (v > n.val) {
                    // greater element is inserted on right side, count all smaller
                    // elements passed on the way down including duplicates
                    smaller += n.leftCount + n.count;
                    if (n.right == null) {
                        n.right = new BstNode(v);
                        return smaller;
                    } else {
                        n = n.right;
                    }
                } else {
                    // duplicate is inserted
                    n.count++;
                    // count all smaller elements on the left side
                    smaller += n.leftCount;
                    return smaller;
                }
            }
            return smaller;
        }
    }

    public static void main(String... args) {
        System.out.println(new SmallerNumbersToTheRight().countSmaller(new int[] { 5, 2, 6, 1 }));
    }
}
