package alg.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * M by N matrix consisting of booleans represents a board. 
 * Each True boolean represents a wall. Each False boolean represents a tile you can walk on.
 * Given this matrix, a start coordinate, and an end coordinate, return the minimum number of steps 
 * required to reach the end coordinate from the start. 
 * If there is no possible path, then return null. 
 * You can move up, left, down, and right. 
 * You cannot move through walls. 
 * You cannot wrap around the edges of the board.
 */
public class PathFinder {
    /**
     * Matrix can be represented as a graph, where each cell with false value is a vertex.
     * Edges identify possible moves between cells - up, left, down, and right. 
     * There is maximum M * N vertices. Each vertex can have max 4 edges.
     * BFS algorithm will find minimum path between two given vertices (if exists) in O(V + E) time -> O(MN + 3(M + N))
     */
    static int findBFS(int m, int n, boolean[][] board, int startRow, int startCol, int endRow, int endCol) {
        int numOfVertices = m * n; // m represent rows, n represents columns
        boolean[] visited = new boolean[numOfVertices];
        int[] dist = new int[numOfVertices];
        int startVertex = getVertex(n, startRow, startCol);
        int endVertex = getVertex(n, endRow, endCol);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startVertex);
        visited[startVertex] = true;
        while (!queue.isEmpty()) {
            int v = queue.remove();
            for (int u : getNeighbors(m, n, board, v)) {
                if (!visited[u]) {
                    visited[u] = true;
                    dist[u] += dist[v] + 1;
                    if (u == endVertex) {
                        // found minimum path
                        break;
                    }
                    queue.add(u);
                }
            }
        }
        return dist[endVertex];
    }

    // calculates vertex number for given cell
    private static int getVertex(int cols, int row, int col) {
        return row * cols + col;
    }

    // finds all neighbors for given vertex
    private static int[] getNeighbors(int rows, int cols, boolean[][] board, int v) {
        // find coordinates for v
        int row = v / rows;
        int col = v % cols;
        List<Integer> neighbors = new ArrayList<>();
        // up
        if (row > 0 && !board[row - 1][col]) {
            neighbors.add(getVertex(cols, row - 1, col));
        }
        // down
        if (row < rows - 1 && !board[row + 1][col]) {
            neighbors.add(getVertex(cols, row + 1, col));
        }
        // left
        if (col > 0 && !board[row][col - 1]) {
            neighbors.add(getVertex(cols, row, col - 1));
        }
        // right
        if (col < cols - 1 && !board[row][col + 1]) {
            neighbors.add(getVertex(cols, row, col + 1));
        }
        return neighbors.stream().mapToInt(i -> i).toArray();
    }

    /**
     * DP solution with bottom-up or top-down approach.
     * Bottom-up calculates distance to target cell from any other cell.
     * Top-down calculates distance to any cell from starting one.
     * It stores calculated distance in auxiliary table.  
     * It computes values (steps) for each cell in the matrix (or optionally till it reaches target/destination cell).
     * Running time is O(M * N). Required space is also O(M * N).
     */
    static int findDP(int rows, int cols, boolean[][] board, int endRow, int endCol) {
        int[][] dist = new int[rows][cols]; // dist[row][col] means number of steps to reach given cell from starting
                                            // point
        // it makes calculation easier if start/end cell is located in first or last row/column
        // otherwise if it starts in the middle it has to be done for both parts - above and below
        // let's assume we start at call 0,0
        // calculate distance for first row and column considering obstacles
        return dist[endRow][endCol];
    }

    public static void main(String... args) {
        boolean[][] board = { //
                { false, false, false, false }, //
                { true, true, false, true }, //
                { false, false, false, false }, //
                { false, false, false, false } //
        };
        int startRow = 3;
        int startCol = 0;
        int endRow = 0;
        int endCol = 0;
        System.out.println(findBFS(4, 4, board, startRow, startCol, endRow, endCol));
    }
}
