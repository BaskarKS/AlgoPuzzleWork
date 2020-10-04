package medium;

import java.util.ArrayList;
import java.util.List;

public class MinHeightBST {
    public static void main(String[] args) {
        List<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(5);
        input.add(7);
        input.add(10);
        input.add(13);
        input.add(14);
        input.add(15);
        input.add(22);
        BST tree = minHeightBst(input);
        System.out.println(tree);
    }
    //---------------------------------------------------------------------------------------------------
    // My Implementation using recursion
    //---------------------------------------------------------------------------------------------------
    public static BST minHeightBst(List<Integer> array) {
        // Write your code here.
        int leftIdx = 0;
        int rightIdx = array.size() - 1;
        int mid = getIndex(leftIdx, rightIdx);
        BST tree = new BST(array.get(mid));
        initTree(leftIdx, mid - 1, tree, array);
        initTree(mid + 1, rightIdx, tree, array);
        return tree;
    }
    private static void initTree(int left, int right, BST tree, List<Integer> array) {
        if (getIndex(left, right) == -1)
            return;
        int mid = getIndex(left, right);
        tree.insert(array.get(mid));
        initTree(left, mid - 1, tree, array);
        initTree(mid + 1, right, tree, array);
    }
    public static int getIndex(int left, int right) {
        if (left > right)
            return -1;
        return (left + right) / 2;
    }

    //---------------------------------------------------------------------------------------------------
    static class BST {
        public int value;
        public BST left;
        public BST right;

        public BST(int value) {
            this.value = value;
            left = null;
            right = null;
        }

        public void insert(int value) {
            if (value < this.value) {
                if (left == null) {
                    left = new BST(value);
                } else {
                    left.insert(value);
                }
            } else {
                if (right == null) {
                    right = new BST(value);
                } else {
                    right.insert(value);
                }
            }
        }
    }
}
