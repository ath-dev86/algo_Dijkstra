package aa.graph;

import java.io.IOException;

/**
 * Représentation d'un graphe par matrice d'adjacence.
 * 
 * @author Abdou THIAM
 * @version 1.0
 * @since 1.0
 * @see Graph
 *
 */
public class GraphMatrix extends Graph {
    private int[][] matrix; // Matrice d'adjacence

    
    /**
     * Constructeur de la classe GraphMatrix
     * @param n l'ordre du graphe
     */
    public GraphMatrix(int n) {
        super(n);
        this.matrix = new int[n + 1][n + 1]; // Indexation de 1 à n
        
        // Initialiser toutes les arêtes à Integer.MAX_VALUE (infini)
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                matrix[i][j] = Integer.MAX_VALUE;
            }
        }
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
        
        // Vérifier si l'arête existe déjà
        // Si elle existe, on garde le premier coût rencontré (comme dans GraphList)
        if (matrix[x][y] != Integer.MAX_VALUE) {
            return; // L'arête existe déjà, ne pas modifier
        }
        
        // Les graphes sont non orientés
        // On stocke l'arête dans la matrice (symétrique)
        // matrix[i][j] = c signifie qu'il y a une arête entre i et j avec coût c
        // matrix[i][j] = Integer.MAX_VALUE signifie qu'il n'y a pas d'arête entre i et j
        matrix[x][y] = c;
        matrix[y][x] = c;
    }

    /**
     * Calculer le coût de l'arête entre les sommets x et y.
     * 
     * @param x le numéro du sommet
     * @param y le numéro du sommet voisin
     * @return le coût de l'arête, ou Integer.MAX_VALUE si l'arête n'existe pas
     */
    @Override
    public int getCost(int x, int y) {
        return matrix[x][y];
    }

    /**
     * Retourner tous les voisins du sommet x.
     * 
     * @param x le numéro du sommet
     * @return les voisins du sommet x dans un tableau d'entiers
     */
    @Override
    public int[] getNeighbors(int x) {
        // Compter le nombre de voisins
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (i != x && matrix[x][i] != Integer.MAX_VALUE) {
                count++;
            }
        }

        // Construire le tableau des voisins
        int[] neighbors = new int[count];
        int index = 0;
        for (int i = 1; i <= n; i++) {
            if (i != x && matrix[x][i] != Integer.MAX_VALUE) {
                neighbors[index++] = i;
            }
        }

        return neighbors;
    }

    /**
     * Lit un graphe depuis un fichier au format spécifié du projet.
     * @param filename le nom du fichier
     * @return le graphe lisible en matrice d'adjacence
     * @throws IOException si le fichier n'est pas trouvé ou si le format est invalide
     * complexite en O(n²)
     */
    public static GraphMatrix readFromFile(String filename) throws IOException {
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
        GraphMatrix graph = new GraphMatrix(n);

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
            
            // Lire la quantité (ne pas stocker dans la matrice, juste dans quantities[])
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
                // En mémoire, on stocke dans la matrice (symétrique) pour faciliter les opérations
                // matrix[i][j] = c signifie qu'il y a une arête entre i et j avec coût c
                // matrix[i][j] = Integer.MAX_VALUE signifie qu'il n'y a pas d'arête
                graph.addEdge(x, y, c);
            }
        }

        return graph;
    }
}
