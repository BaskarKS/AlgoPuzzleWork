package veryhard;

import java.util.Arrays;

public class NumberOfBinaryTreeTopologies {

    /*

    Problem Statement:
        function takes in a non-negative integer n, returns the number of possible
        Binary Tree Topologies that can be created with exactly n nodes.
        Binary Tree Topology is defined as any Binary Tree Configuration irrespective
        of node values, there exist only two binary tree topologies when n = 2, a root
        node with a left node, and a root node with right node.

        note that n = 0, theres one topology can be created the none / null.

        Eg: Ip: n = 3
              op: 5
              
    Using Recursion:
    for any given number n, iterate from 0 to n-1 (1 node as root).
    for left-subtree of size 0, 1, 2 .. n-1. how many topologies do i have for
    the left-subtree, and how many nodes can i have on right subtree(how many
    topologies will have have with that nodes on right). keep multiplying those
    together, eventually you get to the total no of topologies

    Eg: if n = 6,
                               MainRoot(1)
                                /            \
                           left=3        right=2
                             (5)               (2)           => 5 * 2 => 10 (RLLMainRootRL, RLLMainRootRr,
                             RLrMainRootRL, RLrMainRootRr, RLRMainRootRL, RLRMainRootRr,
                             RrLMainRootRL, RrLMainRootRr, RrrMainRootRL, RrrMainRootRr)

                           no of topologies i can have with 3 nodes is 5 (RLL, RLr, RLR, RrL, Rrr)
                           no of topologies i can have with 2 nodes is 2 (RL, Rr)

          topology when n = 3

          RLL               RLr           RLR            RrL               Rrr
            X                  X               X                X                 X
          /                   /               /      \                 \                 \
        X                 X               X        X                X                X
       /                       \                                       /                     \
    X                          X                                   X                        X

        topology when n = 2

         RL            Rr
        X               X
       /                     \
     X                        X

      RLLMainRootRL:

                            X(MainRoot)
                          /    \
                        X      X
                      /        /
     RLL         X       X         RL
                   /
                X

      RLLMainRootRr:

                            X(MainRoot)
                          /    \
                        X      X
                      /            \
     RLL         X              X    Rr
                   /
                X

RLrMainRootRL:

                            X(MainRoot)
                          /    \
                        X      X
                      /        /
     RLr         X       X         RL
                       \
                        X

RLrMainRootRr:

                            X(MainRoot)
                          /    \
                        X      X
                      /            \
     RLr         X              X    Rr
                       \
                        X

For just Left is 3 and Right is 2 and a root, we can have (5 * 2) 10 different topologies combination
same ways if we have left is 2 and right is 3,  we can have (2 * 5) 10 different topologies combination

same way we have to calculate for left is 0 and right is 5 and root 1
+
same way we have to calculate for left is 5 and right is 0 and root 1
+
same way we have to calculate for left is 1 and right is 4 and root 1
+
same way we have to calculate for left is 4 and right is 1 and root 1
+
same way we have to calculate for left is 2 and right is 3 and root 1 (topology left 2 (2) * topology right 3(5)) => 10
+
same way we have to calculate for left is 3 and right is 2 and root 1 (topology left 3 (5) * topology right 2(2)) => 10

    * */


    // # Recursive without caching - Worst Solution
    // # Time : (n * (2n)!) / ((n!) (n+1)!)
    // # Space: O(n)
    public static int numberOfBinaryTreeTopologies(int n) {
        // Write your code here.
        if (n == 0)
            return 1;
        int numberOfTopologies = 0;
        for (int leftTreeSize = 0; leftTreeSize < n; leftTreeSize++) {
            int rightTreeSize = n - 1 - leftTreeSize;
            int leftTopologiesCount = numberOfBinaryTreeTopologies(leftTreeSize);
            int rightTopologiesCount = numberOfBinaryTreeTopologies(rightTreeSize);
            numberOfTopologies += leftTopologiesCount * rightTopologiesCount;
        }
        return numberOfTopologies;
    }

/*
# Recursive with caching - Best Solution
# Time : O(n ^ 2)
# Space: O(n)
*/
    public static int numberOfBinaryTreeTopologiesWithCache(int n) {
        // Write your code here.
        int[] cache = new int[n + 1]; // cache size should be equal to no of tree nodes
        Arrays.fill(cache, Integer.MIN_VALUE); // initialize with min value to find the filled computed result
        cache[0] = 1; // no of tree count for zero nodes is 1
        return numberOfBinaryTreeTopologiesCacheHelper(n, cache);
    }

    private static int numberOfBinaryTreeTopologiesCacheHelper(int n, int[] cache) {
        if (cache[n] != Integer.MIN_VALUE) // if the topology count is cached, return cached value
            return cache[n];
        int totalNumberOfTopologies = 0;
        for (int leftTreeSize = 0; leftTreeSize < n; leftTreeSize++) {
            int rightTreeSize = n - 1 - leftTreeSize;
            int leftTopologiesCount = numberOfBinaryTreeTopologies(leftTreeSize);
            int rightTopologiesCount = numberOfBinaryTreeTopologies(rightTreeSize);
            totalNumberOfTopologies += leftTopologiesCount * rightTopologiesCount;
        }
        cache[n] = totalNumberOfTopologies; // cache filled here
        return totalNumberOfTopologies;
    }
//# Iterative with caching - Best Solution
//# Time : O(n ^ 2)
//# Space: O(n)
    public static int numberOfBinaryTreeTopologiesIterative(int n) {
        int[] cache = new int[n + 1];
        cache[0] = 1;
        for (int m = 1; m <= n; m++) { // building topology count from 0 to the no (input value)
            int totalNumberOfTopologies = 0;
            for (int leftTreeSize = 0; leftTreeSize < m; leftTreeSize++) {
                int rightTreeSize = m - 1 - leftTreeSize;
                int leftTopologiesCount = cache[leftTreeSize]; // using pre calculated value
                int rightTopologiesCount = cache[rightTreeSize];
                totalNumberOfTopologies += leftTopologiesCount * rightTopologiesCount;
            }
            cache[m] = totalNumberOfTopologies; // final calculated total no of topologies for every node count
        }
        return cache[n];
    }
}
