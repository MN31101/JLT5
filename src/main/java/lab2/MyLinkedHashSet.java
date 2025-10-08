package lab2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedHashSet {

    private Node[] table;
    private Node head, tail;
    private int size;
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private static class Node {
        Object value;
        Node nextInBucket;
        Node next, prev;

        Node(Object value) {
            this.value = value;
        }
    }

    public MyLinkedHashSet() {
        table = new Node[INITIAL_CAPACITY];
        head = tail = null;
        size = 0;
    }

    private int hash(Object value) {
        return (value == null) ? 0 : (value.hashCode() & 0x7fffffff) % table.length;
    }

    public boolean add(Object value) {
        if (contains(value)) return false;
        resizeIfNeeded();
        Node newNode = new Node(value);

        int index = hash(value);
        newNode.nextInBucket = table[index];
        table[index] = newNode;

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;
        return true;
    }

    private void resizeIfNeeded() {
        if (size >= table.length * LOAD_FACTOR) {

            table = new Node[table.length * 2];
            Node current = head;
            while (current != null) {
                int index = hash(current.value);
                current.nextInBucket = table[index];
                table[index] = current;
                current = current.next;
            }
        }
    }

    public boolean contains(Object value) {
        int index = hash(value);
        Node current = table[index];
        while (current != null) {
            if ((current.value == null && value == null) || (current.value != null && current.value.equals(value))) {
                return true;
            }
            current = current.nextInBucket;
        }
        return false;
    }

    public boolean remove(Object value) {
        int index = hash(value);
        Node current = table[index];
        Node prevInBucket = null;

        while (current != null) {
            if ((current.value == null && value == null) || (current.value != null && current.value.equals(value))) {
                if (prevInBucket != null) {
                    prevInBucket.nextInBucket = current.nextInBucket;
                } else {
                    table[index] = current.nextInBucket;
                }

                if (current.prev != null) current.prev.next = current.next;
                else head = current.next;

                if (current.next != null) current.next.prev = current.prev;
                else tail = current.prev;

                size--;
                return true;
            }
            prevInBucket = current;
            current = current.nextInBucket;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public Object[] toArray() {
        Object[] array = new Object[size];
        Node current = head;
        int i = 0;
        while (current != null) {
            array[i++] = current.value;
            current = current.next;
        }
        return array;
    }

    public Iterator iterator() {
        return new Iterator() {
            private Node current = head;

            public boolean hasNext() {
                return current != null;
            }

            public Object next() {
                if (!hasNext()) throw new NoSuchElementException();
                Object value = current.value;
                current = current.next;
                return value;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}