package lab2;

public class MyCache {
    private int maxCapacity;
    private Entry[] table;
    private int size;

    private static class Entry {
        Object key;
        Object value;
        long timestamp;

        Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
            this.timestamp = System.nanoTime();
        }
    }

    public MyCache(int maxCapacity) {
        if (maxCapacity <= 0) throw new IllegalArgumentException();
        this.maxCapacity = maxCapacity;
        this.table = new Entry[maxCapacity];
        this.size = 0;
    }

    public Object get(Object key) {
        if (key == null) throw new NullPointerException();
        for (Entry e : table) {
            if (e != null && e.key.equals(key)) {
                e.timestamp = System.nanoTime();
                return e.value;
            }
        }
        return null;
    }
    public Object put(Object key, Object value) {
        if (key == null || value == null) throw new NullPointerException();
        for (Entry e : table) {
            if (e != null && e.key.equals(key)) {
                Object old = e.value;
                e.value = value;
                e.timestamp = System.nanoTime();
                return old;
            }
        }
        if (size < maxCapacity) {
            for (int i = 0; i < table.length; i++) {
                if (table[i] == null) {
                    table[i] = new Entry(key, value);
                    size++;
                    return null;
                }
            }
        }
        evictOldest();
        return put(key, value);
    }

    public Object remove(Object key) {
        if (key == null) throw new NullPointerException();
        for (int i = 0; i < table.length; i++) {
            Entry e = table[i];
            if (e != null && e.key.equals(key)) {
                Object old = e.value;
                table[i] = null;
                size--;
                return old;
            }
        }
        return null;
    }

    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    public int size() {
        return size;
    }

    private void evictOldest() {
        int oldestIndex = -1;
        long oldestTime = Long.MAX_VALUE;
        for (int i = 0; i < table.length; i++) {
            Entry e = table[i];
            if (e != null && e.timestamp < oldestTime) {
                oldestTime = e.timestamp;
                oldestIndex = i;
            }
        }
        if (oldestIndex != -1) {
            table[oldestIndex] = null;
            size--;
        }
    }

}
