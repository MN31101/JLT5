package lab2;

import java.util.RandomAccess;

public class MyArrayList implements RandomAccess, MyList {
    private Object[] data;
    private final static int START_CAPACITY = 10;
    private int size;

    public MyArrayList() {
        data = new Object[START_CAPACITY];
        size = 0;
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
    public void add(Object e) {
        ensureCapacity(size + 1);
        data[size++] = e;
    }

    @Override
    public void add(int index, Object element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    @Override
    public void addAll(Object[] c) {
        ensureCapacity(size + c.length);
        System.arraycopy(c, 0, data, size, c.length);
        size += c.length;
    }

    @Override
    public void addAll(int index, Object[] c) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        ensureCapacity(size + c.length);
        System.arraycopy(data, index, data, index + c.length, size - index);
        System.arraycopy(c, 0, data, index, c.length);
        size += c.length;
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return data[index];
    }

    @Override
    public Object remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Object removed = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null;
        return removed;
    }

    @Override
    public void set(int index, Object element) {
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
    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(data, 0, result, 0, size);
        return result;
    }
}
