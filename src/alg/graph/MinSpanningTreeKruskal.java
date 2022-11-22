package alg.graph;

import java.util.*;

/**
 * Kruskal's algorithm finding Minimum Spanning Tree for an undirected graph.
 * It is greedy algorithm that processes all edges using Priority Queue to select edge with minimum weight.
 * If selected edge doesn't form a cycle with already selected ones it's part of MST.
 * This implementation uses UnionFind to detect cycles - checks for connected components.
 * Kruskal's algorithm runs faster in sparse graphs.
 * It takes O(E log V) time.
 */
public class MinSpanningTreeKruskal {
    private List<Edge> mst;
    private PriorityQueue<Edge> unprocessed;
    private UnionFind uf;
    private int numOfVertices;
    private int min;

    private static class Edge {
        private final int u;
        private final int v;
        private final int w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        @Override
        public String toString() {
            return "Edge[u=" + u + ",v=" + v + ",w=" + w + "]";
        }
    }

    public MinSpanningTreeKruskal(int size, List<Edge> edges) {
        numOfVertices = size;
        uf = new UnionFind(numOfVertices);
        mst = new ArrayList<>(numOfVertices - 1);
        // edges are sorted based on weight
        unprocessed = new PriorityQueue<Edge>(Comparator.comparingInt(edge -> edge.w));
        unprocessed.addAll(edges);

    }

    public void find() {
        while (!unprocessed.isEmpty() && mst.size() < numOfVertices - 1) {
            // take edge with minimum weight
            Edge e = unprocessed.remove();
            if (!uf.find(e.u, e.v)) {
                // if it doesn't create a cycle it is part of minimum spanning tree
                uf.union(e.u, e.v);
                mst.add(e);
                min += e.w;
            }
        }
    }

    private void printMST() {
        System.out.println(mst);
        System.out.println(min);
    }

    public static void main(String... args) {
        List<Edge> edges = new LinkedList<>();
        edges.add(new Edge(0, 1, 4));
        edges.add(new Edge(0, 7, 8));
        edges.add(new Edge(1, 2, 8));
        edges.add(new Edge(1, 7, 11));
        edges.add(new Edge(2, 8, 2));
        edges.add(new Edge(2, 5, 4));
        edges.add(new Edge(2, 3, 7));
        edges.add(new Edge(3, 4, 9));
        edges.add(new Edge(3, 5, 14));
        edges.add(new Edge(4, 5, 10));
        edges.add(new Edge(5, 6, 2));
        edges.add(new Edge(6, 8, 6));
        edges.add(new Edge(6, 7, 1));
        edges.add(new Edge(7, 8, 7));

        MinSpanningTreeKruskal mst = new MinSpanningTreeKruskal(9, edges);
        mst.find();
        mst.printMST();
    }
}
