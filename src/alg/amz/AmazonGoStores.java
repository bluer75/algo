package alg.amz;

import java.util.ArrayList;
import java.util.List;

/**
 * Count number of clusters in a grid.
 * Variation of NumberOfIslands problem.
 */
public class AmazonGoStores {
    public int numberAmazonGoStores(int rows, int column, List<List<Integer>> grid) {
        // validate input parameters
        if (rows <= 0 || column <= 0 || grid == null || grid.size() != rows) {
            return 0;
        }
        // convert to array to for constant access
        int[][] map = new int[rows][column];
        int row = 0;
        int col = 0;
        for (List<Integer> lrow : grid) {
            for (int lcol : lrow) {
                map[row][col++] = lcol;
            }
            row++;
            col = 0;
        }
        // run dfs and count clusters
        int count = 0;
        for (row = 0; row < map.length; row++) {
            for (col = 0; col < map[row].length; col++) {
                if (map[row][col] == 1) {
                    count++;
                    dfs(map, row, col);
                }
            }
        }
        return count;
    }

    private void dfs(int[][] grid, int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[row].length || grid[row][col] != 1) {
            return;
        }
        grid[row][col] = 2; // mark as visited
        dfs(grid, row, col - 1);
        dfs(grid, row, col + 1);
        dfs(grid, row + 1, col);
        dfs(grid, row - 1, col);
    }

    public static void main(String... args) {
        List<List<Integer>> ll = new ArrayList<>();
        ll.add(List.of(1, 1, 0, 0));
        ll.add(List.of(0, 0, 1, 0));
        ll.add(List.of(0, 0, 0, 0));
        ll.add(List.of(1, 0, 1, 1));
        ll.add(List.of(1, 1, 1, 1));

        System.out.println(new AmazonGoStores().numberAmazonGoStores(5, 4, ll));
    }
}
