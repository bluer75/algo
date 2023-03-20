package alg.tree;

/**
 * Let us consider the following problem to understand Segment Trees without recursion.
 * We have an array arr[0 . . . n-1]. We should be able to:
 * 1. Find the sum of elements from index l to r where 0 <= l <= r <= n - 1
 * 2. Change the value of a specified element of the array to a new value x
 *
 * A simple solution is to run a loop from l to r and calculate the sum of elements in the given range.
 * To update a value, simply do arr[i] = x.
 * The first operation takes O(n) time and the second operation takes O(1) time.
 *
 * Another solution is to create another array and calculate prefix sums.
 * The sum of a given range can now be calculated in O(1) time, but the update operation takes O(n) time now.
 * This works well if the number of query operations is large and there are very few updates.
 *
 * We can use a Segment Tree to do both operations in O(log n) time.
 */
public class SegmentTree {
    private int n; // length of input array
    private int[] tree = null;

    // function to build the tree
    void build(int[] array) {
        n = array.length;
        tree = new int[2 * n];
        // insert leaf nodes in tree starting at index n
        for (int i = 0; i < n; i++) {
            tree[n + i] = array[i];
        }
        // build the tree by calculating parents
        for (int i = n - 1; i > 0; --i) {
            tree[i] = tree[i << 1] + tree[i << 1 | 1];
        }
    }

    // update value in input array at index p
    void updateTreeNode(int p, int value) {
        // set value at position p
        tree[p + n] = value;
        p = p + n;
        // move upward and update parents
        for (int i = p; i > 1; i >>= 1) {
            // for node p, parent node is p / 2 or p >> 1
            // p ^ 1 turns (2 * i) to (2 * i + 1) and vice versa to get the second child of p
            tree[i >> 1] = tree[i] + tree[i ^ 1];
        }
    }

    // get sum for interval [l, r]
    int query(int l, int r) {
        int res = 0;
        // loop to find the sum in the range
        for (l += n, r += n; l <= r; l >>= 1, r >>= 1) {
            if ((l & 1) > 0) {
                // left is odd means it's right child -> add it to result and increase
                res += tree[l++];
            }
            // left is even means it's left child -> include its parent
            if ((r & 1) == 0) {
                // right is even means it's left child -> add it to result and decrease
                res += tree[r--];
            }
            // right is odd means it's right child -> include its parent
        }
        return res;
    }

    static public void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        SegmentTree st = new SegmentTree();
        st.build(a);
        // sum in range [1, 2] -> 2 + 3 = 5
        System.out.println(st.query(1, 2));
        // modify element at index 2: 2 -> 1
        st.updateTreeNode(2, 1);
        // sum in range [1, 2] -> 2 + 1 = 3
        System.out.println(st.query(1, 2));
    }
}
