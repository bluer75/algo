package alg.tree;

/**
 * Given a binary tree, flatten it to a linked list in-place.
 * 
 * For example, given the following tree:
 * 
 *     1
 *    / \
 *   2   5
 *  / \   \
 * 3   4   6
 * The flattened tree should look like:
 * 
 * 1
 *  \
 *   2
 *    \
 *     3
 *      \
 *       4
 *        \
 *         5
 *          \
 *           6
 *           
 * Use DFS for pre-order traversal and return last node in flattened subtree.
 * Time complexity is O(n).
 * Space complexity is O(h), where h is height of the tree.
 */
public class FlattenTree {
    public void flatten(TreeNode root) {
        visit(root);
    }

    private TreeNode visit(TreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.left == null && node.right == null) {
            return node;
        }
        TreeNode lastLeftNode = visit(node.left);
        TreeNode lastRightNode = visit(node.right);
        if (lastLeftNode == null) {
            return lastRightNode;
        }
        lastLeftNode.right = node.right;
        node.right = node.left;
        node.left = null;
        return lastRightNode == null ? lastLeftNode : lastRightNode;
    }

    public static void main(String... args) {
        TreeNode _1 = new TreeNode(1);
        TreeNode _2 = new TreeNode(2);
        TreeNode _3 = new TreeNode(3);
        TreeNode _4 = new TreeNode(4);
        TreeNode _5 = new TreeNode(5);
        TreeNode _6 = new TreeNode(6);
        _1.left = _2;
        _1.right = _5;
        _2.left = _3;
        _2.right = _4;
        _5.right = _6;
        new FlattenTree().flatten(_1);

        TreeNode n = _1;
        while (n != null) {
            System.out.print(n.val + "->");
            n = n.right;
        }
    }
}
