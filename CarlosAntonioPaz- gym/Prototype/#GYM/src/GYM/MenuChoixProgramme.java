package GYM;

/**
 * @inheritDoc Menu permettant de choisir la version voulue du logiciel.
 */
class MenuChoixProgramme implements Menu {

    @Override
    public void afficheMenu() {
        int choix = choixAction();

        switch (choix) {
            // Version sur ordinateur
            case 1:
                MenuPrincipal mp = new MenuPrincipal();
                mp.afficheMenu();
                this.afficheMenu();
                break;

            // Version mobile
            case 2:
                Client client = login();

                if (client == null) {
                    System.out.println("L'addresse courriel ne correspond à aucun de nos membres ou professionnels. Veuillez vous rendre à une succursale et vous inscrire au près d'un agent.");
                    this.afficheMenu();
                }

                MenuApplicationMobile map = new MenuApplicationMobile(client);
                map.afficheMenu();
                this.afficheMenu();
                break;

            // Quitter
            default:
                MenuPrincipal.action.close();
                System.out.println("Fin du programme");
                System.exit(0);
                break;
        }
    }

    @Override
    public int choixAction() {
        int choix = -1;

        do {
            System.out.println("---------------------------------------------");
            System.out.println("Veuillez choisir la plateforme voulue :");
            System.out.println("[1] Version d'ordinateur (agent)");
            System.out.println("[2] Version mobile (téléphone)");
            System.out.println("[3] Quitter");
            System.out.print("Choix : ");

            try {
                choix = Integer.parseInt(MenuPrincipal.action.nextLine());
            } catch (NumberFormatException e) {
                choix = -1;
            }
        } while (!(choix == 1 || choix == 2 || choix == 3));

        return choix;
    }

    /**
     * Permet d'identifier le {@link GYM.Client client} au lancement de la version mobile.
     *
     * @return client accédant au programme
     */
    private Client login() {
        System.out.print("Veuillez entrer votre addresse courriel (compte Facebook) : ");
        String courriel = MenuPrincipal.action.nextLine();
        return ListeClients.rechercheClient(courriel, 2);
    }
}