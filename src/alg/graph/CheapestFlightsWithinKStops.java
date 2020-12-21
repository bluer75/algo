package alg.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * There are n cities connected by m flights. Each fight starts from city u and arrives at v with a price w.
 * Now given all the cities and flights, together with starting city src and the destination dst, your task is 
 * to find the cheapest price from src to dst with up to k stops. If there is no such route, output -1.
 * 
 * Example:
 * Input: 
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 1
 * Output: 200
 * THe cheapest price from city 0 to city 2 with at most 1 stop costs 200.
 * 
 *          0
 *    100 /   \ 500
 *       /     \
 *      *       *  
 *     1--------*2
 *         100
 *
 *  Use BSF with min heap - time complexity is proportional to number of edges O(E).
 */
public class CheapestFlightsWithinKStops {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        // store flights in hash map to allow fast search
        Map<Integer, List<int[]>> edges = new HashMap<>(flights.length);
        for (int[] flight : flights) {
            edges.putIfAbsent(flight[0], new LinkedList<int[]>());
            edges.get(flight[0]).add(flight);
        }
        // min heap allowing finding edge with minimum cost
        Queue<int[]> queue = new PriorityQueue<>((f1, f2) -> f1[2] - f2[2]);
        queue.add(new int[] { src, -1, 0 }); // src, stops, cost
        while (!queue.isEmpty()) {
            int[] d = queue.poll();
            if (d[0] == dst) {
                // found destination
                return d[2];
            }
            if (d[1] >= K) {
                // too many stops - don't explore this path
                continue;
            }
            if (edges.containsKey(d[0])) {
                // check all neighbors
                edges.get(d[0]).forEach(edge -> queue.offer(new int[] { edge[1], d[1] + 1, d[2] + edge[2] }));
            }
        }
        // path not found
        return -1;
    }

    public static void main(String... args) {
        int[][] flights = { { 0, 1, 100 }, { 1, 2, 100 }, { 0, 2, 500 } };
        System.out.println(new CheapestFlightsWithinKStops().findCheapestPrice(flights.length, flights, 0, 2, 1));
    }
}
