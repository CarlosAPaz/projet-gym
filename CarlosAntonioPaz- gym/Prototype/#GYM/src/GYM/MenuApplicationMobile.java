package GYM;

/**
 * @inheritDoc Menu de l'application mobile.
 */
public class MenuApplicationMobile implements Menu {
    private Client client;
    private ControleurClient controleurClient = new ControleurClient();
    private ControleurService controleurService = new ControleurService();
    private ControleurComptabilite controleurComptabilite = new ControleurComptabilite();

    /**
     * Constructeur
     *
     * @param client client accédant à l'application
     */
    public MenuApplicationMobile(Client client) {
        this.client = client;
    }

    @Override
    public void afficheMenu() {
        if (this.client instanceof Membre) {
            Membre membre = (Membre) this.client;

            if (membre.getSuspendu()) {
                System.out.println("Membre suspendu");
                System.out.println("Vous avez été suspendu puisque des frais impayés vous sont réclamés.\nVeuillez vous rendre en succursale et vous acquitter des frais dus.");
            }
        }

        int choix = choixAction();

        switch (choix) {
            // OPTIONS MEMBRE

            // 1.MAJ informations
            case 1:
                controleurClient.maintenanceCompte(client);
                this.afficheMenu();
                break;

            // 2.Inscription seance
            case 2:
                controleurService.inscriptionSeance(this.client);
                this.afficheMenu();
                break;

            // 3.Resume seance semaine
            case 3:
                controleurComptabilite.creerFacture((Membre) this.client);
                this.afficheMenu();
                break;

            // OPTIONS PRO

            // 1.MAJ informations
            case 5:
                controleurClient.maintenanceCompte(client);
                this.afficheMenu();
                break;

            // 2.Consulter inscriptions
            case 6:
                controleurService.consulterInscriptions();
                this.afficheMenu();
                break;

            // 3.Confirmer inscriptions
            case 7:

                controleurService = new ControleurService((Professionnel) this.client);

                Service service = controleurService.choisirService();

                if (service == null) {
                    this.afficheMenu();
                    break;
                }

                Seance seance = service.getSeanceJour();

                if (seance == null) {
                    System.out.println("Le service ne possède aucune séance aujourd'hui");
                    this.afficheMenu();
                    break;
                }

                boolean continuer = false;
                do {
                    System.out.println("Veuillez scanner le code QR, ou entrer manuellement le code de membre.");
                    Client client = controleurClient.validerNumero();

                    // Verifier si une erreur s'est produite
                    try {
                        Membre membre = (Membre) client;
                        if (client == null || membre.getSuspendu()) {
                            System.out.println("Veuillez réessayer...");
                            continuer = true;
                            continue;
                        }
                    } catch (ClassCastException e) {
                        System.out.println("Le numéro saisi est celui d'un professionnel." +
                                "\nVeuillez réessayer...");
                        continuer = true;
                        continue;
                    }

                    controleurService.confirmerSeance(seance, client);

                    System.out.print("Voulez-vous confirmer une autre inscription ? (Y/N) :");
                    continuer = MenuPrincipal.action.nextLine().equalsIgnoreCase("y");
                } while (continuer);
                this.afficheMenu();
                break;

            // 4.Resume seances semaine
            case 8:
                controleurComptabilite.creerAvisPaiement((Professionnel) this.client);
                this.afficheMenu();
                break;

            // Deconnexion
            default:
                return;
        }
    }


    @Override
    public int choixAction() {
        String statut = "";
        if (this.client instanceof Membre) statut = "Membre";
        else if (this.client instanceof Professionnel) statut = "Professionnel";

        // Informations communes affichees a l'ecran
        System.out.println("---------------------------------------------");
        System.out.println("Votre statut : " + statut);
        System.out.println("Votre nom : " + this.client.getNom());
        System.out.println("Votre numéro : " + this.client.getNumero());
        System.out.println("(... le code QR serait affiché ici ...)");

        int choix = -1;

        // Options pour un membre
        if (this.client instanceof Membre) {
            do {
                System.out.println("\nVeuillez choisir une des actions suivantes :");
                System.out.println("[1] Modifier informations personnelles");
                System.out.println("[2] Inscription service");
                System.out.println("[3] Facture");
                System.out.println("[4] Déconnexion");
                System.out.print("Choix : ");

                try {
                    choix = Integer.parseInt(MenuPrincipal.action.nextLine());
                } catch (NumberFormatException e) {
                    choix = -1;
                }

            } while (!(choix == 1 || choix == 2 || choix == 3 || choix == 4));

            // Options pour un professionnnel
        } else if (this.client instanceof Professionnel) {

            controleurService = new ControleurService((Professionnel) client);

            do {
                System.out.println("\nVeuillez choisir une des actions suivantes :");
                System.out.println("[1] Modifier informations personnelles");
                System.out.println("[2] Consulter inscriptions");
                System.out.println("[3] Confirmer inscriptions");
                System.out.println("[4] Avis de paiement");
                System.out.println("[5] Déconnexion");
                System.out.print("Choix : ");

                try {
                    choix = Integer.parseInt(MenuPrincipal.action.nextLine());
                } catch (NumberFormatException e) {
                    choix = -1;
                }
            } while (!(choix == 1 || choix == 2 || choix == 3 || choix == 4 || choix == 5));

            // Ajustement pour enlever les overlap avec les options membre
            choix += 4;
        }

        return choix;
    }
}