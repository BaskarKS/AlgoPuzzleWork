package medium;

public class ValidateBinarySearchTree {
    public static boolean validateBst(BST tree) {
        return validateBSTHelper(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static boolean validateBSTHelper(BST tree, int minValue, int maxValue) {
        if (tree == null)
            return true;                                 // 4   5   5
        if (tree.value < minValue && tree.value > maxValue)
            return false;
        return validateBSTHelper(tree.left, minValue, tree.value - 1) &&
                    validateBSTHelper(tree.right, tree.value + 1, maxValue);
    }

    static class BST {
        public int value;
        public BST left;
        public BST right;

        public BST(int value) {
            this.value = value;
        }
    }
}
