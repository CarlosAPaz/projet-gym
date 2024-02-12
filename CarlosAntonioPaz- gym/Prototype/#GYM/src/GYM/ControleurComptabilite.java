package GYM;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe de contrôle pour les méthodes reliées à la comptabilité.
 */
public class ControleurComptabilite {

    private ArrayList<HashMap<String, String>> rapportsPro;

    /**
     * Créer un HashMap pour chaque {@link GYM.Professionnel professionnel} contenant son nom, son numéro
     * ainsi que le nombre de services qu'il a donné dans la semaine et le
     * montant généré par ceux-ci.
     *
     * @return liste des rapports des professionnels
     */
    public ArrayList<HashMap<String, String>> creerRapportsPro() {

        ArrayList<Service> listeServices = ListeServices.listeServices;
        ArrayList<HashMap<String, String>> rapports = new ArrayList<>();

        outerLoop:
        for (Service service : listeServices) {

            String numPro = service.getNumeroPro();

            for (HashMap<String, String> rapport : rapports) {

                // Rapport du professionnel trouvé : MAJ de celui-ci avec le nouveau service
                if (rapport.containsValue(numPro)) {
                    int nb = Integer.parseInt(rapport.get("nbServices"));
                    double frais = Double.parseDouble(rapport.get("frais"));

                    nb += service.getServicesDonnesH();
                    frais += service.getMontantGenereH();

                    rapport.replace("nbServices", nb + "");
                    rapport.replace("frais", frais + "");

                    break outerLoop;
                }
            }

            // Rapport du professionnel non trouvé : création d'un nouveau rapport
            Professionnel pro = (Professionnel) ListeClients.rechercheClient(numPro, 1);
            HashMap<String, String> rapport = new HashMap<String, String>();

            rapport.put("numPro", numPro);
            rapport.put("nomPro", pro.getNom());
            rapport.put("nbServices", service.getServicesDonnesH() + "");
            rapport.put("frais", service.getMontantGenereH() + "");

            rapports.add(rapport);
        }

        return rapports;
    }


    /**
     * Création du rapport de synthèse et envoi de celui-ci au gérant
     */
    public void creerRapportSynthese() {
        this.rapportsPro = creerRapportsPro();
        RapportSynthese rapportSynthese = new RapportSynthese(rapportsPro);
        rapportSynthese.enregistrer();
        rapportSynthese.envoyerRapport();
    }


    /**
     * Création des {@link GYM.TEF TEF} pour chaque {@link GYM.Professionnel professionnel} contenant le nom du
     * professionnel, le numéro du professionnel et le montant à lui transférer
     * pour ces services de la semaine.
     */
    public void creerTEFs() {
        this.rapportsPro = creerRapportsPro();

        // Pour chaque professionnel
        for (HashMap<String, String> rapport : rapportsPro) {

            // Création d'un TEF
            String nomPro = rapport.get("nomPro");
            String numPro = rapport.get("numPro");
            String montant = rapport.get("frais");
            TEF tef = new TEF(nomPro, numPro, montant);

            // Enregistrement du TEF
            tef.imprimer();
            tef.enregistrer();
        }
    }


    /**
     * Création de la {@link GYM.Facture facture} de la semaine pour le {@link GYM.Membre membre}
     *
     * @param membre membre dont on veut créer la facture
     */
    public void creerFacture(Membre membre) {
        Facture facture = new Facture(membre);
        facture.imprimer();
        facture.enregistrer();
    }


    /**
     * Création de l'{@link GYM.AvisPaiement avis de paiement} de la semaine pour le {@link GYM.Professionnel
     * professionnel}
     *
     * @param pro professionnel dont on veut créer l'avis de paiement
     */
    public void creerAvisPaiement(Professionnel pro) {
        AvisPaiement avisPaiement = new AvisPaiement(pro);
        avisPaiement.imprimer();
        avisPaiement.enregistrer();
    }

    /**
     * Envoie du {@link GYM.RapportSynthese rapport de synthèse} au gérant et création des {@link GYM.TEF TEF},
     * ainsi que des {@link GYM.Facture factures} pour chaque {@link GYM.Membre membre} facturable et des
     * {@link GYM.AvisPaiement avis de paiement} pour chaque {@link GYM.Professionnel professionnel} payable
     */
    public void procedureComptable() {
        // Création du rapport de syntèse
        this.creerRapportSynthese();

        // Création des TEFs
        this.creerTEFs();

        // Création des factures et des avis de paiement
        for (Client client : ListeClients.listeClients) {

            // Factures
            if (client instanceof Membre) {
                Membre membre = (Membre) client;
                Facture facture = new Facture(membre);
                if (facture.getBesoinFacture()) facture.enregistrer();

                // Avis de paiement
            } else {
                Professionnel pro = (Professionnel) client;
                AvisPaiement avisPaiement = new AvisPaiement(pro);
                if (avisPaiement.getBesoinAvisPaiement()) avisPaiement.enregistrer();
            }
        }
    }
}