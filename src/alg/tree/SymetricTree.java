package alg.tree;

/**
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 * 
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 * 
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 * 
 * Apply DFS to visit both subtrees simultaneously and compare left with right side - O(n).
 */
public class SymetricTree {
    public boolean isSymmetric(IntNode root) {
        if (root == null) {
            return true;
        }
        return check(root.left, root.right);
    }

    private boolean check(IntNode left, IntNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left != null && right != null && left.key == right.key) {
            return check(left.left, right.right) && check(left.right, right.left);
        }
        return false;
    }

    public static void main(String... argsd) {

        IntNode _1 = new IntNode(1);
        IntNode _21 = new IntNode(2);
        IntNode _22 = new IntNode(2);
        IntNode _31 = new IntNode(3);
        IntNode _41 = new IntNode(4);

        _1.left = _21;
        _1.right = _22;
        _21.left = _31;
        _21.right = _41;
        _22.left = _41;
        _22.right = _31;

        System.out.println(new SymetricTree().isSymmetric(_1));
    }
}
