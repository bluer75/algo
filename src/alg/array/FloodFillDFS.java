package alg.array;

import java.util.Arrays;

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
public class FloodFillDFS {
    // use DFS to visit all adjacent cells with matching color
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (image == null || image[0] == null) {
            return image;
        }
        if (sr >= image.length || sc >= image[0].length) {
            return image;
        }
        if (image[sr][sc] == newColor) {
            return image;
        }
        dfs(image, sr, sc, newColor);
        return image;
    }

    private void dfs(int[][] image, int row, int col, int newColor) {
        int color = image[row][col];
        image[row][col] = newColor;
        // left
        if (col > 0 && image[row][col - 1] == color) {
            dfs(image, row, col - 1, newColor);
        }
        // right
        if (col < image[0].length - 1 && image[row][col + 1] == color) {
            dfs(image, row, col + 1, newColor);
        }
        // up
        if (row > 0 && image[row - 1][col] == color) {
            dfs(image, row - 1, col, newColor);
        }
        // down
        if (row < image.length - 1 && image[row + 1][col] == color) {
            dfs(image, row + 1, col, newColor);
        }
    }

    public static void main(String... args) {

        int[][] image = { { 1, 1, 1 }, { 1, 1, 0 }, { 1, 0, 1 } };
        // expected [[2,2,2],[2,2,0],[2,0,1]]
        System.out.println(Arrays.deepToString(new FloodFillDFS().floodFill(image, 1, 1, 2)));
    }
}
