package lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyCacheTest {
    private MyCache cache;

    @BeforeEach
    void setUp() {
        cache = new MyCache(3);
    }

    @Test
    void testPutAndGet() {
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);

        assertEquals(1, cache.get("A"));
        assertEquals(2, cache.get("B"));
        assertEquals(3, cache.get("C"));
        assertEquals(3, cache.size());
    }

    @Test
    void testUpdateValue() {
        cache.put("A", 1);
        cache.put("A", 10);

        assertEquals(10, cache.get("A"));
        assertEquals(1, cache.size());
    }

    @Test
    void testRemove() {
        cache.put("A", 1);
        cache.put("B", 2);

        Object removed = cache.remove("A");
        assertEquals(1, removed);
        assertNull(cache.get("A"));
        assertEquals(1, cache.size());
    }

    @Test
    void testClear() {
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);

        cache.clear();
        assertEquals(0, cache.size());
        assertNull(cache.get("A"));
    }

    @Test
    void testEviction() throws InterruptedException {
        cache.put("A", 1);
        Thread.sleep(5);
        cache.put("B", 2);
        Thread.sleep(5);
        cache.put("C", 3);
        Thread.sleep(5);

        // All three added, cache is full
        assertEquals(3, cache.size());

        // Add one more, should evict the oldest ("A")
        cache.put("D", 4);

        assertNull(cache.get("A"), "Oldest entry should have been evicted");
        assertNotNull(cache.get("B"));
        assertNotNull(cache.get("C"));
        assertNotNull(cache.get("D"));
        assertEquals(3, cache.size());
    }

    @Test
    void testNullKeyOrValueThrows() {
        assertThrows(NullPointerException.class, () -> cache.put(null, 1));
        assertThrows(NullPointerException.class, () -> cache.put("A", null));
        assertThrows(NullPointerException.class, () -> cache.get(null));
        assertThrows(NullPointerException.class, () -> cache.remove(null));
    }
}
