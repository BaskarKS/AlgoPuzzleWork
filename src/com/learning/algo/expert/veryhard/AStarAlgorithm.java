package veryhard;

public class AStarAlgorithm {
    /*
    A*Algorithm:
        you are given a two-dimensional array containing 0's and 1's where each
        0 represents a free space and each 1 represents an obstacle (a space that cannot
        be passed through). You can think of this array as a grid-shaped graph. You are
        also given four integers startRow, startCol, endRow, endCol. representing the positions
        of a start node and an end node in a graph.

        write a function that finds the shortest path between the start node and the end node
        using the A* search algorithm and returns it.

        the shortest path should be returned as an array of node positions, where each node
        position is an array of two elements: the [row, col] of the respective node in the graph.
        the output array should contain the start node's position, the end node's position , and
        all of positions of the remaining nodes in the shortest path and these node positions
        should be ordered from start node to end node.

        if there is no path from the start node to the end node, function should return an empty
        array

        Note:
        1. from each node in graph you can travel only in four directions up, down, left, right, you
        cant travel diagonally.
        2. the distance between all neighbouring nodes in the graph is  the same. you can treat
        it as a distance of 1
        3. the start node and the end node are guaranteed to be located in empty space( cells
        containing 0).
        4. the start node and end node will never be out of bounds and will never overlap.
        5. there will be at most one shortest path from the start node to the end node.

        Eg:
        startRow = 0
        start col = 1
        endRow = 4
        endCol = 3
        graph = [
            [0, 0, 0, 0, 0],
            [0, 1, 1, 1, 0],
            [0, 0, 0, 0, 0],
            [1, 0, 1, 1, 1],
            [0, 0, 0, 0, 0]
        ]

        output =
        [[0, 1], [0, 0], [1, 0], [2, 0], [2, 1], [3, 1], [4, 1], [4, 2], [4, 3]]
        //shortest path can be clearly seen here
        [   [x, x, 0, 0, 0],
            [x, 1, 1, 1, 0],
            [x, x, 0, 0, 0],
            [1, x, 1, 1, 1],
            [0, x, x, x, 0] ]

    */

    /*
    A*Algorithm  is called as  'informed search algorithm' (Heuristic - determines which  node to visit next)
    every time when we decide which node to visit next,, we consider two main things
    1. how long / how much distance as it take to get to the current position.
    2. how long do we think / how long is our guess that it take to get to the end position.

    goal of A* Algorithm is to visit as few nodes as possible and to find the shortest path
    to the end node without visiting most of the nodes in the graph

    Heuristic is a guess, the better guess we make , the better the algorithm will perform.

    in A*Algorithm we decide which nodes we are going to looking at visiting next which is base on
    3 factors (HGF)
        1. Heuristic value (guess from how far our node (H Score is) to the end node.
        2. G is current shortest distance from the start node to the given node
        3. F is addition of G and H.
   When we look at what node we wanna visit next, we will look for the node that has current
     smallest  F score. (reason : its based on our guess and based on the distance that we found
     to get to this node so far, it is the shortest distance and we are hoping that its gonna be in the
     shortest path.

    we maintain (G-Score, H-Score and F-Score for each of the positions of the graph)
    we start with start node and it always has a G-Score of 0, we look at all the four directions
    in our case of start node, we only need to consider the neighbours of left and right, and continue
    to score those nodes and look out for a node which has smallest score, this process will continue
    until we find the ending node.

    computing G-Score for starting node, G-Score is the distance from start node to the current node
    this value is 0 for start node.

      H-Score is heuristic score, in our case we can only move in four directions
     (Moving only four directions, one of the Heuristic distance formula is 'Manhattan Distance' which
     is difference between the start and end nodes. In our case its 4(end row)-0(start row) +
     3(end col) - 1(start col) => 6 steps, looks like L (which is at (1, 1), (2, 1), (3, 1), (4, 1), (4, 2),
     (4, 3)(end node)).

     If we also consider diagonal movement, we can use Euclidean distance formula (take the diagonal
     distance between two points)

     Hence the total F-Score for the start node is 0(G-Score) + 6(H-Score) => 6

     next we have to look for next node which has lowest F-Score, but theres no node to start with, because
     only start node has the F Score to start with. Hence to start our algorithm, we always start with the
     start node.

     pull out the start node from priority queue and mark it as visited, consider its neighbours. In our case
     of start node we have only 2 neighbours(can move only four directions, cant move up / down).
     compute the neighbours F score.

     Right neighbour G-Score will be 1, because the Start node G-Score is 0, we can only move in one step
     its an addition of 1 value. Hence G-Score of right neighbour(0, 2) is 1 and its H-Score
      is 5 (end row(4) - neighbour row(0)) + (end col(3) - neighbour col(2)) and its F Score is (1 + 5) => 6

      Left neighbour G-Score will be 1, because the Start node G-Score is 0, we can only move in one step
      its an addition of 1 value. Hence G-Score of left neighbour(0, 0)  is 1 and its H-Score
      is 7 (end row(4) - neighbour row(0)) + (end col(3) - neighbour col(0)) and its F Score is (1 + 7) => 8

      Both the neighbours are pushed to priority queue, (0, 0) and (0, 2)
      now from both the neighbours of start node, we have to continue to work on neighbour which
      has lowest F score, which is right neighbour(0, 2)

      make the right Neighbour as visited and continue exploring its neighbours and provide a F-score to it
      and continue the process of investigating the lowest F-Score neighbour.

      In finding neighbours of (0, 2) (right neighbour of start node)

      exploring the left neighbour of (0, 2) which is (0, 1), G-Score of (0, 2) is 1 and we add 1 to reach
      left neighbour(0, 1) which is 2(computed G-Score). But the G-Score of (0, 1) which it has is 0, since
      the computed G-Score is greater than the existing G-Score of (0, 1). we avoid this neighbour for further
      investigation. we start exploring the right neighbour of (0, 2) which is (0, 3)

      Since (0, 3) which is the right-node of visiting node (0, 2) and doesn't have computed G-H-F score,
      we compute and set it, G-Score of (0, 2) is 1 and we add 1 to it to reach the (0, 3). G-Score of (0, 3) is 2,
      and H-Score is 4 (end row(4) - neighbour row(0)) + (end col(3) - neighbour col(3)) and its
      H-Score is (4 + 0) => 4
      Hence the total F-Score is (G-Score + H-Score) => (2 + 4) => 6

      now we push this neighbour into the priority queue(already has a node (0, 0)(8)) (0, 3)(6)

      to visit the next neighbour, we take a node from min priority queue, we get (0, 3)(6) because it has
      lowest F-Score node in priority queue to explore further.

      we mark (0, 3) node as visited, explore its left and right neighbours (cant move up(border) / down(filled with 1)
      exploring left neighbour (0, 2)(1G-5H),
      current visiting node which is (0, 3) has a G-Score of 2, if we add 1 to it to visit (0, 2). Hence the computed
      G-Score of left neighbour(0, 2) is 3 which is larger than the left neighbour(0, 2)(1G-5H). Hence we exclude
      looking at left neighbour and look for right neighbour which is (0, 4)

      right neighbour (0, 4) doesn't have computed G-H-F score. we compute it. current visiting node (0, 3) has
      a G-Score of 2, adding 1 to it will produce the G-Score of right neighbour (0, 4) which is 3
      Computing H-Score of right neighbour (0, 4) is 5 (end row(4) - neighbour row(0)) +
      (end col(3) - neighbour col(4)) and its 5. so the total F score is (G-Score + F-Score) => 3 + 5 => 8
      this right neighbour (0, 4)(3G-5H-8F) is put into the priority queue.

      currently, priority queue has 2 nodes ((0, 0)(1G-7H-8F) and (0, 4)(3G-5H-8F))
       we need to get next item from priority queue which has lower F score. In current situation it has
       equal F-Score nodes, we can start picking any node from priority queue,
        we can pick (0, 0) since it has lower G-Score(1G) than the other (0, 4) (3G)

      mark (0, 0) node as visited and start exploring its neighbours
      for (0, 0) we can investigate only right and down neighbours.
      we avoid investigating its right neighbour as computed G-Score by visiting (0, 1)right neighbour
       from (0, 0) is 2 which is larger than (0, 1)(0G-6H-6F). computed G Score is 2 > 0(existing score of
       right neighbour), hence we avoid further exploration of right neighbour

       Explore down neighbour which is (1, 0), visiting (1, 0) from current visiting node (0, 0)(1G-7H-8F)
       we add 1 to the current vising node G-Score which is 2 for down neighbour (1, 0)
       and its H Score is 6 (end row(4) - neighbour row(1)) + (end col(3) - neighbour col(0)) and its 6
        Hence its F Score is 6 + 2 => 8
        final scores of down neighbour (1, 0)(2G-6H-8F) is pushed into the priority queue

        continue till you hit the end node,
        in order to track the path we need to store/update the current visiting node (position) to the
        exploring neighbours

        index       [0]                       [1]                 [2]                 [3]                [4]
                    (1G-7H-8F)     (0G-6H-6F)     (1G-5H-6F)   (2G-4H-6F)   (3G-5H-8F)
                       (0, 1)             (null, null)          (0, 1)            (0, 2)             (0, 3)

                        [1]
                    (2G-6H-8F)               1                   1                    1            (4G-4H-8F)
                       (0, 0)                                                                                    (0, 4)

                        [2]
                    (3G-5H-8F)     (4G-4H-8F)     (5G-3H-8F)   (6G-2H-8F)   (5G-3H-8F)
                       (1, 0)                 (2, 0)               (2, 1)            (2, 2)             (1, 4)

                        [3]
                                            (5G-3H-8F)
                        1                       (2, 1)                 1                    1                    1

                        [4]
                    (7G-3H-10F)    (6G-2H-8F)     (7G-1H-8F)   (8G-0H-8F)
                       (4, 1)                 (3, 1)                (4, 1)            (4, 2)


























    */
    public int[][] aStarAlgorithm(int startRow, int startCol, int endRow, int endCol, int[][] graph) {
        // Write your code here.
        return new int[][] {};
    }
}
