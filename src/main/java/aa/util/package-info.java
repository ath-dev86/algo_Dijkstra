/**
 * Package contenant les structures de données et utilitaires du projet.
 * 
 * <p>Ce package fournit des structures de données personnalisées et des utilitaires
 * pour la manipulation de fichiers et la gestion des données. Toutes les structures
 * sont implémentées manuellement sans utiliser les collections standard de Java
 * (ArrayList, LinkedList, PriorityQueue, etc.).
 * 
 * <h2>Structures de données :</h2>
 * <ul>
 *   <li>{@link aa.util.LinkedList} - Liste chaînée simple pour stocker les cellules
 *       d'adjacence d'un sommet</li>
 *   <li>{@link aa.util.MinHeap} - Tas min (heap) pour l'algorithme de Dijkstra,
 *       optimisant les opérations d'extraction du minimum et de diminution de clé</li>
 * </ul>
 * 
 * <h2>Utilitaires :</h2>
 * <ul>
 *   <li>{@link aa.util.FileReader} - Utilitaire pour lire tous les entiers d'un fichier,
 *       indépendamment de la mise en forme (espaces, tabulations, nouvelles lignes)</li>
 * </ul>
 * 
 * <h2>Complexité des opérations :</h2>
 * 
 * <h3>LinkedList :</h3>
 * <ul>
 *   <li>Ajout : O(1) - ajout en tête de liste</li>
 *   <li>Parcours : O(n) - où n est le nombre d'éléments</li>
 * </ul>
 * 
 * <h3>MinHeap :</h3>
 * <ul>
 *   <li>Insertion : O(log n)</li>
 *   <li>Extraction du minimum : O(log n)</li>
 *   <li>Diminution de clé : O(log n)</li>
 *   <li>Recherche : O(1) grâce au tableau de positions</li>
 * </ul>
 * 
 * <h2>Exemples d'utilisation :</h2>
 * 
 * <h3>LinkedList :</h3>
 * <pre>{@code
 * LinkedList list = new LinkedList();
 * list.add(2, 10);  // Ajouter un voisin avec coût 10
 * list.add(3, 20);  // Ajouter un autre voisin avec coût 20
 * Cell head = list.getHead();
 * }</pre>
 * 
 * <h3>MinHeap :</h3>
 * <pre>{@code
 * MinHeap heap = new MinHeap(100);
 * heap.insert(1, 0);           // Insérer le sommet 1 avec distance 0
 * heap.insert(2, 5);            // Insérer le sommet 2 avec distance 5
 * int min = heap.extractMin();  // Extraire le sommet de distance minimale
 * heap.decreaseKey(2, 3);       // Diminuer la distance du sommet 2 à 3
 * }</pre>
 * 
 * <h3>FileReader :</h3>
 * <pre>{@code
 * int[] integers = FileReader.readAllIntegers("graphe.txt");
 * // Tous les entiers du fichier sont maintenant dans le tableau
 * }</pre>
 * 
 * @author Abdou THIAM
 * @version 1.0
 * @since 1.0
 * @see aa.graph.Cell
 * @see aa.algo.Dijkstra
 */
package aa.util;

