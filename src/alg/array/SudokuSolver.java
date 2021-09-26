package alg.array;

import java.util.*;

/**
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 *
 * A sudoku solution must satisfy all of the following rules:
 *
 * Each of the digits 1-9 must occur exactly once in each row.
 * Each of the digits 1-9 must occur exactly once in each column.
 * Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
 * The '.' character indicates empty cells.
 *
 * Solution is based on backtracking. For each free cell we try all valid values and call recursively solver for
 * next cell.
 *
 * Time complexity is O(n!^n), n = 9.
 * Space complexity is 0(n^2), n = 9.
 */
public class SudokuSolver {

    public void solveSudoku(char[][] board) {
        Map<Integer, Set<Character>> rows = new HashMap<>();
        Map<Integer, Set<Character>> cols = new HashMap<>();
        Map<Integer, Set<Character>> sqrs = new HashMap<>();
        int id = 0;
        for (int row = 0; row < 9; row++) {
            rows.putIfAbsent(row, new HashSet<>());
            for (int col = 0; col < 9; col++) {
                id = row / 3 * 3 + col / 3;
                cols.putIfAbsent(col, new HashSet<>());
                sqrs.putIfAbsent(id, new HashSet<>());
                if (board[row][col] != '.') {
                    rows.get(row).add(board[row][col]);
                    cols.get(col).add(board[row][col]);
                    sqrs.get(id).add(board[row][col]);
                }
            }
        }
        solve(board, 0, rows, cols, sqrs);
    }

    private boolean solve(char[][] board, int rowcol,
        Map<Integer, Set<Character>> rows,
        Map<Integer, Set<Character>> cols,
        Map<Integer, Set<Character>> sqrs) {
        if (rowcol == 81) {
            // no more cells
            return true;
        }
        char value = 0;
        int id = 0;
        int row = rowcol / 9;
        int col = rowcol % 9;
        if (board[row][col] == '.') {
            // try valid values
            for (int i = 1; i < 10; i++) {
                value = (char) ('0' + i);
                id = row / 3 * 3 + col / 3;
                if (!rows.get(row).contains(value) &&
                    !cols.get(col).contains(value) &&
                    !sqrs.get(id).contains(value)) {
                    // set the value
                    board[row][col] = value;
                    rows.get(row).add(value);
                    cols.get(col).add(value);
                    sqrs.get(id).add(value);
                    // move to next cell
                    if (solve(board, rowcol + 1, rows, cols, sqrs)) {
                        return true;
                    }
                    // clear selection
                    rows.get(row).remove(value);
                    cols.get(col).remove(value);
                    sqrs.get(id).remove(value);
                }
            }
            // nothing worked - backtrack
            board[row][col] = '.';
        } else {
            return solve(board, rowcol + 1, rows, cols, sqrs);
        }
        return false;
    }

    public static void main(String[] args) {
        char[][] board = {{'5', '3', '.', '.', '7', '.', '.', '.', '.'}, {'6', '.', '.', '1', '9', '5', '.', '.',
            '.'}, {'.', '9', '8', '.', '.', '.', '.', '6', '.'}, {'8', '.', '.', '.', '6', '.', '.', '.', '3'}, {'4',
            '.', '.', '8', '.', '3', '.', '.', '1'}, {'7', '.', '.', '.', '2', '.', '.', '.', '6'}, {'.', '6', '.',
            '.', '.', '.', '2', '8', '.'}, {'.', '.', '.', '4', '1', '9', '.', '.', '5'}, {'.', '.', '.', '.', '8',
            '.', '.', '7', '9'}};
        new SudokuSolver().solveSudoku(board);
        System.out.println(Arrays.deepToString(board));
    }
}
