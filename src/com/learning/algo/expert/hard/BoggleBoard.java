package hard;

import javax.imageio.plugins.tiff.TIFFImageReadParam;
import java.util.*;

/*
* given a two dimension array of unequal height and width containing
* letters, this matrix represents a boggle board. you are also
* given a list of words. write a function that returns an array
* of all words contained in the boggle board.
*
* A word is constructed in the boggle board by connecting adjacent
* (horizontally / vertically / diagonally) letters, without using
* any single letter at a given position more than once; while words
* can of course have repeated letters, those repeated letters must
* come from different positions in the boggle board in order for
* the word to be contained in the board. Note that two or more
* words are allotted to overlap and use the same letters in the
* boggle board.
*
* Eg:
* Ip:
*
* [["t","h","i","s","i","s","a"]
* ["s","i","m","p","l","e","x"]
* ["b","x","x","x","x","e","b"]
* ["x","o","g","g","l","x","o"]
* ["x","x","x","D","T","r","a"]
* ["R","E","P","E","A","d","x"]
* ["x","x","x","x","x","x","x"]
* ["N","O","T","R","E","-","P"]
* ["x","x","D","E","T","A","E"]],
*
* ["this", "is", "not", "a", "simple", "boggle", "board", "test",
* "REPEATED", "NOTRE-PEATED"]
*
* Op:
* ["this", "is", "a", "simple", "boggle", "board", "NOTRE-PEATED"]
* */
public class BoggleBoard {
    public BoggleBoard(String[] words) {
        for (var word : words)
            addWordToTrie(word);
    }

    class TrieNode {
        char value;
        boolean isEndOfWord;
        Map<Character,TrieNode> childrens;
        TrieNode(char value) {
            this.value = value;
            childrens = new HashMap<>();
        }
        public boolean isEndOfWord() {
            return isEndOfWord;
        }
        public void setEndOfWord(boolean endOfWord) {
            this.isEndOfWord = endOfWord;
        }
        public Map<Character,TrieNode> getChildrens() {
            return hasChildrens() ? childrens : null;
        }
        public boolean hasChild(Character child) {
            return hasChildrens() ? childrens.containsKey(child) : false;
        }
        public TrieNode getChild(Character child) {
            return hasChild(child) ? childrens.get(child) : null;
        }
        public void removeChild(Character child) {
            if (hasChildrens() && hasChild(child))
                getChildrens().remove(child);
        }
        public void addChild(Character child) {
            childrens.put(child, new TrieNode(child));
        }
        public boolean hasChildrens() {
            return !childrens.isEmpty();
        }
    }

    TrieNode root = new TrieNode(' ');

    private TrieNode getRoot() {
        return root;
    }

    public void addWordToTrie(String word) {
        if (word == null || word.isEmpty())
            return;
        TrieNode current = getRoot();
        for (char ch : word.toCharArray()) {
            if (!current.hasChild(ch)) {
                current.addChild(ch);
            }
            current = current.getChild(ch);
        }
        current.setEndOfWord(true);
    }

    public boolean containsLetter(TrieNode node, char ch) {
        return node.hasChild(ch);
    }

    public void print() {
        TrieNode root = getRoot();
        if (root != null)
            print(root);
    }

    private void print(TrieNode current) {
        if (current == null)
            return;
        System.out.print(current.value);
        if (current.isEndOfWord()) {
            System.out.println();
            return;
        }
        for (var child : current.getChildrens().keySet())
            print(current.getChild(child));
    }

    public static void main(String[] args) {
        String[] words = new String[] {"this", "is", "not", "a", "simple", "boggle",
                "board", "test", "REPEATED", "NOTRE-PEATED"};
//        var trie = new BoggleBoard(words);
//        trie.print();

        char[][] boggleBoard = new char[][] {
                {'t','h','i','s','i','s','a'},
                {'s','i','m','p','l','e','x'},
                {'b','x','x','x','x','e','b'},
                {'x','o','g','g','l','x','o'},
                {'x','x','x','D','T','r','a'},
                {'R','E','P','E','A','d','x'},
                {'x','x','x','x','x','x','x'},
                {'N','O','T','R','E','-','P'},
                {'x','x','D','E','T','A','E'}};
        List<String> containedWords = boggleBoard(boggleBoard, words);
        System.out.println(containedWords);
    }
    public static List<String> boggleBoard(char[][]board, String[] words) {
        List<String> containedWords = new ArrayList<>();
        BoggleBoard trie = new BoggleBoard(words);
        TrieNode node = trie.getRoot();
        for (int rowIdx = 0; rowIdx < board.length; rowIdx++) {
            for (int colIdx = 0; colIdx < board[rowIdx].length; colIdx++) {
                char ch = board[rowIdx][colIdx];
                if (!trie.containsLetter(node, ch)) {
                    continue;
                }
                else {
                    String word = "";
                    word = getWordFromBoard(board, ch, node, rowIdx, colIdx, word);
                    if (!word.isEmpty())
                        containedWords.add(word);
                }
            }
        }
        return containedWords;
    }
    public static String getWordFromBoard(char[][] board, char ch, TrieNode node,
                                          int rowIdx, int colIdx, String word) {
        TrieNode current = node;

        if (current.isEndOfWord() || !current.hasChild(ch))
            return word;

        word += ch;
        current = current.getChild(ch);
        List<Neighbour> neighbours = getNeighbours(board, ch, rowIdx, colIdx);
        for (Neighbour neighbour : neighbours) {
            if (current.hasChild(neighbour.ch))
                return getWordFromBoard(board, neighbour.ch, current,
                        neighbour.row, neighbour.col, word);
        }
        return word;
    }

    public static List<Neighbour> getNeighbours(char[][] board, char ch, int rowNo, int colNo) {
        List<Neighbour> neighbourList = new ArrayList<>();
        int maxRow = board.length - 1;
        int maxCol = board[rowNo].length - 1;
        boolean hasTop = (rowNo - 1) >= 0;
        boolean hasTopLeft = ((rowNo - 1) >= 0) && ((colNo - 1) >= 0);
        boolean hasTopRight = ((rowNo - 1) >= 0) && ((colNo + 1) <= maxCol);
        boolean hasBottom = (rowNo + 1) <= maxRow;
        boolean hasLeft = (colNo - 1) >= 0;
        boolean hasRight = (colNo + 1) <= maxCol;
        boolean hasBottomRight = ((rowNo + 1) <= maxRow) && ((colNo + 1) <= maxCol);
        boolean hasBottomLeft = ((rowNo + 1) <= maxRow) && ((colNo - 1) >= 0);

        if (hasTopLeft) neighbourList.add(new Neighbour(board[rowNo - 1][colNo - 1], rowNo - 1, colNo - 1));
        if (hasTop) neighbourList.add(new Neighbour(board[rowNo - 1][colNo], rowNo - 1, colNo));
        if (hasTopRight) neighbourList.add(new Neighbour(board[rowNo - 1][colNo + 1], rowNo - 1, colNo + 1));
        if (hasRight) neighbourList.add(new Neighbour(board[rowNo][colNo + 1], rowNo, colNo + 1));
        if (hasBottomRight) neighbourList.add(new Neighbour(board[rowNo + 1][colNo + 1], rowNo + 1, colNo + 1));
        if (hasBottom) neighbourList.add(new Neighbour(board[rowNo + 1][colNo], rowNo + 1, colNo));
        if (hasBottomLeft) neighbourList.add(new Neighbour(board[rowNo + 1][colNo - 1], rowNo + 1, colNo - 1));
        if (hasLeft) neighbourList.add(new Neighbour(board[rowNo][colNo - 1], rowNo, colNo - 1));

        return neighbourList;
    }
    static class Neighbour {
        char ch;
        int row;
        int col;
        public Neighbour(char ch, int row, int col) {
            this.ch = ch;
            this.row = row;
            this.col = col;
        }
    }
}
