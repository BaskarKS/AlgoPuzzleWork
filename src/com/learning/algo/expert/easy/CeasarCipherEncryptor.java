package easy;

/*
* given a non-empty lowercase string and non negative integer as key
* function returns shifting every letter in input for the given key
* letter should wrap around 'z' shifted by one is 'a'
*
* ip : "xyz", 2
* op : "zab"
* */
public class CeasarCipherEncryptor {
    public static void main(String[] args) {
        System.out.println(caesarCypherEncryptorBetter("xyz", 3));
    }

    // ------------------------------------------ My Implementation -----------------------------------
    // O(n) Time and O(n) space
    public static String caesarCypherEncryptor(String str, int key) {
        // Write your code here.
        StringBuilder encryptedString = new StringBuilder();
        final char END_CHAR = 'z';
        final char START_CHAR = 'a';
        final int LOWERCASE_CHAR_COUNT = 26;
        if (key > LOWERCASE_CHAR_COUNT)
            key %= LOWERCASE_CHAR_COUNT;
        for (char ch : str.toLowerCase().toCharArray()) {
            int charVal = ch;
            charVal += key;
            if (charVal <= END_CHAR) {
                encryptedString.append((char)charVal);
                continue;
            }
            int roundCharVal = charVal - END_CHAR;
            int roundedChar = START_CHAR + (roundCharVal - 1);
            encryptedString.append((char)roundedChar);
        }
        return encryptedString.toString();
    }
// ------------------------------------------ End My Implementation -----------------------------------

    // ------------------------------------------ Start Implementation One -----------------------------------
    // O(n) Time and O(n) space
    public static String caesarCypherEncryptorBetter(String str, int key) {
        char[] encryptedString = new char[str.length()];
        for (int idx = 0; idx < str.length(); idx++) {
            encryptedString[idx] = getEncryptedChar(str.charAt(idx), key);
        }
        return new String(encryptedString);
    }
    private static char getEncryptedChar(char charToEncrypt, int key) {
        if (key > 26) key %= 26;
        int charAppliedKey = charToEncrypt + key;
        return (char) ((charAppliedKey <= 'z') ? charAppliedKey : 'a' + ((charAppliedKey % 'z')  - 1));
    }

    //29091 , 29019, Chandra@999
// ------------------------------------------ End Implementation One -----------------------------------
// ------------------------------------------ Start Implementation Two -----------------------------------
    // O(n) Time and O(n) space
    public static String caesarCypherEncryptorBest(String str, int key) {
        char[] result = new char[str.length()];
        key = key % 26;
        String alphabets = "abcdefghijklmnopqrstuvwxyz";
        for (int idx = 0; idx < str.length(); idx++) {
            result[idx] = getEncryptChar(str.charAt(idx), key, alphabets);
        }
        return new String(result);
    }
    public static char getEncryptChar(char origChar, int key, String alphabets) {
        int newLetterCode = alphabets.indexOf(origChar) + key;
        return newLetterCode <= 25 ?
            alphabets.charAt(newLetterCode) :
            alphabets.charAt(((newLetterCode % 25) - 1));
    }
    // index of a is 0
    // z is 25,
    // z + 2(key) => 27
    // 27 % 25 => 2 (which is expected 'b') but 'b' index is 1. hence need to subtract one from result
    // ------------------------------------------ End Implementation Two -----------------------------------

}
