package GYM;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

/**
 * @inheritDoc Rapport spécifique représentant combien #GYM doit à chaque {@link GYM.Professionnel professionnel}.
 */
public class TEF extends Rapport {
    String nomPro;
    String numPro;
    String montant;

    /**
     * Constructeur du TEF
     *
     * @param nomPro  nom du professionnel
     * @param numPro  numéro unique du professionnel
     * @param montant montant à régler au professionnel
     */
    public TEF(String nomPro, String numPro, String montant) {
        this.nomPro = nomPro;
        this.numPro = numPro;
        this.montant = montant;

        contenu = "\n-------------------------------------------------\n";
        contenu += "Professionnel : " + nomPro + " (" + numPro + ")\n";
        contenu += "Montant à transférer : " + montant + "$\n";
        contenu += "-------------------------------------------------\n";
    }

    /**
     * Création des enregistrements sur le disque
     */
    @Override
    public void enregistrer() {
        new File("Enregistrements/TEF").mkdirs();

        String path = "Enregistrements/TEF/TEF-" + nomPro + "-" + LocalDate.now() + ".txt";

        try {
            Files.write(Paths.get(path), contenu.getBytes());
        } catch (IOException e) {
            System.out.println("\nErreur lors de l'enregistrement du TEF...\n");
            e.printStackTrace();
        }
    }
}