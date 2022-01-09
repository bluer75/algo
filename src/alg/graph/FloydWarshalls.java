package alg.graph;

import java.util.Arrays;

/**
 * Floyd Warshall's algorithm to find the shortest paths for all pairs.
 *
 * This is DP solution and takes O(n^3) time - where n is number of vertices.
 * dp[i][j] - represents the shortest path so far between vertices i and j, it is initially initialised with
 * weights from direct edges and INFINITY for non-direct edges.
 *
 * The basic idea behind Floyd Warshall’s is to gradually allow the usage of intermediate vertices (vertex 0..k) to
 * form the shortest paths.
 * We start with direct edges only, then, we check if there is a shorter path going through vertex 0, then 0, 1, then
 * 0..k.
 */
public class FloydWarshalls {
    public int[][] find(int v, int[][] edges) {
        int[][] dp = init(v, edges);
        // loop order is k->i->j
        for (int k = 0; k < v; k++) {
            for (int i = 0; i < v; i++) {
                for (int j = 0; j < v; j++) {
                    // check if there is a shorter path from i to j going through vertex k
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                }
            }
        }
        return dp;
    }

    private int[][] init(int v, int[][] edges) {
        int[][] dp = new int[v][v];
        for (int[] row : dp) {
            // infinity for non-direct edges
            Arrays.fill(row, (int) 1e9);
        }
        for (int[] edge : edges) {
            // direct edges
            dp[edge[0]][edge[1]] = edge[2];
        }
        for (int i = 0; i < v; i++) {
            // self edges
            dp[i][i] = 0;
        }
        return dp;
    }

    public static void main(String[] args) {
        int[][] edges = {
            {0, 1, 2}, {0, 2, 1}, {0, 4, 3},
            {1, 3, 4},
            {2, 1, 1}, {2, 4, 1}, {3, 0, 1},
            {3, 2, 3}, {3, 4, 5}};
        System.out.println(Arrays.deepToString(new FloydWarshalls().find(5, edges)));
    }
}