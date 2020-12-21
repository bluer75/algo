package alg.dp;

import java.util.Arrays;

/**
 * The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon. 
 * The dungeon consists of M x N rooms laid out in a 2D grid. Our valiant knight (K) was initially 
 * positioned in the top-left room and must fight his way through the dungeon to rescue the princess.
 * 
 * The knight has an initial health point represented by a positive integer. 
 * If at any point his health point drops to 0 or below, he dies immediately.
 * 
 * Some of the rooms are guarded by demons, so the knight loses health (negative integers) upon entering 
 * these rooms; other rooms are either empty (0's) or contain magic orbs that increase the knight's 
 * health (positive integers).
 * 
 * In order to reach the princess as quickly as possible, the knight decides to move only rightward or 
 * downward in each step.
 * 
 * Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.
 * 
 * For example, given the dungeon below, the initial health of the knight must be at least 7 if he follows 
 * the optimal path RIGHT-> RIGHT -> DOWN -> DOWN.
 * 
 * [
 *   [-2, -3,  3],
 *   [-5,-10,  1],
 *   [10, 30, -5]
 * ]
 * 
 * This has to be solved using bottom-up approach. We need to start from last cell and calculate minimum health from 
 * there. In order to rescue the princess alive, knight needs at least 1 health point after rescuing. 
 * For any cell we take the minimum from cell on the right and down. If calculated minimum is negative (means current 
 * health score is positive) we set it to 1.
 * 
 * [
 *   [-2, -3,  3], max
 *   [-5,-10,  1], max
 *   [10, 30, -5]  1
 *   max  max  1
 * ]
 * 
 * Complexity is O(n * m).
 */
public class DungeonGame {

    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] dp = new int[m + 1][n + 1];

        // initialize the matrix to maximum possible.
        for (int i = 0; i <= m; i++)
            Arrays.fill(dp[i], Integer.MAX_VALUE);

        // in order to rescue the princess alive, knight needs at least 1 health point after rescuing
        // dp[m][n-1],dp[m-1][n] need to be 1
        dp[m][n - 1] = 1;
        dp[m - 1][n] = 1;

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int minHp = Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j];
                dp[i][j] = (minHp <= 0) ? 1 : minHp;
            }
        }
        return dp[0][0];
    }

    /**
     * This top-down approach tries to calculate minimum health form upper-left cell and takes minimum from cell 
     * above and on the left. It doesn't actually work as this may select wrong path using just minimum criteria.
     * Proper solution has to start from bottom.
     */
    public int calculateMinimumHPWrong(int[][] dungeon) {
        if (dungeon == null) {
            return 0;
        }
        Score[][] res = new Score[dungeon.length + 1][dungeon[0].length + 1];
        for (int row = 0; row < res.length; row++) {
            for (int col = 0; col < res[row].length; col++) {
                res[row][col] = new Score();
            }
        }
        res[0][1].min = res[1][0].min = 0;
        for (int row = 1; row < res.length; row++) {
            for (int col = 1; col < res[0].length; col++) {
                int scoreL = res[row][col - 1].score + dungeon[row - 1][col - 1];
                long minL = normalize(res[row][col - 1].min, scoreL);
                int scoreU = res[row - 1][col].score + dungeon[row - 1][col - 1];
                long minU = normalize(res[row - 1][col].min, scoreU);
                if (minL > minU) {
                    res[row][col].min = minL;
                    res[row][col].score = scoreL;
                    System.out.println(
                            "L [" + (row - 1) + "," + (col - 1) + "] " + res[row][col].min + " " + res[row][col].score);
                } else {
                    res[row][col].min = minU;
                    res[row][col].score = scoreU;
                    System.out.println(
                            "U [" + (row - 1) + "," + (col - 1) + "] " + res[row][col].min + " " + res[row][col].score);
                }
            }
        }
        int min = (int) res[res.length - 1][res[0].length - 1].min;
        return min < 0 ? Math.abs(min) + 1 : 1;
    }

    private long normalize(long min, int score) {
        if (score >= 0) {
            // if current score is positive min won't change
            return min;
        }
        // if score is negative check if it is greater than current min
        return score < min ? score : min;
    }

    private static class Score {
        int score = 0;
        long min = Integer.MIN_VALUE;
    }

    public static void main(String... args) {
        int[][] grid = { { 1, -3, 3 }, { 0, -2, 0 }, { -3, -3, -3 } };
        // { { -2, -3, 3 }, { -5, -10, 1 }, { 10, 30, -5 } }; // here top-down solution works
        System.out.println(new DungeonGame().calculateMinimumHP(grid));
    }
}
