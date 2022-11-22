package alg.graph;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
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
    private enum Algorithm {
        DFS_ITERATIVE, DFS_RECURSIVE, BFS
    }

    public boolean isBipartite(int[][] graph, Algorithm alg) {
        if (graph == null || graph.length <= 1) {
            return true;
        }
        int n = graph.length;
        int[] colors = new int[n]; // 0 - unvisited, 1/-1 colored/visited
        var res = true;
        for (int v = 0; v < n && res; v++) {
            if (colors[v] == 0) {
                res = switch (alg) {
                    case DFS_ITERATIVE -> dfsIterative(graph, colors, v, 1);
                    case DFS_RECURSIVE -> dfsRecursive(graph, colors, v, 1);
                    case BFS -> bfs(graph, colors, v, 1);
                };
            }
        }
        return res;
    }

    private boolean dfsRecursive(int[][] graph, int[] colors, int node, int color) {
        colors[node] = color;
        for (int n : graph[node]) {
            if (colors[n] == 0) {
                if (!dfsRecursive(graph, colors, n, -color)) {
                    return false;
                }
            } else if (colors[n] == color) {
                return false;
            }
        }
        return true;
    }

    private boolean dfsIterative(int[][] graph, int[] colors, int node, int color) {
        Deque<Integer> stack = new ArrayDeque<>(graph.length);
        stack.push(node);
        colors[node] = color;
        while (!stack.isEmpty()) {
            int v = stack.pop();
            for (int n : graph[v]) {
                if (colors[n] == 0) {
                    colors[n] = -colors[v];
                    stack.push(n);
                } else if (colors[n] == colors[v]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean bfs(int[][] graph, int[] colors, int node, int color) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(node);
        colors[node] = color;
        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int n : graph[v]) {
                if (colors[n] == 0) {
                    colors[n] = -colors[v];
                    queue.offer(n);
                } else if (colors[n] == colors[v]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String... args) {
        int[][][] graphs = new int[][][]{
            {{1, 2, 3}, {0, 2}, {0, 1, 3}, {0, 2}},
            {{1, 3}, {0, 2}, {1, 3}, {0, 2}},
            {{}, {2, 4, 6}, {1, 4, 8, 9}, {7, 8}, {1, 2, 8, 9}, {6, 9}, {1, 5, 7, 8, 9}, {3, 6, 9}, {2, 3, 4, 6, 9},
                {2, 4, 5, 6, 7, 8}}
        };
        for (Algorithm alg : Algorithm.values()) {
            System.out.println(alg);
            for (int[][] graph : graphs) {
                System.out.println(new Bipartite().isBipartite(graph, alg));
            }
        }
    }
}
