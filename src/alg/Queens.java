package alg;

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
    }
}
