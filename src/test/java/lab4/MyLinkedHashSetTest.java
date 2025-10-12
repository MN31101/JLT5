package lab4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

public class MyLinkedHashSetTest {

    private MyLinkedHashSet<String> set;

    @BeforeEach
    void setUp() {
        set = new MyLinkedHashSet<>();
    }

    @Test
    void testAddAndContains() {
        assertTrue(set.add("A"));
        assertTrue(set.add("B"));
        assertFalse(set.add("A")); // duplicate
        assertTrue(set.contains("A"));
        assertTrue(set.contains("B"));
        assertFalse(set.contains("C"));
        assertEquals(2, set.size());
    }

    @Test
    void testRemove() {
        set.add("X");
        set.add("Y");
        set.add("Z");

        assertTrue(set.remove("Y"));
        assertFalse(set.contains("Y"));
        assertEquals(2, set.size());

        assertFalse(set.remove("Unknown"));
        assertTrue(set.contains("X"));
    }

    @Test
    void testOrderPreserved() {
        set.add("1");
        set.add("2");
        set.add("3");

        Object[] arr = set.toArray();
        assertArrayEquals(new Object[]{"1", "2", "3"}, arr);
    }

    @Test
    void testIterator() {
        set.add("A");
        set.add("B");
        set.add("C");

        Iterator<String> it = set.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertEquals("B", it.next());
        assertEquals("C", it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void testResize() {
        MyLinkedHashSet<Integer> bigSet = new MyLinkedHashSet<>(2);
        for (int i = 0; i < 100; i++) {
            assertTrue(bigSet.add(i));
        }
        assertEquals(100, bigSet.size());
        for (int i = 0; i < 100; i++) {
            assertTrue(bigSet.contains(i));
        }
    }

    @Test
    void testToArray() {
        set.add("alpha");
        set.add("beta");
        set.add("gamma");

        Object[] arr = set.toArray();
        assertEquals(3, arr.length);
        assertArrayEquals(new Object[]{"alpha", "beta", "gamma"}, arr);
    }
}
