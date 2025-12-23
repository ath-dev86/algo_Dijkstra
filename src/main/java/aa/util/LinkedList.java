package aa.util;

import aa.graph.Cell;

/**
 * Liste chaînée simple pour stocker les cellules d'adjacence d'un sommet.
 * Implémentation manuelle sans utiliser les collections Java comme ArrayList, LinkedList, etc.
 * la classe LinkedList est une liste chaînée simple pour stocker les cellules (voir {@link Cell}) d'adjacence d'un sommet.
 * @author Abdou THIAM
 * @version 1.0
 * @since 1.0 
 * @see Cell
 */
public class LinkedList {
    private Cell head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Ajouter une cellule à la liste
     * @param vertex le numéro du sommet voisin
     * @param cost le coût de l'arête
     */
    public void add(int vertex, int cost) {
        Cell newCell = new Cell(vertex, cost);
        newCell.setNext(head);
        head = newCell;
        size++;
    }

    /**
     * Retourner la tête de la liste
     * @return la tête de la liste
     */
    public Cell getHead() {
        return head;
    }

    /**
     * Retourner la taille de la liste
     * @return la taille de la liste
     */
    public int getSize() {
        return size;
    }

    /**
     * Vérifier si la liste est vide
     * @return true si la liste est vide, false sinon
     */
    public boolean isEmpty() {
        return head == null;
    }
}

