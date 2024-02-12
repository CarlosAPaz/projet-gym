package GYM;

/**
 * @inheritDoc Menu permettant de gérer les actions relatives aux {@link GYM.Client clients}.
 */
public class MenuGererClient implements Menu {

    private ControleurClient controleurClient = new ControleurClient();

    /**
     * Affiche les différentes actions possibles et permet à l'utilisateur de
     * choisir l'action qu'il désire effectuer
     */
    @Override
    public void afficheMenu() {

        int choix = choixAction();

        switch (choix) {
            // Ajouter membre
            case 1:
                controleurClient.ajouterMembre();
                afficheMenu();
                break;

            // Ajouter professionnel
            case 2:
                controleurClient.ajouterPro();
                afficheMenu();
                break;

            // MAJ dossier client
            case 3:
                System.out.println("---------------------------------------------");
                Client client = null;
                while (client == null) {
                    System.out.print("Numéro du client (9 chiffes): ");
                    String numero = MenuPrincipal.action.nextLine();
                    client = ListeClients.rechercheClient(numero, 1);
                }
                controleurClient.maintenanceCompte(client);
                afficheMenu();
                break;

            // Retour au menu principal
            case 4:
                MenuPrincipal mp = new MenuPrincipal();
                mp.afficheMenu();
                break;

            // Quitter
            default:
                MenuChoixProgramme mcp = new MenuChoixProgramme();
                mcp.afficheMenu();
                break;
        }
    }


    /**
     * Demande à l'utilisateur de choisir l'action à effectuer
     *
     * @return int numéro associé à l'action choisie
     */
    @Override
    public int choixAction() {

        int choix = -1;

        do {
            System.out.println("---------------------------------------------");
            System.out.println("Veuillez entrer une des actions suivantes :");
            System.out.println("[1] Ajouter membre");
            System.out.println("[2] Ajouter professionnel");
            System.out.println("[3] Maintenance compte client");
            System.out.println("[4] Retour menu principal");
            System.out.println("[5] Quitter");

            System.out.print("Choix : ");

            try {
                choix = Integer.parseInt(MenuPrincipal.action.nextLine());
            } catch (NumberFormatException e) {
                choix = -1;
            }

        } while (!(choix == 1 || choix == 2 || choix == 3 || choix == 4 || choix == 5));

        return choix;
    }
}

	