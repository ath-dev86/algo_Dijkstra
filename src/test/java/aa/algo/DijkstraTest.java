package aa.algo;

import aa.graph.GenerateGraph;
import aa.graph.Graph;
import aa.graph.GraphList;
import aa.graph.GraphMatrix;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DijkstraTest {
    Graph graphList;
    @Before
    public void setUp() throws Exception {
        graphList = new GraphList(10);
        graphList.addEdge(1, 2, 85);
        graphList.addEdge(1, 3, 217);
        graphList.addEdge(1, 5, 173);

        graphList.addEdge(2, 6, 80);

        graphList.addEdge(3, 7, 186);
        graphList.addEdge(3, 8, 103);

        graphList.addEdge(4, 8, 183);

        graphList.addEdge(5, 10, 502);

        graphList.addEdge(6, 9, 250);

        graphList.addEdge(8, 10, 167);

        graphList.addEdge(9, 10, 85);
    }

    @Test
    public void executeGraphConnect() {
        //GenerateGraph.printGraphList((GraphList) graphList);
        int source = 1;
        Dijkstra.Result result = Dijkstra.execute(graphList, source);
        assertTrue(result.isConnected);
        int[] distances = result.distances;
        int[] preds = result.predecessors;
        //tester la distance minimal entre le sommet 1 et 10
        assertEquals(487, distances[10]);
        //tester quelques predecessors
        assertEquals(-1, preds[1]);
        assertEquals(3, preds[8]);
        assertEquals(3, preds[8]);
        assertEquals(1, preds[5]);
    }

    @Test
    public void executeGraphNotConnect() {
        //faire un graph non connexe
        Graph graphMatrix = new GraphMatrix(6);
        int source = 1;
        graphMatrix.addEdge(1, 2, 25);
        graphMatrix.addEdge(1, 3, 85);
        graphMatrix.addEdge(1, 4, 105);

        graphMatrix.addEdge(2, 5, 89);

        graphMatrix.addEdge(3, 4, 35);
        graphMatrix.addEdge(3, 5, 180);

        graphMatrix.addEdge(4, 5, 8);

        Dijkstra.Result result = Dijkstra.execute(graphMatrix, source);

        assertFalse(result.isConnected);
        assertEquals(113, result.distances[5]);
        assertEquals(-1, result.distances[6]);
        assertEquals(-1, result.predecessors[6]);
    }
}