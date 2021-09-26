package alg.array;

import java.util.LinkedList;
import java.util.Queue;

public class MinStopsToTarget {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        if (startFuel >= target) {
            return 0;
        }
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < stations.length && stations[i][0] <= startFuel; i++) {
            queue.offer(new int[]{i, startFuel - stations[i][0]});
        }
        int size = 0;
        int[] data = null;
        int stops = 0;
        while (!queue.isEmpty()) {
            stops++;
            size = queue.size();
            while (size-- > 0) {
                System.out.println("Got " + queue.peek()[0] + " " + queue.peek()[1]);
                data = queue.poll();
                if (stations[data[0]][0] + data[1] + stations[data[0]][1] >= target) {
                    return stops;
                }
                int reach = stations[data[0]][0] + data[1] + stations[data[0]][1];
                for (int i = data[0] + 1; i < stations.length && stations[i][0] <= reach; i++) {
                    queue.offer(new int[]{i, reach - stations[i][0]});
                    System.out.println("Added " + i + " " + (reach - stations[i][0]));
                }
            }
        }
        return -1;
    }

    public static void main(String... args) {
        System.out.println(
            new MinStopsToTarget().minRefuelStops(100, 10, new int[][]{{10, 60}, {20, 30}, {30, 30}, {60, 40}}));
    }
}
