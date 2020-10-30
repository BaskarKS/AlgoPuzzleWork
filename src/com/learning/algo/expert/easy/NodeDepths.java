package easy;

import java.util.Stack;

import javax.management.MBeanNotificationInfo;
/*

Ip:
                            1
                       /          \
                    2               3
                /       \         /        \
             4          5      6          7
         /       \
       8        9

Op:
16
depth of node 2 is 1
depth of node 3 is 1
depth of node 4 is 2
depth of node 8 is 3
sum of all depths is 16



* */
public class NodeDepths {
    public static void main(String[] args) {
        BinaryTree node1 = new BinaryTree(1);
        BinaryTree node2 = new BinaryTree(2);
        BinaryTree node3 = new BinaryTree(3);
        BinaryTree node4 = new BinaryTree(4);
        BinaryTree node5 = new BinaryTree(5);
        BinaryTree node6 = new BinaryTree(6);
        BinaryTree node7 = new BinaryTree(7);
        BinaryTree node8 = new BinaryTree(8);
        BinaryTree node9 = new BinaryTree(9);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        node4.left = node8;
        node4.right = node9;
        //System.out.println(nodeDepths(node1));
        System.out.println(nodeDepthsIterative(node1));

    }
    public static int nodeDepths(BinaryTree root) {
        // Write your code here.
        if (root == null)
            return 0;
        return nodeDepth(root, 0);
    }
    public static int nodeDepth(BinaryTree root, int depth) {
        if (root == null)
            return 0;
        if (isLeaf(root))
            return depth;
        return depth +
            nodeDepth(root.left, depth + 1) +
            nodeDepth(root.right, depth + 1);
    }
    public static boolean isLeaf(BinaryTree root) {
        return root.left == null && root.right == null;
    }
    public static int nodeDepthsIterative(BinaryTree root) {
        if (root == null)
            return 0;
        Stack<StackItem> treeNodes = new Stack<>();
        treeNodes.push(new StackItem(root, 0));
        int totalDepth = 0;
        while (!treeNodes.empty()) {
            StackItem currentNode = treeNodes.pop();
//            if (currentNode.node == null)
//                continue;
            totalDepth += currentNode.depth;

            if (currentNode.node.left != null)
                treeNodes.push(new StackItem(currentNode.node.left,
                currentNode.depth + 1));
            if (currentNode.node.right != null)
                treeNodes.push(new StackItem(currentNode.node.right,
                    currentNode.depth + 1));
        }
        return totalDepth;
    }

    static class StackItem {
        private BinaryTree node;
        int depth;
        public StackItem(BinaryTree node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

    static class BinaryTree {
        int value;
        BinaryTree left;
        BinaryTree right;

        public BinaryTree(int value) {
            this.value = value;
            left = null;
            right = null;
        }
    }
}
