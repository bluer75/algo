package alg.array;

import java.util.Arrays;

/**
 * Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.
 * A region is captured by flipping all 'O's into 'X's in that surrounded region.
 * 
 * Example:
 * 
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * 
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * 
 * Surrounded regions shouldn’t be on the border, which means that any 'O' on the border of 
 * the board are not flipped to 'X'. Any 'O' that is not on the border and it is not 
 * connected to an 'O' on the border will be flipped to 'X'. Two cells are connected if 
 * they are adjacent cells connected horizontally or vertically.
 */
public class SurroundedRegions {

    /**
     * Check edges of the grid and run DFS for each cell with value 'O' marking all connected cells.
     * These cells are not surrounded.
     * Then go through each cell again and flip all remaining 'O's to 'X' and revert marked cells back to 'X'.
     * 
     * Complexity: O(n * m).
     */
    public void solve(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == 'O') {
                dfs(board, i, 0);
            }
            if (board[i][board[i].length - 1] == 'O') {
                dfs(board, i, board[i].length - 1);
            }
        }
        for (int i = 0; i < board[0].length; i++) {
            if (board[0][i] == 'O') {
                dfs(board, 0, i);
            }
            if (board[board.length - 1][i] == 'O') {
                dfs(board, board.length - 1, i);
            }
        }
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == 'O') {
                    board[row][col] = 'X';
                } else {
                    if (board[row][col] == 'Z') {
                        board[row][col] = 'O';
                    }
                }
            }
        }
    }

    private void dfs(char[][] board, int row, int col) {
        if (row < 0 || col < 0 || row >= board.length || col >= board[row].length || board[row][col] != 'O') {
            return;
        }
        board[row][col] = 'Z';
        dfs(board, row, col - 1);
        dfs(board, row, col + 1);
        dfs(board, row - 1, col);
        dfs(board, row + 1, col);
    }

    public static void main(String... args) {

        char[][] board = { { 'X', 'O', 'X', 'O', 'X', 'O' }, { 'O', 'X', 'O', 'X', 'O', 'X' },
                { 'X', 'O', 'X', 'O', 'X', 'O' }, { 'O', 'X', 'O', 'X', 'O', 'X' } };
        // expected [[2,2,2],[2,2,0],[2,0,1]]
        new SurroundedRegions().solve(board);
        System.out.println(Arrays.deepToString(board));
    }
}
