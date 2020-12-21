package alg.graph;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GraphDFS {
    private Graph graph;
    private boolean[] discovered;
    private boolean[] processed;
    private int[] parents;
    private int[] dist;
    boolean hasCycle;

    public void dfsRecursively(Graph graph) {
        this.graph = graph;
        List<Integer> vertices = graph.getVertices();
        parents = new int[vertices.size()];
        discovered = new boolean[vertices.size()];
        processed = new boolean[vertices.size()];
        dist = new int[vertices.size()];
        for (int vertex : vertices) {
            if (!discovered[vertex]) {
                visitRecursively(vertex);
            }
        }
    }

    /**
     * Recursively visits vertices.
     */
    private void visitRecursively(int vertex) {
        discovered[vertex] = true;
        for (int v : graph.getAdj(vertex)) {
            if (!discovered[v]) {
                parents[v] = vertex;
                dist[v] = dist[vertex] + 1;
                visitRecursively(v);
            } else if (!processed[v]) {
                hasCycle = true;
            }
        }
        processed[vertex] = true;
    }

    public void dfsIteratively(Graph graph) {
        this.graph = graph;
        List<Integer> vertices = graph.getVertices();
        parents = new int[vertices.size()];
        discovered = new boolean[vertices.size()];
        processed = new boolean[vertices.size()];
        dist = new int[vertices.size()];
        for (int vertex : vertices) {
            if (!discovered[vertex]) {
                visitIteratively(vertex);
            }
        }
    }

    /**
     * Iteratively visits vertices.
     */
    private void visitIteratively(int vertex) {
        Deque<Integer> stack = new LinkedList<>();
        stack.push(vertex);
        int v;
        while (!stack.isEmpty()) {
            v = stack.pop();
            discovered[v] = true;
            for (int u : graph.getAdj(v)) {
                if (!discovered[u]) {
                    parents[u] = v; // vertex which pushed it on the stack is the parent
                    dist[u] = dist[v] + 1;
                    stack.push(u);
                } else if (!processed[u]) {
                    hasCycle = true;
                }
            }
            processed[v] = true;
        }
    }

    /**
     * Returns traversal order.
     */
    public String getParents() {
        String res = "";
        for (int i = 0; i < parents.length; i++) {
            res += parents[i] + "->" + i + " ";
        }
        return res;
    }

    /**
     * Returns distance.
     */
    public String getDistance() {
        return Arrays.toString(dist);
    }

    public static void main(String... args) {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);

        GraphDFS dfs = new GraphDFS();
        dfs.dfsRecursively(g);
        System.out.println(dfs.getParents());
        System.out.println(dfs.getDistance());
        System.out.println("Has cycle: " + dfs.hasCycle);
        dfs = new GraphDFS();
        dfs.dfsIteratively(g);
        System.out.println(dfs.getParents());
        System.out.println(dfs.getDistance());
        System.out.println("Has cycle: " + dfs.hasCycle);
    }
}
