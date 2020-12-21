package alg.tree;

/**
 * Write a method to find the 2nd largest element in a binary search tree in O(log n) time.
 * For following tree is should return 11. 
 *          5
 *        /   \
 *       3     8 
 *      / \   / \
 *     1   4 7  12
 *              /
 *             10
 *            /  \
 *           9   11
 */
public class FindSecondLargestElement {

    public IntNode find(IntNode root) {

        if (root == null || (root.left == null && root.right == null)) {
            return null;
        }
        IntNode current = root;

        while (true) {
            // left subtree and no right subtree, current is the largest
            if (current.left != null && current.right == null) {
                // second largest is on the left
                return findLargest(current.left);
            }
            // no left subtree and just one node on the right - current is the second largest
            if (current.left == null && current.right != null && current.right.left == null
                    && current.right.right == null) {
                return current;
            }
            // more than one node on the right - go right
            if (current.right != null
                    && (current.right.left != null || current.right.right != null)) {
                current = current.right;
            }
        }
    }

    // finds the largest element from root in O(log n) time.
    private IntNode findLargest(IntNode root) {

        IntNode current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    // clean solution without complicated if conditions
    public IntNode findOpt(IntNode root) {

        if (root == null || (root.left == null && root.right == null)) {
            return null;
        }

        // go right to find the largest and keep track of its parent
        IntNode parent = null;
        IntNode node = root;
        while (node.right != null) {
            parent = node;
            node = node.right;
        }
        // if there is left subtree find second largest there
        if (node.left != null) {
            return findLargest(node.left);
        }
        // parent is the second largest
        return parent;
    }

    public static void main(String... args) {

        IntNode root = IntNode.of(5);
        root.left = IntNode.of(3);
        root.right = IntNode.of(8);
        root.left.left = IntNode.of(1);
        root.left.right = IntNode.of(4);
        root.right.left = IntNode.of(7);
        root.right.right = IntNode.of(12);
        root.right.right.left = IntNode.of(10);
        root.right.right.left.left = IntNode.of(9);
        root.right.right.left.right = IntNode.of(11);
        System.out.println(new FindSecondLargestElement().findOpt(root));
    }
}
