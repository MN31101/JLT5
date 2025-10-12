package lab4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {

    private MyLinkedList<String> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
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
        assertEquals(Arrays.asList("A", "B", "C"), Arrays.asList(java.util.Arrays.copyOf(list.toArray(), list.size(), String[].class)));
    }

    @Test
    void testAddAll() {
        list.add("X");
        list.addAll(Arrays.asList("A", "B", "C"));
        assertEquals(Arrays.asList("X", "A", "B", "C"), Arrays.asList(java.util.Arrays.copyOf(list.toArray(), list.size(), String[].class)));
    }

    @Test
    void testAddAllAtIndex() {
        list.add("A");
        list.add("D");
        list.addAll(1, Arrays.asList("B", "C"));
        assertEquals(Arrays.asList("A", "B", "C", "D"), Arrays.asList(java.util.Arrays.copyOf(list.toArray(), list.size(), String[].class)));
    }

    @Test
    void testRemove() {
        list.addAll(Arrays.asList("A", "B", "C"));
        assertEquals("B", list.remove(1));
        assertEquals(Arrays.asList("A", "C"), Arrays.asList(java.util.Arrays.copyOf(list.toArray(), list.size(), String[].class)));
    }

    @Test
    void testSet() {
        list.addAll(Arrays.asList("A", "B", "C"));
        list.set(1, "X");
        assertEquals("X", list.get(1));
    }

    @Test
    void testIndexOf() {
        list.addAll(Arrays.asList("A", "B", "C"));
        assertEquals(1, list.indexOf("B"));
        assertEquals(-1, list.indexOf("Z"));
    }

    @Test
    void testToArray() {
        list.addAll(Arrays.asList("A", "B"));
        Object[] arr = list.toArray();
        assertArrayEquals(new Object[]{"A", "B"}, arr);
    }

    @Test
    void testIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        list.add("A");
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(2, "X"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(1, "Y"));
    }
}
