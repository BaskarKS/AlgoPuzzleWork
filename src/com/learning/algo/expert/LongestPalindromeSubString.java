public class LongestPalindromeSubString {
    public static boolean isPalindrome(String str) {
        if (str == null || str.isEmpty())
            return false;
        var lastIdx = str.length() - 1;
        for (var i = 0; i <= (lastIdx / 2); i++) {
            if (str.charAt(i) != str.charAt(lastIdx - i))
               return false;
        }
        return true;
    }

    public static String longestPalindrome(String str) {
        var maxSubStr = "";
        for (var L = 0; L < str.length(); L++) {
            for (var R = L; R < str.length(); R++) {
                var subString = str.substring(L, R+1);
                if ((subString.length() > maxSubStr.length()) &&
                        isPalindrome(subString)) {
                    maxSubStr = subString;
                }
            }
        }
        return maxSubStr;
    }
    public static void main(String[] args) {
        System.out.println(longestPalindrome("hebassablobaskksabcheck"));
        //System.out.println(isPalindrome("basksab"));
    }
}
