package veryhard;

public class AllKindOfNodeDepths {
/*

the distance between a node in a BinaryTree, and the tree's root is called
the node's depth.

function that takes in a binary tree and returns the sum of all of its
subtrees nodes depths.

Each BinaryTree node has an integer value, a left child node and a right
child node. children nodes can be either be binary tree or null.

Ip:
                             1  (16)(9)
                       /             \
      (6)(5)     2                  3  (2)(3)
                /       \             /        \
 (2)(3)  4          5         6          7
         /       \   (0)(1)  (0)(1)     (0)(1)
       8        9
    (0)(1)  (0)(1)
Op:
16
depth of node 2 is 1
depth of node 3 is 1
depth of node 4 is 2
depth of node 8 is 3
sum of all depths is 16

each node maintain 2 values which compute the current depth(left node depth + left node count +
right node depth + right node count) and current node count (left nodes + right nodes + 1(current node))

these two values current depth and current node count is passed to its parent for computation

here each node depth is the the total depths of all the nodes below, considering the current
node as the root. The depth is not computed based on the root node, its based on current node.
the current node depths is the combination of all children nodes and edges to the children nodes.

Eg: For Node 2, the depth of node 2 is computed (left child depth 2, left child count 3 and right
child depth 0, right child count 1) hence the Node 2 depth is 6.

 */
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
        System.out.println(allKindsOfNodeDepths(node1));

    }

    public static int allKindsOfNodeDepths(BinaryTree root) {
        // Write your code here.
        int[] depthHeight = new int[2];
        depthHeight = allNodeDepths(root);
        return depthHeight[2];
    }

    public static int[] allNodeDepths(BinaryTree node) {
        final int NODE_COUNT = 1;
        final int DEPTH = 0;
        if (node == null)
            return new int[]{0, 0, 0};
        if (node.left == null && node.right == null) {
            return new int[]{0, 1, 0};
        }

        int[] leftDepthNodeCount = allNodeDepths(node.left);
        int[] rightDepthNodeCount = allNodeDepths(node.right);

        int leftTreeDepth = leftDepthNodeCount[DEPTH];
        int leftTreeNodeCount = leftDepthNodeCount[NODE_COUNT];
        int rightTreeDepth = rightDepthNodeCount[DEPTH];
        int rightTreeNodeCount = rightDepthNodeCount[NODE_COUNT];

        int currentDepth =  leftTreeDepth + leftTreeNodeCount +
                                            rightTreeDepth + rightTreeNodeCount;
        int currentCount = 1 + leftTreeNodeCount + rightTreeNodeCount;

        return new int[]{currentDepth, currentCount, (currentDepth + leftDepthNodeCount[2] + rightDepthNodeCount[2])};
    }

    static class BinaryTree {
        int value;
        BinaryTree left;
        BinaryTree right;

        @Override
        public String toString() {
            return "value=" + value;
        }

        public BinaryTree(int value) {
            this.value = value;
            left = null;
            right = null;
        }
    }
}

