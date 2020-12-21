package alg.tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a binary tree, return the inorder traversal of its nodes' values.
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
 * Output: [1,2,3,4,5,6,7]
 * 
 * Recursive/Iterative solution takes O(n) time and space.
 * 
 * Morris Traversal (based on Threaded Binary Tree) reduces space to O(1) as it modifies input tree.
 * Step 1: Initialize current as root
 * Step 2: While current is not NULL,
 *   if current does not have left child
 *     a. visit current 
 *     b. go right - current = current.right
 *   else
 *     a. make current the right child of its predecessor - rightmost node in left subtree
 *     b. go left - current = current.left
 *     
 * Implementation below requires two passes and it undo(s) the modification so the input tree is 
 * unchanged at the end.
 */
public class InOrderTraversal {

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
        values.add(node.val);
        visit(node.right, values);
    }

    public List<Integer> visitIterative(TreeNode root) {
        List<Integer> values = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            // visit
            values.add(node.val);
            node = node.right;
        }
        return values;
    }

    public List<Integer> visitMorris(TreeNode root) {
        List<Integer> values = new LinkedList<>();
        TreeNode node = root;
        TreeNode predecessor = null;
        while (node != null) {
            if (node.left == null) {
                // visit and go right
                values.add(node.val);
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
                    // undo modification
                    predecessor.right = null;
                    values.add(node.val);
                    node = node.right;
                }
            }
        }
        return values;
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

        System.out.println(new InOrderTraversal().visitRecursive(_4));
        System.out.println(new InOrderTraversal().visitIterative(_4));
        System.out.println(new InOrderTraversal().visitMorris(_4));
        System.out.println(new InOrderTraversal().visitMorris(_4));
    }
}
