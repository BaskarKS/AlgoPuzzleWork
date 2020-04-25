package hard;

public class MergeLinkedLists {

    static class LinkedList {
        int value;
        LinkedList next;
        public LinkedList(int value) {
            this.value = value;
        }
    }

    // Iterative Solution
    //Time Complexity O(N + M) - N is length of listOne and M is length of listTwo
    //Space Complexity is O(1)
    public static LinkedList mergeLinkedLists(LinkedList headOne, LinkedList headTwo) {
        // Write your code here.
        if (headOne == null || headTwo == null)
            return headOne == null ? headTwo : headOne;

        LinkedList listOne = headOne, toReturn = headOne;
        LinkedList listTwo = headTwo;
        LinkedList listOnePrev = null;

        while (listOne != null && listTwo != null) {
            if (listOne.value < listTwo.value) {
                listOnePrev = listOne;
                listOne = listOne.next;
                continue;
            }
/*
            if (listOnePrev == null) {
                listOnePrev = listTwo;
                listTwo = listTwo.next;
                listOnePrev.next = listOne;
                toReturn = listOnePrev;
                continue;
            }
*/
            if (listOnePrev != null)
                listOnePrev.next = listTwo;
            listOnePrev = listTwo;
            listTwo = listTwo.next;
            listOnePrev.next = listOne;
        }
        if (listOne == null && listTwo != null)
            listOnePrev.next = listTwo;

        //return toReturn;
        return headOne.value < headTwo.value ? headOne : headTwo;
    }


    //Recursive solution
    //O(N + M) time complexity, space complexity - O(N + M) recursive calls
    public static LinkedList mergeLinkedListsRecursive(LinkedList headOne, LinkedList headTwo) {
        mergeRecursive(headOne, headTwo, null);
        return headOne.value < headTwo.value ? headOne : headTwo;
    }
    private static void mergeRecursive(LinkedList listOne, LinkedList listTwo, LinkedList listOnePrev) {
        if (listOne == null) {
            listOnePrev.next = listTwo;
            return;
        }
        if (listTwo == null)
            return;
        if (listOne.value < listTwo.value)
            mergeRecursive(listOne.next, listTwo, listOne);
        else {
            if (listOnePrev != null)
                listOnePrev.next = listTwo;
            LinkedList holdListTwoLink = listTwo.next;
            listTwo.next = listOne;
            mergeRecursive(listOne, holdListTwoLink, listTwo);
        }
    }
}
