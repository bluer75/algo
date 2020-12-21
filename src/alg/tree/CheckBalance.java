package alg.tree;

/**
 * Check if given binary tree is balanced (size of left and right differs max by 1).
 * It checks recursively if left and right child is balanced and also returns size of subtree. 
 */
public class CheckBalance {
    private static class Res {
        boolean balanced;
        int size;

        Res(boolean balanced, int size) {
            this.balanced = balanced;
            this.size = size;
        }
    }

    public boolean isBalanced(IntNode n) {
        if (n == null) {
            return true;
        }
        return check(n).balanced;
    }

    private Res check(IntNode n) {
        if (n == null) {
            return new Res(true, 0);
        }
        Res lres = check(n.left);
        Res rres = check(n.right);
        Res res = new Res(false, 0);
        res.balanced = lres.balanced && rres.balanced && Math.abs(lres.size - rres.size) <= 1;
        res.size = lres.size + rres.size + 1;
        return res;
    }

    /**
     * Solution that recursively checks height of both subtrees for each node.
     * It uses -1 value to indicate that subtree is not balanced so it is then propagated up.
     */
    public boolean isBalancedUsingHeight(IntNode root) {
        if (root == null) {
            return true;
        }
        return getHeight(root) != -1;
    }

    private int getHeight(IntNode node) {
        if (node == null) {
            return 0;
        }
        int left = getHeight(node.left);
        int right = getHeight(node.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) {
            return -1;
        }
        return Math.max(left, right) + 1;
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
        System.out.println(new CheckBalance().isBalanced(_5));
        System.out.println(new CheckBalance().isBalancedUsingHeight(_5));
    }
}
