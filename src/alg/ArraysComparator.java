package alg;

/**
 * Check if given arrays are equal. 
 * There were originally char[] where there can be a BACKSPACE (B)
 * character that should remove previous character.
 * [a,b,b,B,c]
 * [a,b,c,d,B]
 * [0,1,2,3,4]
 */
public class ArraysComparator {

    static boolean areEqual(char[] arr1, char[] arr2) {
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1 == null || arr2 == null) {
            return false;
        }
        int i = arr1.length - 1;
        int j = arr2.length - 1;
        int bs = 0;
        while (i >= 0 && j >= 0) {
            bs = 0;
            while (i >= bs && arr1[i] == 'B') {
                bs++;
                i--;
            }
            i -= bs;
            bs = 0;
            if (j >= bs && arr2[j] == 'B') {
                bs++;
                j--;
            }
            j -= bs;

            if (arr1[i] != arr2[j]) {
                return false;
            }
            i--;
            j--;
        }
        // skip B at the beginning
        // ADDED_START
        bs = 0;
        while (i >= bs && arr1[i] == 'B') {
            bs++;
            i--;
        }
        i -= bs;
        bs = 0;
        if (j >= bs && arr2[j] == 'B') {
            bs++;
            j--;
        }
        j -= bs;
        // ADDED_END
        return i == j ? true : false;
    }

    public static void main(String... args) {
        char[] arr1 = { 'a', 'b', 'b', 'B', 'c' };
        char[] arr2 = { 'a', 'b', 'c', 'd', 'B' };
        // System.out.println(areEqual(arr1, arr2));
        arr1 = new char[] { 'B', 'B', 'a', 'b', 'c', 'B', 'B' };
        arr2 = new char[] { 'a' };
        System.out.println(areEqual(arr1, arr2));
    }

}
