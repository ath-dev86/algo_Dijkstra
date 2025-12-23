package aa.graph;

import java.io.IOException;

import aa.util.LinkedList;

/**
 * Représentation d'un graphe par listes d'adjacence.
 * Cette classe herite de la classe {@link Graph} et implémente les méthodes abstraites de la classe : 
 * {@link Graph#addEdge(int, int, int)}, {@link Graph#getCost(int, int)}, {@link Graph#getNeighbors(int)}.
 * Elle stocke les listes d'adjacence dans un tableau de listes d'adjacence. chaque indice du tableau (de 1 à n) correspond à un sommet du graphe et son contenu est la liste
 * d'adjacence (sommet voisin et coût de l'arête) du sommet correspondant.
 * Exemple de Représentation :
 * 1 : (2, 10)->(3, 20)->(4, 22)->null
 * 2 : (1, 10)->(3, 30)->null
 * 3 : (1, 20)->(2, 30)->null
 * 4 : (5, 40)->null
 * 5 : (4, 40)->null
 * 6 : (7, 50)->(8, 23)->null
 * 7 : (6, 50)->(8, 76)->null
 * 8 : (6, 50)->(7, 98)->null
 * 
 * @author Abdou THIAM
 * @version 1.0
 * @since 1.0 
 * @see Graph
 * @see LinkedList
 * @see Cell
 */
public class GraphList extends Graph {
    private LinkedList[] adjacencyLists; // Tableau de listes d'adjacence

    /**
     * Constructeur de la classe GraphList
     * @param n l'ordre du graphe
     */

    public GraphList(int n) {
        super(n);
        this.adjacencyLists = new LinkedList[n + 1]; // Indexation de 1 à n
        for (int i = 1; i <= n; i++) {
            adjacencyLists[i] = new LinkedList();
        }
    }

    public LinkedList[] getAdjacencyLists() {
        return adjacencyLists;
    }

    /**
     * Ajouter une arête entre les sommets x et y avec le coût c
     * @param x le numéro du sommet
     * @param y le numéro du sommet voisin
     * @param c le coût de l'arête
     */
    @Override
    public void addEdge(int x, int y, int c) {
        // Les graphes sont non orientés et simples (pas de boucle, pas de doublons)
        if (x == y) {
            return; // Ignorer les boucles
        }
        
        // Vérifier que les sommets sont dans les limites valides [1, n]
        if (x < 1 || x > n || y < 1 || y > n) {
            throw new IndexOutOfBoundsException(
                String.format("Sommet hors limites : x=%d, y=%d (ordre du graphe: %d)", x, y, n));
        }

        // Vérifier si l'arête (x, y) existe déjà
        // Si elle existe, on ne change PAS le coût : on garde le premier coût rencontré
        Cell cellInX = findCell(adjacencyLists[x], y);
        if (cellInX != null) {
            // L'arête existe déjà, ne rien modifier (ni créer de doublon)
            return;
        }

        // L'arête n'existe pas, l'ajouter dans les deux listes
        // En mémoire, on stocke l'arête dans les DEUX listes pour que chaque sommet
        // ait accès à tous ses voisins (nécessaire pour getNeighbors et Dijkstra)
        // Note: dans le format d'entrée du fichier, l'arête n'est définie qu'une fois
        // (dans les voisins du sommet du plus grand numéro), mais en mémoire on la stocke
        // dans les deux listes pour avoir une représentation complète
        adjacencyLists[x].add(y, c);
        adjacencyLists[y].add(x, c);
    }
    
    /**
     * Méthode utilitaire pour trouver une cellule contenant un sommet donné dans une liste.
     * @param list La liste à parcourir
     * @param vertex Le sommet à chercher
     * @return La cellule contenant le sommet, ou null si non trouvée
     */
    private Cell findCell(LinkedList list, int vertex) {
        Cell current = list.getHead();
        while (current != null) {
            if (current.getVertex() == vertex) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * Calculer le coût de l'arête entre les sommets x et y
     * @param x le numéro du sommet
     * @param y le numéro du sommet voisin
     * @return le coût de l'arête
     */
    @Override
    public int getCost(int x, int y) {
        // Chercher dans la liste du sommet x (les deux listes contiennent l'arête)
        Cell current = adjacencyLists[x].getHead();
        while (current != null) {
            if (current.getVertex() == y) {
                return current.getCost();
            }
            current = current.getNext();
        }
        return Integer.MAX_VALUE; // Arête n'existe pas
    }

    /**
     * Retourner tous les voisins du sommet x
     * @param x le numéro du sommet
     * @return les voisins du sommet x dans un tableau d'entiers
     */
    @Override
    public int[] getNeighbors(int x) {
        // Utiliser la taille de la liste pour éviter de compter
        int count = adjacencyLists[x].getSize();
        
        if (count == 0) {
            return new int[0];
        }
        // Construire le tableau des voisins dans l'ordre d'insertion
        // (la liste chaînée stocke en ordre inverse car on ajoute en tête)
        int[] neighbors = new int[count];
        Cell current = adjacencyLists[x].getHead();
        // Remplir le tableau à l'envers pour avoir l'ordre d'insertion
        int index = count - 1;
        while (current != null) {
            neighbors[index--] = current.getVertex();
            current = current.getNext();
        }
        return neighbors;
    }

    /**
     * Lit un graphe depuis un fichier au format spécifié du projet.
     * @param filename le nom du fichier
     * @return le graphe lisible
     * @throws IOException si le fichier n'est pas trouvé ou si le format est invalide
     */
    public static GraphList readFromFile(String filename) throws IOException {
        // Lire tous les entiers du fichier
        int[] integers = aa.util.FileReader.readAllIntegers(filename);
        if (integers.length == 0) {
            throw new IOException("Fichier vide");
        }
        
        int index = 0;
        int n = integers[index++];
        if (n < 1) {
            throw new IOException("L'ordre du graphe doit être au moins 1");
        }
        GraphList graph = new GraphList(n);

        // Lire les sommets et leurs voisins
        // Format : pour chaque sommet x : x quantité voisin1 coût1 voisin2 coût2 ... 0
        // On doit lire exactement n sommets
        for (int i = 0; i < n && index < integers.length; i++) {
            // Lire le numéro du sommet
            int x = integers[index++];
            
            // Vérifier que le sommet est dans les limites [1, n]
            if (x < 1 || x > n) {
                throw new IOException(
                    String.format("Format invalide : sommet %d hors limites [1, %d]", x, n));
            }
            
            if (index >= integers.length) {
                throw new IOException("Format invalide : quantité manquante pour le sommet " + x);
            }
            
            // Lire la quantité (ne pas stocker dans la liste d'adjacence, juste dans quantities[])
            int qx = integers[index++];
            graph.setQuantity(x, qx);

            // Lire les voisins jusqu'à rencontrer 0
            // Format du fichier : l'arête est définie dans les voisins du sommet du plus grand numéro
            // Exemple : "2 0 1 61 0" signifie sommet 2, quantité 0, voisin 1 avec coût 61
            // L'arête (1,2) est stockée dans la liste du sommet 2 car 2 > 1
            while (index < integers.length) {
                int y = integers[index++];
                if (y == 0) {
                    break; // Fin de la liste des voisins pour ce sommet
                }

                // Vérifier que le voisin est dans les limites [1, n]
                // IMPORTANT : y doit être un numéro de sommet, pas une quantité !
                if (y < 1 || y > n) {
                    throw new IOException(
                        String.format("Format invalide : voisin %d hors limites [1, %d] pour le sommet %d. " +
                                    "Vérifiez que la quantité (%d) n'est pas traitée comme un voisin.", 
                                    y, n, x, qx));
                }

                if (index >= integers.length) {
                    throw new IOException(
                        String.format("Format invalide : coût manquant pour l'arête (%d, %d)", x, y));
                }

                int c = integers[index++];
                
                // Ajouter l'arête (x, y) avec coût c
                // En mémoire, on stocke dans les deux listes pour faciliter les opérations
                // (Dijkstra, getNeighbors, etc.)
                graph.addEdge(x, y, c);
            }
        }

        return graph;
    }
}
