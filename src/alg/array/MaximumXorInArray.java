package alg.array;

/**
 * Given an integer array, return the maximum result of nums[i] XOR nums[j], where 0 <= i <= j < n.
 *
 * For array [3,10,5,25,2,8] the result is 28 as it is the xor of 5 and 25.
 *
 * Solution is using binary tree where each node can have maximum two children representing 0 and 1.
 * We go through each number in array and use its binary representation to calculate max xor with values already in the
 * tree. To maximize XOR, we choose the opposite bit at each step whenever it's possible - as 0^1=1 and 1^0=1. Then the
 * value is inserted into the tree.
 *
 * This takes O(n) time.
 */
public class MaximumXorInArray {

    // tree node with max two children: 0 and 1
    private static class Node {
        private Node[] nodes = new Node[2];
    }

    private void insert(int v, Node root) {
        Node node = root;
        // insert all bits into tree
        for (int bit = 0, mask = 0x40000000; mask > 0; mask >>= 1) {
            bit = (v & mask) > 0 ? 1 : 0;
            if (node.nodes[bit] == null) {
                node.nodes[bit] = new Node();
            }
            node = node.nodes[bit];
        }
    }

    private int max(int v, Node root) {
        Node node = root;
        int xor = 0;
        // traverse the tree, calculate xor and select opposite bit if exists
        for (int bit = 0, neg = 0, mask = 0x40000000; mask > 0; mask >>= 1) {
            bit = (v & mask) > 0 ? 1 : 0;
            neg = bit == 1 ? 0 : 1;
            xor <<= 1;
            if (node.nodes[neg] == null) {
                node = node.nodes[bit];
            } else {
                node = node.nodes[neg];
                xor |= 1;
            }
        }
        return xor;
    }

    public int findMaximumXOR(int[] nums) {
        int max = 0;
        Node root = new Node();
        insert(nums[0], root); // insert first value to avoid NPE
        for (int num : nums) {
            max = Math.max(max, max(num, root));
            insert(num, root);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new MaximumXorInArray().findMaximumXOR(new int[]{3, 10, 5, 25, 2, 8}));
    }
}
