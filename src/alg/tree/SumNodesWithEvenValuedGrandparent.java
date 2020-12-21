package alg.tree;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Given a binary tree, return the sum of values of nodes with even-valued grandparent.
 * A grandparent of a node is the parent of its parent, if it exists.
 * If there are no nodes with an even-valued grandparent, return 0.
 * 
 * Example:
 *
 *        6
 *      /   \
 *     7     8
 *    / \   / \
 *   2   7 1   3
 *  /   / \     \
 * 9   1   4     5
 * 
 * Input: root = [6,7,8,2,7,1,3,9,null,1,4,null,null,null,5]
 * Output: 18
 * 
 * The number of nodes in the tree is between 1 and 10^4.
 * The value of nodes is between 1 and 100.
 * 
 * DFS recursive solution - O(n) 
 */
public class SumNodesWithEvenValuedGrandparent {

    public int sumEvenGrandparent(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return dfs(root, null, null);
    }

    private int dfs(TreeNode current, TreeNode parent, TreeNode grandpa) {
        if (current == null) {
            return 0;
        }
        return dfs(current.left, current, parent) + dfs(current.right, current, parent)
                + ((grandpa != null && grandpa.val % 2 == 0) ? current.val : 0);
    }

    /**
     * Since we may have up to 10^4 nodes recursive approach may overflow (after ~ 5000 calls).
     * It performs in-order traversal (left-parent-right).
     */
    public int sumEvenGrandparentIterativelyInOrder(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int sum = 0;
        Deque<TreeNodeDecorator> stack = new LinkedList<>();
        // start with the root
        TreeNodeDecorator current = new TreeNodeDecorator(root, null, null);
        while (!stack.isEmpty() || current != null) {
            // push all left nodes on the way down the (sub)tree
            while (current != null) {
                stack.push(current);
                if (current.node.left != null) {
                    current = new TreeNodeDecorator(current.node.left, current.node, current.parent);
                } else {
                    current = null;
                }
            }
            // got to the bottom of the (sub)tree - process last node
            current = stack.pop();
            if (current.grandpa != null && current.grandpa.val % 2 == 0) {
                sum += current.node.val;
            }
            // switch to right subtree
            if (current.node.right != null) {
                current = new TreeNodeDecorator(current.node.right, current.node, current.parent);
            } else {
                current = null;
            }
        }
        return sum;
    }

    /**
     * Since we may have up to 10^4 nodes recursive approach may overflow (after ~ 5000 calls).
     * It performs pre-order traversal (parent-left-right).
     */
    public int sumEvenGrandparentIterativelyPreOrder(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int sum = 0;
        Deque<TreeNodeDecorator> stack = new LinkedList<>();
        // start with the root
        TreeNodeDecorator current = new TreeNodeDecorator(root, null, null);
        stack.push(current);
        while (!stack.isEmpty()) {
            current = stack.pop();
            // process parent
            if (current.grandpa != null && current.grandpa.val % 2 == 0) {
                sum += current.node.val;
            }
            // push right
            if (current.node.right != null) {
                stack.push(new TreeNodeDecorator(current.node.right, current.node, current.parent));
            }
            // push left
            if (current.node.left != null) {
                stack.push(new TreeNodeDecorator(current.node.left, current.node, current.parent));
            }
        }
        return sum;
    }

    private static class TreeNodeDecorator {
        private TreeNode node;
        private TreeNode parent;
        private TreeNode grandpa;

        TreeNodeDecorator(TreeNode node, TreeNode parent, TreeNode grandpa) {
            this.node = node;
            this.parent = parent;
            this.grandpa = grandpa;
        }
    }

    public static void main(String... args) {
        TreeNode _6 = new TreeNode(6);
        TreeNode _7_1 = new TreeNode(7);
        TreeNode _8 = new TreeNode(8);
        TreeNode _2 = new TreeNode(2);
        TreeNode _7_2 = new TreeNode(7);
        TreeNode _1_1 = new TreeNode(1);
        TreeNode _3 = new TreeNode(3);
        TreeNode _9 = new TreeNode(9);
        TreeNode _1_2 = new TreeNode(1);
        TreeNode _4 = new TreeNode(4);
        TreeNode _5 = new TreeNode(5);

        _6.left = _7_1;
        _6.right = _8;
        _7_1.left = _2;
        _7_1.right = _7_2;
        _8.left = _1_1;
        _8.right = _3;
        _2.left = _9;
        _7_2.left = _1_2;
        _7_2.right = _4;
        _3.right = _5;

        System.out.println(new SumNodesWithEvenValuedGrandparent().sumEvenGrandparent(_6));
        System.out.println(new SumNodesWithEvenValuedGrandparent().sumEvenGrandparentIterativelyInOrder(_6));
        System.out.println(new SumNodesWithEvenValuedGrandparent().sumEvenGrandparentIterativelyPreOrder(_6));
    }
}
