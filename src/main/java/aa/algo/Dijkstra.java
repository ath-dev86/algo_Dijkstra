package aa.algo;

import aa.graph.Graph;
import aa.util.MinHeap;

/**
 * Implémentation de l'algorithme de Dijkstra pour calculer
 * les distances les plus courtes depuis un sommet source.
 * Tous les distances doivent être positives
 *
 * @author Abdou THIAM
 * @since 1.0
 * @version 1.0
 * @see Result
 * @see Graph
 * @see aa.graph.GraphList
 * @see aa.graph.GraphMatrix
 * @see MinHeap
 */
public class Dijkstra {
    
    /**
     * Résultat de l'algorithme de Dijkstra.
     * 
     * <p>Cette classe encapsule les résultats de l'exécution de l'algorithme de Dijkstra,
     * incluant les distances, les prédécesseurs pour reconstruire les chemins, et
     * l'information sur la connexité du graphe.
     * 
     * @author Abdou THIAM
     * @version 1.0
     * @since 1.0
     */
    public static class Result {
        /** 
         * Tableau des distances depuis le sommet source.
         * distances[i] = distance du sommet source au sommet i.
         * Si le sommet i n'est pas atteignable, distances[i] = -1.
         */
        public int[] distances;
        
        /** 
         * Tableau des prédécesseurs pour reconstruire les chemins les plus courts.
         * predecessors[i] = prédécesseur de i dans le chemin le plus court depuis la source.
         * predecessors[source] = -1 (pas de prédécesseur pour la source).
         * Si le sommet i n'est pas atteignable, predecessors[i] = -1.
         */
        public int[] predecessors;
        
        /** 
         * Indique si tous les sommets sont atteignables depuis la source.
         * true si le graphe est connexe depuis la source, false sinon.
         */
        public boolean isConnected;

        /**
         * Constructeur pour créer un résultat avec n sommets.
         * 
         * @param n le nombre de sommets du graphe (les sommets sont numérotés de 1 à n)
         */
        public Result(int n) {
            this.distances = new int[n + 1];
            this.predecessors = new int[n + 1];
            this.isConnected = true;
        }
    }

    /**
     * Exécute l'algorithme de Dijkstra sur un graphe depuis un sommet source.
     * 
     * @param graph Le graphe (peut être GraphList ou GraphMatrix)
     * @param source Le sommet source (somet de départ), les sommets sont numérotés de de 1 à n
     * @return Un objet Result contenant les distances, prédécesseurs et la connexité
     */
    public static Result execute(Graph graph, int source) {
        int n = graph.getOrder();
        Result result = new Result(n);
        // Initialiser les distances à l'infini et les prédécesseurs à -1
        for (int i = 1; i <= n; i++) {
            result.distances[i] = Integer.MAX_VALUE;
            result.predecessors[i] = -1;
        }
        // Distance du sommet source à lui-même est 0
        result.distances[source] = 0; //TODO: à revoir si c'est -1 ou 0
        
        // Créer le tas min et l'insérer dans le tas
        MinHeap heap = new MinHeap(n);
        heap.insert(source, 0);
        
        // Tableau pour marquer les sommets visités
        boolean[] visited = new boolean[n + 1];
        
        // Algorithme de Dijkstra
        while (!heap.isEmpty()) {
            int u = heap.extractMin();
            if (visited[u]) {
                continue;
            }
            visited[u] = true;
            
            // Parcourir tous les voisins de u
            int[] neighbors = graph.getNeighbors(u);
            for (int v : neighbors) {
                if (!visited[v]) {
                    int cost = graph.getCost(u, v);
                    if (cost != Integer.MAX_VALUE) {
                        int newDistance = result.distances[u] + cost;
                        
                        // Si on trouve un chemin plus court
                        if (newDistance < result.distances[v]) {
                            result.distances[v] = newDistance;
                            result.predecessors[v] = u;
                            
                            // Mettre à jour le tas
                            if (heap.contains(v)) {
                                heap.decreaseKey(v, newDistance);
                            } else {
                                heap.insert(v, newDistance);
                            }
                        }
                    }
                }
            }
        }
        
        // Vérifier si le graphe est connexe
        // Le graphe est connexe si tous les sommets sont atteignables depuis la source
        for (int i = 1; i <= n; i++) {
            if (result.distances[i] == Integer.MAX_VALUE) {
                result.isConnected = false;
                result.distances[i] = -1; // Marquer comme non atteignable
            }
        }
        
        return result;
    }
}
