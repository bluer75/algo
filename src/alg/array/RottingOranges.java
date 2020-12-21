package alg.array;

import java.util.LinkedList;
import java.util.Queue;

/**
 * In a given grid, each cell can have one of three values:
 * the value 0 representing an empty cell;
 * the value 1 representing a fresh orange;
 * the value 2 representing a rotten orange.
 * Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.
 * 
 * Return the minimum number of minutes that must elapse until no cell has a fresh orange.  
 * If this is impossible, return -1 instead.
 * 
 * Input: [[2,1,1],[1,1,0],[0,1,1]]
 * Output: 4
 * 
 * Input: [[2,1,1],[0,1,1],[1,0,1]]
 * Output: -1 -> The orange in the bottom left corner [2, 0] is never rotten.
 */
public class RottingOranges {

    /**
     * Brute force solution scanning the whole grid as many times as there are oranges getting rotten.
     * Complexity is O(k * n), k is number of passes and n is number of oranges.
     * The worst case is O(n^2) for k = n/2 - we can mark as rotten just one orange in each pass 
     */
    public int orangesRottingBF(int[][] grid) {
        boolean hasFresh = false;
        boolean newRotten = false;
        int time = 2;
        do {
            time++;
            newRotten = false;
            hasFresh = false;
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[row].length; col++) {
                    if (grid[row][col] == 1) {
                        if (isRotten(grid, row - 1, col, time) || isRotten(grid, row + 1, col, time)
                                || isRotten(grid, row, col - 1, time) || isRotten(grid, row, col + 1, time)) {
                            grid[row][col] = time;
                            newRotten = true;
                        } else {
                            hasFresh = true;
                        }
                    }
                }
            }
        } while (newRotten);
        return hasFresh ? -1 : time - 3;
    }

    private boolean isRotten(int[][] grid, int row, int col, int time) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[row].length) {
            return false;
        }
        return grid[row][col] > 1 && grid[row][col] < time;
    }

    /**
     * Scan the grid once and start BFS for every rotten orange. 
     * Process oranges level by level - each level is a minute.
     * At the end check if any fresh oranges are left.
     * This takes O(n) time - minimum 2 scans are needed.
     */
    public int orangesRottingBFS(int[][] grid) {
        Queue<Integer> queue = new LinkedList<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 2) {
                    queue.offer(pack(row, col));
                }
            }
        }
        int time = 0;
        while (!queue.isEmpty()) {
            int count = queue.size();
            boolean processedFresh = false;
            // process whole level
            while (--count >= 0) {
                int code = queue.remove();
                int row = getRow(code);
                int col = getCol(code);
                if (grid[row][col] == 2 && time > 0) {
                    continue; // visited already (except first level where time is 0)
                }
                if (grid[row][col] == 1) {
                    processedFresh = true;
                }
                grid[row][col] = 2; // mark as visited
                if (isFresh(grid, row - 1, col)) {
                    queue.offer(pack(row - 1, col));
                }
                if (isFresh(grid, row + 1, col)) {
                    queue.offer(pack(row + 1, col));
                }
                if (isFresh(grid, row, col - 1)) {
                    queue.offer(pack(row, col - 1));
                }
                if (isFresh(grid, row, col + 1)) {
                    queue.offer(pack(row, col + 1));
                }
            }
            if (processedFresh) {
                // there were some fresh oranges marked as rotten
                time++;
            }
        }
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 1) {
                    return -1;
                }
            }
        }
        return time;
    }

    private boolean isFresh(int[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[row].length && grid[row][col] == 1;
    }

    private int pack(int row, int col) {
        return row << 8 | col;
    }

    private int getRow(int code) {
        return code >> 8;
    }

    private int getCol(int code) {
        return code & 0xFF;
    }

    public static void main(String... args) {
        int[][] grid1 = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
        int[][] grid2 = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
        System.out.println(new RottingOranges().orangesRottingBF(grid1));
        System.out.println(new RottingOranges().orangesRottingBFS(grid2));
    }
}
