package alg.array;

import java.util.Arrays;

/**
 * Given a board with m by n cells, each cell has an initial state live (1) or dead (0). 
 * Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using following four rules:
 * Any live cell with fewer than two live neighbors dies, as if caused by under-population.
 * Any live cell with two or three live neighbors lives on to the next generation.
 * Any live cell with more than three live neighbors dies, as if by over-population..
 * Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 * Write a function to compute the next state (after one update) of the board given its current state.
 * The next state is created by applying the above rules simultaneously to every cell in the current state, where 
 * births and deaths occur simultaneously.
 * 
 * Example:
 * 
 * Input: 
 * [
 *   [0,1,0],
 *   [0,0,1],
 *   [1,1,1],
 *   [0,0,0]
 * ]
 * Output: 
 * [
 *   [0,0,0],
 *   [1,0,1],
 *   [0,1,1],
 *   [0,1,0]
 * ]
 */
public class GameOfLife {
    public void gameOfLife(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                int liveCount = countLiveCells(board, row, col);
                if (board[row][col] == 1) {
                    if (liveCount < 2 || liveCount > 3) {
                        // 1 -> 0
                        board[row][col] = -2;
                    }
                } else {
                    if (liveCount == 3) {
                        // 0 -> 1
                        board[row][col] = -1;
                    }
                }
            }
        }
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == -2) {
                    board[row][col] = 0;
                } else if (board[row][col] == -1) {
                    board[row][col] = 1;
                }
            }
        }
    }

    private int countLiveCells(int[][] board, int row, int col) {
        int[][] distances = new int[][] { { -1, -1 }, { -1, 0 }, { -1, +1 }, { 0, -1 }, { 0, +1 }, { +1, -1 },
                { +1, 0 }, { +1, +1 } };
        int nrow, ncol;
        int liveCount = 0;
        for (int[] dist : distances) {
            nrow = row + dist[0];
            ncol = col + dist[1];
            if (nrow >= 0 && ncol >= 0 && nrow < board.length && ncol < board[row].length
                    && (board[nrow][ncol] == 1 || board[nrow][ncol] == -2)) {
                liveCount++;
            }
        }
        return liveCount;
    }

    public static void main(String... args) {
        int[][] board = { { 0, 1, 0 }, { 0, 0, 1 }, { 1, 1, 1 }, { 0, 0, 0 } };
        new GameOfLife().gameOfLife(board);
        System.out.println(Arrays.deepToString(board));
    }
}
