package veryhard;

import java.util.ArrayList;
import java.util.List;

public class RightSmallerThan {
    /*
    Write a function that takes in an array of Integers and return an array of same length
    each element in the output array corresponds to the number of integers in the input
    array that are to the right of the relevant index and that are strictly smaller than the
    integer in that index.

    value at output[i] represent the number of integers that are to the right of i and that
    are strictly smaller than input[i].

    Ip : [8, 5, 11, -1, 3, 4, 2]
    Op :[5, 4,   4,  0, 1, 1, 0] // there are 5 integers smaller than 8 to the right of it

    This algorithm involves
    1. Constructing a BST
    2. Inserting nodes into the BST with values in input array from right to left
    3. At the point of insertion in BST, we have all of the information
    that we need to construct the final array.
    4. we keep track of the left tree sizes of all the nodes in our traversal path,
    Increment them when we need to, we use them to calculate the no of
    nodes at the point of insertion that are smaller than our current node thats
    been inserted.

    if we keep track of the left tree size value at each node, and in navigating
    tree from root to below level, if we also pass the current node computed result
    to the tree below.we can compute the final result in constant time in each
    insert operation.

    We just need to construct the BST to get the answer, we get TimeComplexity of
    "n Log n" at-least when the tree is balanced. if BST is skewed then we get time
    complexity as N^2. But on average we can get time complexity to "n log n"

    Ip : [8, 5, 11, -1, 3, 4, 2]
    Op :[5, 4,   4,  0, 1, 1, 0]

                                                             2 (1)
                                                        /         \
                                                   -1(0)      4 (1)
                                                               /         \
                                                            3(0)        11(2)
                                                                         /        \
                                                                      5(0)
                                                                    /      \
                                                                            8(0)

    2 different implementation:
    1. After constructing BST, re-traverse and construct the final array.
    2. populate output result at the time of constructing BST.

    TimeComplexity : O(n log n) on average, O(n^2) on worst
    Space Complexity : O(n)
    * */

    public static void main(String[] args) {
        List<Integer> result = rightSmallerThanBest(List.of(8, 5, 11, -1, 3, 4, 2));
        System.out.println(result);
    }
    // First Implementation
    //Time : O(n^2), Space : O(n)
    public static List<Integer> rightSmallerThan(List<Integer> array) {
        List<Integer> result = new ArrayList<>();
        if (array == null || array.size() == 0)
            return result;
        //array.stream().forEach(i -> result.add(i));

        for (int outIdx = 0; outIdx < array.size(); outIdx++) {
            int greaterCount = 0;
            for (int inIdx = outIdx + 1; inIdx < array.size(); inIdx++) {
                if (array.get(inIdx) < array.get(outIdx))
                    greaterCount += 1;
            }
            result.add(greaterCount);
        }
        return result;
    }

    // Second Implementation
    //Time : O(nlog(n)), Space : O(n)
    public static List<Integer> rightSmallerThanBetter(List<Integer> array) {
        List<Integer> result = new ArrayList<>(array.size());
        if (array == null || array.size() == 0)
            return result;
        array.stream().forEach(i -> result.add(0));

        int lastIdx = array.size() - 1;
        SpecialBST root = new SpecialBST(array.get(lastIdx), lastIdx, 0); // create root of BST which is last item of the input list

        for (int idx = lastIdx - 1; idx >= 0; idx--) { // from last prev element to first element we add nodes to BST from right to left
            int value = array.get(idx);
            root.insert(value, idx, 0); // noOfSmallerItemsAtInsert is composed when traversing the BST while insert, initially it will be zero
        }

        getRightSmallerCounts(root, result); // recursively read all the nodes in BST and initialize result list
        return result;
    }

    public static  void getRightSmallerCounts(SpecialBST root, List<Integer> result) {
        if (root == null)
            return;
        result.set(root.idx, root.noOfSmallerItemsAtInsert);
        getRightSmallerCounts(root.left, result);
        getRightSmallerCounts(root.right, result);
    }

    static class SpecialBST {
        private SpecialBST left;
        private SpecialBST right;
        private int value;
        private int leftSubTreeCount; // this we need to maintain for each node
        private int idx; // index is needed to map the result with output array index
        private int noOfSmallerItemsAtInsert; // this is the result of the node

        public SpecialBST(int value, int idx, int noOfSmallerItemsAtInsert) {
            this.value = value;
            this.idx = idx;
            this.noOfSmallerItemsAtInsert = noOfSmallerItemsAtInsert;
            this.leftSubTreeCount = 0;
        }

        public void insert(int value, int idx, int noOfSmallerItemsAtInsert) {
            if (value < this.value) { // traverse left
                this.leftSubTreeCount += 1; // if a node is supposed to be added as left child then the current node is recorded that it left child count is increased to 1
                if (this.left == null)
                    this.left = new SpecialBST(value, idx, noOfSmallerItemsAtInsert);
                else
                    this.left.insert(value, idx, noOfSmallerItemsAtInsert); // we traverse down to find the correct location to insert
            } else {
                    noOfSmallerItemsAtInsert += this.leftSubTreeCount; // if we traverse right of BST, means the current value is greater than to some 'x' values from the path of root to current node. every node is recorded with the "no of left child count", noOfLeftChildCount + ((currentRootValue < currentInsertValue) ? 1 : 0)
                    if (value > this.value)
                        noOfSmallerItemsAtInsert += 1;
                    if (this.right == null)
                        this.right = new SpecialBST(value, idx, noOfSmallerItemsAtInsert);
                    else
                        this.right.insert(value, idx, noOfSmallerItemsAtInsert);
            }
        }
    }

    // Third Implementation
    //Time : O(nlog(n)), Space : O(n)
    public static List<Integer> rightSmallerThanBest(List<Integer> array) {
        List<Integer> result = new ArrayList<>(array.size());
        if (array == null || array.size() == 0)
            return result;
        array.stream().forEach(i -> result.add(0));

        int lastIdx = array.size() - 1;
        CustomBST root = new CustomBST(array.get(lastIdx));

        for (int idx = lastIdx - 1; idx >= 0; idx--) {
            int value = array.get(idx);
            root.insert(value, idx, 0, result); // result list is initialized with result values during insert itself
        }

        return result;
    }

    static class CustomBST {
        private CustomBST left;
        private CustomBST right;
        private int value;
        private int leftSubTreeCount;

        public CustomBST(int value) {
            this.value = value;
            this.leftSubTreeCount = 0;
        }

        public void insert(int value, int idx, int noOfSmallerItemsAtInsert, List<Integer> result) {
            if (value < this.value) {
                this.leftSubTreeCount += 1;
                if (this.left == null) {
                    this.left = new CustomBST(value);
                    result.set(idx, noOfSmallerItemsAtInsert); // instead of storing index and composed result in Tree nodes, we set them directly in returned result
                }
                else
                    this.left.insert(value, idx, noOfSmallerItemsAtInsert, result);
            } else {
                noOfSmallerItemsAtInsert += this.leftSubTreeCount;
                if (value > this.value)
                    noOfSmallerItemsAtInsert += 1;
                if (this.right == null) {
                    this.right = new CustomBST(value);
                    result.set(idx, noOfSmallerItemsAtInsert); // instead of storing index and composed result in Tree nodes, we set them directly in returned result
                }
                else
                    this.right.insert(value, idx, noOfSmallerItemsAtInsert, result);
            }
        }
    }
}
