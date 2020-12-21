package alg.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Articulation Points and Bridges detection.
 */
public class CriticalConnections {

    /**
     * Brute force solution that removes one connection and runs DFS to check if all vertices can be reached.
     * This takes O(EV + E^2).  
     */
    public List<List<Integer>> criticalConnectionsBF(int n, List<List<Integer>> connections) {
        List<List<Integer>> res = new LinkedList<>();
        for (List<Integer> l : connections) {
            if (isDisconnected(getMap(connections, l), n)) {
                res.add(l);
            }
        }
        return res;
    }

    private Map<Integer, List<Integer>> getMap(List<List<Integer>> connections, List<Integer> ignore) {
        Map<Integer, List<Integer>> map = new HashMap<>(connections.size() * 2);
        for (List<Integer> l : connections) {
            if (l == ignore) {
                continue;
            }
            int a = l.get(0);
            int b = l.get(1);
            map.putIfAbsent(a, new LinkedList<Integer>());
            map.get(a).add(b);
            map.putIfAbsent(b, new LinkedList<Integer>());
            map.get(b).add(a);
        }
        return map;
    }

    private boolean isDisconnected(Map<Integer, List<Integer>> graph, int n) {
        boolean[] visited = new boolean[n];
        dfs(graph, 0, visited);
        for (boolean b : visited) {
            if (!b) {
                return true;
            }
        }
        return false;
    }

    private void dfs(Map<Integer, List<Integer>> graph, int i, boolean[] visited) {
        if (visited[i] || !graph.containsKey(i)) {
            return;
        }
        visited[i] = true;
        for (int v : graph.get(i)) {
            dfs(graph, v, visited);
        }
    }

    /**
     * Use DFS to explore the graph and annotates each node with id and low level value. 
     * It allows to detect bridges - cut edges - if id(from) < lowLevelValue(to).
     * This takes O(V + E).  
     */
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        boolean[] visited = new boolean[n];
        int[] ids = new int[n];
        int[] llv = new int[n];
        List<List<Integer>> bridges = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(getMap(connections), i, -1, 0, ids, llv, visited, bridges);
            }
        }
        return bridges;
    }

    private Map<Integer, List<Integer>> getMap(List<List<Integer>> connections) {
        Map<Integer, List<Integer>> map = new HashMap<>(connections.size() * 2);
        for (List<Integer> l : connections) {
            int a = l.get(0);
            int b = l.get(1);
            map.putIfAbsent(a, new LinkedList<Integer>());
            map.get(a).add(b);
            map.putIfAbsent(b, new LinkedList<Integer>());
            map.get(b).add(a);
        }
        return map;
    }

    private void dfs(Map<Integer, List<Integer>> graph, int node, int parent, int id, int[] ids, int[] llv,
            boolean[] visited, List<List<Integer>> bridges) {
        visited[node] = true;
        ids[node] = id;
        llv[node] = id;
        // execute dfs for each neighbor
        for (int n : graph.get(node)) {
            if (n == parent) {
                continue;
            }
            if (!visited[n]) {
                dfs(graph, n, node, id + 1, ids, llv, visited, bridges);
                // update lowLevelValue for current node if neighbor has lower value
                llv[node] = Math.min(llv[node], llv[n]);
                // check bridge condition
                if (ids[node] < llv[n]) {
                    bridges.add(Arrays.asList(node, n));
                }
            } else {
                // update lowLevelValue for current node if node with lower id is reachable
                llv[node] = Math.min(llv[node], ids[n]);
            }
        }
    }

    public static void main(String... args) {
        // [[0,1],[1,2],[2,0],[1,3]]
        List<List<Integer>> connections = List.of(List.of(0, 1), List.of(1, 2), List.of(2, 0), List.of(1, 3));
        System.out.println(new CriticalConnections().criticalConnectionsBF(4, connections));
        System.out.println(new CriticalConnections().criticalConnections(4, connections));
    }
}
