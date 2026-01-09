package aa.app;

import aa.algo.Dijkstra;
import aa.graph.GraphList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Programme dijkstra_L : calcul des distances avec représentation par listes d'adjacence.
 * (Si vous voulez voir la documentation des classes de source de java utilisées ici : {@link FileWriter}, {@link IOException}
 * , {@link PrintWriter}, {@link NumberFormatException}, {@link System}, {@link String})
 * Usage: java aa.app.DijkstraL [fichier_graphe] [sommet_depart] [fichier_sortie]
 * @author Abdou THIAM
 * @since 1.0
 * @version 1.0
 * @see GraphList
 * @see Dijkstra
 */
public class DijkstraL {
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.err.println("Usage: java aa.app.DijkstraL <fichier_graphe> <sommet_depart> [fichier_sortie]");
            System.exit(1);
        }

        String inputFile = args[0];
        int source = Integer.parseInt(args[1]);
        String outputFile = args.length == 3 ?
                args[2] :
                null; // condition ternére

        try {
            // Lire le graphe
            GraphList graph = GraphList.readFromFile(inputFile);
            
            // Vérifier que le sommet source est valide
            if (source < 1 || source > graph.getOrder()) {
                System.err.println("Erreur: le sommet de départ doit être entre 1 et " + graph.getOrder());
                System.exit(1);
            }

            // Mesurer le temps CPU avant l'exécution
            long startTime = System.nanoTime();
            
            // Exécuter l'algorithme de Dijkstra
            Dijkstra.Result result = Dijkstra.execute(graph, source);
            
            // Mesurer le temps CPU après l'exécution
            long endTime = System.nanoTime();
            double cpuTime = (endTime - startTime) / 1_000_000_000.0; // Convertir en secondes

            // Écrire les résultats
            PrintWriter writer;
            if (outputFile != null) {
                writer = new PrintWriter(new FileWriter(outputFile));
            } else {
                writer = new PrintWriter(System.out);
            }

            // Afficher si le graphe est connexe
            if (result.isConnected) {
                writer.println("LE GRAPHE EST CONNEXE");
            } else {
                writer.println("LE GRAPHE N'EST PAS CONNEXE");
            }

            // Afficher les résultats pour chaque sommet
            for (int i = 1; i <= graph.getOrder(); i++) {
                int distance = result.distances[i];
                int predecessor = result.predecessors[i];
                
                // Si c'est le sommet source, le prédécesseur est -1
                if (i == source) {
                    predecessor = -1;
                }
                
                writer.println(i + " " + distance + " " + predecessor);
            }

            // Afficher le temps CPU
            writer.printf("TEMPS CPU: %.6f secondes%n", cpuTime);

            writer.flush();
            if (outputFile != null) {
                writer.close();
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier: " + e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Erreur: le sommet de départ doit être un entier");
            System.exit(1);
        }
    }
}

