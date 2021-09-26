package alg.tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a binary tree, return the preorder traversal of its nodes' values.
 *
 * Example:
 *
 * Input: [4,2,6,1,3,5,7]
 *      4
 *    /   \
 *   2     6
 *  / \   / \
 * 1   3 5   7
 *
 * Output: [4,2,1,3,6,5,7]
 *
 * Recursive/Iterative solution takes O(n) time and space.
 */
public class PreOrderTraversal {

    public List<Integer> visitRecursive(TreeNode root) {
        List<Integer> values = new LinkedList<>();
        visit(root, values);
        return values;
    }

    private void visit(TreeNode node, List<Integer> values) {
        if (node == null) {
            return;
        }
        values.add(node.val);
        visit(node.left, values);
        visit(node.right, values);
    }

    public List<Integer> visitIterative(TreeNode root) {
        List<Integer> values = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        TreeNode node;
        while (!stack.isEmpty()) {
            node = stack.pop();
            // visit
            values.add(node.val);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
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

        System.out.println(new PreOrderTraversal().visitRecursive(_4));
        System.out.println(new PreOrderTraversal().visitIterative(_4));
    }
}
