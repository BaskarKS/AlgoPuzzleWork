package hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class TopologicalSort {

    public static List<Integer> topologicalSort(List<Integer> jobs, List<Integer[]> deps) {
        Map<Integer, List<Integer>> adjacencyList = constructGraph(jobs, deps);

        if (hasCycle(jobs, adjacencyList))
            return new ArrayList<>();

        Stack<Integer> order = new Stack<>();
        Set<Integer> visited = new HashSet<>();
        for (Integer node: jobs) {
            depthFirstTraverse(node, visited, adjacencyList, order);
        }

        return composeResult(order);
    }

    private static Map<Integer, List<Integer>> constructGraph(List<Integer> nodes, List<Integer[]> edges) {
        Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

        for (Integer node : nodes)
            adjacencyList.put(node, new ArrayList<>());

        for (Integer[] eachEdge : edges)
            adjacencyList.get(eachEdge[0]).add(eachEdge[1]);

        return adjacencyList;
    }

    private static List<Integer> composeResult(Stack<Integer> order) {
        List<Integer> result = new ArrayList<>();
        while(!order.empty()) {
            result.add(order.pop());
        }
        return result;
    }

    private static void depthFirstTraverse(Integer node, Set<Integer> visited,
                                                                        Map<Integer, List<Integer>> adjacencyList, Stack<Integer> order) {
        if (visited.contains(node))
            return;
        visited.add(node);
        for (Integer edge : adjacencyList.get(node))
            if (!visited.contains(edge))
                depthFirstTraverse(edge, visited, adjacencyList, order);
        order.push(node);
    }

    public static boolean hasCycle(List<Integer> nodes, Map<Integer, List<Integer>> adjacencyList) {
        Set<Integer> all = new HashSet<>(nodes);
        Set<Integer> visiting = new HashSet<>();
        Set<Integer> visited = new HashSet<>();

        while (!all.isEmpty()) {
            Integer current = all.iterator().next();
            if (hasCycle(current, all, visiting, visited, adjacencyList))
                return true;
        }
        return false;
    }
    private static boolean hasCycle(Integer node, Set<Integer> all,
                             Set<Integer> visiting, Set<Integer> visited, Map<Integer, List<Integer>> adjacencyList) {
        all.remove(node);
        visiting.add(node);

        for (var neighbour : adjacencyList.get(node)) {
            if (visited.contains(neighbour))
                continue;

            if (visiting.contains(neighbour))
                return true;

            if(hasCycle(neighbour, all, visiting, visited, adjacencyList))
                return true;
        }
        visiting.remove(node);
        visited.add(node);
        return false;
    }

    public static void main(String[] args) {
        List<Integer[]> edges = new ArrayList<>();
        edges.add(new Integer[]{3, 1});
        edges.add(new Integer[]{8, 1});
        edges.add(new Integer[]{8, 7});
        edges.add(new Integer[]{5, 7});
        edges.add(new Integer[]{5, 2});
        edges.add(new Integer[]{1, 4});
        edges.add(new Integer[]{6, 7});
        edges.add(new Integer[]{1, 2});
        edges.add(new Integer[]{7, 6});
        /*
                                    1 ------> 2
                                    |           ^
                                    |         /  |
                                    |       /    |
                                   V    /       |
                                    3 <------4
        * */
        List<Integer> sortOrder = topologicalSort(List.of(1, 2, 3, 4, 5, 6, 7, 8), edges);
        System.out.println(sortOrder);
    }

}
