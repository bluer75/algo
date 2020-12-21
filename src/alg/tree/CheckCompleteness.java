package alg.tree;

/**
 * Given a binary tree, determine if it is a complete binary tree. 
 * In a complete binary tree every level, except possibly the last, is completely filled, and all 
 * nodes in the last level are as far left as possible. 
 * It can have between 1 and 2^h nodes inclusive at the last level h.
 * The following tree is not complete because leafs are not left ordered.
 *  
 *      1
 *     / \
 *    2   3
 *   / \   \
 *  4   5   6
 *  
 */
public class CheckCompleteness {

    private static class Res {
        int height;
        boolean full;
        boolean complete;

        Res(int height, boolean full, boolean complete) {
            this.height = height;
            this.full = full;
            this.complete = complete;
        }
    }

    /**
     * Recursive solution (similar to DFS) calculating height and completeness of each subtree.
     */
    public boolean isComplete(IntNode n) {
        return check(n, 0).complete;
    }

    private Res check(IntNode n, int level) {
        if (n == null) {
            return new Res(level, true, true);
        }
        Res l = check(n.left, level + 1);
        Res r = check(n.right, level + 1);
        Res res = new Res(l.height, false, false);
        if (l.height < r.height || l.height > r.height + 1) {
            // left subtree is shorter or right height is shorter be more than one
            res.complete = false;
        } else if (l.height > r.height) {
            // right subtree is shorter by one, it has to be full
            res.complete = l.complete && r.complete && r.full;
        } else {
            // subtrees have the same heights, left one has to be full
            res.complete = l.complete && r.complete && l.full;
            res.full = l.full && r.full;
        }
        return res;
    }

    public static void main(String... args) {
        IntNode _1 = new IntNode(1);
        IntNode _2 = new IntNode(2);
        IntNode _3 = new IntNode(3);
        IntNode _4 = new IntNode(4);
        IntNode _5 = new IntNode(5);
        IntNode _6 = new IntNode(6);

        _1.left = _2;
        _1.right = _3;
        _2.left = _4;
        _2.right = _5;
        _3.left = null;
        _3.right = _6;
        System.out.println(new CheckCompleteness().isComplete(_1));

        _1.left = _2;
        _1.right = _3;
        _2.left = _4;
        _2.right = _5;
        _3.left = _6;
        _3.right = null;
        System.out.println(new CheckCompleteness().isComplete(_1));
    }
}
