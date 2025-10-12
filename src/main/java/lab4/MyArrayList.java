package lab4;

import java.util.Collection;
import java.util.RandomAccess;

public class MyArrayList<E> implements RandomAccess, MyList<E> {
    private Object[] data;
    private final static int START_CAPACITY = 10;
    private int size;

    public MyArrayList() {
        size = 0;
        data = new Object[START_CAPACITY];
    }

    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity > data.length) {
            int newCapacity = Math.max(data.length + 10, requiredCapacity);
            Object[] newData = new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    @Override
    public void add(E e) {
        ensureCapacity(size + 1);
        data[size++] = e;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    @Override
    public void addAll(Collection<? extends E> c) {
        ensureCapacity(size + c.size());
        System.arraycopy(c.toArray(), 0, data, size, c.size());
        size += c.size();
    }

    @Override
    public void addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        ensureCapacity(size + c.size());
        System.arraycopy(data, index, data, index + c.size(), size - index);
        System.arraycopy(c.toArray(), 0, data, index, c.size());
        size += c.size();
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (E) data[index];
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        E removed = (E) data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null;
        return removed;
    }

    @Override
    public void set(int index, E element) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        data[index] = element;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if ((o == null && data[i] == null) || (o != null && o.equals(data[i]))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(data, 0, result, 0, size);
        return (E[]) result;
    }
}
