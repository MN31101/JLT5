package lab4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyCacheTest {

    private MyCache<String, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new MyCache<>(3);
    }

    @Test
    void testPutAndGet() {
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);

        assertEquals(3, cache.size());
        assertEquals(1, cache.get("A"));
        assertEquals(2, cache.get("B"));
        assertEquals(3, cache.get("C"));
    }

    @Test
    void testUpdateExistingKey() {
        cache.put("A", 1);
        cache.put("A", 100);
        assertEquals(1, cache.size());
        assertEquals(100, cache.get("A"));
    }

    @Test
    void testEvictionOfOldest() throws InterruptedException {
        cache.put("A", 1);
        Thread.sleep(1);
        cache.put("B", 2);
        Thread.sleep(1);
        cache.put("C", 3);
        Thread.sleep(1);
        cache.put("D", 4); // should evict oldest ("A")

        assertNull(cache.get("A"));
        assertEquals(3, cache.size());
        assertEquals(4, cache.get("D"));
    }

    @Test
    void testRemove() {
        cache.put("X", 10);
        cache.put("Y", 20);
        assertEquals(10, cache.remove("X"));
        assertEquals(1, cache.size());
        assertNull(cache.get("X"));
        assertEquals(20, cache.get("Y"));
    }

    @Test
    void testClear() {
        cache.put("A", 1);
        cache.put("B", 2);
        cache.clear();
        assertEquals(0, cache.size());
        assertNull(cache.get("A"));
        assertNull(cache.get("B"));
    }

    @Test
    void testNullKeyOrValueThrowsException() {
        assertThrows(NullPointerException.class, () -> cache.put(null, 1));
        assertThrows(NullPointerException.class, () -> cache.put("A", null));
        assertThrows(NullPointerException.class, () -> cache.get(null));
        assertThrows(NullPointerException.class, () -> cache.remove(null));
    }
}
