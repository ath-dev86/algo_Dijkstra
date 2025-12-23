package aa.graph;

/**
 * classe abstraite de base pour représenter les graphes.
 * Définit les méthodes communes aux deux représentations.
 * Cette classe sera heritée par les classes {@link GraphList} et {@link GraphMatrix}.
 * @author Abdou THIAM
 * @version 1.0
 * @since 1.0 
 * @see GraphList
 * @see GraphMatrix
 */
public abstract class Graph {
    protected int n;              // Ordre du graphe (nombre de sommets)
    protected int[] quantities;   // Quantités stockées sur chaque sommet

    /**
     * Constructeur de la classe Graph
     * @param n l'ordre du graphe
     */
    public Graph(int n) {
        this.n = n;
        this.quantities = new int[n + 1]; // Indexation de 1 à n
    }

    /**
     * une methode asbtraite pour Ajouter une arête entre les sommets x et y avec le coût c.
     * Les graphes sont non orientés.
     * @param x le numéro du sommet
     * @param y le numéro du sommet voisin
     * @param c le coût de l'arête
     */
    public abstract void addEdge(int x, int y, int c);

    /**
     * une méthode abstraite pour calculer le coût de l'arête entre x et y,
     * ou Integer.MAX_VALUE si l'arête n'existe pas.
     * @param x le numéro du sommet
     * @param y le numéro du sommet voisin
     * @return le coût de l'arête
     */
    public abstract int getCost(int x, int y);

    /**
     * une méthode abstraite pour retourne tous les voisins du sommet x.
     * Utilisé pour l'algorithme de Dijkstra.
     * @param x le numéro du sommet
     * @return les voisins du sommet x
     */
    public abstract int[] getNeighbors(int x);


    /**
     * Définit la quantité stockée sur le sommet x.
     * @param x le numéro du sommet
     * @param q la quantité stockée sur le sommet x
     */
    public void setQuantity(int x, int q) {
        if (x >= 1 && x <= n) {
            quantities[x] = q;
        }else {
            throw new IndexOutOfBoundsException("L'ordre du sommet est hors limites");
        }
    }

    /**
     * la quantité stockée sur le sommet x.
     * @param x le numéro du sommet
     * @return la quantité stockée sur le sommet x
     */
    public int getQuantity(int x) {
        if (x >= 1 && x <= n) {
            return quantities[x];
        } else {
            throw new IndexOutOfBoundsException("L'ordre du sommet est hors limites");
        }
    }

    /**
     * l'ordre du graphe.
     * @return l'ordre du graphe
     */
    public int getOrder() {
        return n;
    }
}
