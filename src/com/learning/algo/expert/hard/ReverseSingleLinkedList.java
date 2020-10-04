package hard;

public class ReverseSingleLinkedList {
    /*
        Reverse a Singly Linked List and return its new head.
        each node will hold a integer value and the pointer to next Node.
        if a node next value is null then it should ne tail
        passed parameter will always have 1 node, never passed null.
    */
    public static void main(String[] args) {
        var head = new LinkedList(1);
        addNode(head, new LinkedList(2));
        addNode(head, new LinkedList(3));
        addNode(head, new LinkedList(4));
        addNode(head, new LinkedList(5));
        printList(head);
        var newHead = reverseLinkedList(head);
        printList(newHead);
        var reversedHead =
                                                        reverseLinkedListSolution(newHead);
        printList(reversedHead);
    }
    public static void addNode(LinkedList head, LinkedList node) {
        if (head == null)
            return;
        while (head.next != null)
            head = head.next;
        head.next = node;
    }

    public static void printList(LinkedList node) {
        while (node != null) {
            System.out.print(node.value + " -> ");
            node = node.next;
        }
        System.out.println("null");
    }

//    Time Complexity : O(n) n is length of the List,
//    Space Complexity : O(1)

    public static LinkedList reverseLinkedList(LinkedList head) {
        // Write your code here.
        if (head.next == null)
            return head;

        // Eg:  1 -> 2 -> 3 -> null;

        // 1 is made as currentNode
        // 2 is captured as nextNode from currentNode next, after capturing
        // make current node as tail by making its next to null

        var currNode = head;
        var nextNode = head.next;
        currNode.next = null;
        // iterate until nextNode becomes null.
        // before changing nextNode next pointer to currentNode, hold it in temp
        // make nextNode next pointer to currentNode
        // make currentNode as nextNode
        // make nextNode as captured temp.
        while(nextNode != null) {
            var temp = nextNode.next;
            nextNode.next = currNode;
            currNode = nextNode;
            nextNode = temp;
        }
        return currNode;
    }

    public static LinkedList reverseLinkedListSolution(LinkedList head) {
        if (head.next == null)
            return  head;
        LinkedList prev = null;
        var current = head;
        while (current != null) {
            var next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;

    }
    static class LinkedList {
        int value;
        LinkedList next = null;

        public LinkedList(int value) {
            this.value = value;
        }
    }
}
