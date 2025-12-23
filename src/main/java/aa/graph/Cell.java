package aa.graph;

/**
 * Représente une cellule pour les listes d'adjacence.
 * Contient un sommet voisin et le coût de l'arête.
 * Exemple : 
 * le sommet 1 a deux voisins : 2 et 3 avec les coûts 10 et 20.
 * On le représente par la liste : [Cell(2, 10), Cell(3, 20)]
 * un sommet est idenntifié par l'index du tableau adjacencyLists
 *
 * @author Abdou THIAM
 * @version 1.0
 * @since 1.0 //@since signifie que cette classe fait partie de la version 1.0
 */
public class Cell {
    private final int vertex;  // Numéro du sommet voisin
    private int cost;    // Coût de l'arête
    private Cell next;   // Pointeur vers la cellule suivante

    /**
     * Constructeur de la cellule
     * @param vertex le numéro du sommet voisin
     * @param cost le coût de l'arête
     * 
     */
    public Cell(int vertex, int cost) {
        this.vertex = vertex;
        this.cost = cost;
        this.next = null;
    }

    public int getVertex() {
        return vertex;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Cell getNext() {
        return next;
    }

    public void setNext(Cell next) {
        this.next = next;
    }
}
