package alg.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * An image is represented by a 2-D array of integers, each integer representing the pixel value of the image (from 0 to 65535).
 * Given a coordinate (row and column) of the flood fill, and a pixel value newColor, "flood fill" the image.
 * To perform a "flood fill", consider the starting pixel, plus any pixels connected 4-directionally to it of the same color,
 * plus any pixels connected 4-directionally to those pixels (also with the same color as the starting pixel), 
 * and so on. Replace the color of all of the aforementioned pixels with the newColor.
 * 
 * Return the modified image.
 * 
 * The length of image and image[0] will be in the range [1, 50].
 * The given starting pixel will satisfy 0 <= row < image.length and 0 <= column < image[0].length.
 * The value of each color in image[i][j] and newColor will be an integer in [0, 65535].
 * 
 * The simplest solution is to use recursive DFS approach, however it requires O(n) space. 
 */
public class FloodFillBFS {

    // Use BSF to visit all adjacent cells with matching color
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (image == null || image[0] == null) {
            return image;
        }
        if (sr >= image.length || sc >= image[0].length) {
            return image;
        }
        // use HashSet for storing visited cells, pack both coordinates (8 bits row, 8 bits column) to int value
        // otherwise we need to array of boolean of the same size as image
        // Optionally this can be skipped at the cost of revisiting. We change color for given cell so this path
        // won't be explored further when coming form different neighbor
        Set<Integer> visited = new HashSet<>(image.length * image[0].length);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(pack(sr, sc));
        int point;
        int row, col, color = image[sr][sc];
        while (!queue.isEmpty()) {
            point = queue.remove();
            row = getRow(point);
            col = getCol(point);
            image[row][col] = newColor;
            visited.add(point);
            // left
            if (col > 0) {
                point = pack(row, col - 1);
                if (!visited.contains(point) && image[row][col - 1] == color) {
                    queue.add(point);
                }
            }
            // right
            if (col < image[0].length - 1) {
                point = pack(row, col + 1);
                if (!visited.contains(point) && image[row][col + 1] == color) {
                    queue.add(point);
                }
            }
            // up
            if (row > 0) {
                point = pack(row - 1, col);
                if (!visited.contains(point) && image[row - 1][col] == color) {
                    queue.add(point);
                }
            }
            // down
            if (row < image.length - 1) {
                point = pack(row + 1, col);
                if (!visited.contains(point) && image[row + 1][col] == color) {
                    queue.add(point);
                }
            }
        }
        return image;
    }

    private int pack(int row, int col) {
        return row << 8 | col;
    }

    private int getRow(int s) {
        return s >> 8;
    }

    private int getCol(int s) {
        return s & 0xFF;
    }

    public static void main(String... args) {

        int[][] image = { { 1, 1, 1 }, { 1, 1, 0 }, { 1, 0, 1 } };
        // expected [[2,2,2],[2,2,0],[2,0,1]]
        System.out.println(Arrays.deepToString(new FloodFillBFS().floodFill(image, 1, 1, 2)));
    }
}
