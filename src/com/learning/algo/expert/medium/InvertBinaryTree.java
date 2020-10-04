package medium;


import java.util.ArrayDeque;
import java.util.Queue;

public class InvertBinaryTree {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree(7);
        BinaryTree leftRoot = new BinaryTree(4);
        BinaryTree rightRoot = new BinaryTree(10);
        tree.left = leftRoot;
        tree.right = rightRoot;
        leftRoot.left = new BinaryTree(1);
        //leftRoot.right = new BinaryTree(5);
        //rightRoot.left = new BinaryTree(9);
        rightRoot.right = new BinaryTree(12);
        System.out.println(tree);
        invertBinaryTreeRecursive(tree);
        System.out.println(tree);
    }

    //Time : O(N) N is number of nodes, Space : O(d) d is depth of tree
    public static void invertBinaryTree(BinaryTree tree) {
        // Write your code here.
        if (tree == null)
            return;
        swapNodes(tree);
        invertBinaryTree(tree.left);
        invertBinaryTree(tree.right);
    }

    // Time O(n) n is number of nodes, Space : O(n)
    public static void invertBinaryTreeRecursive(BinaryTree tree) {
        // Write your code here.
        if (tree == null)
            return;
        Queue<BinaryTree> queue = new ArrayDeque<BinaryTree>();
        queue.add(tree);
        while (!queue.isEmpty()) {
            BinaryTree root = queue.poll();
            if (root.left != null) {
                queue.add(root.left);
            }
            if (root.right != null) {
                queue.add(root.right);
            }
            swapNodes(root);
        }
    }
    private static void swapNodes(BinaryTree root) {
        if (root == null) return;
        BinaryTree temp = root.left;
        root.left = root.right;
        root.right = temp;
    }

    static class BinaryTree {
        public int value;
        public BinaryTree left;
        public BinaryTree right;

        public BinaryTree(int value) {
            this.value = value;
        }
        @Override
        public String toString() {
            return "Node=" + value;
        }
    }
}
