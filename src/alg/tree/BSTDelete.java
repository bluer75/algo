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
public class BSTDelete {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        TreeNode[] node = find(root, key);
        if (node == null) {
            // nothing to delete
            return root;
        }
        if (node[1].left == null && node[1].right == null) {
            if (node[0] == null) {
                // no parent
                return null;
            }
            // node has no children
            removeChild(node);
            return root;
        }
        if (node[1].left == null ^ node[1].right == null) {
            if (node[0] == null) {
                // no parent
                return node[1].left != null ? node[1].left : node[1].right;
            }
            // node has one child
            shiftChildUp(node);
            return root;
        }
        // node has two children
        TreeNode[] successor = findSuccessor(node[1]);
        if (successor != null) {
            deleteNode(successor[0], successor[1].val);
            node[1].val = successor[1].val;
        }
        return root;
    }

    private void shiftChildUp(TreeNode[] node) {
        if (node[0].left != null && node[0].left.val == node[1].val) {
            if (node[1].left != null) {
                node[0].left = node[1].left;
            } else {
                node[0].left = node[1].right;
            }
        } else {
            if (node[1].left != null) {
                node[0].right = node[1].left;
            } else {
                node[0].right = node[1].right;
            }
        }
    }

    private void removeChild(TreeNode[] node) {
        if (node[0].left != null && node[0].left.val == node[1].val) {
            node[0].left = null;
        } else {
            node[0].right = null;
        }
    }

    private TreeNode[] findSuccessor(TreeNode node) {
        if (node.right == null) {
            return null;
        }
        TreeNode parent = node;
        node = node.right;
        while (node.left != null) {
            parent = node;
            node = node.left;
        }
        return new TreeNode[] { parent, node };
    }

    private TreeNode[] find(TreeNode node, int key) {
        TreeNode parent = null;
        while (true) {
            if (node == null) {
                // not found
                return null;
            }
            if (node.val == key) {
                // found
                return new TreeNode[] { parent, node };
            }
            parent = node;
            if (node.val > key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "" + val;
        }

    }

    /**
     * Compact solution based on recursion.
     */
    public TreeNode delete(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key < root.val) {
            root.left = delete(root.left, key);
        } else if (key > root.val) {
            root.right = delete(root.right, key);
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
                delete(root.right, successor.val);
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
        root = new BSTDelete().deleteNode(root, 5);
    }
}
