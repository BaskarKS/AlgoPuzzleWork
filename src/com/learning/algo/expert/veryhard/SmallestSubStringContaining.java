package veryhard;

import java.util.HashMap;
import java.util.Map;

public class SmallestSubStringContaining {
    /*
    given two non-empty strings , big and small, write a function that returns
    smallest substring in the big string that contains all of the small string's characters

    Note:
        1. sub-string can contain other characters not found in small string
        2. chars in sub-string don't have to be in the same order as they appear in small
        string.
        3. if small string contain duplicate characters, sub-string has to contain those
        duplicate characters (can contain more but not fewer)

     assume there will be only one relevant smallest sub-string

     Eg:
     Ip : bigString : "abcd$ef$axb$c$"
            smallString :  "$$abf";

     op: "f$axb$"

     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    //Initial values
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 0;
    rightPtr = 0'
    bigStrHash = {}
    currCharCount = 0
    subStrLen = Integer.MAX
    subStrIdx = [0, 0]

    Iteration 1 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 0
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 0;
    rightPtr = 0'
    bigStrHash = {'a' : 1}
    currCharCount = 1 // as 'a' count in bigStrHash and smallStrHash is equal means we satisfied/matched a char in smallString
    subStrLen = Integer.MAX
    subStrIdx = [0, 0]

     Iteration 2 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 1
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 0;
    rightPtr = 1'
    bigStrHash = {'a' : 1, 'b' : 1} // 'b' is present in smallString, its added
    currCharCount = 2 // as 'b' count is same in bigStrHash and smallStringHash, its accounted as unique char
    subStrLen = Integer.MAX
    subStrIdx = [0, 0]

     Iteration 3 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 2
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 0;
    rightPtr = 2'
    bigStrHash = {'a' : 1, 'b' : 1}
    currCharCount = 2
    subStrLen = Integer.MAX
    subStrIdx = [0, 0]

    Iteration 3 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 3
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 0;
    rightPtr = 3'
    bigStrHash = {'a' : 1, 'b' : 1} // 'd' not present in smallString, its avoided
    currCharCount = 2
    subStrLen = Integer.MAX
    subStrIdx = [0, 0]

    Iteration 4 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 4
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 0;
    rightPtr = 4'
    bigStrHash = {'a' : 1,'b' : 1, '$' : 1} // '$' present in smallString, its added
    currCharCount = 2 // but '$" is not counted as a char because smallString contains 2 '$'
    subStrLen = Integer.MAX
    subStrIdx = [0, 0]

    Iteration 5 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 5
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 0
    rightPtr = 5
    bigStrHash = {'a' : 1,'b' : 1, '$' : 1} // 'e' is not present in smallStringHash, its avoided
    currCharCount = 2
    subStrLen = Integer.MAX
    subStrIdx = [0, 0]


    Iteration 6 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 6
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 0
    rightPtr = 6
    bigStrHash = {'a' : 1,'b' : 1, '$' : 1, 'f' : 1} // 'f' is present in smallStringHash, its added
    currCharCount = 3 // as 'f' count in smallStringHash and bigStringHash is same, its accounted as a char
    subStrLen = Integer.MAX
    subStrIdx = [0, 0]


    Iteration 7 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 7
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 0
    rightPtr = 7
    bigStrHash = {'a' : 1,'b' : 1, '$' : 2, 'f' : 1} // '$' is present in smallStringHash, its added
    currCharCount = 4 // as '$' count in smallStringHash and bigStringHash is same(2), its accounted as a char
    subStrLen = 7  // as the currCharCount and smallStrCharCount is same(4) means we got a subString, lets record the length and indexes if its less that its previous recorded values
    subStrIdx = [0, 7]

    As we encounter a subString between idx 0-7 now we have to traverse leftPtr to right to remove unwanted chars
    Iteration 7 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 7
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 1
    rightPtr = 7
    bigStrHash = {'a' : 0,'b' : 1, '$' : 2, 'f' : 1} // 'a' is present in smallStringHash, its removed
    currCharCount = 3 // as 'a' count in smallStringHash and bigStringHash is not same, no of Chars is reduced
    subStrLen = 7  // as the currCharCount and smallStrCharCount is same(4) means we got a subString, lets record the length and indexes if its less that its previous recorded values
    subStrIdx = [0, 7]

    As we lose subString between idx 1-7 now we have to traverse rightPtr to right to add the removed char to form a substring
    Iteration 8 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 8
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 1
    rightPtr = 8
    bigStrHash = {'a' : 1,'b' : 1, '$' : 2, 'f' : 1} // 'a' is present in smallStringHash, its added
    currCharCount = 4 // as 'a' count in smallStringHash and bigStringHash is same(1), its accounted as a char
    subStrLen = 7  // as the currCharCount and smallStrCharCount is same(4) means we got a subString, lets record the length and indexes if its less that its previous recorded values
    subStrIdx = [1, 8]

    As we encounter a subString between idx 1-8 now we have to traverse leftPtr to right to remove unwanted chars
    Iteration 8 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 8
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 2
    rightPtr = 8
    bigStrHash = {'a' : 1,'b' : 0, '$' : 2, 'f' : 1} // 'b' is present in smallStringHash, its removed
    currCharCount = 3 // as 'b' count in smallStringHash and bigStringHash is not same(0), no of charCount is reduced
    subStrLen = 7  // as the currCharCount and smallStrCharCount is same(4) means we got a subString, lets record the length and indexes if its less that its previous recorded values
    subStrIdx = [1, 8]

    As we lose subString between idx 2-8 now we have to traverse rightPtr to right to add the removed char(b) to form a substring
    Iteration 9 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 9
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 2
    rightPtr = 9
    bigStrHash = {'a' : 1,'b' : 0, '$' : 2, 'f' : 1} // 'x' is not present in smallStringHash, its avoided
    currCharCount = 3 // since currCharCount and smallStrCharCount doesnt match we still navigate rightPtr to form a subString
    subStrLen = 7  // we dont encounter here
    subStrIdx = [1, 8]

    As we lose subString between idx 2-8 now we have to traverse rightPtr to right to add the removed char to form a substring
    Iteration 10 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 10
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 2
    rightPtr = 10
    bigStrHash = {'a' : 1,'b' : 1, '$' : 2, 'f' : 1} // 'b' is present in smallStringHash, its added
    currCharCount = 4 // since currCharCount and smallStrCharCount match we increment this variable to 1
    subStrLen = 7  // we dont encounter here // since right - left (10 - 2) is > 7 we dont update this size and below indexes
    subStrIdx = [1, 8]


    As we encounter a subString between idx 2-10 now we have to traverse leftPtr to right to remove unwanted chars
    Iteration 10: iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 10
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 3 // c is not present in smallString, hence we ignore and continue incrementing leftPtr until theres is a reduction in currCharCount
    rightPtr = 10
    bigStrHash = {'a' : 1,'b' : 1, '$' : 2, 'f' : 1} // 'c' is not present in smallStringHash, its avoided
    currCharCount = 4 // since currCharCount and smallStrCharCount match we continue navigate leftPtr
    subStrLen = 7  // we dont encounter here // since right - left (10 - 3) is >= 7 we update this size and below indexes
    subStrIdx = [3, 10]

     As we encounter a subString between idx 3-10 now we have to traverse leftPtr to right to remove unwanted chars
    Iteration 10 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 10
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 4 // d is not present in smallString, hence we ignore and continue incrementing leftPtr until theres is a reduction in currCharCount
    rightPtr = 10
    bigStrHash = {'a' : 1,'b' : 1, '$' : 2, 'f' : 1} // 'd' is not present in smallStringHash, its avoided
    currCharCount = 4 // since currCharCount and smallStrCharCount match we continue navigate leftPtr
    subStrLen = 7  // we encounter here // since right - left (10 - 4) is < 7 we update this size and below indexes
    subStrIdx = [4, 10]

    As we encounter a subString between idx 3-10 now we have to traverse leftPtr to right to remove unwanted chars
    Iteration 10 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 10
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 5 // $ is present in smallString, hence we remove it
    rightPtr = 10
    bigStrHash = {'a' : 1,'b' : 1, '$' : 1, 'f' : 1} // '$' is  present in smallStringHash, its removed, now the '$' count in smallStringHash and CurrentBigStringHash doesnt match, hence we decrement the currentCharCount by 1
    currCharCount = 3 // since currCharCount and smallStrCharCount doesn't match we still navigate rightPtr to form a subString
    subStrLen = 7  // we encounter here // since right - left (10 - 4) is < 7 we update this size and below indexes
    subStrIdx = [4, 10]

    As we don't encounter a subString between idx 5-10 now we have to traverse RightPtr to right to add the remove char '$' to form a substring
    Iteration 11 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 11
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 5
    rightPtr = 11 // $ is present in smallString, hence we add it
    bigStrHash = {'a' : 1,'b' : 1, '$' : 2, 'f' : 1} // '$' is  present in smallStringHash, its added, now the '$' count in smallStringHash and CurrentBigStringHash match, hence we increment the currentCharCount by 1
    currCharCount = 4 // since currCharCount and smallStrCharCount match we navigate leftPtr to right to remove unwanted chars
    subStrLen = 6  // we encounter here // since right - left (11 - 5) is < 7 we update this size and below indexes
    subStrIdx = [5, 11]

     As we encounter a subString between idx 5-11 now we have to traverse LeftPtr to right to remove unwanted char to continue maintain substring
    Iteration 11 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 11
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 6
    rightPtr = 11 // 'e'' is not present in smallString, hence we ignore
    bigStrHash = {'a' : 1,'b' : 1, '$' : 2, 'f' : 1} // 'e' is not present in smallStringHash,
    currCharCount = 4 // since currCharCount and smallStrCharCount match we continue navigate leftPtr to right to remove unwanted chars
    subStrLen = 5  // we encounter here // since right - left (11 - 6) is < 6 we update this size and below indexes
    subStrIdx = [6, 11]

    As we encounter a subString between idx 6-11 now we have to traverse LeftPtr to right to remove unwanted char to continue maintain substring
    Iteration 11 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 11
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 7
    rightPtr = 11 // 'f' is present in smallString, hence we decrement the char count in currentHashtable
    bigStrHash = {'a' : 1,'b' : 1, '$' : 2, 'f' : 0} // 'f' is present in smallStringHash, decrement 'f' value by 1
    currCharCount = 3 // since currCharCount and smallStrCharCount doesn't match we continue navigate rightPtr to find the removed char 'f' by leftPtr to form the subStringSeq
    subStrLen = 5  // since currCharCount and smallStrCharCount doesn't match, no changes observed here, we explore here only if theres a substring between leftPtr and RightPtr
    subStrIdx = [6, 11]

    As we don't encounter a subString between idx 7-11 now we have to traverse rightPtr to right to find the removed needed char 'f' by leftPtr to continue maintain substring
    Iteration 12 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 12
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 7
    rightPtr = 12 // 'c' is not present in smallString, hence we ignore and continue
    bigStrHash = {'a' : 1,'b' : 1, '$' : 2, 'f' : 0} // 'c' is not present in smallStringHash we ignore updating currentHashTable
    currCharCount = 3 // since currCharCount and smallStrCharCount doesn't match we continue navigate rightPtr to find the removed char 'f' by leftPtr to form the subStringSeq
    subStrLen = 5  // since currCharCount and smallStrCharCount doesn't match, no changes observed here, we explore here only if theres a substring between leftPtr and RightPtr
    subStrIdx = [6, 11]

    As we don't encounter a subString between idx 7-12 now we have to traverse rightPtr to right to find the removed needed char 'f' by leftPtr to continue maintain substring
    Iteration 13 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 13
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 7
    rightPtr = 13 // '$' is present in smallString, hence we update the currentHashTable
    bigStrHash = {'a' : 1,'b' : 1, '$' : 3, 'f' : 0} // '$' is present in smallStringHash we update currentHashTable
    currCharCount = 3 // since currCharCount and smallStrCharCount doesn't match we continue navigate rightPtr to find the removed char 'f' by leftPtr to form the subStringSeq
    subStrLen = 5  // since currCharCount and smallStrCharCount doesn't match, no changes observed here, we explore here only if theres a substring between leftPtr and RightPtr
    subStrIdx = [6, 11]

    As we reached end of traversing bigString, rightPtr reached the end, the last smallest subString
    recorded in subStrIdx is [6,11] and subStrLen is 5, this will be the smallestSubSeq which
    contain all characters of smallString and its returned to caller

    Iteration 13 : iterating only rightPtr from left till end of String 0 to 13 - rightPtr = 13
     bigString : "a b c d $ e f  $ a x  b   $   c    $"
                         0 1 2 3 4 5 6 7 8 9 10 11 12 13
    smallStrHash = {'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1}
    smallStrCharCount = 4 // len(smaStrHash)
    leftPtr = 7
    rightPtr = 13 // '$' is present in smallString, hence we update currentHashTable
    bigStrHash = {'a' : 1,'b' : 1, '$' : 3, 'f' : 0} // '$' is present in smallStringHash we update currentHashTable
    currCharCount = 3 // since currCharCount and smallStrCharCount doesn't match we continue navigate rightPtr to find the removed char 'f' by leftPtr to form the subStringSeq
    subStrLen = 5  // since currCharCount and smallStrCharCount doesn't match, no changes observed here, we explore here only if theres a substring between leftPtr and RightPtr
    subStrIdx = [6, 11]
    */
    public static void main(String[] args) {
        String bigString = "abcd$ef$axb$c$";
        String smallString = "$$abf";
        String smallSubString = smallestSubstringContaining(bigString, smallString);
        System.out.println(bigString);
        System.out.println(smallString);
        System.out.println(smallSubString);
    }

    /*
    Time O(b + s), Space : O(b + s) // worst case big-str length is equal to small-str length
                                                                    and all chars are unique 2b ~= b
    b is length of bigString
    s is length of smallString
    */
    public static String smallestSubstringContaining(String bigString, String smallString) {
        Map<Character, Integer> charData = new HashMap<>(); // to store the count of chars in small string
        Map<Character, Integer> currentCharData = new HashMap<>(); // temp variable to store the chars of bigString which only present in small string
        for (Character ch: smallString.toCharArray()) { // initializing the smallString char count
            if (!charData.containsKey(ch))
                charData.put(ch, 0);
            charData.put(ch, charData.get(ch) + 1);
        } // charData{'$' : 2, 'a' : 1, 'b' : 1, 'f' : 1} // total 4 chars, $ count should be 2 to consider the $ char
        // actual algorithm starts here, rightPtr will traverse big-string from left to right until all the chars of small string it encounters and stops there then leftptr will start from left to right to remove the unwanted chars and stops when the current char count reduces to total char count of small string, then again rightPtr continues to right
        int leftPtr = 0, rightPtr = 0;
        int charCount = charData.size(), currCharCount = 0; // currCharCount to keep track of all chars of smallString is encountered in bigString
        int subStringLength = Integer.MAX_VALUE; // maintain encountered subString length, if newly encountered substring is less in length then its updated with the lesser length value
        int[] subStringInfo = new int[2]; // maintain encountered start and end index, if newly encountered substring is less in length then its updated with the lesser substring start and end idx values
        while (rightPtr < bigString.length()) { // do a linear traversal of rightPointer on bigString from left to right,
            char rightChar = bigString.charAt(rightPtr);
            if (!charData.containsKey(rightChar)) { // if the encountered char is not in smallString hashTable, we skip it and increment the rightPtr // charData doesn't contain i/p char like 'c' and 'd'
                rightPtr++;
                continue;
            }

            if (!currentCharData.containsKey(rightChar)) // means the encountered char is present in smallString hashtable and not present in hashtable maintained for encountered chars in bigString, we initialize it. Initially '$'. 'a', 'b', 'f' are initialised in map with count 0
                currentCharData.put(rightChar, 0);

            currentCharData.put(rightChar, currentCharData.get(rightChar) + 1); // increment the count of char for bigString hashTable

            if (currentCharData.get(rightChar) == charData.get(rightChar)) // if the encountered char count is equal between bigString hashTable and smallString hashTable, we have accounted/satisfied/matched a char in smallString with bigString. '$' count should be 2 to consider it as a character as the smallString contains '$' (2)
                currCharCount += 1;

            //this loop is to move leftPtr from left to right, to remove the chars not present in smallString and also maintain the encountered substring contain all chars of smallString
            while (charCount == currCharCount && leftPtr <= rightPtr) { // when total chars of smallString and currently navigating bigString is equal, means we have encountered a substring between a leftPtr and rightPtr. // when leftPtr is 0 and rightPtr is 7, this condition will satisfy
                if ((rightPtr - leftPtr) < subStringLength) { // if the newly encountered substring length is smaller than the previously recorded one, then we record its length, start and end indexes
                    subStringLength = (rightPtr - leftPtr); // 7
                    subStringInfo[0] = leftPtr; // 0
                    subStringInfo[1] = rightPtr; // 7
                }
                char leftChar = bigString.charAt(leftPtr); // encounter the char at leftPtr // 'a' (0)
                if (!charData.containsKey(leftChar)) { // if the char is not present in smallString we skip it by reducing the leftPtr which essentially will reduce the subString length(we are in need of smallest subString)
                    leftPtr++;
                    continue;
                }

                if (currentCharData.get(leftChar) == charData.get(leftChar)) // if we encounter the leftPtr char which contains in smallString, means we need it maintain all the chars of smallString in our substring, 'a' count in smallString hashMap is 1 and 'a' count in bigString hashMap is 1 hence this condition satisfies for leftPtr is 0
                    currCharCount -= 1; // we reduce the total smallString chars which are encountered in bigString to 1 // since leftPtr is moved to right (1), we will lose 'a' in our subString, now our substring doesn't contain all smallString chars, it misses 'a'

                currentCharData.put(leftChar, currentCharData.get(leftChar) - 1); // update the bigString hashMap by reducing the leftPtr char to 1, because of left Ptr movement to right. since leftPtr is moved to right from 0 to 1, we lose 'a' in our subString, we update the same in bigString hashTable
                leftPtr++; // moving leftPtr to right // since we lose 'a' in our substring. now currCharCount will be 3, this loop will fail. outer loop will continue until rightPtr finds 'a' and include it in our subString. this loop will again encounter when leftPtr = 1, rightPtr = 8
            } // this loop breaks when the encountered substring lose a char(smallString char) which must present in substring, hence we traverse right to again find that particular character by navigating through right pointer
            rightPtr++;
        }
        return (subStringLength == Integer.MAX_VALUE) ? "" : bigString.substring(subStringInfo[0], subStringInfo[1] + 1); // if we dont encounter a substring then we return a empty string
    }
}
