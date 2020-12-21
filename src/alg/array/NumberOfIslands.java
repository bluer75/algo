package alg.array;

/**
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. 
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. 
 * You may assume all four edges of the grid are all surrounded by water.
 */
public class NumberOfIslands {

    /**
     * Use DFS to visit all cells and find all disconnected components - O(n * m).
     * Use input grid to mark cells as visited.
     */
    public int numIslands(char[][] grid) {
        if (grid == null) {
            return 0;
        }
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == '1') {
                    count++;
                    dfs(grid, row, col);
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[row].length || grid[row][col] != '1') {
            return;
        }
        grid[row][col] = '2'; // mark as visited
        dfs(grid, row, col - 1);
        dfs(grid, row, col + 1);
        dfs(grid, row + 1, col);
        dfs(grid, row - 1, col);
    }

    public static void main(String... args) {
        char[][] grid = {};
        System.out.println(new NumberOfIslands().numIslands(grid));
    }
}
