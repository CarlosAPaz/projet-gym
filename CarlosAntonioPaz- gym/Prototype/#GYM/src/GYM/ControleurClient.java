package GYM;

/**
 * Classe de contrôle pour les {@link GYM.Client clients}.
 */
public class ControleurClient {

    public ControleurClient() {
    }

    /**
     * Demande un numéro unique et vérifie sa validitié
     */
    public Client validerNumero() {
        System.out.println("---------------------------------------------");

        System.out.print("Numéro du client (9 chiffes): ");
        String numero = MenuPrincipal.action.nextLine();

        Client client = ListeClients.rechercheClient(numero, 1);

        // Client non trouvé
        if (client == null) {
            System.out.println("Numéro invalide");

            // Client est un membre
        } else if (client instanceof Membre) {

            Membre membre = (Membre) client;

            // Membre est suspendu
            if (membre.getSuspendu()) {
                System.out.println("Membre suspendu");
            }

            System.out.println("Numéro validé");

            // Client est un professionnel
        } else if (client instanceof Professionnel) {
            System.out.println("Numéro validé");
        }

        return client;
    }


    /**
     * Ajout d'un nouveau membre
     */
    public void ajouterMembre() {
        System.out.println("---------------------------------------------");

        System.out.print("Nom du nouveau membre (25 lettres) : ");
        String nom = entrerNom();

        System.out.print("Courriel associé au compte Facebook du nouveau membre (xxx@xxx.xx) : ");
        String courriel = entrerCourriel();

        System.out.print("Adresse du nouveau membre (25 caractères max) : ");
        String adresse = entrerAdresse();

        System.out.print("Ville du nouveau membre (14 caractères) : ");
        String ville = entrerVille();

        System.out.print("Province du nouveau membre (2 lettres) : ");
        String province = entrerProvince();

        System.out.print("Code postal du nouveau membre (X0X 0X0) : ");
        String codePostal = entrerCodePostal();

        Membre nouveauMembre = new Membre(nom, courriel, adresse, ville, province, codePostal);

        System.out.print("Veuillez acquitter les frais d'adhésion de 50$");
        MenuPrincipal.action.nextLine();

        nouveauMembre.initialiser();
        ListeClients.ajouter(nouveauMembre);
        System.out.println("Numéro du nouveau membre : " + nouveauMembre.getNumero());
    }


    /**
     * Ajout d'un nouveau professionnel
     */
    public void ajouterPro() {
        System.out.println("---------------------------------------------");

        System.out.print("Nom du nouveau professionnel (25 lettres) : ");
        String nom = entrerNom();

        System.out.print("Courriel associé au compte Facebook du nouveau professionnel (xxx@xxx.xx) : ");
        String courriel = entrerCourriel();

        System.out.print("Adresse du nouveau professionnel (25 caractères max) : ");
        String adresse = entrerAdresse();

        System.out.print("Ville du nouveau professionnel (14 caractères max) : ");
        String ville = entrerVille();

        System.out.print("Province du nouveau professionnel (2 lettres) : ");
        String province = entrerProvince();

        System.out.print("Code postal du nouveau professionnel (X0X 0X0) : ");
        String codePostal = entrerCodePostal();

        Professionnel nouveauPro = new Professionnel(nom, courriel, adresse, ville, province, codePostal);

        ListeClients.ajouter(nouveauPro);
        System.out.println("Numéro du nouveau professionnel : " + nouveauPro.getNumero());
    }


    /**
     * Maintenance du compte du client
     *
     * @param client client dont on souhaite modifier le compte
     */
    public void maintenanceCompte(Client client) {
        int choix = -1;

        do {
            System.out.println("Que souhaitez-vous faire ?");
            System.out.println("[1] Modifier compte");
            System.out.println("[2] Supprimer compte");
            System.out.print("Choix : ");

            try {
                choix = Integer.parseInt(MenuPrincipal.action.nextLine());
            } catch (NumberFormatException e) {
                choix = -1;
            }
        } while (!(choix == 1 || choix == 2));

        if (choix == 1) {
            modifierClient(client);
        } else {
            supprimerClient(client);
        }
    }


    /**
     * Met à jour les informations du client
     *
     * @param client client dont on souhaite mettre à jour le compte
     */
    private void modifierClient(Client client) {
        int choix = -1;

        do {
            System.out.println("Que souhaitez-vous modifier ?");
            System.out.println("[1] Nom : " + client.getNom());
            System.out.println("[2] Addresse courriel : " + client.getCourriel());
            System.out.println("[3] Adresse : " + client.getAdresse());
            System.out.println("[4] Ville : " + client.getVille());
            System.out.println("[5] Province : " + client.getProvince());
            System.out.println("[6] Code postal : " + client.getCodePostal());
            System.out.print("Choix : ");

            try {
                choix = Integer.parseInt(MenuPrincipal.action.nextLine());
            } catch (NumberFormatException e) {
                choix = -1;
            }
        } while (choix < 1 || choix > 6);

        switch (choix) {
            case 1:
                System.out.print("Veuillez entrer le nouveau nom (25 lettres max) : ");
                String ancienNom = client.getNom();
                String nom = entrerNom();
                client.setNom(nom);
                System.out.println(ancienNom + " a été changé en " + nom);
                return;

            case 2:
                System.out.print("Veuillez entrer la nouvelle addresse courriel (xxx@xxx.xx) : ");
                String ancienCourriel = client.getCourriel();
                String courriel = entrerCourriel();
                client.setCourriel(courriel);
                System.out.println(ancienCourriel + " a été changé en " + courriel);
                return;

            case 3:
                System.out.print("Veuillez entrer la nouvelle adresse (25 caractères max) : ");
                String ancienneAdresse = client.getAdresse();
                String adresse = MenuPrincipal.action.nextLine();
                client.setAdresse(adresse);
                System.out.println(ancienneAdresse + " a été changé en " + adresse);
                return;

            case 4:
                System.out.print("Veuillez entrer la nouvelle ville (14 caractères max) : ");
                String ancienneVille = client.getVille();
                String ville = MenuPrincipal.action.nextLine();
                client.setVille(ville);
                System.out.println(ancienneVille + " a été changé en " + ville);
                return;

            case 5:
                System.out.print("Veuillez entrer la nouvelle province (2 lettres) : ");
                String ancienneProvince = client.getProvince();
                String province = MenuPrincipal.action.nextLine();
                client.setProvince(province);
                System.out.println(ancienneProvince + " a été changé en " + province);
                return;

            case 6:
                System.out.print("Veuillez entrer le nouveau code postal (X0X 0X0) : ");
                String ancienCodeP = client.getCodePostal();
                String codeP = MenuPrincipal.action.nextLine();
                client.setCodePostal(codeP);
                System.out.println(ancienCodeP + " a été changé en " + codeP);
                return;
        }
    }


    /**
     * Supprime le compte du client
     *
     * @param client client dont on souhaite supprimer le compte
     */
    public void supprimerClient(Client client) {
        ListeClients.supprimer(client);
    }


    /**
     * S'assure que le nom entré a le bon format (25 lettres max)
     *
     * @return nom au format valide
     */
    private String entrerNom() {

        String regex = "^[A-zÀ-ú]{1,25}$";
        String nom = MenuPrincipal.action.nextLine();

        while (!nom.matches(regex)) {
            System.out.println("Nom invalide");
            System.out.print("Veuillez entrer un nom valide (25 lettres max) : ");
            nom = MenuPrincipal.action.nextLine();
        }

        return nom;
    }


    /**
     * S'assure que l'input correspond au format d'une adresse courriel
     *
     * @return courriel au format valide
     */
    private String entrerCourriel() {

        //expression reguliere correspondant au format d'une adresse courriel ( *@([a-zA-Z_0-9]+.)+[a-zA-Z_0-9]+[a-zA-Z_0-9] )
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        String courriel = MenuPrincipal.action.nextLine();

        while (!courriel.matches(regex)) {
            System.out.println(courriel + " n'est pas un courriel valide");
            System.out.print("Veuillez entrer un courriel au bon format (xxx@xxx.xx) : ");
            courriel = MenuPrincipal.action.nextLine();
        }

        return courriel;
    }


    /**
     * S'assure que l'adresse entrée a le bon format (25 caractères max)
     *
     * @return adresse au format valide
     */
    private String entrerAdresse() {

        String adresse = MenuPrincipal.action.nextLine();

        while (adresse.length() < 1 || adresse.length() > 26) {
            System.out.println("Adresse invalide");
            System.out.print("Veuillez entrer une adresse valide (25 caractères max) : ");
            adresse = MenuPrincipal.action.nextLine();
        }

        return adresse;
    }


    /**
     * S'assure que la ville entrée a le bon format (14 caractères max)
     *
     * @return ville au format valide
     */
    private String entrerVille() {

        String ville = MenuPrincipal.action.nextLine();

        while (ville.length() < 1 || ville.length() > 14) {
            System.out.println("Ville invalide");
            System.out.print("Veuillez entrer une ville valide (14 caractères max) : ");
            ville = MenuPrincipal.action.nextLine();
        }

        return ville;
    }


    /**
     * S'assure que la province entrée a le bon format (2 lettres)
     *
     * @return province au format valide
     */
    private String entrerProvince() {

        String regex = "^[A-Za-z]{2}$";
        String province = MenuPrincipal.action.nextLine();

        while (!province.matches(regex)) {
            System.out.println("Province invalide");
            System.out.print("Veuillez entrer une province valide (2 lettres) : ");
            province = MenuPrincipal.action.nextLine();
        }

        return province;
    }


    /**
     * S'assure que le code postal entré a le bon format (X0X 0X0)
     *
     * @return code postal au format valide
     */
    private String entrerCodePostal() {

        String regex = "^[A-Za-z]{1}\\d{1}[A-Za-z]{1}[ ]{0,1}\\d{1}[A-Za-z]{1}\\d{1}$";
        String codePostal = MenuPrincipal.action.nextLine();

        while (!codePostal.matches(regex)) {
            System.out.println("Code postal invalide");
            System.out.print("Veuillez entrer un code postal au format X0X 0X0 : ");
            codePostal = MenuPrincipal.action.nextLine();
        }

        return codePostal;
    }
}