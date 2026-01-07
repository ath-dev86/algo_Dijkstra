package aa.util;

import java.util.PriorityQueue;

/**
 * Tas min (heap) pour l'algorithme de Dijkstra.
 * Implémentation manuelle sans utiliser les collections Java comme PriorityQueue.
 * Sans tas min : il faut parcourir tous les sommets à chaque fois - O(n2)
 *
 * @author Abdou THIAM
 * @version 1.0
 * @since 1.0
 * @see HeapNode voir le tas source de java collections {@link PriorityQueue} pour voir le comportement d'un tas complet.
 *
 *
 */
public class MinHeap {
    private HeapNode[] heap; // tableau de sommets dans le tas
    private int[] positions; // positions[i] = position du sommet i dans le tas (indice dans le tableau heap)
    private int size; // nombre de sommets dans le tas
    private int capacity; // capacité du tas (nombre maximum de sommets dans le tas)

    /**
     * Classe interne pour représenter un sommet dans le tas.
     * @author Abdou THIAM
     * @version 1.0
     * @since 1.0
     * @see MinHeap
     */
    private static class HeapNode {
        int vertex;
        int distance;

        HeapNode(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    /**
     * Constructeur de la classe MinHeap
     * @param capacity la capacité du tas
     */
    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new HeapNode[capacity + 1]; // Indexation à partir de 1
        this.positions = new int[capacity + 1];
        this.size = 0;
        
        // Initialiser les positions à -1 (non présent dans le tas)
        for (int i = 0; i <= capacity; i++) {
            positions[i] = -1;
        }
    }

    /**
     * Vérifie si le tas est vide.
     * @return true si le tas est vide, false sinon.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Insère un sommet dans le tas.
     * @param vertex le numéro du sommet
     * @param distance la distance du sommet
     */
    public void insert(int vertex, int distance) {
        if (size >= capacity) {
            return;
        }
        size++;
        heap[size] = new HeapNode(vertex, distance);
        positions[vertex] = size;
        heapifyUp(size);
    }

    /**
     * Extrait le sommet de distance minimale du tas.
     * @return le numéro du sommet de distance minimale
     */
    public int extractMin() {
        if (isEmpty()) {
            return -1;
        }

        int minVertex = heap[1].vertex;
        positions[minVertex] = -1;
        
        if (size > 1) {
            heap[1] = heap[size];
            positions[heap[1].vertex] = 1;
            size--;
            heapifyDown(1);
        } else {
            size--;
        }

        return minVertex;
    }

    /**
     * Diminue la distance d'un sommet dans le tas.
     * @param vertex le numéro du sommet
     * @param newDistance la nouvelle distance du sommet
     */
    public void decreaseKey(int vertex, int newDistance) {
        int pos = positions[vertex];
        if (pos == -1 || heap[pos].distance <= newDistance) {
            return;
        }

        heap[pos].distance = newDistance;
        //Répare le tas
        heapifyUp(pos);
    }

    /**
     * Vérifie si un sommet est dans le tas.
     * @param vertex le numéro du sommet
     * @return true si le sommet est dans le tas, false sinon.
     */
    public boolean contains(int vertex) {
        return positions[vertex] != -1;
    }

    /**
     * Répare le tas après une insertion.
     * @param index l'indice du sommet
     */
    private void heapifyUp(int index) {
        while (index > 1 && heap[index].distance < heap[parent(index)].distance) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    /**
     * Répare le tas après une extraction.
     * @param index l'indice du sommet
     */
    private void heapifyDown(int index) {
        int smallest = index;
        int left = leftChild(index);
        int right = rightChild(index);

        if (left <= size && heap[left].distance < heap[smallest].distance) {
            smallest = left;
        }
        if (right <= size && heap[right].distance < heap[smallest].distance) {
            smallest = right;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    /**
     * Échange deux sommets dans le tas.
     * @param i l'indice du premier sommet
     * @param j l'indice du deuxième sommet
     */
    private void swap(int i, int j) {
        HeapNode temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        
        positions[heap[i].vertex] = i;
        positions[heap[j].vertex] = j;
    }

    /**
     * Retourne le parent d'un sommet.
     * @param index l'indice du sommet
     * @return le parent du sommet
     */
    private int parent(int index) {
        return index / 2;
    }

    /**
     * Retourne le fils gauche d'un sommet.
     * @param index l'indice du sommet
     * @return le fils gauche du sommet
     */
    private int leftChild(int index) {
        return 2 * index;
    }

    /**
     * Retourne le fils droit d'un sommet.
     * @param index l'indice du sommet
     * @return le fils droit du sommet
     */
    private int rightChild(int index) {
        return 2 * index + 1;
    }

    public int getSize() {
        return size;
    }
}

