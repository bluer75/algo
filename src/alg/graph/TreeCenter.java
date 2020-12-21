package alg.graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * For an undirected graph with tree characteristics, we can choose any node as the root. 
 * The result graph is then a rooted tree. Among all possible rooted trees, those with minimum 
 * height are called minimum height trees (MHTs). Given such a graph, write a function to find 
 * all the MHTs and return a list of their root labels.
 * 
 * The graph contains n nodes which are labeled from 0 to n - 1. You will be given the number n and 
 * a list of undirected edges (each edge is a pair of labels).
 * 
 * You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is 
 * the same as [1, 0] and thus will not appear together in edges.
 * 
 * Example 1 :
 * 
 * Input: n = 4, edges = [[1, 0], [1, 2], [1, 3]]
 * 
 *         0
 *         |
 *         1
 *        / \
 *       2   3 
 * 
 * Output: [1]
 * Example 2 :
 * 
 * Input: n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]
 * 
 *      0  1  2
 *       \ | /
 *         3
 *         |
 *         4
 *         |
 *         5 
 * 
 * Output: [3, 4]
 * 
 * Solution is either one or two nodes in the center of the tree. We use Kahn’s algorithm and peel off
 * leaves in each iteration what remains is either one node or two nodes in the center.
 * 
 * Complexity is O(V + E).
 */
public class TreeCenter {
    public List<Integer> find(int n, int[][] edges) {
        if (n == 0 || edges == null) {
            return Collections.emptyList();
        }
        // compute indegree for each node and create adjacency lists
        Map<Integer, Set<Integer>> neighbors = new HashMap<>();
        int[] indegree = new int[n];
        for (int[] edge : edges) {
            neighbors.putIfAbsent(edge[0], new HashSet<>());
            neighbors.get(edge[0]).add(edge[1]);
            indegree[edge[0]]++;
            neighbors.putIfAbsent(edge[1], new HashSet<>());
            neighbors.get(edge[1]).add(edge[0]);
            indegree[edge[1]]++;
        }
        // insert all leaves in to the queue, count each inserted node
        int processed = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] <= 1) {
                queue.offer(i);
                processed++;
            }
        }
        // as long as there are unprocessed nodes and queue is not empty
        // process all leaves and decrease indegree to detect new leaves
        // keep count of processed nodes to know when to stop
        // we don't want to process node(s) in the center
        int v = -1;
        while (processed < n && !queue.isEmpty()) {
            int count = queue.size();
            while (count-- > 0) {
                v = queue.remove();
                indegree[v] = 0;
                for (int neighbor : neighbors.getOrDefault(v, Collections.emptySet())) {
                    if (--indegree[neighbor] == 1) {
                        queue.offer(neighbor);
                        processed++;
                    }
                }
            }
        }
        // what is left in the queue is tree center node(s)
        return new LinkedList<Integer>(queue);
    }

    public static void main(String... args) {
        System.out.println(new TreeCenter().find(4, new int[][] { { 1, 0 }, { 1, 2 }, { 1, 3 } }));
    }
}
