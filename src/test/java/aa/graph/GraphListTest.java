package aa.graph;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import org.junit.Before;
import org.junit.Test;

public class GraphListTest {
    private GraphList graphList;

    @Before
    public void setUp() throws Exception {
        graphList = new GraphList(6);
    }

    @Test
    public void setQuantity() {
        graphList.setQuantity(1, 200);
        assertEquals(200, graphList.getQuantity(1));
    }

    @Test
    public void getQuantity() {
        assertThrows(IndexOutOfBoundsException.class, () -> graphList.setQuantity(-2, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> graphList.getQuantity(-2));
    }

    @Test
    public void getAdjacencyLists(){
        int length = graphList.getAdjacencyLists().length;
        int n = graphList.getOrder();
        assertEquals(7, length);
        for(int i = 1; i <= n; i++) {
            assertNull(graphList.getAdjacencyLists()[i].getHead());
        }
    }

    @Test
    public void addEdge() {
        // Ajouter des arêtes depuis le sommet 1
        graphList.addEdge(1, 2, 61);
        graphList.addEdge(1, 3, 50);
        graphList.addEdge(1, 4, 30);
        graphList.addEdge(1, 6, 28);
        
        // Vérifier que le sommet 1 a bien 4 voisins
        assertEquals(4, graphList.getAdjacencyLists()[1].getSize());
        // Vérifier que le sommet 2 a bien le sommet 1 comme voisin
        assertEquals(1, graphList.getAdjacencyLists()[2].getSize());
        // Essayer d'ajouter la même arête dans l'autre sens (ne doit pas créer de doublon)
        graphList.addEdge(2, 1, 61);
        // Vérifier qu'il n'y a toujours que 4 voisins (pas de doublon)
        assertEquals(4, graphList.getAdjacencyLists()[1].getSize());
        assertEquals(1, graphList.getAdjacencyLists()[2].getSize());
        // Vérifier que getNeighbors fonctionne correctement
        int[] neighbors1 = graphList.getNeighbors(1);
        assertEquals(4, neighbors1.length);
        // Vérifier que getCost fonctionne dans les deux sens
        assertEquals(61, graphList.getCost(1, 2));
        assertEquals(61, graphList.getCost(2, 1)); // Graphe non orienté
    }

    @Test
    public void getCost() {
        //ajouter une arête de sommet 1 à sommet 2 avec le coût 61
        graphList.addEdge(1, 2, 61);
        // Vérifier que le coût de l'arête (1, 2) est 61
        assertEquals(61, graphList.getCost(1, 2));
        // Vérifier que le coût de l'arête (2, 1) est 61 (graphe non orienté)
        assertEquals(61, graphList.getCost(2, 1));
        // Tester avec une arête qui n'existe pas
        assertEquals(Integer.MAX_VALUE, graphList.getCost(1, 3));
    }
    
    @Test
    public void addEdgeExistingEdge() {
        // Ajouter une arête avec un coût
        graphList.addEdge(1, 2, 61);
        assertEquals(61, graphList.getCost(1, 2));
        assertEquals(61, graphList.getCost(2, 1));
        
        // Mettre à jour le coût de la même arête (dans l'autre sens)
        graphList.addEdge(2, 1, 43);
        
        // Vérifier que le coût a été mis à jour (pas de doublon créé)
        assertEquals(61, graphList.getCost(1, 2));
        assertEquals(61, graphList.getCost(2, 1));
        
        // Vérifier qu'il n'y a toujours qu'un seul voisin pour chaque sommet
        assertEquals(1, graphList.getAdjacencyLists()[1].getSize());
        assertEquals(1, graphList.getAdjacencyLists()[2].getSize());
    }

    @Test
    public void getNeighbors() {
        GenerateGraph.generateGraphList(graphList);
        //tester les voisins du sommet 1
        int[] neighbors1 = graphList.getNeighbors(1);
        assertEquals(4, neighbors1.length);
        assertEquals(2, neighbors1[0]);
        assertEquals(3, neighbors1[1]);
        assertEquals(4, neighbors1[2]);
        assertEquals(6, neighbors1[3]);

        //tester les voisins du sommet 2
        int[] neighbors2 = graphList.getNeighbors(2);
        assertEquals(2, neighbors2.length);
        assertEquals(1, neighbors2[0]);
        assertEquals(3, neighbors2[1]);
        //tester les voisins du sommet 3
        int[] neighbors3 = graphList.getNeighbors(3);
        assertEquals(3, neighbors3.length);
        assertEquals(1, neighbors3[0]);
        assertEquals(2, neighbors3[1]);
        assertEquals(4, neighbors3[2]);
        //tester les voisins du sommet 4
        int[] neighbors4 = graphList.getNeighbors(4);
        assertEquals(3, neighbors4.length);
        assertEquals(1, neighbors4[0]);
        assertEquals(3, neighbors4[1]);
        assertEquals(5, neighbors4[2]);
        //tester les voisins du sommet 5
        int[] neighbors5 = graphList.getNeighbors(5);
        assertEquals(2, neighbors5.length);
        assertEquals(4, neighbors5[0]);
        assertEquals(6, neighbors5[1]);
        //tester les voisins du sommet 6
        int[] neighbors6 = graphList.getNeighbors(6);
        assertEquals(2, neighbors6.length);
        assertEquals(1, neighbors6[0]);
        assertEquals(5, neighbors6[1]);
    }

    @Test
    public void readFromFile() throws IOException {
        //lire le graphe depuis le fichier data/graphe_exemple.txt
        String filename = "data/graphe_exemple.txt";
        GraphList graph = GraphList.readFromFile(filename);
        //verifie les quantités des sommets
        assertEquals(6, graph.getOrder());
        assertEquals(200, graph.getQuantity(1));
        assertEquals(0, graph.getQuantity(2));
        assertEquals(100, graph.getQuantity(3));
        assertEquals(150, graph.getQuantity(4));
        assertEquals(50, graph.getQuantity(5));
        assertEquals(0, graph.getQuantity(6));
        //verifie les voisins des sommets
        int[] neighbors1 = graph.getNeighbors(1);
        assertEquals(4, neighbors1.length);
        assertEquals(2, neighbors1[0]);
        assertEquals(3, neighbors1[1]);
        assertEquals(4, neighbors1[2]);
        assertEquals(6, neighbors1[3]);
        int[] neighbors2 = graph.getNeighbors(2);
        assertEquals(2, neighbors2.length);
        assertEquals(1, neighbors2[0]);
        assertEquals(3, neighbors2[1]);
        int[] neighbors3 = graph.getNeighbors(3);
        assertEquals(3, neighbors3.length);
        assertEquals(1, neighbors3[0]);
        assertEquals(2, neighbors3[1]);
        assertEquals(4, neighbors3[2]);
        int[] neighbors4 = graph.getNeighbors(4);
        assertEquals(3, neighbors4.length);
        assertEquals(1, neighbors4[0]);
        assertEquals(3, neighbors4[1]);
        assertEquals(5, neighbors4[2]);
        int[] neighbors5 = graph.getNeighbors(5);
        assertEquals(2, neighbors5.length);
        assertEquals(4, neighbors5[0]);
        assertEquals(6, neighbors5[1]);
        int[] neighbors6 = graph.getNeighbors(6);
        assertEquals(2, neighbors6.length);
        assertEquals(1, neighbors6[0]);
        assertEquals(5, neighbors6[1]);
        //verifie les coûts des arêtes
        assertEquals(61, graph.getCost(1, 2));
        assertEquals(61, graph.getCost(2, 1));
        assertEquals(50, graph.getCost(1, 3));
        assertEquals(50, graph.getCost(3, 1));
        assertEquals(30, graph.getCost(1, 4));
        assertEquals(30, graph.getCost(4, 1));
        assertEquals(28, graph.getCost(1, 6));
        assertEquals(28, graph.getCost(6, 1));
        assertEquals(1, graph.getCost(2, 3));
        assertEquals(1, graph.getCost(3, 2));
        assertEquals(68, graph.getCost(3, 4));
        assertEquals(68, graph.getCost(4, 3));
        assertEquals(84, graph.getCost(4, 5));
        assertEquals(84, graph.getCost(5, 4));
        assertEquals(34, graph.getCost(5, 6));
        assertEquals(34, graph.getCost(6, 5));
    }

    @Test
    public void getOrder(){
        // Vérifier que l'ordre du graphe est 6
        assertEquals(6, graphList.getOrder());
    }
}