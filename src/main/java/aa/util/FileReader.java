package aa.util;

import java.io.BufferedReader;
import java.io.IOException;


/**
 * Utilitaire pour lire tous les entiers d'un fichier,
 * indépendamment de la mise en forme (une ou plusieurs lignes).
 */
public class FileReader {
    
    /**
     * Lit tous les entiers d'un fichier et les retourne dans un tableau.
     * On pourrait supposer que le fichier contient uniquement des entiers, mais il peut contenir des espaces, des tabulations, des nouvelles lignes, etc.
     * @param filename le nom du fichier
     * @return le tableau d'entiers
     * @throws IOException si le fichier n'est pas trouvé ou si le format est invalide
     */
    public static int[] readAllIntegers(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new java.io.FileReader(filename));
        
        // Utiliser une liste dynamique (implémentée manuellement)
        DynamicArray tokens = new DynamicArray();
        
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            
            String[] parts = line.split("\\s+");
            for (String part : parts) {
                if (!part.isEmpty()) {
                    tokens.add(Integer.parseInt(part));
                }
            }
        }
        
        reader.close();
        return tokens.toArray();
    }
    
    /**
     * Tableau dynamique simple pour stocker les entiers.
     */
    private static class DynamicArray {
        private int[] array;
        private int size;
        private static final int INITIAL_CAPACITY = 100;
        
        public DynamicArray() {
            this.array = new int[INITIAL_CAPACITY];
            this.size = 0;
        }
        
        public void add(int value) {
            if (size >= array.length) {
                // Agrandir le tableau
                int[] newArray = new int[array.length * 2];
                for (int i = 0; i < size; i++) {
                    newArray[i] = array[i];
                }
                array = newArray;
            }
            array[size++] = value;
        }
        
        public int[] toArray() {
            int[] result = new int[size];
            for (int i = 0; i < size; i++) {
                result[i] = array[i];
            }
            return result;
        }
    }
}

