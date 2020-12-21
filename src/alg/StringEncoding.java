package alg;

/**
 * Run-length encoding is a fast and simple method of encoding strings. 
 * The basic idea is to represent repeated successive characters as a single count and character. 
 * For example, the string "AAAABBBCCDAA" would be encoded as "4A3B2C1D2A".
 * Implement run-length encoding and decoding. 
 * You can assume the string to be encoded have no digits and consists solely of alphabetic characters. 
 * You can assume the string to be decoded is valid.
 */
public class StringEncoding {
    public static String encode(String str) {
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        int length = 0;
        char prev = chars[0];
        for (char ch : chars) {
            if (ch == prev) {
                length++;
            } else {
                sb.append(length).append(prev);
                length = 1;
                prev = ch;
            }
        }
        sb.append(length).append(prev);
        return sb.toString();
    }

    public static String decode(String str) {
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        int length = 0;
        for (int i = 0; i < chars.length; i++) {
            if (i % 2 == 0) {
                length = chars[i] - '0';
            } else {
                while (length-- > 0) {
                    sb.append(chars[i]);
                }
            }
        }
        return sb.toString();
    }

    public static void main(String... args) {
        String str = "AAAABBBCCDAA";
        System.out.format("%s %s %s", str, str = encode(str), decode(str));
    }
}
