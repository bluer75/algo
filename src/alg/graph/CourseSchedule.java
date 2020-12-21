package alg.graph;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * There are a total of n courses you have to take, labeled from 0 to n-1.
 * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, 
 * which is expressed as a pair: [0,1]
 * Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?
 * 
 * Example 1:
 * 
 * Input: 2, [[1,0]] 
 * Output: true
 * 
 * Example 2:
 * 
 * Input: 2, [[1,0],[0,1]]
 * Output: false
 * 
 * Solution uses DFS to discover if there is back edge in the graph that indicates cycle.
 * 
 * Faster solution is based on checking indegree for each vertex and eliminating leaves. 
 * What remains contains a cycle - Kahn’s algorithm.
 * 
 * This requires O(V + E). 
 */
public class CourseSchedule {
    private enum Color {
        WHITE, GRAY, BLACK;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses == 0 || prerequisites == null) {
            return true;
        }
        HashMap<Integer, List<Integer>> neighbors = new HashMap<>(numCourses);
        for (int[] prerequisite : prerequisites) {
            neighbors.putIfAbsent(prerequisite[1], new LinkedList<>());
            neighbors.get(prerequisite[1]).add(prerequisite[0]);
        }
        Color[] colors = new Color[numCourses];
        Arrays.fill(colors, Color.WHITE);
        for (int i = 0; i < numCourses; i++) {
            if (colors[i] == Color.WHITE) {
                if (!dfs(i, colors, neighbors)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean dfs(int i, Color[] colors, Map<Integer, List<Integer>> neighbors) {
        if (colors[i] == Color.GRAY) {
            return false;
        }
        colors[i] = Color.GRAY;
        for (int neighbor : neighbors.getOrDefault(i, Collections.emptyList())) {
            if (!dfs(neighbor, colors, neighbors)) {
                return false;
            }
        }
        colors[i] = Color.BLACK;
        return true;
    }

    public boolean canFinishKahn(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        HashMap<Integer, List<Integer>> neighbors = new HashMap<>(numCourses);
        for (int[] prerequisite : prerequisites) {
            // convert prerequisites to adjacency list
            neighbors.putIfAbsent(prerequisite[1], new LinkedList<>());
            neighbors.get(prerequisite[1]).add(prerequisite[0]);
            // count incoming edges for each vertex
            indegree[prerequisite[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) {
                // enqueue all vertex with no incoming edges - leaves
                queue.offer(i);
            }
        }
        // as long as there are leaves we decrease indegree for all its neighbors and
        // enqueue new leaves - each iteration peels off vertex
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (Integer neighbor : neighbors.getOrDefault(vertex, Collections.emptyList())) {
                if (--indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        // if there are still vertices with incoming edges there must be a cycle - no more leaves
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] > 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String... args) {
        int[][] prerequisites = new int[][] { { 3, 4 }, { 3, 0 }, { 0, 2 }, { 2, 1 }, { 1, 0 } };
        System.out.println(new CourseSchedule().canFinish(5, prerequisites));
        System.out.println(new CourseSchedule().canFinishKahn(5, prerequisites));
    }
}
