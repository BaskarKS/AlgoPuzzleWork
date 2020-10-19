package veryhard;

import java.beans.BeanInfo;
import java.util.ArrayList;
import java.util.List;

public class FlattenBinaryTree {
    /*
        function takes in a binary tree, flattens it and returns the leftmost node.
        flattened binary tree is a structure thats identical to a DoublyLinkedList
        where nodes follow the original tree's left-to-right order.

        Note: If input binary tree happens to be a valid binary search tree, then
        nodes in the flattened tree will be sorted.

        Flattening should be done in in-place (no new Data-Structure should be
        created.

        Each node has left and right child nodes, children nodes can either be
        BinaryTree or None/Null

        Eg:

                        1
                  /           \
               2               3
           /      \          /
        4         5     6
                /     \
              7       8           => 7/8 will return [7 , 7] , 8 will return [8, 8/

    => 5 will get its left subtree return [7, 7] and right subtree return [8, 8], 5 will make
    left connection with left-subtree-right-child(7) and
    right connection with right sub-tree-left-child(8) and
    return its left-subtree-left child as left-child(7) and
    right-subtree-right-child as right-child(8) to (7, 8) its parent (2)

    => 2 will get its left subtree return [4, 4] and right subtree return [7, 8], 2 will make
    left connection with left-subtree-right-child(4) and
    right connection with right sub-tree-left-child(7) and
    return its left-subtree-left child as left-child(4) and
    right-subtree-right-child as right-child(8) to (4, 8) its parent (1)

    => 3 will get its left subtree return [6, 6] and right child as 3, 3 will make
    left connection with left-subtree-right-child(6) and
    return its left-subtree-left child as left-child(6) and
    right-subtree-right-child as right-child(3) to (6, 3) its parent (1)

    => 1 will get its left subtree return [4, 8] and right subtree return [6, 3], 1 will make
    left connection with left-subtree-right-child(8) and
    right connection with right sub-tree-left-child(6) and
    return its left-subtree-left child as left-child(4) and
    right-subtree-right-child as right-child(3) to (4, 3) its parent which is the result

    Output :
     4 <-> 2 <-> 7 <-> 5 <-> 8 <-> 1 <-> 6 <-> 3
     output should return the node 4 (left most)
    */

    // This is the class of the input root. Do not edit it.
    static class BinaryTree {
        int value;
        BinaryTree left = null;
        BinaryTree right = null;

        public BinaryTree(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "value=" + value;
        }
    }

    public static BinaryTree constructSomeSampleTree() {
        BinaryTree root = new BinaryTree(1);
        BinaryTree left = new BinaryTree(2);
        BinaryTree leftLeft = new BinaryTree(4);
        BinaryTree leftRight = new BinaryTree(5);
        leftRight.left = new BinaryTree(7);
        leftRight.right = new BinaryTree(8);
        left.left = leftLeft;
        left.right = leftRight;
        BinaryTree right = new BinaryTree(3);
        right.left = new BinaryTree(6);
        root.left = left;
        root.right = right;
        return root;
    }


    public static void printDoubleLinkedList(BinaryTree doubleListHead) {
        // print the double linked list which we got
        while (doubleListHead != null) {
            System.out.print(doubleListHead.value + " <=> ");
            doubleListHead = doubleListHead.right;
        }
        System.out.println();
    }

    // ----------------------------------------------- Implementation One -----------------------------------------------------
    // Time : O(n) and Space : O(n)
    public static BinaryTree flattenBinaryTreeConsumeSpace(BinaryTree root) {
        List<BinaryTree> nodesInOrder = new ArrayList<>();
        getInOrderNodes(root, nodesInOrder);
        for (int idx = 0; idx < nodesInOrder.size() - 1; idx++) {
            BinaryTree leftNode = nodesInOrder.get(idx);
            BinaryTree rightNode = nodesInOrder.get(idx + 1);
            leftNode.right = rightNode;
            rightNode.left = leftNode;
        }
        return nodesInOrder.get(0);
    }

    public static void getInOrderNodes(BinaryTree root, List<BinaryTree> treeList) {
        if (root == null)
            return;
        getInOrderNodes(root.left, treeList);
        treeList.add(root);
        getInOrderNodes(root.right, treeList);
    }
    // ----------------------------------------------- Implementation One -----------------------------------------------------
    public static void main(String[] args) {
        BinaryTree rootOne = constructSomeSampleTree();
        BinaryTree doubleListOne = flattenBinaryTreeConsumeSpace(rootOne);
        printDoubleLinkedList(doubleListOne);

        BinaryTree rootTwo = constructSomeSampleTree();
        BinaryTree doubleListTwo = flattenBinaryTree(rootTwo);
        printDoubleLinkedList(doubleListTwo);

    }

    // ----------------------------------------------- Implementation Two -----------------------------------------------------
    // Time : O(n) and Space : O(d), d is depth of the tree, if the tree is balanced then space will be O(log n)
    public static BinaryTree flattenBinaryTree(BinaryTree root) {
        BinaryTree[] children = flattenTree(root); // recursively traverse and rewire the connections
        BinaryTree headLeftChild = children[0]; // child 0 is the left most child of left subtree which is the Head of our list
        return headLeftChild;                               // child 1 is the right most child of right subtree which is the Tail of our list
    }

    public static BinaryTree[] flattenTree(BinaryTree node) {
        BinaryTree currentLeftChild = null;
        BinaryTree currentRightChild = null;
        if (node.left == null) { // means we are in leaf node
            currentLeftChild = node; // the current node will be the left most child of the left subtree of leaf
        } else {
            BinaryTree[] leftSubTreeChildren = flattenTree(node.left); // get the left most child and right most child of left node
            BinaryTree leftSubtreeLeftMostChild = leftSubTreeChildren[0];
            BinaryTree leftSubtreeRightMostChild = leftSubTreeChildren[1];
            connectNodes(leftSubtreeRightMostChild, node); // the right most child of left subtree will become the left child of current node
            currentLeftChild = leftSubtreeLeftMostChild; // the left most child of the left subtree will be the left child of current node
        }

        if (node.right == null) { // means we are in leaf node
            currentRightChild = node;// the current node will be the right most child of the right subtree of leaf
        } else {
            BinaryTree[] rightSubTreeChildren = flattenTree(node.right); // get the left most child and right most child of right node
            BinaryTree rightSubTreeLeftMostChild = rightSubTreeChildren[0];
            BinaryTree rightSubTreeRightMostChild = rightSubTreeChildren[1];
            connectNodes(node, rightSubTreeLeftMostChild); // the left most child of right subtree will become the right child of current node
            currentRightChild = rightSubTreeRightMostChild; // the right most child of the right subtree will be the right child of current node
        }

        return new BinaryTree[]{currentLeftChild, currentRightChild}; // return the left most child of left subtree and right most child of right subtree as left and right child to the current node parent
    }
    public static void connectNodes(BinaryTree left, BinaryTree right) {
        left.right = right;
        right.left = left;
    }
    // ----------------------------------------------- Implementation Two -----------------------------------------------------
}
