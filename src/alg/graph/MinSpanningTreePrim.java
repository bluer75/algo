package alg.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Prim's algorithm finding Minimum Spanning Tree for an undirected graph.
 * It is greedy algorithm that processes edges using Priority Queue to select edge with minimum weight.
 * It starts at any node, marks it as visited and adds all its edges to the min heap.
 * It keeps polling next min edge from the heap, and if it points to non-visited vertex it marks it as visited and adds
 * that edge to MST and all its edges to the heap (excluding the ones pointing to visited vertices).
 * Prim's algorithm runs faster in dense graphs.
 * It takes O(V^2) and can be improved up to O(E log V) using Fibonacci heaps.
 */
public class MinSpanningTreePrim {
    private Graph g;
    private List<Edge> mst;
    private PriorityQueue<Edge> minHeap;
    private UnionFind uf;
    private int numOfVertices;
    private Set<Integer> visited;
    private int min;

    private static class Edge {
        final int u;
        final int v;
        final int w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        int getWeight() {
            return w;
        }

        @Override
        public String toString() {
            return "Edge[u=" + u + ",v=" + v + ",w=" + w + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + u;
            result = prime * result + v;
            result = prime * result + w;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Edge other = (Edge) obj;
            if (u != other.u)
                return false;
            if (v != other.v)
                return false;
            if (w != other.w)
                return false;
            return true;
        }
    }

    public MinSpanningTreePrim(Graph g) {
        this.g = g;
        visited = new HashSet<>();
        numOfVertices = g.getNumOfVertices();
        mst = new ArrayList<>(numOfVertices - 1);
        // edges are sorted based on weight
        minHeap = new PriorityQueue<Edge>(Comparator.comparingInt(Edge::getWeight));
    }

    private static Set<Edge> getEdges(Graph g) {
        Set<Edge> edges = new HashSet<>();
        for (int i = 0; i < g.getNumOfVertices(); i++) {
            int index = i;
            edges.addAll(g.getEdges(i).stream().map(e -> new Edge(index, e.to, e.weight)).collect(Collectors.toSet()));
        }
        return edges;
    }

    public void find() {
        // add all edges for starting node -> 0
        visited.add(0);
        g.getEdges(0).stream().map(e -> new Edge(0, e.to, e.weight)).forEach(minHeap::offer);
        while (!minHeap.isEmpty() && mst.size() < numOfVertices - 1) {
            // take edge with minimum weight
            Edge edge = minHeap.poll();
            if (!visited.contains((edge.v))) {
                g.getEdges(edge.v).stream().filter(e -> !visited.contains(e.to)).map(
                    e -> new Edge(edge.v, e.to, e.weight)).forEach(minHeap::offer);
                mst.add(edge);
                visited.add(edge.v);
                min += edge.w;
            }
        }
    }

    private void printMST() {
        System.out.println(mst);
        System.out.println(min);
    }

    public static void main(String... args) {
        Graph g = new Graph(9);
        // make it undirected
        g.addEdge(0, 1, 4);
        g.addEdge(1, 0, 4);
        g.addEdge(0, 7, 8);
        g.addEdge(7, 0, 8);
        g.addEdge(1, 2, 8);
        g.addEdge(2, 1, 8);
        g.addEdge(1, 7, 11);
        g.addEdge(7, 1, 11);
        g.addEdge(2, 8, 2);
        g.addEdge(8, 2, 2);
        g.addEdge(2, 5, 4);
        g.addEdge(5, 2, 4);
        g.addEdge(2, 3, 7);
        g.addEdge(3, 2, 7);
        g.addEdge(3, 4, 9);
        g.addEdge(4, 3, 9);
        g.addEdge(3, 5, 14);
        g.addEdge(5, 3, 14);
        g.addEdge(4, 5, 10);
        g.addEdge(5, 4, 10);
        g.addEdge(5, 6, 2);
        g.addEdge(6, 5, 2);
        g.addEdge(6, 8, 6);
        g.addEdge(8, 6, 6);
        g.addEdge(6, 7, 1);
        g.addEdge(7, 6, 1);
        g.addEdge(7, 8, 7);
        g.addEdge(8, 7, 7);

        MinSpanningTreePrim mst = new MinSpanningTreePrim(g);
        mst.find();
        mst.printMST();
    }
}
