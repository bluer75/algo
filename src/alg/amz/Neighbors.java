package alg.amz;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Neighbors 
public class Neighbors {
    public List<Integer> cellCompete(int[] states, int days) {
        while (days-- > 0) {
            int prev = 0;
            int current;
            int next = (states.length > 1) ? states[1] : 0;
            for (int i = 0; i < states.length; i++) {
                current = states[i];
                states[i] = (prev == next) ? 0 : 1;
                prev = current;
                next = (i >= states.length - 2) ? 0 : states[i + 2];
            }
        }
        return Arrays.stream(states).boxed().collect(Collectors.toList());
    }

    public static void main(String... args) {
        System.out.println(new Neighbors().cellCompete(new int[] { 1, 1, 1, 0, 1, 1, 1, 1 }, 2));
    }
}
