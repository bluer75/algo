package alg.array;

/**
 * You are given a map in form of a two-dimensional integer grid where 1 represents land and 0 represents water.
 * Grid cells are connected horizontally/vertically (not diagonally). The grid is completely surrounded by water, 
 * and there is exactly one island (i.e., one or more connected land cells).
 * The island doesn't have "lakes" (water inside that isn't connected to the water around the island). 
 * One cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100. 
 * Determine the perimeter of the island.
 * 
 * Example:
 * 
 * [[0,1,0,0],
 *  [1,1,1,0],
 *  [0,1,0,0],
 *  [1,1,0,0]]
 * 
 * Output: 16
 */
public class IslandPerimeter {
    public int islandPerimeter(int[][] grid) {
        if (grid == null) {
            return 0;
        }
        // find first non-empty cell
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 1) {
                    // run dfs for filled cells only
                    return dfs(grid, row, col);
                }
            }
        }
        return 0;
    }

    private int dfs(int[][] grid, int row, int col) {
        int edges = 0;
        // mark as visited
        grid[row][col] = 2;
        // left
        if (col > 0) {
            if (grid[row][col - 1] == 0) {
                edges++;
            }
            if (grid[row][col - 1] == 1) {
                edges += dfs(grid, row, col - 1);
            }
        } else {
            edges++;
        }
        // right
        if (col < grid[row].length - 1) {
            if (grid[row][col + 1] == 0) {
                edges++;
            }
            if (grid[row][col + 1] == 1) {
                edges += dfs(grid, row, col + 1);
            }
        } else {
            edges++;
        }
        // up
        if (row > 0) {
            if (grid[row - 1][col] == 0) {
                edges++;
            }
            if (grid[row - 1][col] == 1) {
                edges += dfs(grid, row - 1, col);
            }
        } else {
            edges++;
        }
        // down
        if (row < grid.length - 1) {
            if (grid[row + 1][col] == 0) {
                edges++;
            }
            if (grid[row + 1][col] == 1) {
                edges += dfs(grid, row + 1, col);
            }
        } else {
            edges++;
        }
        return edges;
    }
}
