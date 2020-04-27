package veryhard;
import java.util.List;
import java.util.function.Function;

public class IterativeInOrderTraversal {

    private static BinaryTree head = null;

    public static void main(String[] args) {

        List<Integer> nodes = List.of(5, 4, 2, 1, 3, 7, 6, 8);
        createTree(nodes);
        printInOrder(head);
        System.out.println("Iterative navigating the Tree");
        iterativeInOrderTraversal(head, IterativeInOrderTraversal::callBack);
    }

    public static void printInOrder(BinaryTree tree) {
        if (tree == null)
            return;
        printInOrder(tree.left);
        System.out.println(tree.value);
        printInOrder(tree.right);
    }

    public static void printPreOrder(BinaryTree tree) {
        if (tree == null)
            return;

        System.out.println(tree.value);
        printPreOrder(tree.left);
        printPreOrder(tree.right);
    }

    public static void printPostOrder(BinaryTree tree) {
        if (tree == null)
            return;

        printPostOrder(tree.left);
        printPostOrder(tree.right);
        System.out.println(tree.value);
    }

    private static void createTree(List<Integer> values) {
        for (int value : values)
            head = addNode(head, null, value);
    }

    private static BinaryTree addNode(BinaryTree tree, BinaryTree parent, int value) {
        if (tree == null)
            return new BinaryTree(value, parent);
        if (tree.value == value)
            return tree;
        if (tree.value > value)
            tree.left = addNode(tree.left, tree, value);
        else if (tree.value < value)
            tree.right = addNode(tree.right, tree, value);
        return tree;
    }
    private static Void callBack(BinaryTree tree) {
        System.out.println("callback("+tree.value+")");
        return null;
    }

    public static void iterativeInOrderTraversal(BinaryTree tree,
                                                 Function<BinaryTree, Void> callback) {
        if (tree == null)
            return;
        BinaryTree currentNode = tree;
        BinaryTree previousNode = null;
        BinaryTree nextNode = null;
        while (currentNode != null) {
            if (previousNode == null || previousNode == currentNode.parent) {
                if (currentNode.left != null)
                    nextNode = currentNode.left;
                else {
                    callback.apply(currentNode);
                    nextNode = (currentNode.right != null) ? currentNode.right : currentNode.parent;
                }
            }
            else if (previousNode == currentNode.right)
                nextNode = currentNode.parent;
            else if (previousNode == currentNode.left) {
                callback.apply(currentNode);
                nextNode = (currentNode.right != null) ? currentNode.right : currentNode.parent;
            }
            previousNode = currentNode;
            currentNode = nextNode;
        }
    }

    static class BinaryTree {
        public int value;
        public BinaryTree left;
        public BinaryTree right;
        public BinaryTree parent;

        public BinaryTree(int value) {
            this.value = value;
        }

        public BinaryTree(int value, BinaryTree parent) {
            this.value = value;
            this.parent = parent;
        }
    }

}
