package alg.tree;

/**
 * Check if given binary tree satisfies BST condition.
 * 
 * Maintain min and max value for each node and check recursively each node.
 * Left value has to be in (min, parent).
 * Right value has to be in (parent, max). 
 * Use long values to deal with cases where value is Integer.MIN_VALUE or Integer.MAX_VALUE.
 * This recursive traversal requires O(n) time/space. 
 * 
 * Morris in-order traversal requires O(n) time and O(1) space.
 */
public class CheckBST {

    public boolean check(IntNode root) {
        return check(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean check(IntNode node, long min, long max) {

        // base case
        if (node == null) {
            return true;
        }
        if (node.key > min && node.key < max) {
            return check(node.left, min, node.key) && check(node.right, node.key, max);
        }
        return false;
    }

    /**
     * Morris in-order traversal.
     */
    public boolean checkMorris(IntNode root) {
        IntNode node = root;
        IntNode predecessor = null;
        IntNode prev = null;
        while (node != null) {
            if (prev != null && prev.key >= node.key) {
                return false;
            }
            if (node.left == null) {
                prev = node;
                node = node.right;
            } else {
                predecessor = node.left;
                while (predecessor.right != null && predecessor.right != node) {
                    predecessor = predecessor.right;
                }
                if (predecessor.right == null) {
                    predecessor.right = node;
                    node = node.left;
                } else if (predecessor.right == node) {
                    predecessor.right = null;
                    prev = node;
                    node = node.right;
                }
            }
        }
        return true;
    }

    public static void main(String... args) {

        IntNode _5 = new IntNode(5);
        IntNode _3 = new IntNode(3);
        IntNode _7 = new IntNode(7);
        IntNode _2 = new IntNode(2);
        IntNode _4 = new IntNode(4);
        IntNode _6 = new IntNode(6);
        IntNode _8 = new IntNode(8);

        _5.left = _3;
        _5.right = _7;
        _3.left = _2;
        _3.right = _4;
        _7.left = _6;
        _7.right = _8;

        System.out.println(new CheckBST().check(_5));
        System.out.println(new CheckBST().checkMorris(_5));
    }
}
