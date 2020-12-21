package alg.tree;

/**
 * Given a binary search tree and a range [a, b] (inclusive), return the sum of the elements 
 * of the binary search tree within the range.
 * For example, given the following tree:
 * 
 *      5
 *     / \
 *    3   8
 *   / \ / \
 *  2  4 6  10
 * 
 * and the range [4, 9], return 23 (5 + 4 + 6 + 8).
 */
public class RangeSum {

    /**
     * Visit nodes in order, checking if given element is in range, skip irrelevant subtrees.
     * O(n).
     */
    public int sum(IntNode node, int a, int b) {
        if (node == null) {
            return 0;
        }
        if (node.key < a) {
            return sum(node.right, a, b);
        }
        if (node.key > b) {
            return sum(node.left, a, b);
        }
        return node.key + sum(node.left, a, b) + sum(node.right, a, b);
    }

    public static void main(String... args) {
        IntNode root = IntNode.of(5);
        root.left = IntNode.of(3);
        root.right = IntNode.of(8);
        root.left.left = IntNode.of(2);
        root.left.right = IntNode.of(4);
        root.right.left = IntNode.of(6);
        root.right.right = IntNode.of(10);
        System.out.println(new RangeSum().sum(root, 4, 9));
    }
}
