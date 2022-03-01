package alg.graph;

import java.util.Arrays;

/**
 * Floyd Warshall algorithm.
 * This algorithm allows to find the shortest path between all vertices in the weighted graph.
 * It works with both directed and undirected graphs, but it does not work along with the graph with negative cycles.
 * This algorithm follows the dynamic programming approach and uses adjacency matrix D that gives the length of the
 * shortest path between each pair of nodes. The matrix is initialized with direct distances between nodes, and then it
 * does n iterations, after iteration k, D gives the length of the shortest paths that only use nodes in {1,2….k} as
 * intermediate nodes. After n iterations, D, therefore, gives the length of the shortest paths using any of the nodes
 * in graph as an intermediate node. If Dk represents the matrix D after kth iteration it can be implemented by:
 * Dk[i, j] = min (Dk-1[i, j], Dk-1[i, k] + Dk-1[k, j])
 *
 * Time complexity is O(n^3)
 * Space complexity is O(n^2)
 */
public class FloydWarshall {
    public double[][] find(int numNodes, int[][] edges) {
        double[][] dist = new double[numNodes][numNodes];
        // initialize distance with 0 (the same node), Infinity (no edge) or given weight (from edges)
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                dist[i][j] = (i == j) ? 0 : Double.POSITIVE_INFINITY;
            }
        }
        // set weights from edges
        for (int[] edge : edges) {
            dist[edge[0]][edge[1]] = edge[2];
        }
        // calculate the shortest paths
        for (int k = 0; k < numNodes; k++) {
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        int[][] edges = new int[][]{{0, 1, 5}, {1, 0, 50}, {1, 2, 15}, {1, 3, 5}, {2, 0, 30}, {2, 3, 15}, {3, 0, 15},
            {3, 2, 5}};
        double[][] dist = new FloydWarshall().find(4, edges);
        System.out.println(Arrays.deepToString(dist));
    }
}
