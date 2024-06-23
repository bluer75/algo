package alg.array;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A wall consists of several rows of bricks of various integer lengths and uniform height. Your goal is to
 * find a vertical line going from the top to the bottom of the wall that cuts through the fewest
 * number of bricks. If the line goes through the edge between two bricks, this does not count as a cut.
 *
 * Example:
 * [[3, 5, 1, 1],
 * [2, 3, 3, 2],
 * [5, 5],
 * [4, 4, 2],
 * [1, 3, 3, 3],
 * [1, 1, 6, 1, 1]]
 *
 * The best we can we do here is to draw a line after the eighth brick, which will only require cutting
 * through the bricks in the third and fifth row.
 *
 * Solution would be to calculate edges of bricks for each row and find element that appears the most - meaning minimal
 * cut.
 * Complexity is O(n) where n is the number of bricks in the wall.
 */
public class BrickWallCut {
    public int leastBricks(List<List<Integer>> wall) {
        if (wall == null || wall.isEmpty()) {
            return 0;
        }
        int wallLength = 0;
        Map<Integer, Integer> edges = new HashMap<>();
        // calculate edges and store their count in a Map edge->count
        for (List<Integer> row : wall) {
            int edge = 0;
            for (int b : row) {
                edge += b;
                edges.merge(edge, 1, Integer::sum);
            }
            wallLength = edge;
        }
        // cut at the end of the wall is not allowed - remove it
        edges.remove(wallLength);
        // find entry with the highest value in the map - max common edge
        return wall.size() - edges.values().stream().max(Comparator.naturalOrder()).get();
    }

    public static void main(String... args) {
        List<List<Integer>> wall = List.of(List.of(3, 5, 1, 1), List.of(2, 3, 3, 2), List.of(5, 5), List.of(4, 4, 2),
            List.of(1, 3, 3, 3), List.of(1, 1, 6, 1, 1));
        long time = System.nanoTime();
        int minCutEdge = new BrickWallCut().leastBricks(wall);
        time = System.nanoTime() - time;
        System.out.println(minCutEdge + " " + TimeUnit.NANOSECONDS.toMillis(time));
    }
}
