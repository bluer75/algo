package alg.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Directed and weighted graph.
 */
public class Graph {
    static class Edge {
        final int to;
        final int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + to;
            result = prime * result + weight;
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
            if (to != other.to)
                return false;
            if (weight != other.weight)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Edge[to=" + to + ",weight=" + weight + "]";
        }
    }

    private final boolean directed;
    private List<List<Edge>> adj;

    public Graph(int numOfVertices) {
        this(numOfVertices, true);
    }

    public Graph(int numOfVertices, boolean directed) {
        this.directed = directed;
        adj = new ArrayList<>(numOfVertices);
        IntStream.range(0, numOfVertices).forEach(v -> adj.add(new ArrayList<>()));
    }

    /**
     * Constructs graph from given adjacency matrix.
     */
    public Graph(int[][] matrix) {
        this(matrix.length);
        validate(matrix);
        // convert matrix to adjacency list
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] != 0) {
                    addEdge(i, j, matrix[i][j]);
                }
                if (matrix[j][i] != 0) {
                    addEdge(j, i, matrix[j][i]);
                }
            }
        }

    }

    public void addEdge(int from, int to) {
        addEdge(from, to, 0);
        if (!directed) {
            addEdge(to, from, 0);
        }
    }

    public void addEdge(int from, int to, int weight) {
        validate(from, to);
        Edge e = new Edge(to, weight);
        if (!adj.get(from).contains(e)) {
            adj.get(from).add(e);
        }
        if (!directed) {
            e = new Edge(from, weight);
            if (!adj.get(to).contains(e)) {
                adj.get(to).add(e);
            }
        }
    }

    public int getNumOfVertices() {
        return adj.size();
    }

    public List<Integer> getVertices() {
        return IntStream.range(0, adj.size()).boxed().collect(Collectors.toList());
    }

    public List<Integer> getAdj(int vertex) {
        validate(vertex);
        return adj.get(vertex).stream().mapToInt(e -> e.to).boxed().collect(Collectors.toList());
    }

    public List<Edge> getEdges(int vertex) {
        validate(vertex);
        return Collections.unmodifiableList(adj.get(vertex));
    }

    public List<List<Edge>> getAllEdges() {
        return adj.stream().map(Collections::unmodifiableList).collect(Collectors.toList());
    }

    public String toString() {
        final StringBuilder str = new StringBuilder();
        IntStream.range(0, adj.size()).forEach(v -> str.append(v).append("->").append(adj.get(v)).append("\n"));
        return str.toString();
    }

    private void validate(int[][] matrix) {
        // validate size
        boolean ok = matrix != null && matrix.length > 0;
        ok = ok && Arrays.stream(matrix).allMatch(r -> r.length == matrix.length);
        // validate content
        ok = ok && Arrays.stream(matrix).flatMapToInt(Arrays::stream).allMatch(v -> v == 0 || v == 1);
        if (!ok) {
            throw new IllegalArgumentException("invalid matrix " + Arrays.deepToString(matrix));
        }
    }

    private void validate(int... vertices) {
        for (int vertex : vertices) {
            if (vertex >= adj.size()) {
                throw new IllegalArgumentException("invalid vertex " + vertex);
            }
        }
    }

    public static void main(String... args) {
        Graph g = new Graph(4);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);

        System.out.println(g);
        System.out.println(g.getVertices());
        System.out.println(g.getAdj(2));

        g = new Graph(new int[][] { { 0, 1, 1, 0 }, { 0, 0, 1, 0 }, { 1, 0, 0, 1 }, { 0, 0, 0, 1 } });
        System.out.println(g);
        System.out.println(g.getVertices());
        System.out.println(g.getAdj(2));
    }
}
