
package alg.dp;

/**
 * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right 
 * which minimizes the sum of all numbers along its path.
 * Note: You can only move either down or right at any point in time.
 * Example:
 * 
 * Input:
 * [
 *   [1,3,1],
 *   [1,5,1],
 *   [4,2,1]
 * ]
 * Output: 7 
 * Explanation: Because the path 1->3->1->1->1 minimizes the sum.
 */
public class MinPathSum {
    /**
     * Bottom-up approach, use input array and calculate sum in-place - O(n*m).
     */
    public int minPathSum(int[][] grid) {
        if (grid == null) {
            return 0;
        }
        // calculate first row
        for (int i = 1; i < grid[0].length; i++) {
            grid[0][i] += grid[0][i - 1];
        }
        // calculate first column
        for (int i = 1; i < grid.length; i++) {
            grid[i][0] += grid[i - 1][0];
        }
        // calculate value for each cell starting from second row/column
        for (int row = 1; row < grid.length; row++) {
            for (int col = 1; col < grid[0].length; col++) {
                // take lower value from cell above and on the left
                grid[row][col] += Math.min(grid[row - 1][col], grid[row][col - 1]);
            }
        }
        return grid[grid.length - 1][grid[0].length - 1];
    }

    public static void main(String... args) {
        int[][] grid = { { 1, 3, 1 }, { 1, 5, 1 }, { 4, 2, 1 } };
        System.out.println(new MinPathSum().minPathSum(grid));
    }
}
