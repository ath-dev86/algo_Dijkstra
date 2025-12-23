package aa.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedListTest {
    private LinkedList list;

    @Before
    public void setUp() throws Exception {
        list = new LinkedList();
    }

    @Test
    public void getSizeEmptyList() {
        assertNull(list.getHead());
        assertEquals(0, list.getSize());
    }

    @Test
    public void add() {
        list.add(2, 61);
        list.add(3, 50);
        list.add(4, 30);
        list.add(6, 28);
        assertNotNull(list.getHead());
        assertEquals(4, list.getSize());
        assertFalse(list.isEmpty() );
    }

    @Test
    public void getHead() {
        assertNull(list.getHead());
    }

    @Test
    public void isEmpty() {
        assertTrue(list.isEmpty());
    }
}