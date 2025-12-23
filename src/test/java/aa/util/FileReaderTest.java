package aa.util;

import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe FileReader.
 */
public class FileReaderTest {
    private String testDir;

    @Before
    public void setUp() {
        testDir = "src/test/resources/";
        // Créer le répertoire s'il n'existe pas
        try {
            Path path = Paths.get(testDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            // Ignorer si le répertoire existe déjà
        }
    }

    @Test
    public void readAllIntegersMultipleLines() throws IOException {
        // Créer un fichier de test avec plusieurs lignes
        String filename = testDir + "test_multiple_lines.txt";
        FileWriter writer = new FileWriter(filename);
        writer.write("1 2 3\n");
        writer.write("4 5 6\n");
        writer.write("7 8 9");
        writer.close();

        int[] result = FileReader.readAllIntegers(filename);
        
        assertEquals(9, result.length);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, result);
        
        // Nettoyer
        Files.deleteIfExists(Paths.get(filename));
    }

    @Test
    public void readAllIntegersSingleLine() throws IOException {
        // Créer un fichier de test avec une seule ligne
        String filename = testDir + "test_single_line.txt";
        FileWriter writer = new FileWriter(filename);
        writer.write("6 1 200 0 2 0 1 61 0");
        writer.close();

        int[] result = FileReader.readAllIntegers(filename);
        
        assertEquals(9, result.length);
        assertArrayEquals(new int[]{6, 1, 200, 0, 2, 0, 1, 61, 0}, result);
        
        // Nettoyer
        Files.deleteIfExists(Paths.get(filename));
    }

    @Test
    public void readAllIntegersWithEmptyLines() throws IOException {
        // Créer un fichier avec des lignes vides
        String filename = testDir + "test_empty_lines.txt";
        FileWriter writer = new FileWriter(filename);
        writer.write("1 2 3\n");
        writer.write("\n");
        writer.write("4 5 6\n");
        writer.write("   \n");
        writer.write("7 8 9");
        writer.close();

        int[] result = FileReader.readAllIntegers(filename);
        
        assertEquals(9, result.length);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, result);
        
        // Nettoyer
        Files.deleteIfExists(Paths.get(filename));
    }

    @Test
    public void readAllIntegersWithMultipleSpaces() throws IOException {
        // Créer un fichier avec plusieurs espaces entre les nombres
        String filename = testDir + "test_multiple_spaces.txt";
        FileWriter writer = new FileWriter(filename);
        writer.write("1   2    3\n");
        writer.write("4\t5  6");
        writer.close();

        int[] result = FileReader.readAllIntegers(filename);
        
        assertEquals(6, result.length);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, result);
        
        // Nettoyer
        Files.deleteIfExists(Paths.get(filename));
    }

    @Test
    public void readAllIntegersEmptyFile() throws IOException {
        // Créer un fichier vide
        String filename = testDir + "test_empty.txt";
        FileWriter writer = new FileWriter(filename);
        writer.close();

        int[] result = FileReader.readAllIntegers(filename);
        
        assertEquals(0, result.length);
        
        // Nettoyer
        Files.deleteIfExists(Paths.get(filename));
    }

    @Test
    public void readAllIntegersFileWithOnlySpaces() throws IOException {
        // Créer un fichier avec seulement des espaces
        String filename = testDir + "test_only_spaces.txt";
        FileWriter writer = new FileWriter(filename);
        writer.write("   \n   \n\t");
        writer.close();

        int[] result = FileReader.readAllIntegers(filename);
        
        assertEquals(0, result.length);
        
        // Nettoyer
        Files.deleteIfExists(Paths.get(filename));
    }

    @Test(expected = IOException.class)
    public void readAllIntegersFileNotFound() throws IOException {
        // Tester qu'une exception est levée si le fichier n'existe pas
        FileReader.readAllIntegers("fichier_inexistant.txt");
    }

    @Test
    public void readAllIntegersRealGraphFile() throws IOException {
        // Tester avec le fichier d'exemple réel
        String filename = "data/graphe_exemple.txt";
        int[] result = FileReader.readAllIntegers(filename);
        
        // Vérifier qu'on a bien lu tous les entiers
        assertTrue(result.length > 0);
        assertEquals(6, result[0]); // Premier entier est l'ordre du graphe
    }

    @Test(expected = NumberFormatException.class)
    public void readAllIntegersWithLetters() throws IOException {
        // Créer un fichier avec des chiffres et des lettres mélangés
        // Le FileReader doit lever une NumberFormatException
        String filename = testDir + "test_letters.txt";
        FileWriter writer = new FileWriter(filename);
        writer.write("1 2 abc 3 4\n");
        writer.write("5 def 6 7\n");
        writer.write("8 9 xyz 10");
        writer.close();

        try {
            FileReader.readAllIntegers(filename);
        } finally {
            // Nettoyer
            Files.deleteIfExists(Paths.get(filename));
        }
    }

    @Test
    public void readAllIntegersWithNumbersAndSpaces() throws IOException {
        // Créer un fichier avec seulement des chiffres (sans lettres)
        // pour vérifier que les chiffres sont bien lus
        String filename = testDir + "test_numbers_only.txt";
        FileWriter writer = new FileWriter(filename);
        writer.write("1 2 3 4 5\n");
        writer.write("6 7 8 9 10");
        writer.close();

        int[] result = FileReader.readAllIntegers(filename);
        
        assertEquals(10, result.length);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, result);
        
        // Nettoyer
        Files.deleteIfExists(Paths.get(filename));
    }
}