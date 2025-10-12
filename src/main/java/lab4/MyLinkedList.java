package lab4;


import java.util.Collection;

public class MyLinkedList<E> implements MyList<E> {

    private Node<E> firstNode;
    private Node<E> lastNode;
    private int size = 0;

    private static class Node<E> {
        Node<E> prev;
        E current;
        Node<E> next;

        Node(Node<E> prev, E current, Node<E> next) {
            this.prev = prev;
            this.current = current;
            this.next = next;
        }
    }

    @Override
    public void add(E e) {
        if (firstNode == null) {
            firstNode = lastNode = new Node<>(null, e, null);
        } else {
            Node<E> newNode = new Node<>(lastNode, e, null);
            lastNode.next = newNode;
            lastNode = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (index == size) {
            add(element);
        }
        Node<E> current = getNode(index);
        Node<E> newNode = new Node<>(current.prev, element, current);
        if (current.prev != null) {
            current.prev.next = newNode;
        } else {
            firstNode = newNode;
        }
        current.prev = newNode;

        size++;
    }

    @Override
    public void addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
    }

    @Override
    public void addAll(int index,Collection<? extends E> c) {
        Object[] a = c.toArray();
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        for (int i = 0; i < a.length; i++) {
            add(index + i, (E) a[i]);
        }
    }

    @Override
    public E get(int index) {
        return getNode(index).current;
    }

    private Node<E> getNode(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<E> temp;
        if (index < size / 2) {
            temp = firstNode;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = lastNode;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
        }
        return temp;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Node<E> node = getNode(index);
        E removed = node.current;

        Node<E> prev = node.prev;
        Node<E> next = node.next;

        if (prev != null) {
            prev.next = next;
        } else {
            firstNode = next;
        }

        if (next != null) {
            next.prev = prev;
        } else {
            lastNode = prev;
        }

        size--;
        return removed;
    }

    @Override
    public void set(int index, E element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        getNode(index).current = element;

    }

    @Override
    public int indexOf(E o) {
        Node<E> current = firstNode;
        for (int i = 0; i < size; i++) {
            if ((o == null && current.current == null) || (o != null && o.equals(current.current))) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E[] toArray() {
        Object[] arr = new Object[size];
        Node<E> current = firstNode;
        for (int i = 0; i < size; i++) {
            arr[i] = current.current;
            current = current.next;
        }
        return (E[]) arr;
    }
}
