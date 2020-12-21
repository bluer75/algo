package alg.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given a binary search tree, write a function to find the k-th smallest element in it.
 * You may assume k is always valid, 1 ≤ k ≤ BST's total elements.
 * Example:
 * Input: k = 1
 *    3
 *   / \
 *  1   4
 *   \
 *    2
 * Output: 1
 * 
 * Solution uses in order traversal iterative. 
 * Time/space complexity : O(H + k), where H is a tree height. This complexity is defined by the stack, which 
 * contains at least H+k elements, since before starting to pop out one has to go down to a leaf. 
 * This results in O(log N + k) for the balanced tree and O(N + k) for completely unbalanced tree with 
 * all the nodes in the left subtree.
 */
public class KthSmallestElementBST {
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            // visit
            if (--k == 0) {
                return node.val;
            }
            node = node.right;
        }
        return 0;
    }
}
