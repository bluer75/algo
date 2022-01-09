package alg.string;

public class RollingHash {
    public static void main(String[] args) {
        String s = "bcdeeebcdxyabcdee";
        long hash = 0;
        int L = 3; // length of substring
        long R = 26; // alphabet size
        long RL = (long) Math.pow(R, L); // max R ^ L
        long M = (long) Math.pow(2, 24); // modulus: 2 ^ 24 to prevent overflow
        // first hash
        for (int i = 0; i < L; ++i) {
            hash = (hash * R + (s.charAt(i) - 'a')) % M; // hash * R + ch
        }
        System.out.println(s.substring(0, L) + " " + hash);
        // rolling hash
        for (int i = 1; i < s.length() - L + 1; i++) {
            hash = (hash * R - (s.charAt(i - 1) - 'a') * RL % M + M) % M; // remove first char -> hash * R - ch * RL
            hash = (hash + (s.charAt(i + L - 1) - 'a')) % M; // add next char -> ch * R ^ 0
            System.out.println(s.substring(i, i + L) + " " + hash);
        }
    }
}
