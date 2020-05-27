package hard;

public class ShiftLinkedList {
    /*
    function shifts the linked list for K positions in-place and returns new head.
    shifting means moving its nodes forward / backward and wrapping around
    the list when appropriate.
    Shifting a list by one position is tail becomes new head of the list.
    whether nodes forward / backward is determined by 'k' is positive / negative
    Eg:
    Ip:
    head = 0 -> 1 -> 2 -> 3 -> 4 -> 5
    k = 2
    Op:
    head = 4 -> 5 -> 0 -> 1 -> 2 -> 3
    * */
    public static void main(String[] args) {
        LinkedList head = new LinkedList(0);
        LinkedList one = new LinkedList(1);
        head.next = one;
        LinkedList two = new LinkedList(2);
        LinkedList three = new LinkedList(3);
        LinkedList four = new LinkedList(4);
        LinkedList five = new LinkedList(5);
        one.next = two;
        two.next = three;
        three.next = four;
        four.next = five;
        five.next = null;
        shiftLinkedList(head, 18);
    }
//    Time Complexity : O(n),
//    Space Complexity : O(1)
    public static LinkedList shiftLinkedList(LinkedList head, int k) {
        // Write your code here.
        LinkedList current = head, end = head;
        LinkedList newHead = null;
        int size = 1;
        while (end.next != null) {
            end = end.next;
            size++;
        }
        int actualCount = Math.abs(k) % size;
        int traverseCount = 0;
        if (k < 0)
            traverseCount = actualCount;
        else
            traverseCount = size - actualCount;

        if (actualCount == 0) {
            return head;
        }
        int start = 1;
        while (start < traverseCount) {
            current = current.next;
            start++;
        }
        newHead = current.next;
        end.next = head;
        current.next = null;
        return newHead;
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
