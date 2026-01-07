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
    public static void generateGraphMatrix(GraphMatrix graphMatrix) {
        //les voisins du sommet 1 sont 2, 3, 4 et 6
        graphMatrix.addEdge(1, 2, 61);
        graphMatrix.addEdge(1, 3, 50);
        graphMatrix.addEdge(1, 4, 30);
        graphMatrix.addEdge(1, 6, 28);
        //les voisins du sommet 2 sont 1, 3
        graphMatrix.addEdge(2, 3, 1);
        //les voisins du sommet 3 sont 1, 2, 4
        graphMatrix.addEdge(3, 4, 68);
        //les voisins du sommet 4 sont 1, 3, 5
        graphMatrix.addEdge(4, 5, 84);
        //les voisins du sommet 5 sont 4, 6
        graphMatrix.addEdge(5, 6, 34);
        //les voisins du somet 6 sont 1, 5
        //deja fait
    }

    /**
     * Une méthode pour afficher un graphe par liste d'adjacence
     * Les voisins sont affichés dans l'ordre d'insertion
     * @param graphList : le graphe à afficher
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

    /**
     * Affiche un graphe par matrice d'adjacence sous forme de tableau
     * Affiche le coût si les sommets sont voisins, 0 sinon
     * @param graphMatrix: le graphe à afficher
     */
    public static void printGraphMatrix(GraphMatrix graphMatrix) {
        System.out.println("Graphe par matrice d'adjacence :");
        int n = graphMatrix.getOrder();
        
        // Afficher les en-têtes de colonnes
        System.out.print("     ");
        for (int j = 1; j <= n; j++) {
            System.out.printf("%5d", j);
        }
        System.out.println();
        
        // Afficher la matrice
        for (int i = 1; i <= n; i++) {
            System.out.printf("%3d  ", i);
            for (int j = 1; j <= n; j++) {
                int cost = graphMatrix.getCost(i, j);
                if (cost == Integer.MAX_VALUE) {
                    System.out.printf("%5d", 0); // Pas d'arête = 0
                } else {
                    System.out.printf("%5d", cost); // Coût de l'arête
                }
            }
            System.out.println("  (q=" + graphMatrix.getQuantity(i) + ")");
        }
    }
}
