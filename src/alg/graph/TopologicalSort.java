package alg.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.stream.Collectors;

public class TopologicalSort {
    private Graph g;
    private boolean[] discovered;
    private boolean[] processed;
    private int[] parents;
    private Deque<Integer> stack;

    TopologicalSort(Graph g) {
        this.g = g;
    }

    void sort() {
        int numOfVertices = g.getNumOfVertices();
        discovered = new boolean[numOfVertices];
        processed = new boolean[numOfVertices];
        parents = new int[numOfVertices];
        stack = new ArrayDeque<>(numOfVertices);
        for (int v : g.getVertices()) {
            if (!discovered[v]) {
                // use deep first search
                dfs(v);
            }
        }
    }

    private void dfs(int v) {
        discovered[v] = true;
        for (int u : g.getAdj(v)) {
            if (!discovered[u]) {
                parents[u] = v;
                dfs(u);
            } else if (!processed[u]) {
                throw new RuntimeException("Cycle detected by processing edge [" + v + "," + u + "]");
            }
        }
        processed[v] = true;
        // processed vertices in reversed order -> topological sort
        stack.push(v);
    }

    /**
     * Returns topological order.
     */
    public String getSorted() {
        return stack.stream().map(String::valueOf).collect(Collectors.joining("->"));
    }

    /**
     * Kahn’s algorithm.
     */
    public int[] sortKahn() {
        int n = g.getNumOfVertices();
        int[] indegree = new int[n];
        int[] topo = new int[n];
        int idx = 0;
        int processed = 0; // allows cycle detection
        Deque<Integer> queue = new ArrayDeque<>();
        // count indegree for each node
        for (int v = 0; v < n; v++) {
            for (int neighbour : g.getAdj(v)) {
                indegree[neighbour]++;
            }
        }
        // add vertices with no incoming edges to queue
        for (int v = 0; v < n; v++) {
            if (indegree[v] == 0) {
                queue.offer(v);
            }
        }
        int v = 0;
        while (!queue.isEmpty()) {
            v = queue.poll();
            processed++;
            topo[idx++] = v;
            // peel off vertices with no incoming edges and decrease indegree for its neighbors 
            for (int neighbour : g.getAdj(v)) {
                indegree[neighbour]--;
                if (indegree[neighbour] == 0) {
                    queue.offer(neighbour);
                }
            }
        }
        if (processed < n) {
            throw new RuntimeException("Cycle detected");
        }
        return topo;
    }

    /**
     * Returns topological order.
     */
    public String getSorted(int[] topo) {
        return Arrays.stream(topo).boxed().map(String::valueOf).collect(Collectors.joining("->"));
    }

    public static void main(String... args) {
        Graph g = new Graph(8);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 5);
        g.addEdge(4, 5);
        g.addEdge(3, 4);
        g.addEdge(6, 7);
        g.addEdge(0, 7);

        TopologicalSort ts = new TopologicalSort(g);
        ts.sort();
        System.out.println(ts.getSorted());
        int[] topo = ts.sortKahn();
        System.out.println(ts.getSorted(topo));

        // create cycle
        g.addEdge(5, 0);
        ts = new TopologicalSort(g);
        try {
            ts.sort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            topo = ts.sortKahn();
            System.out.println(ts.getSorted(topo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
