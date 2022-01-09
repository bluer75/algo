package alg.tree;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Given an array of integers, which represents the preorder traversal of a BST, construct the tree and return its
 * root.
 *
 * Naive recursive solution finds indices for parent left and right child and takes O(n^2) time.
 *
 * Optimal solution iterate through the preorder and creates node for each value. Then, based on value it connects it as
 * either left or right child. This takes O(n) time.
 *
 * This can be implemented as recursive solution that uses ranges or iterative solution that uses stack.
 */
public class BstFromPreOrder {

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * Recursive O(n) solution that uses ranges to determine if node is left or right child.
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        return build(preorder, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private int i = 0;

    private TreeNode build(int[] preorder, int min, int max) {
        if (i == preorder.length || preorder[i] < min || preorder[i] > max) {
            return null;
        }
        TreeNode p = new TreeNode(preorder[i++]);
        if (i < preorder.length) {
            p.left = build(preorder, min, p.val);
            p.right = build(preorder, p.val, max);
        }
        return p;
    }

    /**
     * Recursive O(n^2) solution that finds indices of left and right child.
     */
    public TreeNode bstFromPreorder2(int[] preorder) {
        return build2(preorder, 0, preorder.length - 1);
    }

    private TreeNode build2(int[] pre, int l, int r) {
        if (l > r) {
            return null;
        }
        int pvalue = pre[l];
        TreeNode p = new TreeNode(pvalue);
        if (l < r) {
            int i = l + 1;
            while (i <= r && pre[i] < pvalue) {
                i++;
            }
            p.left = build2(pre, l + 1, i - 1);
            p.right = build2(pre, i, r);
        }
        return p;
    }

    /**
     * Iterative solution.
     */
    public TreeNode bstFromPreorder3(int[] preorder) {
        TreeNode root = new TreeNode(preorder[0]);
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        TreeNode child = null, parent = null;
        for (int i = 1; i < preorder.length; i++) {
            parent = stack.peek();
            child = new TreeNode(preorder[i]);
            while (!stack.isEmpty() && stack.peek().val <= child.val) {
                parent = stack.pop();
            }
            if (parent.val <= child.val) {
                parent.right = child;
            } else {
                parent.left = child;
            }
            stack.push(child);
        }
        return root;
    }

    public static void main(String[] args) {
        int[] preorder = new int[]{8, 5, 1, 7, 10, 12};
        new BstFromPreOrder().bstFromPreorder(preorder);
        new BstFromPreOrder().bstFromPreorder2(preorder);
        new BstFromPreOrder().bstFromPreorder3(preorder);
    }
}
