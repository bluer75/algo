package alg.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Kruskal's algorithm finding Minimum Spanning Tree.
 * It is greedy algorithm that processes all edges using Priority Queue to select edge with minimum weight.
 * If selected edge doesn't form a cycle with already selected ones it's part of MST.
 * This implementation uses UnionFind to detect cycles - checks for connected components.
 */
public class MinSpanningTreeKruskal {
    private List<Edge> mst;
    private PriorityQueue<Edge> unprocessed;
    private UnionFind uf;
    private int numOfVertices;

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

    public MinSpanningTreeKruskal(Graph g) {
        numOfVertices = g.getNumOfVertices();
        uf = new UnionFind(numOfVertices);
        mst = new ArrayList<>(numOfVertices - 1);
        // edges are sorted based on weight
        unprocessed = new PriorityQueue<Edge>(Comparator.comparingInt(Edge::getWeight));
        unprocessed.addAll(getEdges(g));

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
        while (!unprocessed.isEmpty() && mst.size() < numOfVertices - 1) {
            // take edge with minimum weight
            Edge e = unprocessed.remove();
            if (!uf.find(e.u, e.v)) {
                // if it doesn't create a cycle it is part of minimum spanning tree
                uf.union(e.u, e.v);
                mst.add(e);
            }
        }
    }

    private void printMST() {
        System.out.println(mst);
    }

    public static void main(String... args) {
        Graph g = new Graph(9);
        g.addEdge(0, 1, 4);
        g.addEdge(0, 7, 8);
        g.addEdge(1, 2, 8);
        g.addEdge(1, 7, 11);
        g.addEdge(2, 8, 2);
        g.addEdge(2, 5, 4);
        g.addEdge(2, 3, 7);
        g.addEdge(3, 4, 9);
        g.addEdge(3, 5, 14);
        g.addEdge(4, 5, 10);
        g.addEdge(5, 6, 2);
        g.addEdge(6, 8, 6);
        g.addEdge(6, 7, 1);
        g.addEdge(7, 8, 7);

        MinSpanningTreeKruskal mst = new MinSpanningTreeKruskal(g);
        mst.find();
        mst.printMST();
    }
}
