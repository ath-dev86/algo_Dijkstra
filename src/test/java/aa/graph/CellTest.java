package aa.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CellTest {
    private Cell cell;

    @org.junit.Before
    public void setUp() throws Exception {
        cell = new Cell(2, 61);
    }

    @org.junit.Test
    public void getVertex() {
        //tester le numéro du sommet voisin du sommet actuel
        assertEquals(2, cell.getVertex());
    }

    @org.junit.Test
    public void getCost() {
        //tester le coût de l'arête
        assertEquals(61, cell.getCost());
    }

    @org.junit.Test
    public void getNext() {
        //tester la prochaine cellule
        assertNull(cell.getNext());
    }

    @org.junit.Test
    public void setNext() {
        //tester la prochaine cellule qui est differente de null
        Cell cell1 = new Cell(3, 50);
        cell.setNext(cell1);
        assertNotNull(cell.getNext());
        assertEquals(3, cell.getNext().getVertex());
        assertEquals(50, cell.getNext().getCost());
        assertNull(cell.getNext().getNext());
    }
}