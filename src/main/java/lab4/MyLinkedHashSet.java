package lab4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedHashSet<E> {

    private Node<E>[] table;
    private Node<E> head, tail;
    private int size;
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private static class Node<E> {
        E value;
        Node<E> nextInBucket;
        Node<E> next, prev;

        Node(E value) {
            this.value = value;
        }
    }

    public MyLinkedHashSet() {
        this.table = new Node[INITIAL_CAPACITY];
        this.size = 0;
    }

    public MyLinkedHashSet(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException();
        this.table = new Node[capacity];
        this.size = 0;
    }

    private int hash(E value) {
        return (value == null) ? 0 : (value.hashCode() & 0x7fffffff) % table.length;
    }

    public boolean add(E value) {
        if (contains(value)) return false;
        resizeIfNeeded();
        Node<E> newNode = new Node<>(value);

        int index = hash(value);
        newNode.nextInBucket = (Node<E>) table[index];
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
            Node<E>[] oldTable = table;
            table = (Node<E>[]) new Node[oldTable.length * 2];

            Node<E> current = head;
            while (current != null) {
                int index = hash(current.value);
                current.nextInBucket = table[index];
                table[index] = current;
                current = current.next;
            }
        }
    }


    public boolean contains(E value) {
        int index = hash(value);
        Node<E> current = table[index];
        while (current != null) {
            if ((current.value == null && value == null) || (current.value != null && current.value.equals(value))) {
                return true;
            }
            current = current.nextInBucket;
        }
        return false;
    }

    public boolean remove(E value) {
        int index = hash(value);
        Node<E> current = table[index];
        Node<E> prevInBucket = null;

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

    public E[] toArray() {
        Object[] array = new Object[size];
        Node<E> current = head;
        int i = 0;
        while (current != null) {
            array[i++] = current.value;
            current = current.next;
        }
        return (E[]) array;
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

            public boolean hasNext() {
                return current != null;
            }

            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E value = current.value;
                current = current.next;
                return value;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}