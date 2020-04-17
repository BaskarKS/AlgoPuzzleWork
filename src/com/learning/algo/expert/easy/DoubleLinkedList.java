package easy;


import java.util.ArrayList;
import java.util.List;

public class DoubleLinkedList {
    public static void main(String[] args) {
        DoubleLinkedList list = new DoubleLinkedList();
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(1));
        nodes.add(new Node(2));
        nodes.add(new Node(3));
        nodes.add(new Node(4));

        list.setHead(nodes.get(0));
        list.insertAfter(nodes.get(0), nodes.get(1));
        list.insertBefore(nodes.get(1), nodes.get(2));
        list.insertAfter(nodes.get(2), nodes.get(3));
    }
    static class Node {
        int value;
        Node prev;
        Node next;
        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node=" + value;
        }
    }

    //Class Members
    private Node head = null;
    private Node tail = null;

    public void setHead(Node node) {
        if (node == null) return;
        if (head == null) {
            head = node;
            tail = node;
            return;
        }
        insertBefore(head, node);
    }

    public void setTail(Node node) {
        if (node == null) return;
        if (tail == null) {
            setHead(node);
            return;
        }
        insertAfter(tail, node);
    }

    public void insertBefore(Node node, Node nodeToInsert) {
        if (nodeToInsert == null) return;
        if (nodeToInsert == head && nodeToInsert == tail)
            return;
        nodeToInsert.prev = node.prev;
        nodeToInsert.next = node;
        if (node.prev == null)
            head = nodeToInsert;
        else
            node.prev.next = nodeToInsert;
        node.prev = nodeToInsert;
    }

    public void insertAfter(Node node, Node nodeToInsert) {
        if (nodeToInsert == null) return;
        if (nodeToInsert == head && nodeToInsert == tail)
            return;
        nodeToInsert.prev = node;
        nodeToInsert.next = node.next;
        if (node.next == null)
            tail = nodeToInsert;
        else
            node.next.prev = nodeToInsert;
        node.next = nodeToInsert;
    }

    public void insertAtPosition(int position, Node nodeToInsert) {
        if (nodeToInsert == null) return;
        if (position == 1) {
            setHead(nodeToInsert);
            return;
        }
        Node node = head;
        int currentPosition = 1;
        while(node != null && currentPosition != position) {
            node = node.next;
            currentPosition++;
        }
        if (node != null)
            insertBefore(node, nodeToInsert);
        else
            setTail(nodeToInsert);
    }

    public void removeNodesWithValue(int value) {
        Node node = head;
        while (node != null) {
            Node nodeToRemove = node;
            node = node.next;
            if (nodeToRemove.value == value)
                remove(nodeToRemove);
        }
    }

    public void remove(Node node) {
        if (node == null)
            return;
        if (node == head)
            head = head.next;
        if (node == tail)
            tail = tail.prev;
        removeNodeBindings(node);
    }

    private void removeNodeBindings(Node node) {
        if (node.prev != null)
            node.prev.next = node.next;
        if (node.next != null)
            node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
    }

    public boolean containsNodeWithValue(int value) {
        Node node = this.head;
        if (node == null)
            return false;
        while(node != null && node.value != value)
            node = node.next;
        return node != null ? node.value == value : false;
    }
}
