package alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Given a collection of distinct integers, return all possible permutations.
 * If there are n elements, there is n possibilities for first element, n-1 for second and so on.
 * 
 * We fix one element and try recursively permutation for remaining elements. Then un-fix that element and
 * fix next one. This can be also done with swapping elements.
 * Complexity O(2^n).
 */
public class PermutationGenerator {

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> available = Arrays.stream(nums).boxed().collect(Collectors.toList());
        permute(available, new ArrayList<Integer>(), res);
        return res;
    }

    private void permute(List<Integer> available, List<Integer> selected, List<List<Integer>> res) {
        if (available.isEmpty()) {
            res.add(new ArrayList<Integer>(selected));
            return;
        } else {
            for (int i = 0; i < available.size(); i++) {
                Integer v = available.remove(i);
                // choose
                selected.add(v);
                // explore
                permute(available, selected, res);
                // un-choose
                available.add(i, v);
                selected.remove(v);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void permutations(int[] nums, int currentIndex) {
        if (currentIndex == nums.length - 1) {
            System.out.println(Arrays.toString(nums));
            return;
        }

        for (int i = currentIndex; i < nums.length; i++) {
            swap(nums, currentIndex, i);
            permutations(nums, currentIndex + 1);
            swap(nums, currentIndex, i);
        }
    }

    public static void main(String... args) {
        PermutationGenerator pg = new PermutationGenerator();
        System.out.println(pg.permute(new int[] { 1, 2, 3, 4 }));
        pg.permutations(new int[] { 1, 2, 3, 4 }, 0);
    }
}
