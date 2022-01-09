package alg.array;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.*;

/**
 * There are n computers numbered from 0 to n-1 connected by ethernet cables connections forming a network where 
 * connections[i] = [a, b] represents a connection between computers a and b. 
 * Any computer can reach any other computer directly or indirectly through the network.
 * 
 * Given an initial computer network connections. You can extract certain cables between two directly connected 
 * computers, and place them between any pair of disconnected computers to make them directly connected. 
 * Return the minimum number of times you need to do this in order to make all the computers connected. 
 * If it's not possible, return -1. 
 * 
 * Solutions is based on the fact that in order to connect n computers we need minimum n-1 cables and we don't need to 
 * find out which cables can be reconnected. 
 * Explore graph (here with DFS) and find number of disconnected components and compare it with amount of cables (connections).
 * Time complexity is O(n + 2m), n - nodes, m - edges, we need to be able to traverse both direction.
 * 
 * Other option is to use Union-Find algorithm to count connected components.
 */
public class ConnectedNetwork {
    public int makeConnected(int n, int[][] connections) {
        int clusters = 0;
        int cables = connections.length;
        boolean[] visited = new boolean[n];
        Map<Integer, List<Integer>> conns = new HashMap<>(connections.length);
        for (int[] c : connections) {
            conns.putIfAbsent(c[0], new LinkedList<>());
            conns.get(c[0]).add(c[1]);
            conns.putIfAbsent(c[1], new LinkedList<>());
            conns.get(c[1]).add(c[0]);
        }
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                ++clusters;
                explore(i, conns, visited);
            }
        }
        return cables >= n - 1 ? clusters - 1 : -1;
    }

    private void explore(int i, Map<Integer, List<Integer>> connections, boolean[] visited) {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(i);
        int n = 0;
        while (!queue.isEmpty()) {
            n = queue.remove();
            visited[n] = true;
            if (connections.containsKey(n)) {
                for (int c : connections.get(n)) {
                    if (!visited[c]) {
                        queue.offer(c);
                    }
                }
            }
        }
    }

    /**
     * Uses UnionFind structure - time complexity is O(m) where m is number of edges, 
     * space complexity is O(n) where n is number of vertices
     */
    public int makeConnectedUF(int n, int[][] connections) {
        UnionFind uf = new UnionFind(n);
        for (int[] c : connections) {
            uf.union(c[0], c[1]);
        }
        int cc = uf.getNumCC();
        return connections.length >= n - 1 ? cc - 1 : -1;
    }

    @SuppressWarnings("unused")
    // <Complexity time(s):
    // Construction: O(n)
    // Union/Find: O(1) - amortized constant time
    private static class UnionFind {
        private int[] ids;
        private int[] sizes;
        private int cc;

        public UnionFind(int n) {
            ids = new int[n];
            for (int i = 0; i < n; i++) {
                ids[i] = i;
            }
            sizes = new int[n];
            Arrays.fill(sizes, 1);
            cc = n;
        }

        // connect a and b
        // O(k) - amortized
        public void union(int a, int b) {
            int roota = root(a);
            int rootb = root(b);
            if (roota == rootb) {
                return;
            }
            if (sizes[roota] < sizes[rootb]) {
                sizes[rootb] += sizes[roota];
                ids[roota] = rootb;
            } else {
                sizes[roota] += sizes[rootb];
                ids[rootb] = roota;
            }
            cc--;
        }

        // true if a and b are connected
        public boolean find(int a, int b) {
            return root(a) == root(b);
        }

        // finds root of i
        // performs path compression
        private int root(int i) {
            int root = i;
            while (root != ids[root]) {
                root = ids[root];
            }
            int n = i;
            int next;
            while (n != root) {
                next = ids[n];
                ids[n] = root; // set root as parent
                n = next;
            }
            return root;
        }

        // number of connected components
        public int getNumCC() {
            return cc;
        }
    }

    public static void main(String... args) {
        int n = 11;
        int[][] connections = new int[][] { { 1, 4 }, { 0, 3 }, { 1, 3 }, { 3, 7 }, { 2, 7 }, { 0, 1 }, { 2, 4 },
                { 3, 6 }, { 5, 6 }, { 6, 7 }, { 4, 7 }, { 0, 7 }, { 5, 7 } };
        System.out.println(new ConnectedNetwork().makeConnected(n, connections));
        System.out.println(new ConnectedNetwork().makeConnectedUF(n, connections));
    }
}
