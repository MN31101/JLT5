package lab4;

import java.util.Arrays;

public class MyCache<K, V> {
    private final int maxCapacity;
    private Entry<K, V>[] table;
    private int size;

    private static class Entry<K, V> {
        K key;
        V value;
        long timestamp;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.timestamp = System.nanoTime();
        }
    }

    public MyCache(int maxCapacity) {
        if (maxCapacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.maxCapacity = maxCapacity;
        this.table = (Entry<K, V>[]) new Entry[maxCapacity];
        this.size = 0;
    }

    public V get(K key) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        for (Entry<K, V> e : table) {
            if (e != null && e.key.equals(key)) {
                e.timestamp = System.nanoTime(); // refresh usage time
                return e.value;
            }
        }
        return null;
    }

    public V put(K key, V value) {
        if (key == null || value == null) throw new NullPointerException("Key or value cannot be null");

        for (Entry<K, V> e : table) {
            if (e != null && e.key.equals(key)) {
                V old = e.value;
                e.value = value;
                e.timestamp = System.nanoTime();
                return old;
            }
        }

        if (size < maxCapacity) {
            for (int i = 0; i < table.length; i++) {
                if (table[i] == null) {
                    table[i] = new Entry<>(key, value);
                    size++;
                    return null;
                }
            }
        }

        evictOldest();
        return put(key, value);
    }

    public V remove(K key) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> e = table[i];
            if (e != null && e.key.equals(key)) {
                V old = e.value;
                table[i] = null;
                size--;
                return old;
            }
        }
        return null;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    private void evictOldest() {
        int oldestIndex = -1;
        long oldestTime = Long.MAX_VALUE;
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> e = table[i];
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
