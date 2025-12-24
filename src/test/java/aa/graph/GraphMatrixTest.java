package aa.graph;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GraphMatrixTest {
    private GraphMatrix graphMatrix;

    @Before
    public void setUp() throws Exception {
        graphMatrix = new GraphMatrix(6);
        //verifier tous les coûts sont à Integer.MAX_VALUE
        for(int i = 1; i <= 6; i++) {
            for(int j = 1; j <= 6; j++) {
                assertEquals(Integer.MAX_VALUE, graphMatrix.getCost(i, j));
            }
        }
    }

    @Test
    public void setQuantity() {
        graphMatrix.setQuantity(1, 200);
        assertEquals(200, graphMatrix.getQuantity(1));
    }

    @Test
    public void getQuantity() {
        GenerateGraph.generateGraphMatrix(graphMatrix);
        assertEquals(0, graphMatrix.getQuantity(1));
    }

    @Test
    public void getOrder() {
        assertEquals(6, graphMatrix.getOrder());
    }

    @Test
    public void addEdge() {
        //ajouter une arête entre les sommets 1 et 2 avec le coût 61
        graphMatrix.addEdge(1, 2, 61);
        //verifier que l'arête a été ajoutée
        assertEquals(61, graphMatrix.getCost(1, 2));
        assertEquals(61, graphMatrix.getCost(2, 1));
        //verifier que l'arête n'a pas été ajoutée deux fois
        graphMatrix.addEdge(1, 2, 234444444);
        assertEquals(61, graphMatrix.getCost(1, 2));
        assertEquals(61, graphMatrix.getCost(2, 1));
        //verifier que qu'il n'existe pas d'arête entre les sommets 1 et 3
        assertEquals(Integer.MAX_VALUE, graphMatrix.getCost(1, 3));
        assertEquals(Integer.MAX_VALUE, graphMatrix.getCost(3, 1));
    }

    @Test
    public void getCost() {
        GenerateGraph.generateGraphMatrix(graphMatrix);
        assertEquals(34, graphMatrix.getCost(5, 6));
        assertEquals(34, graphMatrix.getCost(6, 5));
        assertEquals(Integer.MAX_VALUE, graphMatrix.getCost(1, 5));
    }

    @Test
    public void getNeighbors() {
        GenerateGraph.generateGraphMatrix(graphMatrix);
        assertEquals(4, graphMatrix.getNeighbors(1).length);
        assertEquals(2, graphMatrix.getNeighbors(2).length);
        assertEquals(3, graphMatrix.getNeighbors(3).length);
        assertEquals(3, graphMatrix.getNeighbors(4).length);
        assertEquals(2, graphMatrix.getNeighbors(5).length);
        assertEquals(2, graphMatrix.getNeighbors(6).length);

        int[] neighbors1 = graphMatrix.getNeighbors(1);
        
        assertEquals(2, neighbors1[0]);
        assertEquals(3, neighbors1[1]);
        assertEquals(4, neighbors1[2]);
        assertEquals(6, neighbors1[3]);
    }

    @Test
    public void readFromFile() throws IOException {
        //lire le graphe depuis le fichier data/graphe_exemple.txt
        String filename = "data/graphe_exemple.txt";
        GraphMatrix graphMatrix1 = GraphMatrix.readFromFile(filename);
        assertEquals(28, graphMatrix1.getCost(1, 6));
        assertEquals(Integer.MAX_VALUE, graphMatrix1.getCost(1, 5));
    }
}