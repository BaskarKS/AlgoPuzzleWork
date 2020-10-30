package veryhard;

public class RearrangeLinkedList {
    /*
    Function takes in a head of a single linked list and a integer k,

    Rearranges the list in-place(doesn't create new list / nodes)
    and returns the new head.

    Rearranging a linked list around nodes with value k is
    moving all nodes with node value less than k value before the node value equal to k
    moving all nodes with node value greater than k value after the node value equal to k

    all moved nodes should maintain the relative ordering as its before in the input list
    Note: linked list should be arranged even if it doesn't have any nodes with value k

    Each node will have a integer value and a next node pointer.

    Input list will always contain at-least one node, means head will never be null

    Eg: Input : 3 -> 0 -> 5 -> 2 -> 1 -> 4  // head with value 3
            k = 3
           output : 0 ->  2 -> 1 -> 3 -> 5  -> 4  // new head with value 0
           nodes 0 ->  2 -> 1 have maintained the relative order
    */

    //Time : O(N), Space : O(1)
    public static LinkedList rearrangeLinkedList(LinkedList head, int k) {
        LinkedList smallerHead = null, smallerTail = null;
        LinkedList equalHead = null, equalTail = null;
        LinkedList largerHead = null, largerTail = null;
        LinkedList current = head;
        while(current != null) { // iterating the entire length of List only once
            LinkedList[] headAndTail = null;
            if (current.value < k) {
                headAndTail = growLinkedList(smallerHead, smallerTail, current); // every node is investigated and added it as part of small  / equal / larger list
                smallerHead = headAndTail[0];
                smallerTail = headAndTail[1];
            } else if (current.value > k) {
                headAndTail = growLinkedList(largerHead, largerTail, current); // this method will construct the list with current node passed, will return the new head and tail
                largerHead = headAndTail[0];
                largerTail = headAndTail[1];
            } else {
                headAndTail = growLinkedList(equalHead, equalTail, current);
                equalHead = headAndTail[0];
                equalTail = headAndTail[1];
            }
            LinkedList previous = current;
            current = current.next;
            previous.next = null;
        }
        LinkedList[] firstMerge = connectLinkedLists(smallerHead, smallerTail, equalHead, equalTail); // smaller and equal linked list is merged and new head and tail is returned to be merged with larger list
        LinkedList[] finalMerge = connectLinkedLists(firstMerge[0], firstMerge[1], largerHead, largerTail); // previous merged lists is merged with the larger list
        return finalMerge[0];
    }

    public static LinkedList[] connectLinkedLists(LinkedList headOne,
                                                  LinkedList tailOne, LinkedList headTwo,
                                                  LinkedList tailTwo) {
        LinkedList newHead = (headOne == null) ? headTwo : headOne;
        LinkedList newTail = (tailTwo == null) ? tailOne : tailTwo;

        if (tailOne != null)
            tailOne.next = headTwo;
        return new LinkedList[] {newHead, newTail};
    }

    public static LinkedList[] growLinkedList(LinkedList head,
                                              LinkedList  tail, LinkedList node) {
        LinkedList newHead = head;
        LinkedList newTail = node;
        if (newHead == null)
            newHead = node;
        if (tail != null)
            tail.next = node;
        return new LinkedList[] {newHead, newTail};
    }

    static class LinkedList {
        public int value;
        public LinkedList next;

        public LinkedList(int value) {
            this.value = value;
            next = null;
        }
    }
}
