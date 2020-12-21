package alg.tree;

/**
 * A tree is "super-balanced" if the difference between the depths of any two leaf nodes 
 * is no greater than one. Note that linked list is considered as balanced as there is just one leaf.
 * 
 * The solution is to traverse the tree, find all leafs and register their depths. 
 * Then we can determine difference between min and max depth.
 * 
 * Space optimization would be to store just min and max depth. 
 * 
 * Time/Space complexity is O(n).
 */
public class CheckSuperbalance {
    public boolean isBalanced(IntNode treeRoot) {
        int[] depth = getDepth(treeRoot, 0);
        return depth[1] - depth[0] <= 1;
    }

    private int[] getDepth(IntNode node, int depth) {
        if (node.left == null && node.right == null) {
            // found leaf
            return new int[] { depth, depth };
        }
        if (node.left == null) {
            // keep searching on the right
            return getDepth(node.right, depth + 1);
        }
        if (node.right == null) {
            // keep searching on the left
            return getDepth(node.left, depth + 1);
        }
        // check both subtrees
        int[] left = getDepth(node.left, depth + 1);
        int[] right = getDepth(node.right, depth + 1);
        left[0] = Math.min(left[0], right[0]);
        left[1] = Math.max(left[1], right[1]);
        return left; // reuse left
    }

    public static void main(String... args) {
        IntNode _5 = new IntNode(5);
        IntNode _3 = new IntNode(3);
        IntNode _2 = new IntNode(2);
        IntNode _6 = new IntNode(6);
        IntNode _8 = new IntNode(8);

        _5.left = _3;
        _5.right = _8;
        _3.left = _2;
        _2.left = _6;
        System.out.println(new CheckSuperbalance().isBalanced(_5));
    }
}
