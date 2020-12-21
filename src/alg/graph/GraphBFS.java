package alg.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GraphBFS {
    private Graph graph;
    int[] parents;
    int[] dist;
    boolean[] visited;

    public void bfsIteratively(Graph graph) {
        this.graph = graph;
        List<Integer> vertices = graph.getVertices();
        parents = new int[vertices.size()];
        dist = new int[vertices.size()];
        visited = new boolean[vertices.size()];
        for (int vertex : vertices) {
            if (!visited[vertex]) {
                visitIteratively(vertex);
            }
        }
    }

    private void visitIteratively(int vertex) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(vertex);
        while (!queue.isEmpty()) {
            int v = queue.remove();
            if (!visited[v]) {
                visited[v] = true;
                graph.getAdj(v).stream().filter(n -> !visited[n] && !queue.contains(n)).forEach(n -> {
                    parents[n] = v;
                    dist[n] = dist[v] + 1;
                    queue.add(n);
                });
            }
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

        GraphBFS bfs = new GraphBFS();
        bfs.bfsIteratively(g);
        System.out.println(bfs.getParents());
        System.out.println(bfs.getDistance());
    }
}
