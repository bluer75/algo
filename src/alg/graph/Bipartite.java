package alg.graph;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Given an undirected graph, return true if and only if it is bipartite.
 * Graph is bipartite if we can split it's set of nodes into two independent subsets A and B such 
 * that every edge in the graph has one node in A and another node in B.
 * 
 * The graph is given as adjacency list.
 * 
 * Example 1:
 * Input: [[1,3], [0,2], [1,3], [0,2]]
 * Output: true
 * 0----1
 * |    |
 * |    |
 * 3----2
 * We can divide the vertices into two groups: {0, 2} and {1, 3}.
 * Example 2:
 * Input: [[1,2,3], [0,2], [0,1,3], [0,2]]
 * Output: false
 * The graph looks like this:
 * 0----1
 * | \  |
 * |  \ |
 * 3----2
 * We cannot find a way to divide the set of nodes into two independent subsets.
 * 
 * Solution is based on BFS traversal.
 * It requires O(V + E) time and O(V) space.
 */
public class Bipartite {
    public boolean isBipartite(int[][] graph) {
        if (graph == null || graph.length <= 1) {
            return true;
        }
        // 0 unvisited, 1 - visited/colored with 1, 2 - visited/colored with 2
        int[] colors = new int[graph.length];
        for (int v = 0; v < graph.length; v++) {
            if (colors[v] == 0) {
                if (!bfs(graph, colors, v)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean bfs(int[][] graph, int[] colors, int v) {
        Queue<Integer> queue = new ArrayDeque<>(graph.length);
        queue.offer(v);
        colors[v] = 1;
        int color = 0;
        while (!queue.isEmpty()) {
            v = queue.poll();
            color = colors[v] == 1 ? 2 : 1;
            for (int neighbor : graph[v]) {
                if (colors[neighbor] == colors[v]) {
                    return false;
                } else if (colors[neighbor] == 0) {
                    colors[neighbor] = color;
                    queue.offer(neighbor);
                }
            }
        }
        return true;
    }

    public static void main(String... args) {
        System.out.println(new Bipartite().isBipartite(new int[][] { { 1, 2, 3 }, { 0, 2 }, { 0, 1, 3 }, { 0, 2 } }));
    }
}
