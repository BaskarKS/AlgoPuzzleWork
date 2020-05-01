package medium;

import java.util.Arrays;
import java.util.List;

public class RemoveKthNodeFromEnd {
    /*
    * function takes a head of linked list and a integer k, removes kth node from
    * the end of the list
    *
    * input:
    * 0 ->1 ->2 ->3 ->4 ->5 ->6 ->7 ->8 ->9
    * k = 4
    * output:
    * 0 ->1 ->2 ->3 ->4 ->5 ->7 ->8 ->9
    * */
    private LinkedList head = null;

    public static void removeKthNodeFromEnd(LinkedList head, int k) {
        // Write your code here.
        if (k == 0 || head == null)
            return;
        LinkedList traverse = head, nodeToRemove = head;
        int count = 1;
        while (count <= k) {
            traverse = (traverse != null) ? traverse.next : null;
            count++;
        }
        // if traverse is null here, have to remove the head here
        if (traverse == null) {
            head.value = head.next.value;
            head.next = head.next.next;
            return;
        }
        while (traverse.next != null) {
            nodeToRemove = nodeToRemove.next;
            traverse = traverse.next;
        }
        nodeToRemove.next = nodeToRemove.next.next;
/*
        if (nodeToRemove.next.next == null)
            nodeToRemove.next = null; // to remove tail node
        else {
            LinkedList toRemove = nodeToRemove.next; // remove in-between node
            nodeToRemove.next = toRemove.next;
            toRemove = null;
        }
*/
    }

    public static void main(String[] args) {
        List<Integer> values = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        RemoveKthNodeFromEnd list = new RemoveKthNodeFromEnd();
        list.createLinkedList(values);
        list.printList();
        removeKthNodeFromEnd(list.head, 10);
        list.printList();
    }
    public void createLinkedList(List<Integer> values) {
        if (head == null) {
            head = new LinkedList(values.get(0));
        }
        LinkedList current = head;
        for (int index = 1; index < values.size(); index++) {
            current.next = new LinkedList(values.get(index));
            current = current.next;
        }
    }

    public void printList() {
        System.out.println();
        LinkedList current = head;
        while(current != null) {
            System.out.print(current.value + " -> ");
            current = current.next;
        }
        System.out.print("null");
        System.out.println();
    }

    static class LinkedList {
        int value;
        LinkedList next = null;

        public LinkedList(int value) {
            this.value = value;
        }
    }
}
