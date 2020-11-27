package hard;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class DijkstraAlgorithm {
    /*
    You are given an integer start and a list edges of pairs of integers

    This list is what 's called a adjacency list and it represents a graph. The number
    of vertices in the graph is equal to the length of the edges. where each index i in
    edges contains vertex i's outbound edges, in no particular order. Each individual edge
    is represented by an pair of two numbers, [destination, distance] where the destination
    is a positive integer denoting the destination vertex and the distance is a positive
    integer representing the length of the edge (distance from vertex i to vertex destination)

    Write a function that computes the length of the shortest path between the start and all of
    the other vertices in the graph using dijkstra algorithm and returns them in array. each index
    i in the output array should represent the length of the shortest path between start and
    vertex i. if no path is found from start to vertex i then  output [i] should be -1

    Note the graph represented by edges wont contain any self loops (vertices that have
    an outbound edge to themselves) and will only have positive weighed edges (i.e no
    negative distances).

    Eg: start = 0
    edges = [
                        [[1, 7]],
                        [[2, 6], [3, 20], [4, 3]],
                        [[3, 14]],
                        [[4, 2]],
                        [],
                        [],
                    ]
        Output : [0, 7, 13, 27, 10, -1]


    */
    public static int[] dijkstrasAlgorithm(int start, int[][][] edges) {
        // Write your code here.
        Map<Integer, Node> adjacencyList = buildGraph(edges);
        return getMinDistances(adjacencyList.get(start), adjacencyList);
    }

    public static int[] getMinDistances(Node startNode, Map<Integer, Node> graph) {
        int[] minDistances = new int[graph.size()];
        Arrays.fill(minDistances, Integer.MAX_VALUE);
        minDistances[startNode.value] = 0;

        Queue<NodeEntry> queue = new PriorityQueue<>(Comparator.comparingInt(ne -> ne.distance));
        queue.add(new NodeEntry(startNode, 0)); // adding starting node to kick start
        Set<Node> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            NodeEntry entry = queue.poll();
            Node currentNode = entry.node;
            visited.add(currentNode);

            for (Edge edge : currentNode.edges) {
                Node toNode = edge.toNode;
                if (visited.contains(toNode))
                    continue;
                int toNodeValue = toNode.value;
                int currentNodeValue = currentNode.value;
                int newDistance = minDistances[currentNodeValue] + edge.distance;
                if (newDistance < minDistances[toNodeValue]) {
                    minDistances[toNodeValue] = newDistance;
                    queue.add(new NodeEntry(toNode, newDistance));
                }
            }

        }
        markUnreachableNodes(minDistances);
        return minDistances;
    }


    public static void markUnreachableNodes(int[] minDistances) {
        for (int idx = 0; idx < minDistances.length; idx++) {
            if (minDistances[idx] == Integer.MAX_VALUE)
                minDistances[idx] = -1;
        }
    }
    public static Map<Integer, Node> buildGraph(int[][][] edges) {
        Map<Integer, Node> adjacencyList = new HashMap<>();
        for (int idx = 0; idx < edges.length; idx++) {
            Node vertex = new Node(idx);
            adjacencyList.put(idx, vertex);
        }
        for (int idx = 0; idx < edges.length; idx++) {
            int[][] edgeList = edges[idx];
            Node vertex = adjacencyList.get(idx);
            for (int[] edge : edgeList) {
                Node toNode = adjacencyList.get(edge[0]);
                int weight = edge[1];
                vertex.addEdge(toNode, weight);
            }
            adjacencyList.replace(idx, vertex);
        }
        return adjacencyList;
    }

    static class NodeEntry {
        private Node node;
        private int distance;
        public NodeEntry(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    static class Node {
        private int value;
        private List<Edge> edges;
        public Node(int value) {
            this.value = value;
            this.edges = new ArrayList<>();
        }
        public void addEdge(Node to, int weight) {
            edges.add(new Edge(this, to, weight));
        }
    }

    static class Edge {
        public Node fromNode;
        public Node toNode;
        public int distance;
        public Edge(Node fromNode, Node toNode, int distance) {
            this.fromNode = fromNode;
            this.toNode = toNode;
            this.distance = distance;
        }
    }
    public static void main(String[] args) {
        int[][][] edges = new int[][][]{
                                                            {{1, 7}},
                                                            {{2, 6}, {3, 20}, {4, 3}},
                                                            {{3, 14}},
                                                            {{4, 2}},
                                                            {},
                                                            {}};
        int[] result = dijkstrasAlgorithm(0, edges);
        System.out.println(Arrays.toString(result));
    }
}
