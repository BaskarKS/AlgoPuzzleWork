package hard;

public class InterweavingStrings {
/*
    function takes 3 strings and returns boolean representing whether third string
    can be formed by interweaving first and second string.

    interweave is merge string one and two by altering their letters without any pattern
    first = "abc"
    second = "123"
    third = "abc123" or "a1b2c3" or "ab1c23"

    eg:
    ip:
    first = "algoexpert"
    second = "your-dream-job"
    third = "your-algodream-expertjob"
    op: true
 */
    public static void main(String[] args) {
        boolean status = interweavingStringsCached("a",
            "af", "afa");
        System.out.println(status);
    }
    public static boolean interweavingStrings(String one, String two, String three) {
        // Write your code here.
        if (three.length() != (one.length() + two.length()))
            return false;

        return checkWeave(one, two, three, 0, 0);
    }

    // time Complexity : O(2 ^ (n + m)) - n is length of one, m is length of two
    //space complexity: O ( n + m)
    private static boolean checkWeave(String one, String two, String three,
                                      int oneIdx, int twoIdx) {
        // unnecessary recursive function calls which the situation is already faced in other call path is
        // stored to avoid the unnecessary function calls by using a cache
        int thirdIdx = oneIdx + twoIdx;
        if (thirdIdx == three.length())
            return true; // if second string part is at the end of the third string, this true is returned to caller

        if (oneIdx < one.length() && one.charAt(oneIdx) == three.charAt(thirdIdx)) {
            // if the second string is at beginning of third string, in that case the recursive calls done for
            // first string should be dropped instead of returning false to caller of this function
            if (checkWeave(one, two, three, oneIdx + 1, twoIdx)) // false returned by this checkWeave() is dropped, force the below function call of investigating second string in third string
            return true; // if first string part is at end of third string this true is returned to caller
        }
        if (twoIdx < two.length() && two.charAt(twoIdx) == three.charAt(thirdIdx))
            return checkWeave(one, two, three, oneIdx, twoIdx + 1);

        return false;
    }

    // time Complexity : O(n * m) - n is length of one, m is length of two
    //space complexity: O (n m) - because of cache
    public static boolean interweavingStringsCached(String one, String two, String three) {
        // Write your code here.
        if (three.length() != (one.length() + two.length()))
            return false;

        Boolean[][] cache = new Boolean[one.length() + 1][two.length() + 1];
        return checkWeaveUsingCaching(one, two, three, 0, 0, cache);
    }
    private static boolean checkWeaveUsingCaching(String one, String two, String three,
                                      int oneIdx, int twoIdx, Boolean[][] cache) {
        if (cache[oneIdx][twoIdx] != null)
            return cache[oneIdx][twoIdx];

        int k = oneIdx + twoIdx;
        if (k == three.length())
            return true;

        if (oneIdx < one.length() && one.charAt(oneIdx) == three.charAt(k)) {
            cache[oneIdx][twoIdx] = checkWeaveUsingCaching(one, two, three, oneIdx + 1, twoIdx, cache);
            if (cache[oneIdx][twoIdx])
                return true;
        }

        if (twoIdx < two.length() && two.charAt(twoIdx) == three.charAt(k)) {
            cache[oneIdx][twoIdx] = checkWeaveUsingCaching(one, two, three, oneIdx, twoIdx + 1, cache);
            return cache[oneIdx][twoIdx];
        }

        cache[oneIdx][twoIdx] = false;
        return false;
    }
}
