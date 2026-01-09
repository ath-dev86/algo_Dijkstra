/**
 * Package contenant les applications et programmes exécutables du projet.
 * 
 * <p>Ce package contient les classes principales qui permettent d'exécuter
 * les différents algorithmes et fonctionnalités du projet. Ces classes
 * implémentent les points d'entrée (méthode main) pour les différents
 * programmes du projet.
 * 
 * <h2>Programmes disponibles :</h2>
 * <ul>
 *   <li>{@link aa.app.CalculProvinces} - Partitionne un graphe en provinces selon
 *       des contraintes de distance et de population</li>
 *   <li>{@link aa.app.DijkstraL} - Calcule les distances avec représentation
 *       par listes d'adjacence</li>
 *   <li>{@link aa.app.DijkstraM} - Calcule les distances avec représentation
 *       par matrice d'adjacence</li>
 *   <li>{@link aa.app.GraphGenerator} - Génère des graphes aléatoires pour les tests</li>
 * </ul>
 * 
 * <h2>Exemples d'utilisation :</h2>
 * 
 * <h3>Calcul des provinces :</h3>
 * <pre>{@code
 * java aa.app.CalculProvinces graphe.txt 100 1000
 * }</pre>
 * 
 * <h3>Dijkstra avec listes d'adjacence :</h3>
 * <pre>{@code
 * java aa.app.DijkstraL graphe.txt 1 resultat.txt
 * }</pre>
 * 
 * <h3>Dijkstra avec matrice d'adjacence :</h3>
 * <pre>{@code
 * java aa.app.DijkstraM graphe.txt 1 resultat.txt
 * }</pre>
 * 
 * <h3>Génération de graphe :</h3>
 * <pre>{@code
 * java aa.app.GraphGenerator 10 40 1 10 10 1000 > graphe.txt
 * }</pre>
 * 
 * @author Abdou THIAM
 * @version 1.0
 * @since 1.0
 * @see aa.algo.Dijkstra
 * @see aa.graph.Graph
 * @see aa.graph.GraphList
 * @see aa.graph.GraphMatrix
 */
package aa.app;

