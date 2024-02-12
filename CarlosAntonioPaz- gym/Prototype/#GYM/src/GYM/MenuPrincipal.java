package GYM;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @inheritDoc Menu principal de la version desktop (de l'agent).
 */
public class MenuPrincipal implements Menu {

    private Client client;
    private ControleurClient controleurClient = new ControleurClient();
    private ControleurService controleurService = new ControleurService();
    private ControleurComptabilite controleurComptabilite = new ControleurComptabilite();
    static Scanner action = new Scanner(System.in);

    /**
     * Affiche les différentes actions possibles et permet à l'utilisateur de
     * choisir l'action qu'il désire effectuer
     */
    @Override
    public void afficheMenu() {
        timerProcedureComptable();

        int choix = choixAction();

        switch (choix) {
            // Valider
            case 1:
                controleurClient.validerNumero();
                afficheMenu();
                break;

            // Gérer client
            case 2:
                MenuGererClient gc = new MenuGererClient();
                gc.afficheMenu();
                break;

            // Gérer services
            case 3:
                client = controleurClient.validerNumero();

                // Vérifie que le client est bien un professionnel
                if (!(client instanceof Professionnel)) {
                    System.out.println("... Vous n'êtes pas un professionnel!");
                    afficheMenu();
                }

                MenuGererService gs = new MenuGererService((Professionnel) client);
                gs.afficheMenu();
                break;

            // Inscription à une séance
            case 4:
                client = controleurClient.validerNumero();

                // Vérifie que le client est bien un membre
                if (!(client instanceof Membre)) {
                    System.out.println("... Vous n'êtes pas un membre!");
                    afficheMenu();
                }

                controleurService.inscriptionSeance(this.client);
                afficheMenu();
                break;

            // Confirmation à une séance
            case 5:
                client = controleurClient.validerNumero();

                // Vérifie que le client est bien un membre
                if (!(client instanceof Membre)) {
                    System.out.println("... Vous n'êtes pas un membre!");
                    afficheMenu();
                    return;
                }

                confirmationSeance();
                afficheMenu();
                break;

            // Rapport de synthèse
            case 6:
                controleurComptabilite.creerRapportSynthese();
                afficheMenu();
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
            System.out.println("Veuillez choisir une des actions suivantes :");
            System.out.println("[1] Valider numéro");
            System.out.println("[2] Gérer client");
            System.out.println("[3] Gérer services");
            System.out.println("[4] Inscription service");
            System.out.println("[5] Confirmation service");
            System.out.println("[6] Rapport de synthèse");
            System.out.println("[7] Quitter");

            System.out.print("Choix : ");

            try {
                choix = Integer.parseInt(action.nextLine());
            } catch (NumberFormatException e) {
                choix = -1;
            }

        } while (!(choix == 1 || choix == 2 || choix == 3 || choix == 4 || choix == 5 || choix == 6 || choix == 7));

        return choix;
    }


    /**
     * Confirmation d'inscription d'un {@link GYM.Membre membre} à une {@link GYM.Seance séance}
     */
    private void confirmationSeance() {
        System.out.println("---------------------------------------------");

        Seance seance = null;

        while (seance == null) {
            System.out.print("Code de séance à confirmer (7 chiffres): ");
            String codeSeance = action.nextLine();
            seance = ListeServices.trouverSeance(codeSeance);
        }

        System.out.println("Cette séance est à " + seance.getHeure());

        int conf = -1;

        do {
            System.out.println("Voulez-vous confirmer votre inscription ? ");
            System.out.println("[1] Confirmer");
            System.out.println("[2] Abandon");
            System.out.print("Choix : ");
            conf = action.nextInt();
        } while (!(conf == 1 || conf == 2));

        if (conf == 2) {
            System.out.println("Abandon");
            return;
        }

        controleurService.confirmerSeance(seance, client);

    }


    /**
     * Timer qui lance la procédure comptable chaque vendredi à minuit
     */
    private void timerProcedureComptable() {

        // Date actuelle
        LocalDate now = LocalDate.now();
        LocalDateTime nowDateTime = LocalDateTime.now();

        // Vendredi prochain à minuit
        LocalDate vendrediPro = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        LocalDateTime vendrediProMinuit = vendrediPro.atTime(0,0,0);

        // Calcul de l'intervalle entre maintenant et vendredi prochain à minuit
        Duration intervalle = Duration.between(nowDateTime, vendrediProMinuit);
        
        Timer t = new Timer();
        
        // Lance le timer
        t.scheduleAtFixedRate(
            new TimerTask() {
                public void run() {
                    controleurComptabilite = new ControleurComptabilite();
                    controleurComptabilite.procedureComptable();
                }
            },
            intervalle.toMillis(),    // première exécution le vendredi prochain à minuit
            TimeUnit.DAYS.toMillis(7) // exécute tous les 7 jours
        );  
    }
}
