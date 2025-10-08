package lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {
    private MyLinkedList list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList();
    }

    @Test
    void testAddAndGet() {
        list.add("A");
        list.add("B");
        list.add("C");

        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    void testAddAtIndex() {
        list.add("A");
        list.add("C");
        list.add(1, "B");

        assertEquals(3, list.size());
        assertEquals("B", list.get(1));
    }

    @Test
    void testAddAll() {
        list.addAll(new Object[]{"A", "B", "C"});

        assertEquals(3, list.size());
        assertEquals("C", list.get(2));
    }

    @Test
    void testAddAllAtIndex() {
        list.add("X");
        list.add("Y");
        list.addAll(1, new Object[]{"A", "B", "C"});

        assertEquals(5, list.size());
        assertEquals("X", list.get(0));
        assertEquals("A", list.get(1));
        assertEquals("C", list.get(3));
        assertEquals("Y", list.get(4));
    }

    @Test
    void testRemove() {
        list.addAll(new Object[]{"A", "B", "C"});
        Object removed = list.remove(1);

        assertEquals("B", removed);
        assertEquals(2, list.size());
        assertEquals("C", list.get(1));
    }

    @Test
    void testSet() {
        list.addAll(new Object[]{"A", "B", "C"});
        list.set(1, "Z");

        assertEquals("Z", list.get(1));
    }

    @Test
    void testIndexOf() {
        list.addAll(new Object[]{"A", "B", "C", "B"});

        assertEquals(1, list.indexOf("B"));
        assertEquals(-1, list.indexOf("X"));
    }

    @Test
    void testToArray() {
        list.addAll(new Object[]{"A", "B", "C"});
        Object[] arr = list.toArray();

        assertArrayEquals(new Object[]{"A", "B", "C"}, arr);
    }

    @Test
    void testEnsureCapacityExpansion() {
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        assertEquals(20, list.size());
        assertEquals(0, list.get(0));
        assertEquals(19, list.get(19));
    }

    @Test
    void testEmptyList() {
        assertEquals(0, list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }
}

