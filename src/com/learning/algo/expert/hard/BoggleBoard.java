package hard;

import java.util.*;

/* Boggle-Board
* Given a two dimension array of unequal height and width containing
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
    /*
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
    */
    public static void main(String[] args) {
        String[] words = new String[] {"this", "is", "not", "a", "simple", "boggle",
                "board", "test", "REPEATED", "NOTRE-PEATED"};
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
        Trie trie = new Trie();
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (String word : words)
            trie.addWord(word);
        Set<String> foundStrings = new HashSet<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                explore(row, col, board, trie, visited, foundStrings);
            }
        }
        List<String> result = new ArrayList<>();
        result.addAll(foundStrings);
        return result;
    }
    public static List<int[]> getNeighbours(int row, int col, char[][] board) {
        List<int[]> neighbours = new ArrayList<>();
        int maxRow = board.length - 1;
        int maxCol = board[0].length - 1;
        if (row > 0 && col > 0)
            neighbours.add(new int[] {row - 1, col - 1});
        if (row > 0 && col < maxCol)
            neighbours.add(new int[]{row - 1, col + 1});
        if (row < maxRow && col > 0)
            neighbours.add(new int[]{row + 1, col - 1});
        if (row < maxRow && col < maxCol)
            neighbours.add(new int[]{row + 1, col + 1});
        if (row > 0)
            neighbours.add(new int[]{row - 1, col});
        if (col > 0)
            neighbours.add(new int[]{row, col - 1});
        if (col < maxCol)
            neighbours.add(new int[]{row, col + 1});
        if (row < maxRow)
            neighbours.add(new int[]{row + 1, col});
        return neighbours;
    }
    public static void explore(int row, int col, char[][] board, Trie trie,
                               boolean[][] visited, Set<String> foundStrings) {
        if (visited[row][col])
            return;
        char letter = board[row][col];
        if (!trie.childs.containsKey(letter))
            return;
        visited[row][col] = true;
        Trie current = trie.childs.get(letter);
        if (current.isEndOfWord)
            foundStrings.add(current.word);
        List<int[]> neighbours = getNeighbours(row, col, board);
        for (int[] neighbour : neighbours)
            explore(neighbour[0], neighbour[1], board, current, visited, foundStrings);
        visited[row][col] = false;
    }
    static class Trie {
        Map<Character, Trie> childs;
        String word = "";
        boolean isEndOfWord = false;
        public Trie() {
            childs = new HashMap<>();
        }
        public void setEndOfWord(boolean isEndWord, String word) {
            this.word = word;
            this.isEndOfWord = isEndWord;
        }
        public void addWord(String word) {
            if (word == null || word.isEmpty())
                return;
            Trie current = this;
            for (char ch : word.toCharArray()) {
                if(!current.childs.containsKey(ch))
                    current.childs.put(ch, new Trie());
                current = current.childs.get(ch);
            }
            current.setEndOfWord(true, word);
        }
    }
}
