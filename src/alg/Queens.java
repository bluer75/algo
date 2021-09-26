package alg;

import java.util.LinkedList;
import java.util.List;

/**
 * Brute force solution would generate all possible combinations of queens - O(N^2N).
 * Backtracking requires O(n!) time and O(n^2) space.
 * Optimized solution reduces space to O(n).
 */
public class Queens {
    private int size;
    private boolean[][] board;

    Queens(int size) {
        this.size = size;
        board = new boolean[size][size];
    }

    /**
     * Checks if Queen can be placed on the board and it does‘t conflict with any
     * other Queen placed already. Since we place Queens from left side column by
     * column we should check only left side of the board from this Queen.
     */
    boolean isSafe(int x, int y) {
        // check columns on the left
        for (int col = 0; col < x; col++) {
            if (board[col][y]) {
                return false;
            }
        }
        // check diagonal left up
        for (int col = x, row = y; col >= 0 && row >= 0; col--, row--) {
            if (board[col][row]) {
                return false;
            }
        }

        // check diagonal left down
        for (int col = x, row = y; col >= 0 && row < size; col--, row++) {
            if (board[col][row]) {
                return false;
            }
        }
        return true;
    }

    void place(int x, int y) {
        board[x][y] = true;
    }

    void remove(int x, int y) {
        board[x][y] = false;
    }

    void placeQueens() {
        if (placeQueens(0)) {
            print();
        }
    }

    private boolean placeQueens(int column) {
        // we can place only one queen in each column
        if (column == size) {
            return true;
        } else {
            // try all rows for this column
            for (int row = 0; row < size; row++) {
                if (isSafe(column, row)) {
                    // choose - place queen in this row
                    place(column, row);
                    // explore - recursively try to place Queens in remaining columns
                    if (placeQueens(column + 1)) {
                        // stop after finding first solution
                        return true;
                    }
                    // un-choose - did‘t find solution for this row so remove queen from board
                    remove(column, row);
                }
            }
        }
        return false;
    }

    void print() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                sb.append(board[x][y] ? " X " : " - ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    public static void main(String... args) {
        Queens b = new Queens(8);
        b.placeQueens();
        OptimizedWithAllSolutions queensWithAllSolutions = new OptimizedWithAllSolutions();
        System.out.println(queensWithAllSolutions.solveNQueens(8));
        System.out.println(queensWithAllSolutions.countSolutions(8));
    }

    private static class OptimizedWithAllSolutions {
        public List<List<String>> solveNQueens(int n) {
            List<List<String>> results = new LinkedList<>();
            int[] queens = new int[n]; // queens[i] -> position/row of queen in col i
            place(n, queens, 0, results);
            return results;
        }

        private boolean place(int n, int[] queens, int col, List<List<String>> results) {
            if (col == n) {
                // found solution -> add to results
                List<String> result = new LinkedList<>();
                for (int row = 0; row < n; row++) {
                    // generate row based on value at queens[row]
                    result.add(
                        ".".repeat(queens[row]).concat("Q").concat(".".repeat(n - 1 - queens[row])));
                }
                results.add(result);
                return true;
            }
            for (int row = 0; row < n; row++) {
                if (isSafe(n, queens, row, col)) {
                    queens[col] = row;
                    place(n, queens, col + 1, results);
                    // no need to un-choose as the value queens[col] will be overwritten
                }
            }
            return false;
        }

        private boolean isSafe(int n, int[] queens, int row, int col) {
            for (int prev = 0; prev < col; prev++) {
                // two queens share the same diagonal if vertical and horizontal distances between them are equal
                if (queens[prev] == row || Math.abs(queens[prev] - row) == Math.abs(prev - col)) {
                    return false;
                }
            }
            return true;
        }

        public int countSolutions(int n) {
            return place((1 << n) - 1, 0, 0, 0);
        }

        /**
         * Optimized solution using bitmask.
         * row - keeps track of rows where queens have been already placed
         * ld/rd - left/right diagonal represents fields attacked by previously placed queens
         * For n = 5, ld/rd = 01000 means row 3 is attacked (counted from least significant bit)
         * To simulate moving to next column (to the right) we simply shift them by 1 -> ld << 1 (down) or rd >> 1 (up)
         * For n = 5 and prev ld/rd = 01000, they become ld = 00100 rd = 10000
         * Combining row and ld/rd we can determine all position not attacked by previously placed queens
         */
        private int place(int mask, int row, int ld, int rd) {
            if (row == mask) {
                // all queens were placed
                return 1;
            }
            int res = 0;
            // pos represents given column with n rows
            // where queen is currently to be placed, 1s mean position is safe
            int pos = mask & (~(row | ld | rd));
            while (pos > 0) { // try all possibilities
                int p = pos & -pos; // find least significant 1
                pos -= p; // place queen at position p
                // move to next column with updated row/ld/rd
                res += place(mask, row | p, (ld | p) << 1, (rd | p) >> 1);
            }
            return res;
        }
    }
}
