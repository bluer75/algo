package alg.graph;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), 
 * write a function to find the number of connected components in an undirected graph.
 * Example:
 * Input: n = 5 and edges = [[0, 1], [1, 2], [3, 4]]
 * 
 *      0          3
 *      |          |
 *      1 --- 2    4 
 * 
 * Output: 2
 *
 * BFS/DFS based solution takes O(V + E) time and space.
 * UnionFind takes O(E) time and O(V) space.
 */
public class CountConnectedComponents {

    private int[] ids;
    private int[] sizes;

    public int countComponentsUF(int n, int[][] edges) {
        ids = new int[n];
        sizes = new int[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
            sizes[i] = 1;
        }
        int cc = n;
        for (int[] edge : edges) {
            if (union(edge[0], edge[1])) {
                cc--;
            }
        }
        return cc;
    }

    private int root(int i) {
        int root = i;
        while (ids[root] != root) {
            root = ids[root];
        }
        int n = i;
        int parent = i;
        while (n != root) {
            parent = ids[n];
            ids[n] = root;
            n = parent;
        }
        return root;
    }

    private boolean union(int a, int b) {
        int rootA = root(a);
        int rootB = root(b);
        if (rootA == rootB) {
            return false;
        }
        if (sizes[rootA] < sizes[rootB]) {
            sizes[rootB] += sizes[rootA];
            ids[rootA] = rootB;
        } else {
            sizes[rootB] += sizes[rootA];
            ids[rootB] = rootA;
        }
        return true;
    }

    public int countComponentsDFS(int n, int[][] edges) {
        Map<Integer, Set<Integer>> neighbors = new HashMap<>();
        for (int[] edge : edges) {
            neighbors.putIfAbsent(edge[0], new HashSet<>());
            neighbors.get(edge[0]).add(edge[1]);
            neighbors.putIfAbsent(edge[1], new HashSet<>());
            neighbors.get(edge[1]).add(edge[0]);
        }
        boolean[] visited = new boolean[n];
        int cc = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                cc++;
                visited[i] = true;
                dfs(neighbors, visited, i);
            }
        }
        return cc;
    }

    private void dfs(Map<Integer, Set<Integer>> neighbors, boolean[] visited, int vertex) {
        if (neighbors.containsKey(vertex)) {
            for (int neighbor : neighbors.get(vertex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    dfs(neighbors, visited, neighbor);
                }
            }
        }
    }

    public int countComponentsBFS(int n, int[][] edges) {
        Map<Integer, Set<Integer>> neighbors = new HashMap<>();
        for (int[] edge : edges) {
            neighbors.putIfAbsent(edge[0], new HashSet<>());
            neighbors.get(edge[0]).add(edge[1]);
            neighbors.putIfAbsent(edge[1], new HashSet<>());
            neighbors.get(edge[1]).add(edge[0]);
        }
        boolean[] visited = new boolean[n];
        int cc = 0;
        Deque<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                cc++;
                visited[i] = true;
                queue.offer(i);
                int vertex = 0;
                while (!queue.isEmpty()) {
                    vertex = queue.poll();
                    if (neighbors.containsKey(i)) {
                        for (int neighbor : neighbors.get(vertex)) {
                            if (!visited[neighbor]) {
                                visited[neighbor] = true;
                                queue.offer(neighbor);
                            }
                        }
                    }
                }

            }
        }
        return cc;
    }

    public static void main(String... args) {
        int n = 5;
        int[][] edges = { { 0, 1 }, { 1, 2 }, { 3, 4 } };
        CountConnectedComponents ccc = new CountConnectedComponents();
        System.out.println(ccc.countComponentsBFS(n, edges));
        System.out.println(ccc.countComponentsDFS(n, edges));
        System.out.println(ccc.countComponentsUF(n, edges));
    }
}
