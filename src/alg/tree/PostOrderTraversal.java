package alg.tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a binary tree, return the postorder traversal of its nodes' values.
 * 
 * Example:
 * 
 * Input: [4,2,6,1,3,5,7]
 *       4
 *     /   \
 *    2     6
 *   / \   / \
 *  1   3 5   7
 * 
 * Output: [1,3,2,5,7,6,4]
 * 
 * Recursive/Iterative solution takes O(n) time and space.
 * 
 * Threaded Binary Tree traversal (similar to Morris inorder traversal) reduces space to O(1).
 * Step 1: Initialize current as root
 * Step 2: While current is not NULL,
 *   if current does not have left child
 *     a. go right - current = current.right
 *   else
 *     a. make current the right child of its predecessor - rightmost node in left subtree
 *     b. go left - current = current.left
 *     
 * Implementation below requires two passes and it undo(s) the modification so the input tree is 
 * unchanged at the end.
 */
public class PostOrderTraversal {

    public List<Integer> visitRecursive(TreeNode root) {
        List<Integer> values = new LinkedList<>();
        visit(root, values);
        return values;
    }

    private void visit(TreeNode node, List<Integer> values) {
        if (node == null) {
            return;
        }
        visit(node.left, values);
        visit(node.right, values);
        values.add(node.val);
    }

    public List<Integer> visitIterative(TreeNode root) {
        List<Integer> values = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;
        TreeNode prev = null;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            if (stack.peek().right == null || stack.peek().right == prev) {
                node = stack.pop();
                // visit
                values.add(node.val);
                prev = node;
                node = null;
            } else {
                node = stack.peek().right;
            }
        }
        return values;
    }

    public List<Integer> visitMorris(TreeNode root) {
        List<Integer> values = new LinkedList<>();
        TreeNode node = new TreeNode(0); // dummy node as parent of root (root is its left child)
        node.left = root;
        TreeNode predecessor = null;
        while (node != null) {
            if (node.left == null) {
                node = node.right;
            } else {
                // find inorder predecessor - rightmost node in left subtree
                predecessor = node.left;
                while (predecessor.right != null && predecessor.right != node) {
                    predecessor = predecessor.right;
                }
                if (predecessor.right == null) {
                    // make current the right child of the rightmost node                    
                    predecessor.right = node;
                    node = node.left;
                } else {
                    // visit and undo modification
                    TreeNode n = predecessor;
                    reverse(node.left, predecessor);
                    while (n != node.left) {
                        values.add(n.val);
                        n = n.right;
                    }
                    values.add(n.val);
                    reverse(predecessor, node.left);
                    predecessor.right = null;
                    node = node.right;
                }
            }
        }
        return values;
    }

    private void reverse(TreeNode from, TreeNode to) {
        if (from == to) {
            return;
        }
        TreeNode next = null;
        TreeNode prev = from;
        TreeNode node = from.right;
        while (prev != to) {
            next = node.right;
            node.right = prev;
            prev = node;
            node = next;
        }
    }

    public static void main(String... args) {
        TreeNode _1 = new TreeNode(1);
        TreeNode _2 = new TreeNode(2);
        TreeNode _3 = new TreeNode(3);
        TreeNode _4 = new TreeNode(4);
        TreeNode _5 = new TreeNode(5);
        TreeNode _6 = new TreeNode(6);
        TreeNode _7 = new TreeNode(7);

        _4.left = _2;
        _4.right = _6;
        _2.left = _1;
        _2.right = _3;
        _6.left = _5;
        _6.right = _7;

        System.out.println(new PostOrderTraversal().visitRecursive(_4));
        System.out.println(new PostOrderTraversal().visitIterative(_4));
        System.out.println(new PostOrderTraversal().visitMorris(_4));
        System.out.println(new PostOrderTraversal().visitMorris(_4));
    }
}
