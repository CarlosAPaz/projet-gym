package GYM;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @inheritDoc Rapport spécifique aux {@link GYM.Professionnel professionnels}.
 */
public class AvisPaiement extends Rapport {

    private Professionnel pro;
    private boolean besoinAvisPaiement = true;

    /**
     * Constructeur
     *
     * @param pro
     */
    public AvisPaiement(Professionnel pro) {
        this.pro = pro;
        this.contenu = creerAvisPaiement();
    }

    /**
     * Création de l'avis de paiement du professionnel qui contient le nom, le
     * numéro, l'adresse, la ville, la province et le code postal de celui-ci,
     * ainsi qu'un résumé des services qu'il a offert cette semaine
     *
     * @return contenu le contenu de l'avis de paiement
     */
    private String creerAvisPaiement() {
        String str = "---------------------------------------------\n";

        str += "AVIS DE PAIEMENT :\n---------------------------------------------\n"
                + "Nom :         " + pro.getNom() + "\n"
                + "Numéro :      " + pro.getNumero() + "\n"
                + "Adresse :     " + pro.getAdresse() + "\n"
                + "Ville :       " + pro.getVille() + "\n"
                + "Province :    " + pro.getProvince() + "\n"
                + "Code postal : " + pro.getCodePostal() + "\n\n"
                + "Résumé des services :\n\n";

        ArrayList<Confirmation> confirmations = new ArrayList<Confirmation>();
        for (Service service : pro.getServices()) {
            for (Seance seance : service.getSeancesH()) {
                for (Confirmation confirmation : seance.getConfirmations()) {
                    confirmations.add(confirmation);
                }
            }
        }

        if (confirmations.size() == 0) {
            str += "\tVous n'avez fourni aucun service cette semaine.\n";
            this.besoinAvisPaiement = false;
            return str;
        }

        // Mettre les confirmations en ordre chronologique
        Collections.sort(confirmations, new Comparator<Confirmation>() {
            @Override
            public int compare(Confirmation o1, Confirmation o2) {
                return o1.getDateActu().compareTo(o2.getDateActu());
            }
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
        for (Confirmation confirmation : confirmations) {
            Seance seance = ListeServices.trouverSeance(confirmation.getCodeSeance());
            str += "\tDate du service :                                                      "
                    + formatter.format(seance.getDateSeance()) + "\n";
            str += "\tDate et heure à laquelle les données étaient reçues par l'ordinateur : "
                    + formatter.format(confirmation.getDateActu()) + " " + confirmation.getHeureActuelle() + "\n";
            str += "\tNom du membre :                                                        "
                    + ListeClients.rechercheClient(confirmation.getNumeroMembre(), 1).getNom() + "\n";
            str += "\tNuméro du membre :                                                     "
                    + confirmation.getNumeroMembre() + "\n";
            str += "\tCode de la séance :                                                    "
                    + confirmation.getCodeSeance() + "\n";
            str += "\tMontant à payer :                                                      "
                    + 0.70 * seance.getFrais() + "\n\n";

        }

        return str;
    }

    @Override
    public void enregistrer() {
        new File("Enregistrements/AvisPaiement").mkdirs();

        String path = "Enregistrements/AvisPaiement/Avis_Paiement-" + pro.getNom() + "-" + LocalDate.now() + ".txt";

        try {
            Files.write(Paths.get(path), contenu.getBytes());
        } catch (IOException e) {
            System.out.println("\nErreur lors de l'enregistrement de l'avis de paiement...\n");
            e.printStackTrace();
        }
    }

    // Getter
    public boolean getBesoinAvisPaiement() {
        return this.besoinAvisPaiement;
    }
}