package lab2;


public class MyLinkedList implements MyList {

    private Node firstNode;
    private Node lastNode;
    private int size = 0;

    private class Node {
        Node prev;
        Object current;
        Node next;

        Node(Node prev, Object current, Node next) {
            this.prev = prev;
            this.current = current;
            this.next = next;
        }
    }

    @Override
    public void add(Object e) {
        if (firstNode == null) {
            firstNode = lastNode = new Node(null, e, null);
        } else {
            Node newNode = new Node(lastNode, e, null);
            lastNode.next = newNode;
            lastNode = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, Object element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (index == size) {
            add(element);
        }
        Node current = getNode(index);
        Node newNode = new Node(current.prev, element, current);
        if (current.prev != null) {
            current.prev.next = newNode;
        } else {
            firstNode = newNode;
        }
        current.prev = newNode;

        size++;
    }

    @Override
    public void addAll(Object[] c) {
        for (Object e : c) {
            add(e);
        }
    }

    @Override
    public void addAll(int index, Object[] c) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        for (int i = 0; i < c.length; i++) {
            add(index + i, c[i]);
        }
    }

    @Override
    public Object get(int index) {
        return getNode(index).current;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node temp;
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
    public Object remove(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Node node = getNode(index);
        Object removed = node.current;

        Node prev = node.prev;
        Node next = node.next;

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
    public void set(int index, Object element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        getNode(index).current = element;

    }

    @Override
    public int indexOf(Object o) {
        Node current = firstNode;
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
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node current = firstNode;
        for (int i = 0; i < size; i++) {
            arr[i] = current.current;
            current = current.next;
        }
        return arr;
    }
}
