package aa.app;

import java.util.Random;

/**
 * Générateur de graphes aléatoires pour les tests et expérimentations.
 * 
 * <p>Ce programme génère des graphes non orientés et pondérés avec des paramètres
 * configurables. Les graphes sont générés selon un format spécifique compatible
 * avec les classes {@link aa.graph.GraphList} et {@link aa.graph.GraphMatrix}.
 * 
 * <p>Le format de sortie suit la structure suivante :
 * <pre>{@code
 * n
 * 1 population1 voisin1 coût1 voisin2 coût2 ... 0
 * 2 population2 voisin1 coût1 voisin2 coût2 ... 0
 * ...
 * n populationn voisin1 coût1 voisin2 coût2 ... 0
 * }</pre>
 * 
 * <p>Les arêtes sont générées de manière aléatoire selon un pourcentage donné,
 * et les coûts sont choisis aléatoirement dans une plage spécifiée.
 * 
 * <h2>Usage :</h2>
 * <pre>{@code
 * java aa.app.GraphGenerator [nbSommets] [pourArcs] [poidsMin] [poidsMax] [popMin] [popMax]
 * }</pre>
 * 
 * <h2>Paramètres :</h2>
 * <ul>
 *   <li>nbSommets : nombre de sommets du graphe (défaut: 10)</li>
 *   <li>pourArcs : pourcentage de probabilité qu'une arête existe entre deux sommets (défaut: 40)</li>
 *   <li>poidsMin : poids minimum des arêtes (défaut: 1)</li>
 *   <li>poidsMax : poids maximum des arêtes (défaut: 10)</li>
 *   <li>popMin : population minimum d'un sommet (défaut: 10)</li>
 *   <li>popMax : population maximum d'un sommet (défaut: 1000)</li>
 * </ul>
 * 
 * <h2>Exemple :</h2>
 * <pre>{@code
 * java aa.app.GraphGenerator 20 50 1 100 50 500 > graphe.txt
 * }</pre>
 * Génère un graphe de 20 sommets avec 50% de probabilité d'arêtes,
 * des poids entre 1 et 100, et des populations entre 50 et 500.
 * 
 * @author abergey
 * @version 1.0
 * @since 1.0
 * @see aa.graph.GraphList
 * @see aa.graph.GraphMatrix
 */
public class GraphGenerator {
    /** Nombre de sommets par défaut */
    public static final int NB_SOMMETS_DEFAUT = 10;
    
    /** Pourcentage d'arcs par défaut */
    public static final int POURCENTAGE_ARCS_DEFAULT = 40;
    
    /** Poids maximum par défaut */
    public static final int POIDS_MAX_DEFAUT = 10;
    
    /** Poids minimum par défaut */
    public static final int POIDS_MIN_DEFAUT = 1;
    
    /** Population maximum par défaut */
    public static final int POP_MAX_DEFAUT = 1000;
    
    /** Population minimum par défaut */
    public static final int POP_MIN_DEFAUT = 10;

    /**
     * Point d'entrée principal du générateur de graphes.
     * 
     * <p>Lit les paramètres depuis les arguments de la ligne de commande
     * et génère un graphe aléatoire selon ces paramètres. Le graphe est
     * affiché sur la sortie standard au format attendu par les classes
     * de lecture de graphes.
     * 
     * @param args les arguments de la ligne de commande :
     *             [nbSommets] [pourArcs] [poidsMin] [poidsMax] [popMin] [popMax]
     */
    public static void main(String[] args) {

        // initialisation des paramètres du graphe
        int nbSommets = NB_SOMMETS_DEFAUT ;
        int pourArcs = POURCENTAGE_ARCS_DEFAULT ;
        int poidsMax = POIDS_MAX_DEFAUT ;
        int poidsMin = POIDS_MIN_DEFAUT ;
        int popMax   = POP_MAX_DEFAUT ;
        int popMin   = POP_MIN_DEFAUT ;
        switch (args.length){
            case 6 : popMax =  Integer.parseInt(args[5]) ;
            case 5 : popMin =  Integer.parseInt(args[4]) ;
            case 4 : poidsMax = Integer.parseInt(args[3]) ;
            case 3 : poidsMin = Integer.parseInt(args[2]) ;
            case 2 : pourArcs = Integer.parseInt(args[1]) ;
            case 1 : nbSommets = Integer.parseInt(args[0]) ;
        }

        // Génération du graphe

        System.out.println("" + nbSommets+" ");
        Random r = new Random();
        for (int x = 1 ; x <= nbSommets ; x++ ){
            int pop  = r.nextInt(popMax-popMin+1)+popMin ;
            System.out.print ("" + x + " " + pop + " ");
            for (int y = 1 ; y < x ; y++){
                boolean existeArc = (r.nextInt(100)<pourArcs);
                if (existeArc){ // Il y a un arc entre x et y
                    System.out.print("" +y+ " ");
                    int poids = r.nextInt(poidsMax-poidsMin+1)+poidsMin ;
                    System.out.print(""+poids+ " ");
                }
            }
            System.out.println (""+0+ " ");
        }
    }
}
