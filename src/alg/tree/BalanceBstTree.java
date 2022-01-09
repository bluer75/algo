package alg.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Given the root of a binary search tree, return a balanced binary search tree with the same node values.
 *
 * A binary search tree is balanced if the depth of the two subtrees of every node never differs by more than 1.
 *
 * Example:
 *  1
 *   \
 *    2
 *     \     =>       4
 *      3            / \
 *       \          2   5
 *        4        / \
 *         \      1   3
 *          5
 *
 * Solution requires two steps:
 * - in-order traversal - constructs sorted array with all nodes -> O(n) time/space
 * - recursively divide array into left, parent and right part and construct BST -> O(n) time, O(log n) space
 */
public class BalanceBstTree {
    public TreeNode balanceBST(TreeNode root) {
        List<Integer> nodes = new ArrayList<>();
        dfs(root, nodes);
        return build(nodes, 0, nodes.size() - 1);
    }

    private TreeNode build(List<Integer> nodes, int lo, int hi) {
        if (lo > hi) {
            return null;
        }
        if (lo == hi) {
            return new TreeNode(nodes.get(lo));
        }
        TreeNode left = null, right = null, parent = null;
        int m = (lo + hi) / 2;
        if ((hi - lo + 1) % 2 == 0) {
            // even
            left = build(nodes, lo, m);
            parent = new TreeNode(nodes.get(m + 1));
            right = build(nodes, m + 2, hi);
        } else {
            // odd
            left = build(nodes, lo, m - 1);
            parent = new TreeNode(nodes.get(m));
            right = build(nodes, m + 1, hi);
        }
        parent.left = left;
        parent.right = right;
        return parent;
    }

    private void dfs(TreeNode node, List<Integer> nodes) {
        if (node == null) {
            return;
        }
        dfs(node.left, nodes);
        nodes.add(node.val);
        dfs(node.right, nodes);
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        TreeNode _1 = new TreeNode(1);
        TreeNode _2 = new TreeNode(2);
        TreeNode _3 = new TreeNode(3);
        TreeNode _4 = new TreeNode(4);
        TreeNode _5 = new TreeNode(5);

        _1.right = _2;
        _2.right = _3;
        _3.right = _4;
        _4.right = _5;

        new BalanceBstTree().balanceBST(_4);
    }
}
