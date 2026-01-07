package aa.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinHeapTest {
    private MinHeap tas;

    @Before
    public void setUp() throws Exception {
        tas = new MinHeap(6);
    }

    @Test
    public void isEmpty() {
        assertTrue(tas.isEmpty());
    }

    @Test
    public void insert() {
        tas.insert(2, 61);
        assertFalse(tas.isEmpty());
        assertEquals(1, tas.getSize());
    }


    @Test
    public void extractMin() {
        tas.insert(2, 61);
        tas.insert(3, 50);
        tas.insert(6, 28);
        tas.insert(4, 30);

        int minVertex = tas.extractMin();
        assertEquals(6, minVertex);
    }

    @Test
    public void decreaseKey() {
        tas.insert(2, 61);
        tas.insert(3, 50);
        tas.insert(6, 28);
        tas.insert(4, 30);

        int minVertex = tas.extractMin();
        assertEquals(6, minVertex);
        //deminuer la distance du sommet 2 par exemple
        tas.decreaseKey(2, 12);
        int newMinVertex = tas.extractMin();
        assertEquals(2, newMinVertex);
    }

    @Test
    public void contains() {
        tas.insert(2, 61);
        tas.insert(3, 50);
        tas.insert(6, 28);
        tas.insert(4, 30);

        assertTrue(tas.contains(6));
        assertFalse(tas.contains(1));
        assertFalse(tas.contains(5));
    }
}