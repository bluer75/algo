package alg.graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

public class ShortestPathDJ {
	private Graph graph;
	private int[] parents;
	private int[] dist;
	private Map<Integer, Integer> distMap = new HashMap<>();;

	public ShortestPathDJ(Graph graph) {
		this.graph = graph;
	}

	/**
	 * Finds shortest paths from given source.
	 */
	public boolean findFrom(int source) {
		init(source);
		Integer vertex;
		while ((vertex = getMinDistanceVertex(distMap)) != null) {
			final int v = vertex;
			graph.getEdges(v).forEach(e -> relax(v, e.to, e.weight));
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

	private void init(int source) {
		int numOfVertices = graph.getNumOfVertices();
		parents = new int[numOfVertices];
		Arrays.fill(parents, -1);
		parents[source] = source;
		dist = new int[numOfVertices];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[source] = 0;
		IntStream.range(0, dist.length).forEach(v -> distMap.put(v, dist[v]));
	}

	private Integer getMinDistanceVertex(Map<Integer, Integer> dists) {
		Entry<Integer, Integer> entry = dists.entrySet().stream()
				.min(Comparator.<Entry<Integer, Integer>>comparingInt(e -> e.getValue())).orElse(null);
		if (entry != null) {
			dists.remove(entry.getKey());
			return entry.getKey();
		}
		return null;
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
			distMap.put(v, dist[v]);
			parents[v] = u;
			return true;
		}
		return false;
	}

	public static void main(String... args) {
		Graph g = new Graph(5);
		g.addEdge(0, 1, 10);
		g.addEdge(0, 3, 5);
		g.addEdge(1, 2, 1);
		g.addEdge(1, 3, 2);
		g.addEdge(2, 4, 4);
		g.addEdge(3, 1, 3);
		g.addEdge(3, 2, 9);
		g.addEdge(3, 4, 2);
		g.addEdge(4, 0, 7);
		g.addEdge(4, 2, 6);
		System.out.println(g);
		ShortestPathDJ bf = new ShortestPathDJ(g);
		if (bf.findFrom(0)) {
			System.out.println(bf.getParents());
			System.out.println(bf.getPaths(0));
		}
	}
}
