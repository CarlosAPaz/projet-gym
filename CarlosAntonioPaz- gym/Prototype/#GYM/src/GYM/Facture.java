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
 * @inheritDoc Rapport spécifique aux {@link GYM.Membre membres}.
 */
public class Facture extends Rapport {
    private Membre membre;
    private boolean besoinFacture = true;

    public Facture(Membre membre) {
        this.membre = membre;
        this.contenu = creerFacture();
    }

    /**
     * Création de la facture du membre, contenant ses informations personnelles
     * ainsi qu'un résumé des services auxquels il a assisté cette semaine
     *
     * @return contenu le contenu de la facture
     */
    private String creerFacture() {
        ArrayList<Seance> seancesConfirmees = new ArrayList<Seance>();

        String str = "---------------------------------------------\n";

        str += "FACTURE :\n---------------------------------------------\n"
                + "Nom :         " + membre.getNom() + "\n"
                + "Numéro :      " + membre.getNumero() + "\n"
                + "Adresse :     " + membre.getAdresse() + "\n"
                + "Ville :       " + membre.getVille() + "\n"
                + "Province :    " + membre.getProvince() + "\n"
                + "Code postal : " + membre.getCodePostal() + "\n\n"
                + "Résumé des services :\n\n";

        // Sortir toutes les seances auxquelles le membre etait inscrit dans la derniere semaine
        for (Service service : ListeServices.listeServices) {
            for (Seance seance : service.getSeancesH()) {
                for (Confirmation confirmation : seance.getConfirmations()) {
                    if (confirmation.getNumeroMembre().equals(membre.getNumero())) {
                        seancesConfirmees.add(seance);
                        break;
                    }
                }
            }
        }

        if (seancesConfirmees.size() == 0) {
            str += "\tVous n'avez participé à aucune séance cette semaine.\n";
            this.besoinFacture = false;
            return str;
        }

        // Mettre les seances en ordre chronologique
        Collections.sort(seancesConfirmees, new Comparator<Seance>() {
            @Override
            public int compare(Seance o1, Seance o2) {
                return o1.getDateSeance().compareTo(o2.getDateSeance());
            }
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
        for (Seance seance : seancesConfirmees) {
            str += "\tDate :                 " + formatter.format(seance.getDateSeance()) + "\n";
            String nomPro = ListeClients.rechercheClient(seance.getNumPro(), 1).getNom();
            str += "\tNom du professionnel : " + nomPro + "\n";
            str += "\tNom du service :       " + seance.getNomTypeService() + "\n\n";
        }

        return str;
    }


    @Override
    public void enregistrer() {
        new File("Enregistrements/Factures").mkdirs();

        String path = "Enregistrements/Factures/Factures-" + membre.getNom() + "-" + LocalDate.now() + ".txt";

        try {
            Files.write(Paths.get(path), contenu.getBytes());
        } catch (IOException e) {
            System.out.println("\nErreur lors de l'enregistrement de la facture...\n");
            e.printStackTrace();
        }
    }

    // Getter
    public boolean getBesoinFacture() {
        return this.besoinFacture;
    }
}