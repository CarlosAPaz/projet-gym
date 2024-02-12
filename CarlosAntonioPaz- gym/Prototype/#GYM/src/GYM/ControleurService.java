package GYM;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Classe de contrôle pour les {@link GYM.Service services}.
 */
public class ControleurService {

    Professionnel pro;
    Scanner demande = MenuPrincipal.action;
    ArrayList<TypeService> types = TypeService.listeTypes;


    /**
     * Constructeur avec {@link GYM.Professionnel professionnel}
     *
     * @param pro professionnel prodiguant le service
     */
    public ControleurService(Professionnel pro) {
        this.pro = pro;
    }

    /**
     * Constructeur nu
     */
    public ControleurService() {
    }

    /**
     * Permet de choisir le type de service a creer
     *
     * @return codeType code a 3 chiffres correspondant au type de service
     */
    private String entrerType() {

        TypeService rep = null;
        int choix;
        int n;

        //Pour imprimer sur la console les differents types
        do {
            n = 0;

            System.out.println("[0] creer un nouveau type");
            for (TypeService type : types) {
                n++;
                System.out.println("[" + n + "] " + type.getNom());
            }

            System.out.print("Entrer le choix: ");
            try {
                choix = Integer.parseInt(MenuPrincipal.action.nextLine());
            } catch (NumberFormatException e) {
                choix = -1;
            }
        } while (choix < 0 || choix > types.size());

        // Pour retrouver le code correspondant au choix
        if (choix == 0) {
            System.out.print("Entre le nom du nouveau service : ");
            String type;
            do {
                type = MenuPrincipal.action.nextLine();
            } while (type.equalsIgnoreCase(""));
            rep = new TypeService(type);

        } else {
            n = 0;

            for (TypeService type : types) {
                n++;
                if (choix == n) {
                    rep = type;
                }
            }
        }

        return rep.getCode();
    }


    /**
     * Permet de saisir et vérifier la validité de la récurrence d'un certain {@link GYM.Service service}.
     *
     * @return liste de Strings représentant la récurrence
     */
    private ArrayList<String> entrerRecurrenceH() {
        System.out.print("Veuillez entrer les jours où vous offrez le service (ex : lundi mardi samedi ...) : ");
        String input;

        do {
            input = MenuPrincipal.action.nextLine();
        } while (input.equalsIgnoreCase(""));
        ArrayList<String> rep = new ArrayList<String>();

        StringTokenizer st = new StringTokenizer(input);

        while (st.hasMoreTokens()) {

            String mot = st.nextToken();

            // s'assurer que les mots entrés correspondent aux jours de la semaine
            if (mot.equalsIgnoreCase("lundi") || mot.equalsIgnoreCase("mardi") ||
                    mot.equalsIgnoreCase("mercredi") || mot.equalsIgnoreCase("jeudi") ||
                    mot.equalsIgnoreCase("vendredi") || mot.equalsIgnoreCase("samedi") ||
                    mot.equalsIgnoreCase("dimanche")) {

                // ajouter seulement si le jour n'est pas déjà dans la liste
                if (rep.indexOf(mot) == -1) {

                    rep.add(mot);

                } else {
                    // appel recursif pour former le tableau
                    rep = entrerRecurrenceH();
                    // une fois le tableau crée sortir de la boucle
                    break;
                }

            } else {
                // appel recursif pour former le tableau
                rep = entrerRecurrenceH();
                // une fois le tableau crée sortir de la boucle
                break;
            }
        }

        return rep;
    }


    /**
     * Méthode récursive qui s'occupe de s'assurer que l'input reçu a le bon format (jj-mm-AAAA)
     *
     * @return LocalDate date
     */
    private LocalDate entrerDate() {

        String input = MenuPrincipal.action.nextLine();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        LocalDate rep = null;
        LocalDate auj = LocalDate.now();

        try {
            LocalDate date = LocalDate.parse(input, formatDate);
            rep = date;

            // si la date indiquer est avant la date actuelle

            if (date.isBefore(auj)) {

                System.out.println("Date entrée déja passée");
                throw new Exception();

            }


            // Si la date n'est pas du bon format
        } catch (Exception e) {

            System.out.println("Entrée invalide");
            System.out.print("Veuillez entrer une date (JJ-MM-AAAA) : ");

            rep = entrerDate();
        }

        return rep;
    }


    /**
     * Méthode récursive qui s'occupe de s'assurer que l'input reçu a le bon format (HH:mm)
     *
     * @return LocalTime heure
     */
    private LocalTime entrerHeure() {

        System.out.print("Heure à laquelle vous offrez le service (HH:mm) : ");

        String input = MenuPrincipal.action.nextLine();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime rep = null;

        try {
            LocalTime heure = LocalTime.parse(input, formatDate);
            rep = heure;

            // Le format entré est incorrect
        } catch (DateTimeParseException e) {
            System.out.println("Entrée invalide");
            rep = entrerHeure();
        }

        return rep;
    }


    /**
     * Demande au {@link GYM.Professionnel professionnel} d'entrer les frais du service
     *
     * @return int frais
     */
    private int entrerFrais() {
        int frais;

        do {
            System.out.print("Prix du service (entre 0$ et 100$) : ");
            try {
                frais = Integer.parseInt(MenuPrincipal.action.nextLine());
            } catch (NumberFormatException e) {
                frais = -1;
            }
        } while (frais < 0 || frais > 100);

        return frais;
    }


    /**
     * Demande au {@link GYM.Professionnel professionnel} d'entrer la capacité du service
     *
     * @return int capacité
     */
    private int entrerCapacite() {
        int capacite;

        do {
            System.out.print("Capacité maximale (entre 1 et 30) : ");
            try {
                capacite = Integer.parseInt(MenuPrincipal.action.nextLine());
            } catch (NumberFormatException e) {
                capacite = -1;
            }
        } while (capacite < 1 || capacite > 30);

        return capacite;
    }


    /**
     * Demande au {@link GYM.Professionnel professionnel} d'entrer les commentaires du service
     *
     * @return String commentaires
     */
    private String entrerCommentaires() {
        System.out.print("Commentaires (Y/N) : ");
        String choix = MenuPrincipal.action.nextLine();

        while (!(choix.equalsIgnoreCase("Y") || choix.equalsIgnoreCase("N"))) {
            choix = MenuPrincipal.action.nextLine();
        }

        String com = null;
        if (choix.equalsIgnoreCase("Y")) {
            System.out.print("Commentaires (max 100 caractères) : ");
            com = MenuPrincipal.action.nextLine();
        }

        return com;
    }


    /**
     * Ajout du service dans la liste des {@link GYM.Service services} du {@link GYM.Professionnel professionnel}
     * et dans la {@link GYM.ListeServices liste des services}
     *
     * @param frais
     * @param capacite
     * @param dateDebut
     * @param dateFin
     * @param heure
     * @param recurrenceH
     * @param comm
     * @param type
     */
    private void ajoutService(int frais, int capacite, LocalDate dateDebut, LocalDate dateFin, LocalTime heure,
                              ArrayList<String> recurrenceH, String comm, String type) {

        Service service = new Service(this.pro, frais, capacite, dateDebut, dateFin, heure, recurrenceH, comm, type);
        this.pro.ajoutService(service);
        ListeServices.ajouter(service);
        System.out.println("Votre service est : \"" + service.getNom() + "\" à " + service.getHeure() + ".");
    }

    /**
     * Permet au {@link GYM.Professionnel professionnel} de choisir parmi les {@link GYM.Service services} qu'il offre
     *
     * @return service service choisi par le pro
     */
    public Service choisirService() {
        int n = 1;
        int choix = -1;

        ArrayList<Service> services = this.pro.getServices();

        if (services.size() == 0) {
            System.out.println("Vous n'avez aucun service");
            return null;
        }

        do {
            System.out.println("---------------------------------------------");
            System.out.println("Veuillez choisir parmi les services que vous offrez :");
            for (Service service : services) {
                System.out.println("[" + n++ + "] " + service.getNom() + " à " + service.getHeure());
            }
            n = 1;
            System.out.print("Choix : ");

            try {
                choix = Integer.parseInt(demande.nextLine());
            } catch (NumberFormatException e) {
                choix = -1;
            }
        } while (choix < 1 || choix > this.pro.getServices().size());

        Service serviceChoisi = this.pro.getServices().get(choix - 1);

        System.out.println("Vous avez choisi le service suivant : " + serviceChoisi.getNom() + " à " + serviceChoisi.getHeure());
        System.out.print("Est-ce correct (Y/N) : ");
        String confirmation = demande.nextLine();
        if (confirmation.equalsIgnoreCase("y")) return serviceChoisi;
        else return this.choisirService();
    }

    /**
     * Maintenance d'un {@link GYM.Service service} du {@link GYM.Professionnel professionnel}
     */
    public void maintenanceService() {
        System.out.println("---------------------------------------------");

        if (this.pro.getServices().size() == 0) {
            System.out.println("Vous n'avez aucun service!");
            return;
        }

        Service service = this.choisirService();

        // if (service.getNumeroPro() != this.pro.getNumero()) {
        // 	System.out.println("Vous n'êtes pas autorisé à supprimer ce service");
        // 	return;
        // }

        // Affichage du service
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("\nService trouvé : ");
        System.out.println("Frais : " + service.getFrais());
        System.out.println("Capacité : " + service.getCapacite());
        System.out.println("Date de début : " + service.getDateDebut().format(formatter));
        System.out.println("Date de fin : " + service.getDateFin().format(formatter));
        System.out.println("Heure de service : " + service.getHeure());
        System.out.println("Récurrence hebdomadaire : " + service.getRec());
        if (service.getCom() != null) System.out.println("Commentaires : " + service.getCom());

        int choix = -1;

        do {
            System.out.println("\nQue souhaitez-vous faire ?");
            System.out.println("[1] Modifier service");
            System.out.println("[2] Supprimer service");
            System.out.print("Choix : ");
            try {
                choix = Integer.parseInt(MenuPrincipal.action.nextLine());
            } catch (NumberFormatException e) {
                choix = -1;
            }
        } while (!(choix == 1 || choix == 2));

        if (choix == 1) {
            modifierService(service);
        } else {
            supprimerService(service);
        }
    }


    /**
     * Permet au {@link GYM.Professionnel professionnel} de modifier les informations d'un de ses
     * {@link GYM.Service services}
     */
    private void modifierService(Service service) {
        System.out.println("Que souhaitez-vous modifier ?");
        System.out.println("[1] Frais : " + service.getFrais());
        System.out.println("[2] Capacité : " + service.getCapacite());
        System.out.println("[3] Heure de service : " + service.getHeure());
        System.out.println("[4] Les séances : ");
        System.out.println("-> Informations sur les séances");
        System.out.println("--> Date de début : " + service.getDateDebut());
        System.out.println("--> Date de fin : " + service.getDateFin());
        System.out.println("--> Récurrence : " + service.getRec());

        System.out.print("Choix : ");

        int choix = -1;

        do {
            try {
                choix = Integer.parseInt(MenuPrincipal.action.nextLine());
            } catch (NumberFormatException e) {
                choix = -1;
            }
        } while (!(choix == 1 || choix == 2 || choix == 3 || choix == 4));

        if (choix == 1) {
            int frais = entrerFrais();
            service.setFrais(frais);
        }

        if (choix == 2) {
            int capacite = entrerCapacite();
            service.setCapacite(capacite);
        }

        if (choix == 3) {
            LocalTime heure = entrerHeure();
            service.setHeure(heure);
        }


        if (choix == 4) {


            int index = pro.getServices().indexOf(service);

            resetService(service);
            System.out.print("Date de début du service (JJ-MM-AAAA) : ");
            LocalDate debut = entrerDate();
            System.out.print("Date de fin du service (JJ-MM-AAAA) : ");
            LocalDate fin = entrerDate();
            ArrayList<String> recurrenceH = entrerRecurrenceH();

            service.setDateDebut(debut);
            service.setDateFin(fin);
            service.setRec(recurrenceH);

            //modifier les seances dans notre service

            service.modifierSeances(debut, fin, recurrenceH);

            // une fois le service modifier dans notre liste de service officielle, modifie la liste associe avec le pro


            pro.getServices().set(index, service);


        }

        System.out.println("Informations modifiées");
    }

    /**
     * Méthode nous permettant d'enlever toutes les séances qui ne seront pas honorées
     */
    private void resetService(Service service) {


        ArrayList<Seance> listeSeances;

        listeSeances = service.getSeance();

        for (int n = 0; n < listeSeances.size(); n++) {

            Seance seance = listeSeances.get(n);

            LocalDate auj = LocalDate.now();

            // pour supprimer toutes les seances apres aujourd'hui

            if (seance.getDateSeance().isAfter(auj)) {

                listeSeances.remove(n);

            }


        }

    }

    /**
     * Permet au {@link GYM.Professionnel professionnel} de supprimer un de ses {@link GYM.Service services}
     */
    private void supprimerService(Service service) {
        int confirmation;

        do {
            System.out.println("Veuillez confirmer la suppression du service :");
            System.out.println("[1] Confirmer");
            System.out.println("[2] Abandon");
            System.out.print("Choix : ");
            try {
                confirmation = Integer.parseInt(MenuPrincipal.action.nextLine());
            } catch (NumberFormatException e) {
                confirmation = -1;
            }
        } while (!(confirmation == 1 || confirmation == 2));

        if (confirmation == 1) {
            ListeServices.supprimer(service);
            this.pro.supprimerService(service);
        } else {
            System.out.println("Abandon");
        }
    }


    /**
     * Création d'un nouveau {@link GYM.Service services} pour le {@link GYM.Professionnel professionnel}
     */
    public void creerService() {

        System.out.println("---------------------------------------------");


        System.out.println("Veuillez choisir le type de service : ");
        String type = entrerType();

        String code = type + "-" + pro.getNumero().substring(7);
        Service service = this.pro.trouverService(code);
        if (service != null) {

            System.out.println("Vous offrez déjà le service \"" + service.getNom() + "\". Voici les options pour modifier vos services existants :");
            maintenanceService();

        } else {

            System.out.print("Date de début du service (JJ-MM-AAAA) : ");
            LocalDate dateDebut = entrerDate();
            System.out.print("Date de fin du service (JJ-MM-AAAA) : ");
            LocalDate dateFin = entrerDate();
            LocalTime heureService = entrerHeure();
            ArrayList<String> recurrenceH = entrerRecurrenceH();
            int frais = entrerFrais();
            int capacite = entrerCapacite();
            String commentaires = entrerCommentaires();

            // Confirmer avec l'utilisateur
            int confirmation;

            do {
                System.out.println("Veuillez confirmer l'ajout du service : ");
                System.out.println("[1] Confirmer");
                System.out.println("[2] Abandon");
                System.out.print("Choix : ");
                try {
                    confirmation = Integer.parseInt(MenuPrincipal.action.nextLine());
                } catch (NumberFormatException e) {
                    confirmation = -1;
                }
            } while (!(confirmation == 1 || confirmation == 2));

            if (confirmation == 1) {
                ajoutService(frais, capacite, dateDebut, dateFin, heureService, recurrenceH, commentaires, type);
            }

            if (confirmation == 2) {
                System.out.println("Abandon");
            }

        }
    }


    /**
     * Permet au {@link GYM.Professionnel professionnel} de consulter les inscriptions à ses {@link GYM.Seance séances}
     */
    public void consulterInscriptions() {

        Service service = this.choisirService();

        System.out.println("Veuillez choisir une séance : ");
        ArrayList<Seance> seances = service.getSeancesHVenir();
        int i = 1;

        if (seances.size() == 0) {
            System.out.println("... Le service n'a aucune séance aujourd'hui !");
            return;
        }

        // Affiche toutes les séances de la semaine
        for (Seance seance : seances) {
            String code = seance.getCodeSeance();
            String heure = seance.getHeure() + "";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date = seance.getDateSeance().format(formatter);
            System.out.println("[" + (i++) + "] Séance numéro " + code + " à " + heure + " le " + date);
        }

        Seance seance = null;

        // Choix de la séance
        while (seance == null) {
            String choix = MenuPrincipal.action.nextLine();

            try {
                int num = Integer.parseInt(choix) - 1;
                seance = seances.get(num);
            } catch (Exception e) {
                System.out.println("Choix invalide");
            }
        }

        ArrayList<Inscription> inscriptions = seance.getInscriptions();
        i = 1;

        if (inscriptions.size() == 0) {
            System.out.println("Aucune inscription à votre séance");
            return;
        }

        System.out.println("Voici les inscriptions à votre séance :");

        // Affichage des inscriptions
        for (Inscription inscription : inscriptions) {
            System.out.println("  " + (i++) + ". Membre numéro " + inscription.getNumeroMembre());
        }
    }

    /**
     * Permet de créer une {@link GYM.Confirmation confirmation d'inscription} d'un {@link GYM.Membre membre} à une
     * {@link GYM.Seance séance} (si celle-ci n'existe pas déjà).
     *
     * @param seance séance que l'on désire confirmer
     * @param client client voulant confirmer sa présence
     */
    public void confirmerSeance(Seance seance, Client client) {

        // Vérifie si le client a été inscrit préalablement
        if (!seance.validiteInscription(client.getNumero())) {
            return;
        }

        System.out.print("Commentaires (Y/N) : ");
        String choix = MenuPrincipal.action.nextLine();

        while (!(choix.equalsIgnoreCase("Y") || choix.equalsIgnoreCase("N"))) {
            choix = MenuPrincipal.action.nextLine();
        }

        String com = null;
        if (choix.equalsIgnoreCase("Y")) {
            com = MenuPrincipal.action.nextLine();
        }

        // Création de l'enregistrement
        Confirmation confirmation = new Confirmation(seance, client.getNumero(), com);
        // Verifier que ce n'est pas un duplicata
        if (seance.confirmationDupliquee(confirmation)) {
            System.out.println("Vous aviez déja confirmé votre présence à cette séance.");
        } else {
            System.out.println(confirmation);
            System.out.println("Confirmation validée");
        }
    }

    /**
     * Inscription d'un {@link GYM.Membre membre} à une {@link GYM.Seance séance}
     *
     * @param client client que l'on cherche à inscrire
     */
    public void inscriptionSeance(Client client) {
        System.out.println("---------------------------------------------");

        ArrayList<Seance> seancesChoix = ListeServices.getServicesDuJour();

        if (seancesChoix.size() == 0) {
            System.out.println("Il n'y a aucune séance aujourd'hui...");
            return;
        }

        int i = 1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = LocalDate.now().format(formatter);
        System.out.println("Veuillez choisir une séance parmi les séances du jour (" + date + ") : ");

        // Affiche toutes les séances du jour disponibles
        for (Seance seance : seancesChoix) {
            String code = seance.getCodeSeance();
            String heure = seance.getHeure() + "";
            String nom = seance.getNomTypeService();
            System.out.println("[" + i++ + "] Séance de " + nom + " à " + heure + " (" + code + ")");
        }

        Seance seance = null;

        // Choix de la séance du jour
        while (seance == null) {
            String choix = MenuPrincipal.action.nextLine();

            try {
                int num = Integer.parseInt(choix) - 1;
                seance = seancesChoix.get(num);
            } catch (Exception e) {
                System.out.println("Choix invalide");
            }
        }

        // Vérifier si capacité maximale est atteinte
        if (seance.placesDisponibles()) {
            System.out.println("La capacité maximale a été atteinte");
            inscriptionSeance(client);
            return;
        }

        System.out.print("Commentaires (Y/N) : ");
        String choix = MenuPrincipal.action.nextLine();

        while (!(choix.equalsIgnoreCase("Y") || choix.equalsIgnoreCase("N"))) {
            choix = MenuPrincipal.action.nextLine();
        }

        String com = null;
        if (choix.equalsIgnoreCase("Y")) {
            do {
                com = MenuPrincipal.action.nextLine();
            } while (com.equalsIgnoreCase(""));
        }

        Inscription inscription = seance.ajouterInscription(client.getNumero(), com);

        System.out.print("Veuillez acquitter les frais de " + seance.getFrais() + "$");
        MenuPrincipal.action.nextLine();

        System.out.println(inscription);
        System.out.println("Inscription à la séance de " + seance.getNomTypeService() +
                " (" + seance.getCodeSeance() + ") validée");
    }
}