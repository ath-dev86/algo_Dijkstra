package aa.app;

import aa.algo.Dijkstra;
import aa.graph.Graph;
import aa.graph.GraphList;
import java.io.IOException;

/**
 * Programme calculProvinces : partitionne un graphe en provinces selon des contraintes
 * de distance et de population.
 * 
 * Usage: java aa.app.CalculProvinces [fichier_graphe] [dMax] [popMax]
 * @author Abdou THIAM
 * @since 1.0
 * @version 1.0
 * @see Graph
 * @see GraphList
 * @see Dijkstra
 * @see Province
 */
public class CalculProvinces {
    
    /**
     * Représente une province avec sa capitale et ses villes.
     */
    private static class Province {
        int capitale;
        int[] villes;
        int populationTotale;

        Province(int capitale, int[] villes, int populationTotale) {
            this.capitale = capitale;
            this.villes = villes;
            this.populationTotale = populationTotale;
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java aa.app.CalculProvinces <fichier_graphe> <dMax> <popMax>");
            System.exit(1);
        }

        String inputFile = args[0];
        int dMax = Integer.parseInt(args[1]);
        int popMax = Integer.parseInt(args[2]);

        try {
            // Lire le graphe (on utilise GraphList pour ce programme)
            GraphList graph = GraphList.readFromFile(inputFile);
            
            // Calculer les provinces
            Province[] provinces =  calculerProvinces(graph, dMax, popMax);
            
            // Afficher les résultats
            for (Province province : provinces) {
                if (province != null) {
                    System.out.println("PROVINCE DE CAPITALE " + province.capitale + 
                                     " POPULATION " + province.populationTotale);
                    
                    // Calculer une seule fois les distances depuis la capitale
                    Dijkstra.Result result = Dijkstra.execute(graph, province.capitale);
                    
                    // Afficher toutes les villes de la province avec leur distance à la capitale
                    for (int ville : province.villes) {
                        int distance = result.distances[ville];
                        if (distance == Integer.MAX_VALUE) {
                            distance = -1;
                        }
                        System.out.println("VILLE " + ville + " DISTANCE CAPITALE " + distance);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier: " + e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Erreur: dMax et popMax doivent être des entiers");
            System.exit(1);
        }
    }

    /**
     * Calcule les provinces selon l'algorithme naïf suivant :
     * 1. Trier les villes par population décroissante
     * 2. Pour chaque ville non assignée :
     *    - Si sa population > popMax, créer une province seule avec cette ville
     *    - Sinon, essayer de l'ajouter à une province existante (en respectant les contraintes)
     *    - Si aucune province ne convient, créer une nouvelle province avec cette ville comme capitale
     */
    private static Province[] calculerProvinces(Graph graph, int dMax, int popMax) {
        int n = graph.getOrder();
        
        // Tableau pour marquer les villes assignées
        boolean[] assigned = new boolean[n + 1];
        
        // Tableau pour stocker les provinces
        Province[] provinces = new Province[n + 1];
        int provinceCount = 0;
        
        // Trier les villes par population décroissante (tri simple par sélection)
        int[] sortedVertices = new int[n];
        for (int i = 0; i < n; i++) {
            sortedVertices[i] = i + 1;
        }
        
        // Tri par sélection selon la population
        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (graph.getQuantity(sortedVertices[j]) > graph.getQuantity(sortedVertices[maxIndex])) {
                    maxIndex = j;
                }
            }
            // Échanger
            int temp = sortedVertices[i];
            sortedVertices[i] = sortedVertices[maxIndex];
            sortedVertices[maxIndex] = temp;
        }
        
        // Pour chaque ville, essayer de l'assigner à une province
        for (int i = 0; i < n; i++) {
            int ville = sortedVertices[i];
            if (assigned[ville]) {
                continue;
            }
            
            int popVille = graph.getQuantity(ville);
            
            // Si la population de la ville dépasse popMax, elle forme une province seule
            if (popVille > popMax) {
                int[] villes = new int[1];
                villes[0] = ville;
                provinces[provinceCount++] = new Province(ville, villes, popVille);
                assigned[ville] = true;
                continue;
            }
            
            // Chercher la meilleure province existante pour cette ville
            int bestProvince = -1;
            int minDistance = Integer.MAX_VALUE;
            
            for (int p = 0; p < provinceCount; p++) {
                Province prov = provinces[p];
                if (prov == null) continue;
                
                // Si la capitale seule dépasse popMax, on ne peut pas ajouter d'autres villes
                if (graph.getQuantity(prov.capitale) > popMax) {
                    continue;
                }
                
                // Vérifier la contrainte de population
                int nouvellePop = prov.populationTotale + popVille;
                if (nouvellePop <= popMax) {
                    // Calculer la distance de la ville à la capitale
                    Dijkstra.Result result = Dijkstra.execute(graph, prov.capitale);
                    int distance = result.distances[ville];
                    
                    // Vérifier la contrainte de distance
                    if (distance != -1 && distance <= dMax && distance < minDistance) {
                        minDistance = distance;
                        bestProvince = p;
                    }
                }
            }
            
            // Si on a trouvé une province, ajouter la ville
            if (bestProvince != -1) {
                Province prov = provinces[bestProvince];
                
                // Agrandir le tableau des villes
                int[] nouvellesVilles = new int[prov.villes.length + 1];
                for (int j = 0; j < prov.villes.length; j++) {
                    nouvellesVilles[j] = prov.villes[j];
                }
                nouvellesVilles[prov.villes.length] = ville;
                prov.villes = nouvellesVilles;
                prov.populationTotale += popVille;
                assigned[ville] = true;
            } else {
                // Si aucune province ne convient, créer une nouvelle province avec cette ville comme capitale
                int[] villes = new int[1];
                villes[0] = ville;
                provinces[provinceCount++] = new Province(ville, villes, popVille);
                assigned[ville] = true;
            }
        }
        
        // Créer un tableau final avec seulement les provinces non nulles
        Province[] result = new Province[provinceCount];
        for (int i = 0; i < provinceCount; i++) {
            result[i] = provinces[i];
        }
        
        return result;
    }
}

