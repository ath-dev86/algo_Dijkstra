package aa.graph;

import static org.junit.Assert.*;

public class CellTest {
    private Cell cell;

    @org.junit.Before
    public void setUp() throws Exception {
        cell = new Cell(2, 61);
    }

    @org.junit.Test
    public void getVertex() {
        assertEquals(2, cell.getVertex());
    }

    @org.junit.Test
    public void getCost() {
        assertEquals(61, cell.getCost());
    }

    @org.junit.Test
    public void getNext() {
        assertNull(cell.getNext());
    }

    @org.junit.Test
    public void setNext() {
        Cell cell1 = new Cell(3, 50);
        cell.setNext(cell1);
        assertNotNull(cell.getNext());
        assertEquals(3, cell.getNext().getVertex());
        assertEquals(50, cell.getNext().getCost());
        assertNull(cell.getNext().getNext());
    }
}