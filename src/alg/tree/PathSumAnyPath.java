package alg.tree;

/**
 * Given a binary tree and a sum, find the number of paths that sum to a given value.
 * The path does not need to start or end at the root or a leaf, but it must go from parent nodes to child nodes.
 * For given tree and sum 8
 * 
 *       10
 *      / \
 *     5   -3
 *    /   / \
 *   3   2  11
 *  /  \      \
 * 3   -2      1
 * 
 * return 3, as there exist a 3 different paths with sum 8:
 * 1.  5 -> 3
 * 2.  5 -> 2 -> 1
 * 3. -3 -> 11
 * 
 * Solution is based on prefix sums. 
 * We do recursive dfs traversal and for each node we pass prefix sums from nodes above and check if 
 * current node creates target sum. Then we follow left and right child with new list of sums created 
 * by adding value from current node to all sums from above.   
 * 
 * It takes O(n) time and O(log n) space - n is the number of nodes.
 *  
 */
public class PathSumAnyPath {
    public int pathSum(TreeNode root, int sum) {
        return visit(root, new int[0], sum);
    }

    private int visit(TreeNode node, int[] sums, int sum) {
        if (node == null) {
            return 0;
        }
        // new sums is greater by one
        int[] newSums = new int[sums.length + 1];
        System.arraycopy(sums, 0, newSums, 1, sums.length);
        int count = 0;
        // add current node to sums from parent
        for (int i = 0; i < newSums.length; i++) {
            newSums[i] += node.val;
            if (newSums[i] == sum) {
                count++;
            }
        }
        // visit left and right child
        return count + visit(node.left, newSums, sum) + visit(node.right, newSums, sum);
    }

    public static void main(String... argsd) {

        TreeNode _10 = new TreeNode(10);
        TreeNode _5 = new TreeNode(5);
        TreeNode _n3 = new TreeNode(-3);
        TreeNode _3 = new TreeNode(3);
        TreeNode _2 = new TreeNode(2);
        TreeNode _11 = new TreeNode(11);
        TreeNode _3_2 = new TreeNode(3);
        TreeNode _n2 = new TreeNode(-2);
        TreeNode _1 = new TreeNode(1);

        _10.left = _5;
        _10.right = _n3;
        _5.left = _3;
        _5.right = _2;
        _n3.right = _11;
        _3.left = _3_2;
        _3.right = _n2;
        _2.right = _1;

        System.out.println(new PathSumAnyPath().pathSum(_10, 8));
    }
}
