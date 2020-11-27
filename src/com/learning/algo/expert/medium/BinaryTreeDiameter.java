package medium;

import com.sun.source.tree.Tree;

public class BinaryTreeDiameter {
    /*
    Function takes in a Binary Tree and returns a Diameter.
    Diameter of Binary Tree is defined as the length of its longest Path, even if that path doesn't
    pass through the root of the tree.

    A Path is a collection of connected nodes in a tree, where no node is connected to more than
    two other nodes. The length of the path is the number of edges between the path's first node
    and its last node.

    Each binaryTree node has an integer value, a left and a right child node, children node can be
    binaryTree nodes themselves or Null

    eg:
                                        1                               --- Height - 5(including 1) left-tree-height(4), right-tree-height(1)
                                    /         \
                                  3           2                        --- Height - 4(including 3) left-tree-height(3), right-tree-height(3)
                              /        \
                            7          4                               ----Height - 3 (including 7) left-tree-height(2), right-tree-height(0)
                          /               \
                        8                 5                            ----Height  - 2
                       /                     \
                     9                        6                        ---- Height - 1

         OP: 6
    */
    static class BinaryTree {
        public int value;
        public BinaryTree left = null;
        public BinaryTree right = null;

        public BinaryTree(int value) {
            this.value = value;
        }
    }

    // Time Complexity : O(n), Space : O(n)
    public static int binaryTreeDiameter(BinaryTree tree) {
            return getTreeInfo(tree).getDiameter();
    }

    // to calculate diameter we need the height info the current node (height of left and height of right)

    // we either calculate the diameter of current node as the maximum among the largest diameter of
    // among the left and right subtree or the longestPath through the current node.

    // hence we calculate the height from the deepest node to top node, using height we calculate the
    //diameter and maintain the largest while traversing from the deepest node to way back to top node.
    public static TreeInfo getTreeInfo(BinaryTree tree) {
        if (tree == null)
            return new TreeInfo(0, 0);

        TreeInfo leftTreeData = getTreeInfo(tree.left);
        TreeInfo rightTreeData = getTreeInfo(tree.right);

        int longestPathThroughRoot = leftTreeData.getHeight() + rightTreeData.getHeight();

        int maxDiameterSoFar = Math.max(leftTreeData.getDiameter(), rightTreeData.getDiameter());
        int currentDiameter = Math.max(longestPathThroughRoot, maxDiameterSoFar);

        int currentHeight = 1 + Math.max(leftTreeData.getHeight(), rightTreeData.getHeight());

        return new TreeInfo(currentDiameter, currentHeight);
    }

    static class TreeInfo {
        private int diameter;
        private int height;
        public TreeInfo(int diameter, int height) {
            this.diameter = diameter;
            this.height = height;
        }
        public int getHeight() {
            return this.height;
        }
        public int getDiameter() {
            return  this.diameter;
        }
    }
}
