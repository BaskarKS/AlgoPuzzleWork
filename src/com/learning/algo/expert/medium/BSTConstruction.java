package medium;

public class BSTConstruction {
    public static void main(String[] args) {
        BST binarySearchTree = new BST(10);
        binarySearchTree.insert(5).insert(15).insert(5).insert(2).insert(14).insert(22);
        binarySearchTree.contains(5);
    }

    // Time Complexity - O(log n) (Average) - O(n) (Worst)
    // Space Complexity Recursion- O(log n) (Average) - O(n) (Worst)
    // Space Complexity Iterative- O(1) (Average) - O(1) (Worst)
    // ---------------------------- BST Construction --------------------------------------- //
    static class BST {
        public int value;
        public BST left;
        public BST right;

        public BST(int value) {
            this.value = value;
        }

        public BST insert(int value) {
            // Write your code here.
            // Do not edit the return statement of this method.
            BST root = this;
            BST node = new BST(value);
            while (root != null) {
                if (value >= root.value) {
                    if (root.right != null) {
                        root = root.right;
                        continue;
                    }
                    root.right = node;
                    break;
                } else {
                    if (root.left != null) {
                        root = root.left;
                        continue;
                    }
                    root.left = node;
                    break;
                }
            }
            return this;
        }

        public boolean contains(int value) {
            // Write your code here.
            BST root = this;
            while (root != null) {
                int val = root.value;
                if (val == value)
                    return true;
                if (value > val)
                    root = root.right;
                else if (value < val)
                    root = root.left;
                else
                    break;
            }
            return false;
        }

        public BST remove(int value) {
            // Write your code here.
            // Do not edit the return statement of this method.
            BST root = this;
            BST parent = this;
            while (root != null) {
                int val = root.value;
                if (val == value) {
                    if (root.left == null && root.right == null) {
                        if (root == parent.left)
                            parent.left = null;
                        else
                            parent.right = null;
                    } else if (root.left != null && root.right == null) {
                        if (root.left.right == null) {
                            root.value = root.left.value;
                            parent.left = root.left;
                        } else {
                            BST currParent = root.left;
                            BST toReturn = root.left;
                            while (toReturn.right != null) {
                                currParent = toReturn;
                                toReturn = toReturn.right;
                            }
                            root.value = toReturn.value;
                            currParent.right = toReturn.right;
                        }
                    } else {
                        BST toReturn = root.right;
                        BST currParent = root;
                        while (toReturn.left != null) {
                            currParent = toReturn;
                            toReturn = toReturn.left;
                        }
                        root.value = toReturn.value;
                        if (toReturn.left == null && toReturn.right == null) {
                            if (root == currParent)
                                root.right = null;
                            else
                                currParent.left = null;
                        } else {
                            currParent.left = toReturn.right;
                        }
                    }
                }
                parent = root;
                if (value > val)
                    root = root.right;
                else if (value < val)
                    root = root.left;
                else
                    break;
            }
            return this;
        }

        public BST removeBetter(int value) {
            removeBetter(value, null);
            return this;
        }

        public void removeBetter(int value, BST parent) {
            BST currentNode = this;
            while (currentNode != null) {
                if (value < currentNode.value) {
                    parent = currentNode;
                    currentNode = currentNode.left;
                } else if (value > currentNode.value) {
                    parent = currentNode;
                    currentNode = currentNode.right;
                } else {
                    //Here, currentNode is the node to be removed;
                    if (currentNode.left != null && currentNode.right != null) {
                        currentNode.value = currentNode.right.getMinValue();
                        currentNode.right.removeBetter(currentNode.value, currentNode);
                    } else if (parent == null) {
                        if (currentNode.left != null) {
                            currentNode.value = currentNode.left.value;
                            currentNode.right = currentNode.left.right;
                            currentNode.left = currentNode.left.left;
                        } else if (currentNode.right != null) {
                            currentNode.value = currentNode.right.value;
                            currentNode.left = currentNode.right.left;
                            currentNode.right = currentNode.right.right;
                        } else {
                            currentNode.value = 0;
                            currentNode.left = null;
                            currentNode.right = null;
                        }
                    } else if (parent.left == currentNode) {
                        parent.left = (currentNode.left != null) ? currentNode.left : currentNode.right;
                    } else if (parent.right == currentNode) {
                        parent.right = (currentNode.left != null) ? currentNode.left : currentNode.right;
                    }
                    break;
                }
            }
        }

        public int getMinValue() {
            if (left == null) {
                return value;
            } else {
                return left.getMinValue();
            }
        }
    }

    // ---------------------------- BST Construction --------------------------------------- //

}
