package alg.tree;

/**
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that 
 * adding up all the values along the path equals the given sum.
 * 
 * Given the below binary tree and sum = 22,
 * 
 *       5
 *      / \
 *     4   8
 *    /   / \
 *   11  13  6
 *  /  \      \
 * 7    2      1
 * 
 * return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
 */
public class PathSumRootToLeaf {
    public boolean hasPathSum(IntNode root, int sum) {
        if (root == null) {
            return false;
        }
        return hasSum(root, sum, 0);
    }

    private boolean hasSum(IntNode n, int s, int c) {
        if (n.left != null && n.right != null) {
            return hasSum(n.left, s, c + n.key) || hasSum(n.right, s, c + n.key);
        }
        if (n.left != null) {
            return hasSum(n.left, s, c + n.key);
        }
        if (n.right != null) {
            return hasSum(n.right, s, c + n.key);
        }
        return s == c + n.key;
    }

    public static void main(String... argsd) {

        IntNode _5 = new IntNode(5);
        IntNode _4 = new IntNode(4);
        IntNode _8 = new IntNode(8);
        IntNode _11 = new IntNode(11);
        IntNode _13 = new IntNode(13);
        IntNode _6 = new IntNode(6);
        IntNode _7 = new IntNode(7);
        IntNode _2 = new IntNode(2);
        IntNode _1 = new IntNode(1);

        _5.left = _4;
        _5.right = _8;
        _4.left = _11;
        _8.left = _13;
        _8.right = _6;
        _11.left = _7;
        _11.right = _2;
        _6.left = _1;

        System.out.println(new PathSumRootToLeaf().hasPathSum(_5, 22));
    }
}
