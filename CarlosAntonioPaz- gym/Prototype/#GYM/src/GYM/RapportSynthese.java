package GYM;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @inheritDoc Rapport spécifique créé hebdomadairement et envoyé au gérant.
 */
public class RapportSynthese extends Rapport {

    private int nbTotalServices = 0;
    private double totalFrais = 0;
    private ArrayList<HashMap<String, String>> rapportsPro;

    /**
     * Constructeur
     *
     * @param rapportsPro liste contenant les rapports individuels des professionnels
     */
    public RapportSynthese(ArrayList<HashMap<String, String>> rapportsPro) {
        this.rapportsPro = rapportsPro;
        this.contenu = creerRapport();
    }


    /**
     * Création du rapport de synthèse contenant les comptes payables et la
     * liste de tous les professionnels qui doivent être payés cette semaine-là,
     * le nombre de services de chacun et le total de leurs frais pour cette
     * semaine-là.
     * A la fin du rapport, le nombre total de professionnels qui ont fourni des
     * services cette semaine-là, le nombre total de services et le total des
     * frais sont affichés
     */
    public String creerRapport() {

        String str = "\n        Professionnel            Nombre de services     Frais\n";
        str += "-------------------------------------------------------------\n";

        for (HashMap<String, String> rapport : rapportsPro) {

            // Infos à afficher
            String pro = rapport.get("nomPro") + " (" + rapport.get("numPro") + ")";
            String nbServices = rapport.get("nbServices");
            String frais = rapport.get("frais");

            // Padding
            String paddedPro = String.format("%-25s", pro);
            String paddedNbServices = String.format("%18s", nbServices);
            String paddedFrais = String.format("%10s", frais);

            str += "        " + paddedPro + paddedNbServices + paddedFrais + "\n";

            // MAJ des compteurs de services et frais totaux
            nbTotalServices += Integer.parseInt(rapport.get("nbServices"));
            totalFrais += Double.parseDouble(rapport.get("frais"));
        }

        // Padding
        String paddedNbTotalPro = String.format("%13s", rapportsPro.size());
        String paddedNbTotalServices = String.format("%30s", nbTotalServices);
        String paddedTotalFrais = String.format("%10s", totalFrais);

        str += "-------------------------------------------------------------\n";

        // Nombre total de professionnels, de services et des frais
        str += "Total : ";
        str += paddedNbTotalPro + paddedNbTotalServices + paddedTotalFrais + "\n";

        return str;
    }

    /**
     * Méthode qui simule l'envoi du rapport au gérant
     */
    public void envoyerRapport() {
        this.imprimer();
        System.out.println("\nRapport envoyé au gérant\n");
    }


    /**
     * Enregistre le rapport sur le disque
     */
    @Override
    public void enregistrer() {
        new File("Enregistrements/RapportSynthese").mkdirs();

        String path = "Enregistrements/RapportSynthese/Rapport_Synthese-" + LocalDate.now() + ".txt";

        try {
            Files.write(Paths.get(path), contenu.getBytes());
        } catch (IOException e) {
            System.out.println("\nErreur lors de l'enregistrement du rapport de synthèse...\n");
            e.printStackTrace();
        }
    }
}