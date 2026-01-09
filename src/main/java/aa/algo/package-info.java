/**
 * Package contenant les algorithmes de graphes.
 * 
 * <p>Ce package fournit l'implémentation de l'algorithme de Dijkstra pour 
 * calculer les plus courts chemins dans un graphe pondéré avec des poids positifs.
 * 
 * <p>L'algorithme de Dijkstra est utilisé pour trouver les distances les plus courtes
 * depuis un sommet source vers tous les autres sommets du graphe. Il utilise une
 * structure de données de tas min (MinHeap) pour optimiser les performances.
 * 
 * <h2>Classes principales :</h2>
 * <ul>
 *   <li>{@link aa.algo.Dijkstra} - Implémentation de l'algorithme de Dijkstra</li>
 * </ul>
 * 
 * <h2>Utilisation :</h2>
 * <pre>{@code
 * Graph graph = GraphList.readFromFile("graphe.txt");
 * Dijkstra.Result result = Dijkstra.execute(graph, 1);
 * // result.distances contient les distances depuis le sommet 1
 * // result.predecessors contient les prédécesseurs pour reconstruire les chemins
 * // result.isConnected indique si le graphe est connexe depuis la source
 * }</pre>
 * 
 * @author Abdou THIAM
 * @version 1.0
 * @since 1.0
 * @see aa.graph.Graph
 * @see aa.util.MinHeap
 */
package aa.algo;

