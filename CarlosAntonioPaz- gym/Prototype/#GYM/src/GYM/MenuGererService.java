package GYM;

import java.util.Scanner;

/**
 * @inheritDoc Menu permettant de gérer les actions relatives aux {@link GYM.Service services}.
 */
public class MenuGererService implements Menu {

    private ControleurService controleur;
    private Scanner demande = MenuPrincipal.action;


    /**
     * Constructeur
     *
     * @param pro professionnel à gérer
     */
    public MenuGererService(Professionnel pro) {
        this.controleur = new ControleurService(pro);
    }


    /**
     * Affiche les différentes actions possibles et permet à l'utilisateur de
     * choisir l'action qu'il désire effectuer
     */
    @Override
    public void afficheMenu() {

        int choix = choixAction();

        switch (choix) {
            // Créer un service
            case 1:
                controleur.creerService();
                afficheMenu();
                break;

            // Maintenance d'un service
            case 2:
                controleur.maintenanceService();
                afficheMenu();
                break;

            // Consulter les inscriptions d'un service
            case 3:
                controleur.consulterInscriptions();
                afficheMenu();
                break;

            // Revenir au menu principal
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
            System.out.println("[1] Ajouter un service");
            System.out.println("[2] Maintenance service");
            System.out.println("[3] Consulter les inscriptions");
            System.out.println("[4] Retour au menu principal");
            System.out.println("[5] Quitter");

            System.out.print("Choix : ");

            try {
                choix = Integer.parseInt(demande.nextLine());
            } catch (NumberFormatException e) {
                choix = -1;
            }

        } while (choix < 1 || choix > 5);

        return choix;
    }
}
