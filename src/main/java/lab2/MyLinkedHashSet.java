package lab2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class


MyLinkedHashSet {

    private Node head;
    private Node tail;

    private static class Node {
        final int hash;
        final Object value;
        Node next;

        private Node(int hash, Object value, Node next) {
            this.hash = hash;
            this.value = value;
            this.next = next;
        }

    }

    public void set(Object value) {

    }

    public Object get(Object key) {

        return null;
    }

    public Node getNode() {
        return null;
    }
}
