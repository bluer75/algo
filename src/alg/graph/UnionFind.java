package alg.graph;

import java.util.Arrays;

/**
 * Tree based implementation.
 */
public class UnionFind {
    private int[] size;
    private int[] parent;

    public UnionFind(int num) {
        parent = new int[num];
        for (int i = 0; i < num; i++) {
            parent[i] = i;
        }
        size = new int[num];
        Arrays.fill(size,  1);
    }

    /**
     * Checks if both have the same parent
     */
    public boolean find(int p, int q) {
        return root(p) == root(q);
    }

    /**
     * Merges p and q.
     */
    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        // if not connected
        if (rootP != rootQ) {
            // make smaller root point to larger one
            if (size[rootP] < size[rootQ]) {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            } else {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            }
        }
        print("(" + p + "," + q + ") ");
    }

    /**
     * Finds root of given element.
     */
    private int root(int i) {
        int p = i;
        while (p != parent[p]) {
            p = parent[p];
        }
        return p;
    }

    private void print(String str) {
        System.out.println(str + Arrays.toString(parent));
    }

    public static void main(String... args) {
        UnionFind uf = new UnionFind(10);
        uf.union(3, 4);
        uf.union(4, 9);
        uf.union(8, 0);
        uf.union(2, 3);
        uf.union(5, 6);
        uf.union(5, 9);
        uf.union(7, 3);
        uf.union(4, 8);
        uf.union(6, 1);
    }
}