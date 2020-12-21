package alg.array;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:
 * Each row must contain the digits 1-9 without repetition.
 * Each column must contain the digits 1-9 without repetition.
 * Each of the 9 3x3 sub-boxes of the grid must contain the digits 1-9 without repetition.
 * 
 * The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
 * 
 * Naive solution iterates through n (size of the board) elements and validates each time corresponding row, column and block.
 * For each iteration it executes sub-iteration 3 times - > 3n^2. Complexity is O(n^2).
 * This can be done with n^2 iterations.
 */
public class SudokuValidator {
    private static int counter = 0;
    private Map<Character, Integer> map = IntStream.rangeClosed(0, 8).boxed()
            .collect(Collectors.toMap(i -> (char) ('1' + i), i -> i));

    public boolean isValidSudoku(char[][] board) {
        if (board == null || board.length != 9) {
            return false;
        }
        return IntStream.rangeClosed(0, 8).allMatch(i -> board[i].length == 9 && validateRow(board, i)
                && validateCol(board, i) && validateBox(board, (i / 3) * 3, (i % 3) * 3));
    }

    private boolean validateBox(char[][] box, int x, int y) {
        boolean[] values = new boolean[9];
        for (int i = x; i < x + 3; i++) {
            for (int j = y; j < y + 3; j++) {
                if (!isValid(values, box[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateRow(char[][] board, int row) {
        boolean[] values = new boolean[9];
        return IntStream.rangeClosed(0, 8).allMatch(i -> isValid(values, board[row][i]));
    }

    private boolean validateCol(char[][] board, int col) {
        boolean[] values = new boolean[9];
        return IntStream.rangeClosed(0, 8).allMatch(i -> isValid(values, board[i][col]));
    }

    private boolean isValid(boolean[] values, char ch) {
        System.out.println(++counter);
        if (ch == '.') {
            return true;
        }
        int i = map.getOrDefault(ch, -1);
        if (i >= 0 && !values[i]) {
            return values[i] = true;
        }
        return false;
    }

    public static void main(String... args) {
        char[][] board = { { '5', '3', '.', '.', '7', '.', '.', '.', '.' },
                { '6', '.', '.', '1', '9', '5', '.', '.', '.' }, { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
                { '8', '.', '.', '.', '6', '.', '.', '.', '3' }, { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
                { '7', '.', '.', '.', '2', '.', '.', '.', '6' }, { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
                { '.', '.', '.', '4', '1', '9', '.', '.', '5' }, { '.', '.', '.', '.', '8', '.', '.', '7', '9' } };
        System.out.println(new SudokuValidator().isValidSudoku(board));

    }
}
