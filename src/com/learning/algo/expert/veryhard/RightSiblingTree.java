package veryhard;

public class RightSiblingTree {

    /*
    Transformation should be done in-place, some nodes like 10 below,
    will not have a parent after transformation
    input :
                    1
             /             \
          2                  3
        /     \             /    \
       4       5         6      7
    /    \        \      /       /  \
   8     9      10  11   12   13
                         /
                       14

Output
                           1
                     /
                  2 --------------- 3
               /
            4 ------ 5 ------ 6 ------ 7
         /                      /           /
        8----9    10----11       12 -- 13
                             /
                           14
    * */
    static class BinaryTree {
        int value;
        BinaryTree left = null;
        BinaryTree right = null;

        public BinaryTree(int value) {
            this.value = value;
        }
    }
    public static BinaryTree rightSiblingTree(BinaryTree root) {
        // Write your code here.
        recursiveNavigateTree(root, null, false);
        return root;
    }
    public static void recursiveNavigateTree(BinaryTree node, BinaryTree parent, boolean isLeftNode) {
        if (node == null)
            return;
        BinaryTree left = node.left;
        BinaryTree right = node.right;
        recursiveNavigateTree(left, node, true);
        if (parent == null)
            node.right = null;
        else if (isLeftNode)
            node.right = parent.right;
        else {
            if (parent.right == null)
                node.right = null;
            else
                node.right = parent.right.left;
        }
        recursiveNavigateTree(right, node, false);
    }
}
