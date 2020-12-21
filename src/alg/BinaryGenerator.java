package alg;

/**
 * Generate all binary numbers for given number of bits.
 */
public class BinaryGenerator {
    public static void generate(int size) {
        generate(size, "");
    }

    private static void generate(int size, String prefix) {
        if (size == 0) {
            System.out.println(prefix);
        } else {
            generate(size - 1, prefix + "0");
            generate(size - 1, prefix + "1");
        }
    }

    public static void main(String... args) {
        generate(4);
    }
}
