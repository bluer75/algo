package alg;

/**
 * Given an undirected graph and a number m, determine if the graph can be colored with at most m colors 
 * such that no two adjacent vertices of the graph are colored with same color. 
 * Here coloring of a graph means assignment of colors to all vertices.
 */
public class GraphColoring {
    /**
     * This solution uses backtracking and stops after finding first solution.
     * Running time is O(m^v).
     */
    static boolean color(int[][] graph, int m) {
        return color(graph, m, new int[graph.length], 0);
    }

    /**
     * Tries recursively all combinations of colors.
     * colors[v] - color assigned to vertex v - 0 - no color
     */
    private static boolean color(int[][] graph, int m, int[] colors, int v) {
        if (v == graph.length) {
            // base case - all vertices have been processed
            return true;
        }
        for (int c = 1; c <= m; c++) {
            if (isSafe(graph, colors, v, c)) {
                // choose - color vertex v with color c
                colors[v] = c;
                // explore
                if (color(graph, m, colors, v + 1)) {
                    // return if found first solution
                    return true;
                }
                // un-choose un-color vertex v
                colors[v] = 0;
            }
        }
        return false;
    }

    /**
     * Checks if given color can be assigned to given vertex.
     */
    private static boolean isSafe(int[][] graph, int[] colors, int v, int c) {
        for (int u = 0; u < graph.length; u++) {
            // it is undirected graph so it's enough to check just row (column is symmetric)
            if (graph[v][u] == 1 && colors[u] == c) {
                return false;
            }
        }
        return true;
    }

    public static void main(String... args) {
        /**
         * (3)---(2) 
         *  |   / | 
         *  |  /  | 
         *  | /   | 
         * (0)---(1) 
         */
        int graph[][] = { { 0, 1, 1, 1 }, { 1, 0, 1, 0 }, { 1, 1, 0, 1 }, { 1, 0, 1, 0 } };
        int m = 3;
        System.out.println(color(graph, m));
    }
}
