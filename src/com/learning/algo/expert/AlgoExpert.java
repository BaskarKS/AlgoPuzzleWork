import java.util.*;

public class AlgoExpert {
// -----------------------------MINIMUM REWARDS------------------------------------------- //

    //Time Complexity : O(n) , Space Complexity : O(n)
    public static int minRewards(int[] scores) {
        // Write your code here.
        int[] rewards = new int[scores.length];
        for (int index = 0; index < rewards.length; index++)
            rewards[index] = 1;

        for (int i = 1; i < scores.length; i++)
            if (scores[i] > scores[i - 1])
                rewards[i] = rewards[i - 1] + 1;

        for (int j = scores.length - 2; j >= 0; j--)
            if (scores[j] > scores[j + 1])
                rewards[j] = Math.max(rewards[j], rewards[j + 1] + 1);
        return sum(rewards);
    }

    public static int sum(int[] array) {
        int sum = 0;
        for (int eachVal : array)
            sum += eachVal;
        return sum;
    }

    // -----------------------------BINARY SEARCH------------------------------------------- //
    // Iterative Solution => Time Complexity - O(log n) , Space Complexity - O(1)
    public static int binarySearch(int[] array, int target) {
        // Write your code here.
        int start = 0;
        int end = array.length - 1;
        int ret = -1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (array[mid] == target) {
                ret = mid;
                break;
            }
            if (array[mid] > target)
                end = mid - 1;
            else
                start = mid + 1;
        }
        return ret;
    }

    //-------------------BREATH FIRST SEARCH----------------------------------------//
    static class Node {
        String name;
        List<Node> children = new ArrayList<Node>();

        public Node(String name) {
            this.name = name;
        }

        public List<String> breadthFirstSearch(List<String> array) {
            // Write your code here.
            Queue<Node> processNodes = new ArrayDeque();
            processNodes.add(this);
            while (!processNodes.isEmpty()) {
                Node node = processNodes.poll();
                array.add(node.name);
                for (Node children : node.children)
                    processNodes.add(children);
            }
            return array;
        }

        public Node addChild(String name) {
            Node child = new Node(name);
            children.add(child);
            return this;
        }
    }

    //Time Complexity : O(N * M^2 * N log n)
    // ------------------------- Longest String Chain ----------------------- //
    public static List<String> sortStringOnLength(List<String> array) {
        Comparator<String> compareStrings = new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                int ret = 1;
                if (s.length() == t1.length())
                    ret = 0;
                else if (s.length() < t1.length())
                    ret = -1;
                else
                    ret = 1;
                return ret;
            }
        };
        Object[] wordsInObject = array.toArray();
        String[] words = Arrays.copyOf(wordsInObject, wordsInObject.length, String[].class);
        Arrays.sort(words, compareStrings);
        return Arrays.asList(words);
    }

    static class StringEntry {
        String nextString = "";
        int count = 1;

        public String getNextString() {
            return nextString;
        }

        public int getCount() {
            return count;
        }

        public void setNextString(String nextString) {
            this.nextString = nextString;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static List<String> longestStringChain(List<String> strings) {
        // Write your code here.
        List<String> sortedList = new ArrayList<>(strings);
        sortedList.sort(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                int ret = 1;
                if (s.length() == t1.length())
                    ret = 0;
                else if (s.length() < t1.length())
                    ret = -1;
                else
                    ret = 1;
                return ret;
            }
        });
        //List<String> sortedList = sortStringOnLength(strings);
        Map<String, StringEntry> longestStringMap = new HashMap<>();
        for (String string : sortedList) {
            longestStringMap.put(string, new StringEntry());
            findLongestStringChain(string, longestStringMap);
        }
        //calculate the output string list
        return getFinalList(longestStringMap);
    }

    public static List<String> getFinalList(Map<String, StringEntry> longestStringMap) {
        List<String> outputList = new ArrayList<>();
        int maxCount = 0;
        String maxWord = "";
        for (String word : longestStringMap.keySet()) {
            int currCount = longestStringMap.get(word).getCount();
            if (currCount > maxCount) {
                maxCount = currCount;
                maxWord = word;
            }
        }
        while (!maxWord.isEmpty()) {
            outputList.add(maxWord);
            maxWord = longestStringMap.get(maxWord).getNextString();
        }
        return outputList.size() == 1 ? new ArrayList<>() : outputList;
    }

    public static void findLongestStringChain(String string, Map<String, StringEntry> longestStringMap) {
        for (int index = 0; index < string.length(); index++) {
            String subString = getSubString(string, index);
            if (!longestStringMap.containsKey(subString))
                continue;
            StringEntry currentStringObj = longestStringMap.get(string);
            StringEntry subStringObj = longestStringMap.get(subString);
            if (subStringObj.getCount() + 1 > currentStringObj.getCount()) {
                currentStringObj.setNextString(subString);
                currentStringObj.setCount(subStringObj.getCount() + 1);
                longestStringMap.put(string, currentStringObj);
            }
        }
    }

    public static String getSubString(String string, int index) {
        return string.substring(0, index) + string.substring(index + 1);
    }

    // ------------------------- Longest String Chain ----------------------- //

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

    // ---------------------------- SUB ARRAY SORT ----------------------------------------- //
    // Time Complexity O(n) - Space Complexity O(1)
    public static int[] subArraySort(int[] array) {
        // Write your code here.
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        boolean valid = true;
        int arrLength = array.length - 1;
        //To find the range of wrong values
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && i < arrLength) {
                valid = array[i] >= array[i - 1] && array[i] <= array[i + 1];
            } else {
                valid = (i == 0) ? array[i] <= array[i + 1] : array[i] >= array[i - 1];
            }
            if (!valid) {
                if (i < min)
                    min = i;
                else
                    max = i;
                valid = true;
            }
        }
        //couldn't find a invalid range / subarray in array
        if (min > max)
            return new int[]{-1, -1};
        //finding minimum and maximum value in range
        int valueMin = Integer.MAX_VALUE, valueMax = Integer.MIN_VALUE;
        for (int loop = min; loop <= max; loop++) {
            if (array[loop] < valueMin)
                valueMin = array[loop];
            if (array[loop] > valueMax)
                valueMax = array[loop];
        }
        //finding the right index of the minimum and maximum values are supposed to be
        int minIdx = 0, maxIdx = array.length - 1;
        while (minIdx < array.length) {
            if (array[minIdx] <= valueMin) {
                minIdx++;
                continue;
            }
            break;
        }
        while (maxIdx >= 0) {
            if (array[maxIdx] >= valueMax) {
                maxIdx--;
                continue;
            }
            break;
        }
        return new int[]{minIdx, maxIdx};
    }
    // ---------------------------- SUB ARRAY SORT ----------------------------------------- //
    // ---------------------------- Numbers in Pi ----------------------------------------- //
    static class StringInfo {
        int length;
        String string;

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public void setString(String string) {
            this.string = string;
        }

        public String getString() {
            return this.string;
        }

        public StringInfo(int length, String string) {
            this.length = length;
            this.string = string;
        }
    }

    public static int numbersInPiBetter(String pi, String[] numbers) {
        HashMap<Integer, List<StringInfo>> indexSizeData = new HashMap<>();
        for (String each : numbers) {
            int index = pi.indexOf(each);
            int length = each.length();
            if (!indexSizeData.containsKey(index))
                indexSizeData.put(index, new ArrayList<>());
            indexSizeData.get(index).add(new StringInfo(length, each));
            while (index != -1) {
                index = pi.indexOf(each, index + 1);
                if (index != -1) {
                    if (!indexSizeData.containsKey(index))
                        indexSizeData.put(index, new ArrayList<>());
                    indexSizeData.get(index).add(new StringInfo(length, each));
                }
            }
        }
        List<StringInfo> startStrings = indexSizeData.get(0);
        int min = Integer.MAX_VALUE;
        for (StringInfo startString : startStrings) {
            int count = 0;
            String finalString = startString.string;
            int index = startString.length;
            while (index <= pi.length() && indexSizeData.containsKey(index)) {
                finalString += indexSizeData.get(index).get(0).getString();
                index += indexSizeData.get(index).get(0).getLength();
                count++;
            }
            if (finalString.equals(pi) && count < min)
                min = count;
        }
        return (min == Integer.MAX_VALUE) ? -1 : min;
    }
    // O(n^3 + m)time ( n - n time for prefix, and again n times for suffix, n time for slicing(subString)
    // n is no of digits in pi, m is no of favourite numbers
    //O(n + m) space - n is for space of cache for n numbers, m is size of numbers in HashSet

    //Forward Way - big to solving smaller chunks
    public static int numbersInPiBest1(String pi, String[] numbers) {
        Set<String> numberData = new HashSet<>();
        for (String number : numbers)
            numberData.add(number);
        HashMap<Integer,Integer> cache = new HashMap<>();
        int minimum = getMinimumSpaces(pi, cache, numberData, 0);
        return (minimum == Integer.MAX_VALUE) ? -1 : minimum;
    }
    // Reverse way - solving smaller chunks first and finding solution for bigger then
    public static int numbersInPiBest2(String pi, String[] numbers) {
        Set<String> numberData = new HashSet<>();
        for (String number : numbers)
            numberData.add(number);
        HashMap<Integer,Integer> cache = new HashMap<>();
        for (int i = pi.length() - 1; i >= 0; i--)
            getMinimumSpaces(pi, cache, numberData, i);
        return (cache.get(0) == Integer.MAX_VALUE) ? -1 : cache.get(0);
    }
    public static int getMinimumSpaces(String pi, HashMap<Integer,Integer> cache, Set<String> numberData, int idx ) {
        //Base condition
        if (idx == pi.length())
            return -1;
        if (cache.containsKey(idx))
            return cache.get(idx);
        int minimum = Integer.MAX_VALUE;
        for (int index = idx; index < pi.length(); index++) {
            String prefix = pi.substring(idx, index + 1);
            if (numberData.contains(prefix)) {
                int minimumSuffix = getMinimumSpaces(pi, cache, numberData, index + 1);
                if (minimumSuffix == Integer.MAX_VALUE)
                    minimum = Math.min(minimum, minimumSuffix);
                else
                    minimum = Math.min(minimum, minimumSuffix + 1);
            }
        }
        cache.put(idx, minimum);
        return cache.get(idx);
    }
    public static void main(String[] args) {
        int[] sortedArray = {0, 1, 21, 33, 45, 45, 61, 71, 72, 73};
        int itemToSearch = 33;

        Node root = new Node("A");
        root.addChild("B").addChild("C").addChild("D").addChild("E");
        root.children.get(1).addChild("F");
        List<String> bfs = root.breadthFirstSearch(new ArrayList<String>());
        System.out.println(bfs.toString());

        List<String> wordList = new ArrayList<>();
        wordList.add("abde");
        wordList.add("abc");
        wordList.add("abd");
        wordList.add("abcde");
        wordList.add("ade");
        wordList.add("ae");
        wordList.add("1abde");
        wordList.add("abcdef");
        longestStringChain(wordList);

        BST binarySearchTree = new BST(10);
        binarySearchTree.insert(5).insert(15).insert(5).insert(2).insert(14).insert(22);
        binarySearchTree.contains(5);
    }
}

