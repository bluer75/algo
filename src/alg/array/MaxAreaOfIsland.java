package alg.array;

/**
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) 
 * connected 4-directionally (horizontal or vertical.) 
 * You may assume all four edges of the grid are surrounded by water.
 * 
 * Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)
 * 
 * Example:
 * [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,1,1,0,1,0,0,0,0,0,0,0,0],
 *  [0,1,0,0,1,1,0,0,1,0,1,0,0],
 *  [0,1,0,0,1,1,0,0,1,1,1,0,0],
 *  [0,0,0,0,0,0,0,0,0,0,1,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 *  
 * Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
 */
public class MaxAreaOfIsland {
    
    /**
     * Scan grid to find unvisited cell with 1 and start DFS from there.
     * Select island with max area.
     */
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null) {
            return 0;
        }
        int max = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 1) {
                    max = Math.max(max, dfs(grid, row, col));
                }
            }
        }
        return max;
    }

    private int dfs(int[][] grid, int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[row].length || grid[row][col] != 1) {
            return 0;
        }
        grid[row][col] = 2; // mark as visited
        return 1 + dfs(grid, row, col - 1) + dfs(grid, row, col + 1) + dfs(grid, row + 1, col)
                + dfs(grid, row - 1, col);
    }
}
