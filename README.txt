================================================================================
PROJET ALGORITHME DE DIJKSTRA
Algorithmique Avancée M1 Informatique 2025-2026
================================================================================

COMPILATION
-----------

Le projet utilise Java 1.8. Pour compiler le projet, utilisez la commande suivante
depuis le répertoire racine du projet :

    javac -d target/classes -sourcepath src/main/java src/main/java/aa/app/*.java src/main/java/aa/graph/*.java src/main/java/aa/algo/*.java src/main/java/aa/util/*.java

Ou si vous utilisez Maven :

    mvn compile

Les fichiers compilés seront dans le répertoire target/classes/.


EXÉCUTION
---------

Les trois programmes doivent être exécutés depuis le répertoire racine du projet,
en spécifiant le classpath approprié.

1. Programme dijkstra_L (avec listes d'adjacence) :
   
   java -cp target/classes aa.app.DijkstraL <fichier_graphe> <sommet_depart> [fichier_sortie]
   
   Exemple :
   java -cp target/classes aa.app.DijkstraL data/graphe.txt 1 resultat.txt

2. Programme dijkstra_M (avec matrice d'adjacence) :
   
   java -cp target/classes aa.app.DijkstraM <fichier_graphe> <sommet_depart> [fichier_sortie]
   
   Exemple :
   java -cp target/classes aa.app.DijkstraM data/graphe.txt 1 resultat.txt

3. Programme calculProvinces :
   
   java -cp target/classes aa.app.CalculProvinces <fichier_graphe> <dMax> <popMax>
   
   Exemple :
   java -cp target/classes aa.app.CalculProvinces data/graphe.txt 100 500


FORMAT D'ENTRÉE DES GRAPHES
----------------------------

Le format d'entrée est une suite d'entiers séparés par des espaces :

- Un premier entier n (l'ordre du graphe)
- Pour chaque sommet x (de 1 à n) :
  * Le numéro du sommet x
  * La quantité qx stockée sur ce sommet
  * Une suite de couples (y, c) où y est un voisin et c est le coût de l'arête
  * L'entier 0 pour terminer la liste des voisins

Les graphes sont non orientés. Une arête (x, y) n'est définie que dans les
voisins du sommet avec le plus grand numéro.

Exemple :
6
1 200 0
2 0 1 61 0
3 100 1 50 2 1 0
4 150 1 3 68 0
5 50 4 84 0
6 0 1 28 5 34 0

Ou sur une seule ligne :
6 1 200 0 2 0 1 61 0 3 100 1 50 2 1 0 4 150 1 3 68 0 5 50 4 84 0 6 0 1 28 5 34 0


STRUCTURE DU PROJET
--------------------

src/main/java/aa/
  ├── app/              : Programmes principaux
  │   ├── DijkstraL.java      : Programme avec listes d'adjacence
  │   ├── DijkstraM.java      : Programme avec matrice d'adjacence
  │   └── CalculProvinces.java : Programme de calcul des provinces
  ├── graph/            : Représentations de graphe
  │   ├── Graph.java          : Interface de base
  │   ├── GraphList.java      : Représentation par listes d'adjacence
  │   ├── GraphMatrix.java    : Représentation par matrice d'adjacence
  │   └── Cell.java           : Cellule pour les listes d'adjacence
  ├── algo/             : Algorithmes
  │   └── Dijkstra.java       : Implémentation de l'algorithme de Dijkstra
  └── util/             : Structures de données
      ├── LinkedList.java     : Liste chaînée simple
      └── MinHeap.java        : Tas min pour Dijkstra


NOTES IMPORTANTES
-----------------

- Les sommets sont numérotés de 1 à n (pas de 0 à n-1)
- Les coûts des arêtes sont des entiers positifs
- Integer.MAX_VALUE représente l'infini pour les distances
- Aucune structure de données de la bibliothèque standard Java n'est utilisée
  (pas d'ArrayList, TreeSet, PriorityQueue, etc.)
- Toutes les structures de données sont implémentées manuellement


AUTEUR
------

Abdou THIAM

