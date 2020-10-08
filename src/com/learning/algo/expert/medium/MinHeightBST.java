package medium;

import java.util.ArrayList;
import java.util.List;
/*
Input array passed is in sorted order,

To have min height BST the nodes on left should be equal to nodes on right
to maintain the BSt equal / min height

since the passed in array is in sorted order is really helpful to do construct
it. if we find the mid element of array then elements on left we can put in left
subtree and elements on right we can put in the right sub tree.

if the input elements contain duplicate elements, its difficult to construct the
BST because it will not give us the exact center to start constructing
the tree. in this case any of the sides (left or right) becomes little skewed.

keep picking the middle value of array and futher divided arrrays to
determine the root and further below subtree roots to construct
the entire tree.
* */
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
 /*   //---------------------------------------------------------------------------------------------------
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

 */

    /*
    // 2nd implementation - Time : O(n) , Space : O(n)
    public static BST minHeightBst(List<Integer> array) {
        // Write your code here.
        return initTree(0, array.size() - 1, null, array);
    }
    // we pass the parent object to this function to initialize its child (left /
    // right) based on the value of parent
    private static BST initTree(int left, int right, BST tree, List<Integer> array) {
        if (getIndex(left, right) == -1)
            return null;
        int mid = getIndex(left, right);
        int value = array.get(mid);
        BST newNode = new BST(value);
        if (tree == null) { // on no tree, first invocation
            tree = newNode;
        } else {
            if (value < tree.value) // child is initialized to its parent
                tree.left = newNode;
            else
                tree.right = newNode;
            tree = newNode; // for further navigation, child becomes parent
        }
        initTree(left, mid - 1, tree, array); // we don't utilize the return value
        initTree(mid + 1, right, tree, array);
        return tree;
    }
    public static int getIndex(int left, int right) {
        if (left > right)
            return -1;
        return (left + right) / 2;
    }
*/


    // 3rd implementation - Time : O(n) , Space : O(n)
    public static BST minHeightBst(List<Integer> array) {
        // Write your code here.
        return initTree(0, array.size() - 1, array);
    }
    // we don't pass the parent object to this function
    // but use the return value to initialize its child (left /
    // right)
    private static BST initTree(int left, int right, List<Integer> array) {
        if (getIndex(left, right) == -1)
            return null;
        int mid = getIndex(left, right);
        int value = array.get(mid);
        BST node = new BST(value);
        node.left = initTree(left, mid - 1,  array); // we don't utilize the return value
        node.right = initTree(mid + 1, right, array);
        return node;
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
