package alg.dp;

/**
 * A robot is located at the top-left corner of a m x n grid.
 * The robot can only move either down or right at any point in time. 
 * The robot is trying to reach the bottom-right corner of the grid.
 * How many possible unique paths are there?
 * 
 * Input: m = 3, n = 2
 * Output: 3
 * From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 * 1. Right -> Right -> Down
 * 2. Right -> Down -> Right
 * 3. Down -> Right -> Right
 */
public class UniqePaths {

    /**
     * Number of unique paths to reach point [i,j] is the sum of unique paths to [i-1, j] and [i, j-1].
     * Time/space complexity is O(nm).
     */
    public int uniquePaths(int m, int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }
        int[][] res = new int[n + 1][m + 1];
        res[1][1] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (i != 1 || j != 1) {
                    res[i][j] = res[i - 1][j] + res[i][j - 1];
                }
            }
        }
        return res[n][m];
    }

    /**
     * Space can be reduced to O(m) as for each row we need just values from row above if 
     * we fill it from left to right.
     */
    public int uniquePaths2(int m, int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }
        int[] res = new int[m];
        res[0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < m; j++) {
                res[j] = res[j - 1] + res[j];
            }
        }
        return res[m - 1];
    }

    public static void main(String... args) {
        System.out.println(new UniqePaths().uniquePaths(3, 2));
        System.out.println(new UniqePaths().uniquePaths2(3, 2));
    }
}
