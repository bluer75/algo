package alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
 * Input: 5
 * Output:
 * [
 *      [1],
 *     [1,1],
 *    [1,2,1],
 *   [1,3,3,1],
 *  [1,4,6,4,1]
 * ]
 */
public class GeneratePascalsTriangle {
    /**
     * Recursive approach.
     */
    public List<List<Integer>> generate(int numRows) {
        if (numRows <= 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> res = new ArrayList<>(numRows);
        generate(numRows - 1, res);
        return res;

    }

    private void generate(int level, List<List<Integer>> res) {
        if (level == 0) {
            res.add(List.of(1));
            return;
        }
        generate(level - 1, res);
        List<Integer> row = new ArrayList<>(level + 1);
        row.add(1);
        for (int i = 1; i < level; i++) {
            row.add(res.get(level - 1).get(i - 1) + res.get(level - 1).get(i));
        }
        row.add(1);
        res.add(row);
    }
    
    /**
     * Bottom-up approach.
     */
    public List<List<Integer>> generateBottomUp(int numRows) {
        if (numRows <= 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> res = new ArrayList<>(numRows);
        res.add(List.of(1));
        for (int i = 1; i < numRows; i++) {
            List<Integer> row = new ArrayList<>(i + 1);
            row.add(1);
            for (int j = 1; j < i; j++) {
                row.add(res.get(i - 1).get(j - 1) + res.get(i - 1).get(j));
            }            
            row.add(1);
            res.add(row);
        }
        return res;
    }
    
    /**
     * Generate i-th row. Index starts from 0.
     */
    public List<Integer> generateRow(int rowIndex) {
        if (rowIndex < 0) {
            return Collections.emptyList();
        }
        int[] res = new int[rowIndex + 1];
        Arrays.fill(res, 1);
        int prev, tmp;
        for(int i = 1; i < rowIndex; i++) {
            prev = 1;
            for (int j = 1; j <= i; j++) {
                tmp = res[j];
                res[j] = prev + res[j];
                prev = tmp;
            }
        }
        return Arrays.stream(res).boxed().collect(Collectors.toList());
    }    

    public static void main(String... args) {
        System.out.println(new GeneratePascalsTriangle().generateBottomUp(5));
        System.out.println(new GeneratePascalsTriangle().generateRow(4));
    }
}
