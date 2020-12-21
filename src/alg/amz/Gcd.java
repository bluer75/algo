package alg.amz;

public class Gcd {
    public int generalizedGCD(int num, int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int result = arr[0];
        for (int i = 1; i < arr.length; i++) {
            result = gcd(arr[i], result);
            if (result == 1) {
                return 1;
            }
        }
        return result;
    }

    static int gcd(int a, int b) {
        System.out.println(a + " " + b);
        return a == 0 ? b : gcd(b % a, a);
    }

    public static void main(String... args) {
        System.out.println(gcd(8, 4));
    }
}
