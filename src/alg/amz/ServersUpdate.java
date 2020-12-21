package alg.amz;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Count numbers of days needed to update all servers.
 * Variation of RottingOranges problem.
 */
public class ServersUpdate {
    public int minimumDays(int rows, int columns, List<List<Integer>> grid) {
        // validate input parameters
        if (rows <= 0 || columns <= 0 || grid == null || grid.size() != rows) {
            return 0;
        }
        // convert grid to array for fast access
        int[][] map = new int[rows][columns];
        int row = 0;
        int col = 0;
        for (List<Integer> lrow : grid) {
            for (int lcol : lrow) {
                map[row][col++] = lcol;
            }
            row++;
            col = 0;
        }
        return updateAndCount(map);
    }

    private int updateAndCount(int[][] grid) {
        Queue<Integer> queue = new LinkedList<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 1) {
                    // found updated server
                    queue.offer(pack(row, col));
                }
            }
        }
        int time = 0;
        // run BFS for each updated server
        while (!queue.isEmpty()) {
            int count = queue.size();
            boolean updated = false;
            // process whole level
            while (--count >= 0) {
                int code = queue.remove();
                int row = getRow(code);
                int col = getCol(code);
                if (grid[row][col] == 1 && time > 0) {
                    continue; // visited already (except first level where time is 0)
                }
                if (grid[row][col] == 0) {
                    updated = true;
                }
                grid[row][col] = 1; // mark as visited
                if (isOutdated(grid, row - 1, col)) {
                    queue.offer(pack(row - 1, col));
                }
                if (isOutdated(grid, row + 1, col)) {
                    queue.offer(pack(row + 1, col));
                }
                if (isOutdated(grid, row, col - 1)) {
                    queue.offer(pack(row, col - 1));
                }
                if (isOutdated(grid, row, col + 1)) {
                    queue.offer(pack(row, col + 1));
                }
            }
            if (updated) {
                // there were some updates
                time++;
            }
        }
        return time;
    }

    private boolean isOutdated(int[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[row].length && grid[row][col] == 0;
    }

    // stores row and column in single int value
    private int pack(int row, int col) {
        return row << 8 | col;
    }

    // extracts row from compressed format
    private int getRow(int code) {
        return code >> 8;
    }

    // extracts column from compressed format
    private int getCol(int code) {
        return code & 0xFF;
    }

    public static void main(String... args) {
        List<List<Integer>> ll = new ArrayList<>();
        ll.add(List.of(1, 1, 1, 1, 1));
        ll.add(List.of(1, 1, 1, 0, 1));
        ll.add(List.of(1, 0, 1, 1, 1));
        ll.add(List.of(1, 1, 1, 1, 1));
        System.out.println(new ServersUpdate().minimumDays(4, 5, ll));
    }
}
