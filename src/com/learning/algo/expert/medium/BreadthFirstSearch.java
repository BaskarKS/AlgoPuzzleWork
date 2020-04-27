package medium;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearch {
    //-------------------BREATH FIRST SEARCH----------------------------------------//
    static class Node {
        String name;
        List<Node> children = new ArrayList<Node>();

        public Node(String name) {
            this.name = name;
        }

        public List<String> breadthFirstSearch(List<String> array) {
            // Write your code here.
            Queue<Node> processNodes = new ArrayDeque();
            processNodes.add(this);
            while (!processNodes.isEmpty()) {
                Node node = processNodes.poll();
                array.add(node.name);
                for (Node children : node.children)
                    processNodes.add(children);
            }
            return array;
        }

        public Node addChild(String name) {
            Node child = new Node(name);
            children.add(child);
            return this;
        }
    }

    public static void main(String[] args) {
        Node root = new Node("A");
        root.addChild("B").addChild("C").addChild("D").addChild("E");
        root.children.get(1).addChild("F");
        List<String> bfs = root.breadthFirstSearch(new ArrayList<String>());
        System.out.println(bfs.toString());

    }
}
