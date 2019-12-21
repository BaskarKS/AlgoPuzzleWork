import java.util.HashMap;
import java.util.Map;

public class AlgoExpertLRUCache {
    static class LRUCache {
        Map<String, DoublyLinkedListNode> cache = new HashMap<>();
        int maxSize;
        int currentSize = 0;
        DoublyLinkedList listOfMostRecent = new DoublyLinkedList();

        public LRUCache(int maxSize) {
            this.maxSize = maxSize > 1 ? maxSize : 1;
        }
        // O(1) Time // O(1) Space
        public void insertKeyValuePair(String key, int value) {
            if (!cache.containsKey(key)) {
                if (currentSize == maxSize) {
                   evictLeastRecent();
                } else {
                    currentSize++;
                }
                cache.put(key, new DoublyLinkedListNode(key, value));
            } else {
                replaceKey(key, value);
            }
            updateMostRecent(cache.get(key));
        }
        public void evictLeastRecent() {
            String keyToRemove = listOfMostRecent.tail.key;
            listOfMostRecent.removeTail();
            cache.remove(keyToRemove);
        }
        public void replaceKey(String key, int value) {
            if (!cache.containsKey(key)) {
                return;
            }
            cache.get(key).value = value;
        }
        // O(1) Time // O(1) Space
        public String getMostRecentKey() {
            return listOfMostRecent.head.key;
        }
        // O(1) Time // O(1) Space
        public LRUResult getValueFromKey(String key) {
            if (!cache.containsKey(key)) {
               return new LRUResult(false, -1);
            }
            updateMostRecent(cache.get(key));
            return new LRUResult(true, cache.get(key).value);
        }
        public void updateMostRecent(DoublyLinkedListNode node) {
            listOfMostRecent.setHeadTo(node);
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

    static class DoublyLinkedList {
        DoublyLinkedListNode head = null;
        DoublyLinkedListNode tail = null;

        public void setHeadTo(DoublyLinkedListNode node) {
            if (head == node) {
                return;
            }
            else if (head == null) {
                head = node;
                tail = node;
            }
            else if (head == tail) {
                tail.prev = node;
                head = node;
                head.next = tail;
            }
            else {
                if (tail == node) {
                   removeTail();
                }
                node.removeBindings();
                head.prev = node;
                node.next = head;
                head = node;
            }

        }
        public void removeTail() {
            if (tail == null) {
                return;
            }
            if (tail == head) {
                head = null;
                tail = null;
                return;
            }
            tail = tail.prev;
            tail.next = null;
        }
    }
    static class DoublyLinkedListNode {
        String key;
        int value;
        DoublyLinkedListNode next = null;
        DoublyLinkedListNode prev = null;
        public DoublyLinkedListNode(String key, int value) {
            this.key = key;
            this.value = value;
        }
        public void removeBindings() {
            if (prev != null) {
                prev.next = next;
            }
            if (next != null) {
               next.prev = prev;
            }
            prev = null;
            next = null;
        }
    }
}
