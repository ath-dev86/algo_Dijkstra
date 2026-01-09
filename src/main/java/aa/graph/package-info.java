/**
 * Package contenant les structures de données pour représenter les graphes.
 * 
 * <p>Ce package fournit différentes implémentations pour représenter des graphes
 * non orientés et pondérés. Les graphes peuvent être représentés soit par des
 * listes d'adjacence (plus efficace en mémoire pour les graphes peu denses),
 * soit par des matrices d'adjacence (plus efficace pour les graphes denses).
 * 
 * <h2>Architecture :</h2>
 * <p>La classe abstraite {@link aa.graph.Graph} définit l'interface commune
 * pour toutes les représentations de graphes. Les classes concrètes
 * {@link aa.graph.GraphList} et {@link aa.graph.GraphMatrix} implémentent
 * respectivement les listes d'adjacence et les matrices d'adjacence.
 * 
 * <h2>Classes principales :</h2>
 * <ul>
 *   <li>{@link aa.graph.Graph} - Classe abstraite de base pour tous les graphes</li>
 *   <li>{@link aa.graph.GraphList} - Représentation par listes d'adjacence</li>
 *   <li>{@link aa.graph.GraphMatrix} - Représentation par matrice d'adjacence</li>
 *   <li>{@link aa.graph.Cell} - Cellule pour les listes d'adjacence</li>
 * </ul>
 * 
 * <h2>Caractéristiques :</h2>
 * <ul>
 *   <li>Graphes non orientés : chaque arête est stockée dans les deux sens</li>
 *   <li>Graphes simples : pas de boucles, pas d'arêtes multiples</li>
 *   <li>Pondération : chaque arête a un coût (entier positif)</li>
 *   <li>Quantités : chaque sommet peut stocker une quantité (ex: population)</li>
 *   <li>Indexation : les sommets sont numérotés de 1 à n</li>
 * </ul>
 * 
 * <h2>Format de fichier :</h2>
 * <pre>{@code
 * n
 * 1 quantité1 voisin1 coût1 voisin2 coût2 ... 0
 * 2 quantité2 voisin1 coût1 voisin2 coût2 ... 0
 * ...
 * n quantitén voisin1 coût1 voisin2 coût2 ... 0
 * }</pre>
 * 
 * <h2>Exemple d'utilisation :</h2>
 * <pre>{@code
 * // Lecture depuis un fichier
 * GraphList graph = GraphList.readFromFile("graphe.txt");
 * 
 * // Ajouter une arête
 * graph.addEdge(1, 2, 10);
 * 
 * // Obtenir les voisins
 * int[] neighbors = graph.getNeighbors(1);
 * 
 * // Obtenir le coût d'une arête
 * int cost = graph.getCost(1, 2);
 * 
 * // Définir et obtenir une quantité
 * graph.setQuantity(1, 100);
 * int pop = graph.getQuantity(1);
 * }</pre>
 * 
 * @author Abdou THIAM
 * @version 1.0
 * @since 1.0
 * @see aa.util.LinkedList
 * @see aa.algo.Dijkstra
 */
package aa.graph;

