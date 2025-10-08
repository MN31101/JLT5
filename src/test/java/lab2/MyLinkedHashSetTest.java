package lab2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedHashSetTest {

    private MyLinkedHashSet set;

    @BeforeEach
    void setUp() {
        set = new MyLinkedHashSet();
    }

    @Test
    void testAddAndContains() {
        assertTrue(set.add("A"));
        assertTrue(set.add("B"));
        assertTrue(set.add("C"));

        assertFalse(set.add("A"));

        assertTrue(set.contains("A"));
        assertTrue(set.contains("B"));
        assertTrue(set.contains("C"));
        assertFalse(set.contains("D"));
    }

    @Test
    void testSize() {
        assertEquals(0, set.size());
        set.add("X");
        set.add("Y");
        assertEquals(2, set.size());
        set.add("X"); // duplicate
        assertEquals(2, set.size());
    }

    @Test
    void testRemove() {
        set.add("A");
        set.add("B");
        set.add("C");

        assertTrue(set.remove("B"));
        assertFalse(set.contains("B"));
        assertEquals(2, set.size());

        assertFalse(set.remove("D"));

        assertTrue(set.remove("A"));
        assertTrue(set.remove("C"));
        assertEquals(0, set.size());
    }

    @Test
    void testToArray() {
        set.add("A");
        set.add("B");
        set.add("C");

        Object[] array = set.toArray();
        assertArrayEquals(new Object[]{"A", "B", "C"}, array);
    }

    @Test
    void testIterator() {
        set.add("A");
        set.add("B");
        set.add("C");

        Iterator<Object> it = set.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertTrue(it.hasNext());
        assertEquals("B", it.next());
        assertTrue(it.hasNext());
        assertEquals("C", it.next());
        assertFalse(it.hasNext());

        Iterator<Object> it2 = set.iterator();
        it2.next();
        assertThrows(UnsupportedOperationException.class, it2::remove);
    }

    @Test
    void testInsertionOrder() {
        set.add("first");
        set.add("second");
        set.add("third");

        Iterator<Object> it = set.iterator();
        assertEquals("first", it.next());
        assertEquals("second", it.next());
        assertEquals("third", it.next());
    }
}
