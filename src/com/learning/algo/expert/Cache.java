import java.util.HashMap;
import java.util.Map;

public class Cache {
    static class LRUCache {
        int maxSize;
        lruCache cache = null;
        public LRUCache(int maxSize) {
            this.maxSize = maxSize > 1 ? maxSize : 1;
            cache = new lruCache(this.maxSize);
        }
        //Time Complexity : O(1) and Space Complexity : O(1)
        public void insertKeyValuePair(String key, int value) {
            // Write your code here.
            cache.insertData(key,value);
        }
        //Time Complexity : O(1) and Space Complexity : O(1)
        public LRUResult getValueFromKey(String key) {
            // Write your code here.
            return cache.getValFromKey(key);
        }
        //Time Complexity : O(1) and Space Complexity : O(1)
        public String getMostRecentKey() {
            // Write your code here.
            return cache.getRecentKey();
        }
    }

    static class LRUResult {
        boolean found;
        int value;

        public LRUResult(boolean found, int value) {
            this.found = found;
            this.value = value;
        }
    }

    static class lruCache {
        Map<String, LinkList.Node> map = null;
        LinkList list = null;
        int size = 1;
        int length = 0;
        public lruCache(int size) {
            list = new LinkList();
            map = new HashMap<>();
            this.size = size;
        }
        public void insertData(String key, int value) {
            if (!map.containsKey(key)) {
                if (length == size)
                    removeElement();
                LinkList.Node node = new LinkList.Node(key, value);
                map.put(key, node);
                list.add(node);
                length++;
            } else {
                LinkList.Node node = map.get(key);
                list.updateNode(node, value);
            }
        }
        public LRUResult getValFromKey(String key) {
            // Write your code here.
            LRUResult result = new LRUResult(false, -1);
            if (!map.containsKey(key))
                return result;
            result.found = true;
            result.value = list.readNode(map.get(key));
            return result;
        }
        public String getRecentKey() {
            // Write your code here.
            if (list.tail == null)
                return null;
            return list.tail.key;
        }
        public void removeElement() {
            String key = list.removeHead();
            if (key != null) {
                map.remove(key);
                length--;
            }
        }
    }
    static class LinkList {
        Node head;
        Node tail;
        public LinkList() {
        }
        static class Node {
            String key;
            int value;
            Node next;
            Node prev;
            public Node(String key, int value) {
                this.key = key;
                this.value = value;
                this.next = null;
                this.prev = null;
            }
        }
        public boolean add(Node node) {
            if (head == null) {
                head = tail = node;
            } else {
                Node newNode = node;
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            return true;
        }
        public boolean updateNode(Node node, int value) {
            if (node == null)
                return false;
            node.value = value;
            updateTail(node);
            return true;
        }
        public String removeHead() {
            if (head == null)
                return null;
            Node temp = head;
            head = head.next;
            return temp.key;
        }
        public int readNode(Node node) {
            if (node == null)
                return -1;
            updateTail(node);
            return node.value;
        }
        public void updateTail(Node currNode) {
            if (currNode == null)
                return;

            Node previousNode = currNode.prev;
            Node nextNode = currNode.next;

            if (previousNode == null && nextNode == null)
                return;

            if (previousNode == null) {
                //current Node is the head
                head = head.next;
                head.prev = null; //next item is made head
                tail.next = currNode;
                currNode.prev = tail;
                currNode.next = null;
                tail = currNode;
                return;
            }
            if (nextNode == null) {
                return;
            }
            previousNode.next = currNode.next;
            nextNode.prev = previousNode;
            tail.next = currNode;
            currNode.prev = tail;
            currNode.next = null;
            tail = currNode;
        }
    }
}
