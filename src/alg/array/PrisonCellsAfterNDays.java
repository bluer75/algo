package alg.array;

import java.util.Arrays;

/**
 * There are 8 prison cells in a row, and each cell is either occupied or vacant.
 * Each day, whether the cell is occupied or vacant changes according to the following rules:
 * If a cell has two adjacent neighbors that are both occupied or both vacant, then the cell becomes occupied.
 * Otherwise, it becomes vacant.
 * (Note that because the prison is a row, the first and the last cells in the row can't have two adjacent neighbors.)
 * We describe the current state of the prison in the following way: cells[i] == 1 if the i-th cell is occupied, else cells[i] == 0.$
 * Given the initial state of the prison, return the state of the prison after N days (and N such changes described above.)
 * 
 *  Example:
 * 
 * Input: cells = [0,1,0,1,1,0,0,1], N = 7
 * Output: [0,0,1,1,0,0,0,0]
 * Explanation: 
 * The following table summarizes the state of the prison on each day:
 * Day 0: [0, 1, 0, 1, 1, 0, 0, 1]
 * Day 1: [0, 1, 1, 0, 0, 0, 0, 0]
 * Day 2: [0, 0, 0, 0, 1, 1, 1, 0]
 * Day 3: [0, 1, 1, 0, 0, 1, 0, 0]
 * Day 4: [0, 0, 0, 0, 0, 1, 0, 0]
 * Day 5: [0, 1, 1, 1, 0, 1, 0, 0]
 * Day 6: [0, 0, 1, 0, 1, 1, 0, 0]
 * Day 7: [0, 0, 1, 1, 0, 0, 0, 0]
 * 
 * Solution is based on simulation and checking if combination of cells has been seen already - cycle found.
 * There can be at most 2^K possible states of cells. If we found the cycle we can skip to the last round and 
 * simulate only remaining days.
 * 
 * Time complexity is O(K * min(N, 2^K)) where K is the number of cells. 
 * Space complexity is O(K).
 * 
 * It can be calculated that for 8 cells the pattern repeats every 14 days (after first day).
 */
public class PrisonCellsAfterNDays {
    public int[] prisonAfterNDays(int[] cells, int N) {
        int[] tday = new int[cells.length];
        int[] yday = new int[cells.length];
        String prev = "";
        String current = Arrays.toString(cells);
        // first day
        for (int k = 1; k < cells.length - 1; k++) {
            yday[k] = (cells[k - 1] == cells[k + 1]) ? 1 : 0;
        }
        prev = Arrays.toString(yday);
        int i = 1;
        while (i < N) {
            for (int k = 1; k < cells.length - 1; k++) {
                tday[k] = (yday[k - 1] == yday[k + 1]) ? 1 : 0;
            }
            current = Arrays.toString(tday);
            int[] tmp = yday;
            yday = tday;
            tday = tmp;
            if (current.equals(prev)) {
                // found cycle - skip to last round
                i *= (N - 1) / i;
            }
            i++;
        }
        return yday;
    }
}
