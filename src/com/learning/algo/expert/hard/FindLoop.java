package hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindLoop {
    public static void main(String[] args) {
        int[] nodeValues = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int loopNode = 4;
        LinkedList loopList = getLoopList(nodeValues, loopNode);
        //LinkedList startOfLoop = findLoop(loopList);
        LinkedList startOfLoop = findLoopExtraSpace(loopList);
        System.out.println(startOfLoop.value);
    }

    /*
    function takes head of a single linked list, that contains a loop(tail points to
    some node in the linked list), this function should return a node from which
    the loop originates in constant space.

    sample:
    head = 0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 ---------
                                                  |<- 9 <- 8 <- 7 <-
    function should return Node 4
    * */

    // O(n) Time and O(1) space
    public static LinkedList findLoop(LinkedList head) {
        if (head == null) return null;
        LinkedList singleStep = head.next, doubleStep = (head.next != null) ? head.next.next : null;
        if (singleStep == null || doubleStep == null)
            return null;
        while (singleStep.value != doubleStep.value) {
            singleStep = singleStep.next;
            doubleStep = (doubleStep.next != null) ? doubleStep.next.next : null;
            if (singleStep == null || doubleStep == null)
                return null;
        }
        singleStep = head;
        while (singleStep.value != doubleStep.value) {
            singleStep = singleStep.next;
            doubleStep = doubleStep.next;
        }
        return singleStep;
    }

    private static class LinkedList {
        int value;
        LinkedList next;
        public LinkedList(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "value=" + value;
        }
    }

    private static LinkedList getNewNode(int value) {
        return new FindLoop.LinkedList(value);
    }

    public static LinkedList getLoopList(int[] nodeValues, int loopValue) {
        if (nodeValues == null || nodeValues.length == 0)
            return null;
        LinkedList loopNode = getNewNode(loopValue);
        LinkedList head = getNewNode(nodeValues[0]);
        LinkedList traverse = head;
        for (int idx = 1; idx < nodeValues.length; idx++) {
            int value = nodeValues[idx];
            if (value == loopValue) {
                traverse.next = loopNode;
            } else {
                traverse.next = getNewNode(value);
            }
            traverse = traverse.next;
        }
        traverse.next = loopNode;
        return head;
    }

    // O(n) Time and O(n) space
    public static LinkedList findLoopExtraSpace(LinkedList head) {
        Set<Integer> nodes = new HashSet<>();
        while(!nodes.contains(head.value)) {
            nodes.add(head.value);
            head = head.next;
        }
        return head;
    }
}
