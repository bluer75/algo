package alg.graph;

import java.util.*;

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
    private Map<Integer, List<Edge>> vertexEdges;
    private List<Edge> mst;
    private PriorityQueue<Edge> minHeap;
    private int numOfVertices;
    private Set<Integer> visited;
    private int min;

    private static class Edge {
        final int u;
        final int v;
        final int w;

        Edge(int from, int to, int w) {
            this.u = from;
            this.v = to;
            this.w = w;
        }

        @Override
        public String toString() {
            return "Edge[u=" + u + ",v=" + v + ",w=" + w + "]";
        }
    }

    public MinSpanningTreePrim(int size, List<Edge> edges) {
        visited = new HashSet<>();
        numOfVertices = size;
        vertexEdges = new HashMap<>();
        for (Edge edge : edges) {
            vertexEdges.putIfAbsent(edge.u, new LinkedList<>());
            vertexEdges.get(edge.u).add(edge);
            // make it undirected - create opposite edge
            vertexEdges.putIfAbsent(edge.v, new LinkedList<>());
            vertexEdges.get(edge.v).add(new Edge(edge.v, edge.u, edge.w));
        }
        mst = new ArrayList<>(numOfVertices - 1);
        // edges are sorted based on weight
        minHeap = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.w));
    }

    public void find() {
        // add all edges for starting node -> 0
        visited.add(0);
        vertexEdges.get(0).forEach(minHeap::offer);
        while (!minHeap.isEmpty() && mst.size() < numOfVertices - 1) {
            // take edge with minimum weight
            Edge edge = minHeap.poll();
            if (!visited.contains((edge.v))) {
                vertexEdges.get(edge.v).stream().filter(e -> !visited.contains(e.u)).forEach(minHeap::offer);
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

        MinSpanningTreePrim mst = new MinSpanningTreePrim(9, edges);
        mst.find();
        mst.printMST();
    }
}
