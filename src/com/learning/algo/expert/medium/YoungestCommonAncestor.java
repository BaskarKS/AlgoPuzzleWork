package medium;

import java.util.ArrayList;
import java.util.List;

/*
Given root of the tree, 2 descendants, compute and find the common ancestor among 2
descendants

Ip :
topAncestor = Node A
descendantOne = Node E
descendantTwo = Node I
                  A
              /        \
            B          C
         /      \     /      \
       D      E    F      G
    /      \
   H       I

Op : Node B

* */
public class YoungestCommonAncestor {
    public static void main(String[] args) {
        AncestralTree nodeA = new AncestralTree('A');
        AncestralTree nodeB = new AncestralTree('B');
        AncestralTree nodeC = new AncestralTree('C');
        AncestralTree nodeD = new AncestralTree('D');
        AncestralTree nodeE = new AncestralTree('E');
        AncestralTree nodeF = new AncestralTree('F');
        AncestralTree nodeG = new AncestralTree('G');
        AncestralTree nodeH = new AncestralTree('H');
        AncestralTree nodeI = new AncestralTree('I');
        nodeD.addAsAncestor(new AncestralTree[]{nodeH, nodeI});
        nodeB.addAsAncestor(new AncestralTree[]{nodeD, nodeE});
        nodeC.addAsAncestor(new AncestralTree[]{nodeF, nodeG});
        nodeA.addAsAncestor(new AncestralTree[]{nodeB, nodeC});
        AncestralTree commonAncestor = getYoungestCommonAncestorRecur(nodeA, nodeE, nodeI);
        System.out.println(commonAncestor);
    }
    public static AncestralTree getYoungestCommonAncestor(
        AncestralTree topAncestor, AncestralTree descendantOne, AncestralTree descendantTwo) {
        // Write your code here.
        List<AncestralTree> desOneHierarchy = new ArrayList<>();
        AncestralTree desOne = descendantOne;
        while (desOne.name != topAncestor.name) {
            desOneHierarchy.add(desOne);
            desOne = desOne.ancestor;
        }
        desOneHierarchy.add(desOne);

        List<AncestralTree> desTwoHierarchy = new ArrayList<>();
        AncestralTree desTwo = descendantTwo;
        while (desTwo.name != topAncestor.name) {
            desTwoHierarchy.add(desTwo);
            desTwo = desTwo.ancestor;
        }
        desTwoHierarchy.add(desTwo);

        int idxOne = desOneHierarchy.size() - 1;
        int idxTwo = desTwoHierarchy.size() - 1;

        while (desOneHierarchy.get(idxOne) == desTwoHierarchy.get(idxTwo)) {
            idxOne -= 1;
            idxTwo -= 1;
            if (idxOne < 0 || idxTwo < 0)
                break;
        }
        AncestralTree retTree = (idxOne == -1) ?  desOneHierarchy.get(idxOne + 1) :
            (idxTwo == -1) ? desTwoHierarchy.get(idxTwo + 1) : desOneHierarchy.get(idxOne + 1);
        return retTree;
    }

    //Time : O(d) d is length of lower descendant among passed descendants , Space O(1)
    public static AncestralTree getYoungestCommonAncestorRecur(
        AncestralTree topAncestor, AncestralTree descendantOne, AncestralTree descendantTwo) {
        int desOneDepth = getDepth(topAncestor, descendantOne);
        int desTwoDepth = getDepth(topAncestor, descendantTwo);
        if (desOneDepth > desTwoDepth) {
            descendantOne = decreaseDepth(descendantOne, desOneDepth - desTwoDepth);
        } else if (desTwoDepth > desOneDepth) {
            descendantTwo = decreaseDepth(descendantTwo, desTwoDepth - desOneDepth);
        }
        while (descendantOne != descendantTwo) {
            descendantOne = descendantOne.ancestor;
            descendantTwo = descendantTwo.ancestor;
        }
        return descendantOne;
    }
    public static AncestralTree decreaseDepth(AncestralTree descendent, int depth) {
        AncestralTree current = descendent;
        while (current != null && depth > 0) {
            current = current.ancestor;
            depth -= 1;
        }
        return current;
    }
    public static int getDepth(AncestralTree topNode, AncestralTree descendent) {
        if (descendent == null)
            return -1;
        if (descendent == topNode)
            return 0;
        return 1 + getDepth(topNode, descendent.ancestor);
    }
    static class AncestralTree {
        public char name;
        public AncestralTree ancestor;

        AncestralTree(char name) {
            this.name = name;
            this.ancestor = null;
        }

        // This method is for testing only.
        void addAsAncestor(AncestralTree[] descendants) {
            for (AncestralTree descendant : descendants) {
                descendant.ancestor = this;
            }
        }

        @Override
        public String toString() {
            return "AncestralTree{" +
                "name=" + name +
                '}';
        }
    }
}
