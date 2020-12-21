package alg.array;

/**
 * Given a 2D board and a word, find if the word exists in the grid.
 * The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those 
 * horizontally or vertically neighboring. The same letter cell may not be used more than once.
 * Example:
 * board =
* [
*   ['A','B','C','E'],
*   ['S','F','C','S'],
*   ['A','D','E','E']
* ]
* 
* Given word = "ABCCED", return true.
* Given word = "SEE", return true.
* Given word = "ABCB", return false.
* 
* Solution is based on backtracking/DFS.
* Time complexity is O(n * 4^l) where n - number of cells, l - length of the word. 
* For each letter we have 4 choices/neighbors and in the worst case we need to check all cells.
* Space complexity is O(n + l).
 */
public class WordSearch {

    public boolean exist(char[][] board, String word) {
        if (board == null || word == null) {
            return false;
        }
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                // find first letter to start backtracking/DFS
                if (board[row][col] == word.charAt(0)) {
                    if (backtrack(board, new boolean[board.length][board[0].length], word, 0, row, col)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean backtrack(char[][] board, boolean[][] visited, String word, int pos, int row, int col) {
        // found all letters
        if (pos == word.length()) {
            return true;
        }
        // check if we can proceed
        if (row < 0 || row == board.length || col < 0 || col == board[row].length || visited[row][col]
                || board[row][col] != word.charAt(pos)) {
            return false;
        }
        // choose current cell 
        visited[row][col] = true;
        // check all neighbors
        if (backtrack(board, visited, word, pos + 1, row - 1, col)
                || backtrack(board, visited, word, pos + 1, row + 1, col)
                || backtrack(board, visited, word, pos + 1, row, col - 1)
                || backtrack(board, visited, word, pos + 1, row, col + 1)) {
            return true;
        }
        // un-choose current cell and backtrack
        visited[row][col] = false;
        return false;
    }
}
