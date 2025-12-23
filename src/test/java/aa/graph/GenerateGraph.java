package aa.graph;

public class GenerateGraph {
    //construire un graphe non orienté avec les sommets 1, 2, 3, 4, 5 et 6
    //les coûts des arêtes sont les suivants :
    //(1, 2) : 61
    //(1, 3) : 50
    //(1, 4) : 30
    //(1, 6) : 28
    //(2, 3) : 1
    //(3, 4) : 68
    //(4, 5) : 84
    //(5, 6) : 34
    public static void generateGraphList(GraphList graphList) {
        //les voisins du sommet 1 sont 2, 3, 4 et 6
        graphList.addEdge(1, 2, 61);
        graphList.addEdge(1, 3, 50);
        graphList.addEdge(1, 4, 30);
        graphList.addEdge(1, 6, 28);
        //les voisins du sommet 2 sont 1, 3
        graphList.addEdge(2, 3, 1);
        //les voisins du sommet 3 sont 1, 2, 4
        graphList.addEdge(3, 4, 68);
        //les voisins du sommet 4 sont 1, 3, 5
        graphList.addEdge(4, 5, 84);
        //les voisins du sommet 5 sont 4, 6
        graphList.addEdge(5, 6, 34);
        //les voisins du somet 6 sont 1, 5
        //deja fait
    }

    /**
     * Une méthode pour afficher un graphe par liste d'adjacence
     * Les voisins sont affichés dans l'ordre d'insertion
     */
    public static void printGraphList(GraphList graphList) {
        System.out.println("Graphe par liste d'adjacence :");
        int n = graphList.getOrder();
        for(int i = 1; i <= n; i++) {
            System.out.print("Sommet " + i + " (q=" + graphList.getQuantity(i) + ") : ");
            // Utiliser getNeighbors() pour obtenir les voisins dans l'ordre d'insertion
            int[] neighbors = graphList.getNeighbors(i);
            if (neighbors.length == 0) {
                System.out.println("(aucun voisin)"); // dans le cas d'un graphe non connexe
            } else {
                for (int j = 0; j < neighbors.length; j++) {
                    int neighbor = neighbors[j];
                    int cost = graphList.getCost(i, neighbor);
                    System.out.print("(" + neighbor + ", " + cost + ")");
                    if (j < neighbors.length - 1) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        }
    }
}
