package alg.tree;

/**
 * Given a root node reference of a BST and a key, delete the node with the given key in the BST. 
 * Return the root node reference (possibly updated) of the BST.
 * Note: Time complexity should be O(height of tree).
 * 
 * Example
 * Remove 3 from given tree
 * 
 *     5
 *    / \
 *   3   6
 *  / \   \
 * 2   4   7
 * 
 * One valid answer is:
 * 
 *     5
 *    / \
 *   4   6
 *  /     \
 * 2       7
 * 
 *  or
 *  
 *     5
 *    / \
 *   2   6
 *    \   \
 *     4   7
 */
public class BSTDelete2 {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            }
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            TreeNode successor = min(root.right);
            if (successor != root.right) {
                deleteNode(root.right, successor.val);
                successor.right = root.right;
            }
            successor.left = root.left;
            return successor;
        }
        return root;
    }

    private TreeNode min(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static void main(String... args) {
        TreeNode _5 = new TreeNode(5);
        TreeNode _3 = new TreeNode(3);
        TreeNode _6 = new TreeNode(6);
        TreeNode _2 = new TreeNode(2);
        TreeNode _4 = new TreeNode(4);
        TreeNode _7 = new TreeNode(7);
        _5.left = _3;
        _5.right = _6;
        _3.left = _2;
        _3.right = _4;
        _6.right = _7;
        TreeNode root = _5;
        root = new BSTDelete2().deleteNode(root, 3);
    }
}
