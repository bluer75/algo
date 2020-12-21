package alg.graph;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import alg.graph.Graph.Edge;

public class ShortestPathBF {
	private Graph graph;
	private int[] parents;
	private int[] dist;

	public ShortestPathBF(Graph graph) {
		this.graph = graph;
	}

	private void init(int source) {
		int numOfVertices = graph.getNumOfVertices();
		parents = new int[numOfVertices];
		Arrays.fill(parents, -1);
		parents[source] = source;
		dist = new int[numOfVertices];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[source] = 0;
	}

	/**
	 * Finds shortest paths from given source.
	 */
	public boolean findFrom(int source) {
		init(source);
		List<List<Edge>> edges = graph.getAllEdges();
		// number of vertices - 1
		for (int v = 0; v < graph.getNumOfVertices() - 1; v++) {
			// execute relax for all edges
			IntStream.range(0, edges.size()).forEach(i -> edges.get(i).stream().forEach(e -> relax(i, e.to, e.weight)));
		}
		// check negative weight cycles, verify if we can still relax
		if (IntStream.range(0, edges.size())
				.anyMatch(i -> edges.get(i).stream().anyMatch(e -> relax(i, e.to, e.weight)))) {
			return false;
		}
		return true;
	}

	/**
	 * Returns parents.
	 */
	public String getParents() {
		String res = "";
		for (int i = 0; i < parents.length; i++) {
			if (parents[i] != -1) {
				res += parents[i] + "->" + i + " ";
			}
		}
		return res;
	}

	/**
	 * Returns shortest paths.
	 */
	public String getPaths(int source) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parents.length; i++) {
			visitParent(sb, source, i);
			sb.append(" [").append(dist[i]).append("]");
			sb.append("\n");
		}
		return sb.toString();
	}

	private void visitParent(StringBuilder sb, int source, int vertex) {
		if (vertex == source) {
			sb.append(vertex);
			return;
		}
		visitParent(sb, source, parents[vertex]);
		sb.append("->").append(vertex);
	}

	private boolean relax(int u, int v, int w) {
		if (dist[v] > dist[u] + w) {
			dist[v] = dist[u] + w;
			parents[v] = u;
			return true;
		}
		return false;
	}

	public static void main(String... args) {
		Graph g = new Graph(5);
		g.addEdge(0, 1, 6);
		g.addEdge(0, 3, 7);
		g.addEdge(1, 2, 5);
		g.addEdge(1, 3, 8);
		g.addEdge(1, 4, -4);
		g.addEdge(2, 1, -2);
		g.addEdge(3, 2, -3);
		g.addEdge(3, 4, 9);
		g.addEdge(4, 0, 8);
		g.addEdge(4, 2, 7);
		System.out.println(g);
		ShortestPathBF bf = new ShortestPathBF(g);
		if (bf.findFrom(0)) {
			System.out.println(bf.getParents());
			System.out.println(bf.getPaths(0));
		}
	}
}
