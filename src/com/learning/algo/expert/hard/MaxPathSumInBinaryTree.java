package hard;

import java.util.ArrayList;
import java.util.Arrays;

public class MaxPathSumInBinaryTree {
    // ----------------------------------------------- My Own Implementation ------------------------------------------
    public static int maxPathSum(BinaryTree tree) {
        if (tree == null)
            return -1;
        int leftTreeVal = maxPathSumRecursive(tree.left);
        int rightTreeVal = maxPathSumRecursive(tree.right);
/*     int entireTree = tree.value + leftTreeVal + rightTreeVal;
        int maxAmongLeftRight =Math.max(leftTreeVal + tree.value, rightTreeVal + tree.value );
        return Math.max(entireTree, maxAmongLeftRight);*/
        int maxNodeInclusiveLeft = Math.max(tree.value, leftTreeVal + tree.value);
        int totalMax = Math.max(maxNodeInclusiveLeft, maxNodeInclusiveLeft + rightTreeVal);
        return totalMax;
    }
    private static int maxPathSumRecursive(BinaryTree tree) {
        if (tree == null)
            return 0;
        return Math.max(tree.value + maxPathSumRecursive(tree.left),
                                tree.value +maxPathSumRecursive(tree.right));
    }
    // ----------------------------------------------- My Own Implementation ------------------------------------------

    // ----------------------------------------------- Correct Implementation ------------------------------------------
    public static int maxPathSumInBinaryTree(BinaryTree tree) {
        int[] maxPathValue = maxPathSumInBinaryTreeRecursive(tree);
        return maxPathValue[1];
    }
    // vary simple and beautifully thought and composed solution
    public static int[] maxPathSumInBinaryTreeRecursive(BinaryTree tree) {
        if (tree  == null)
            return new int[]{0, 0};
        int[] leftTreeValues = maxPathSumInBinaryTreeRecursive(tree.left); // get Branch Max and tree max for left tree
        int[] rightTreeValues = maxPathSumInBinaryTreeRecursive(tree.right); // get Branch Max and tree max for right tree

        // branch values are passed to upper tree to decide on compute on building the tree on top of that
        // branch or start building a max tree from the current branches.
        // max tree value is also helpful to pass to upper tree to decide and find max among tree / branches
        int leftBranchMax = leftTreeValues[0]; // get the left tree branch max
        int leftTreeMax = leftTreeValues[1]; // get left tree max value
        int rightBranchMax = rightTreeValues[0]; // get right tree branch max
        int rightTreeMax = rightTreeValues[1]; // get right tree max value

        int leftOrRightBranchMax = Math.max(leftBranchMax, rightBranchMax); // find which branch is max - left / right
        int branchMax = Math.max(tree.value, leftOrRightBranchMax + tree.value); // find max among the current node  and current node + branch max value
        int currentTreeBranchMax = Math.max(branchMax, leftBranchMax + tree.value + rightBranchMax); // find max among the partial branch and complete branches

        int currentTreeMax = Math.max(Math.max(leftTreeMax, rightTreeMax), currentTreeBranchMax); // find max among the "max of left tree" / "current computed max branch tree" / "max of right tree"

        return new int[] {branchMax, currentTreeMax}; // return current branch max and max tree value for further tree computation
    }
    public static BinaryTree getTree() {
        BinaryTree tree = new BinaryTree(1);
        BinaryTree leftTemp = tree.left = new BinaryTree(-5);
        BinaryTree rightTemp = tree.right = new BinaryTree(3);
        leftTemp.left = new BinaryTree(6);
        leftTemp.right = new BinaryTree(5);
        rightTemp.left = new BinaryTree(6);
        rightTemp.right = new BinaryTree(7);
        return tree;
    }

    public static void main(String[] args) {
        BinaryTree tree = getTree();
        System.out.println(maxPathSumInBinaryTree(tree));
        var list = new ArrayList<Integer>(Arrays.asList(0, 0));
    }
    static class BinaryTree {
        public int value;
        public BinaryTree left;
        public BinaryTree right;

        public BinaryTree(int value) {
            this.value = value;
        }
    }
}
