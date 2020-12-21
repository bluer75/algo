package alg.graph;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * Finds islands in given matrix.
 * Uses DFS to mark all neighbors.
 */
public class Islands {
    private boolean[][] visited;
    private int[][] matrix;
    private int size;

    public int countRecursively(int[][] m) {
        this.matrix = m;
        this.size = m.length;
        int num = 0;
        visited = new boolean[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1 && !visited[i][j]) {
                    visitRecursively(i, j);
                    num++;
                }
            }
        }
        return num;
    }

    // detects recursively whole island and marks as visited
    private void visitRecursively(int i, int j) {
        visited[i][j] = true;
        if (matrix[i][j] == 1) {
            // visit recursively all neighbors
            getNeighbors(i, j).forEach(v -> visitRecursively(v[0], v[1]));
        }
    }

    public int countIteratively(int[][] m) {
        this.matrix = m;
        this.size = m.length;
        int num = 0;
        visited = new boolean[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1 && !visited[i][j]) {
                    visitIteratively(i, j);
                    num++;
                }
            }
        }
        return num;
    }

    // detects iteratively whole island and marks as visited
    private void visitIteratively(int i, int j) {
        Deque<int[]> stack = new LinkedList<>();
        stack.push(new int[] { i, j });
        while (!stack.isEmpty()) {
            int[] v = stack.pop();
            if (!visited[v[0]][v[1]]) {
                visited[v[0]][v[1]] = true;
                if (matrix[v[0]][v[1]] == 1) {
                    getNeighbors(v[0], v[1]).forEach(stack::push);
                }
            }
        }
    }

    private Stream<int[]> getNeighbors(int i, int j) {
        int[][] neighbors = { { i - 1, j - 1 }, { i - 1, j }, { i - 1, j + 1 }, { i, j - 1 }, { i, j + 1 },
                { i + 1, j - 1 }, { i + 1, j }, { i + 1, j + 1 }, };
        return Arrays.stream(neighbors)
                // only valid indices and not visited
                .filter(v -> v[0] >= 0 && v[0] < size && v[1] >= 0 && v[1] < size && !visited[v[0]][v[1]]);
    }

    public static void main(String... args) {
        int[][] matrix = { { 1, 1, 0, 0, 0 }, { 0, 1, 0, 0, 1 }, { 1, 0, 0, 1, 1 }, { 0, 0, 0, 0, 0 },
                { 1, 0, 1, 0, 1 } };
        System.out.println("num of islands: " + new Islands().countRecursively(matrix));
        System.out.println("num of islands: " + new Islands().countIteratively(matrix));

        matrix = new int[][] { { 0, 1, 1, 0 }, { 0, 0, 1, 0 }, { 1, 0, 0, 1 }, { 0, 0, 0, 1 } };
        System.out.println("num of islands: " + new Islands().countRecursively(matrix));
        System.out.println("num of islands: " + new Islands().countIteratively(matrix));
    }
}
