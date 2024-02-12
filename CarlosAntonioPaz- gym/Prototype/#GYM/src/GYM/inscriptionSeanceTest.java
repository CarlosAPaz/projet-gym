package GYM;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;


class InscriptionSeanceTest {

    Membre m1 = new Membre("Suzanne", "4@moi.ca", "2 rue principale", "montreal", "QC", "L0L 1N0");
    Membre m2 = new Membre("Courage", "6@moi.ca", "2 rue principale", "montreal", "QC", "L0L 1N0");
    ControleurService controleur = new ControleurService();
    InputStream sysInBackup = System.in;

    @BeforeEach
    void before() {

        m1.initialiser();
        m2.initialiser();

        ListeServices.listeServices = new ArrayList<Service>();

        Professionnel c1 = new Professionnel("Carlos", "1@moi.ca", "2 rue principale", "montreal", "QC", "L0L 1N0");
        Professionnel c2 = new Professionnel("Julie", "3@moi.ca", "2 rue principale", "montreal", "QC", "L0L 1N0");
        LocalDate d1 = LocalDate.of(2020, 10, 20);
        LocalDate d2 = LocalDate.of(2020, 11, 20);
        LocalDate d3 = LocalDate.now();

        LocalTime t1 = LocalTime.of(12, 00);
        LocalTime t2 = LocalTime.of(11, 00);

        String s = LocalDate.now().getDayOfWeek().toString();
        String jour = "";
        if (s.equalsIgnoreCase("monday")) jour = "lundi";

        if (s.equalsIgnoreCase("tuesday")) jour = "mardi";

        if (s.equalsIgnoreCase("wednesday")) jour = "mercredi";

        if (s.equalsIgnoreCase("thursday")) jour = "jeudi";

        if (s.equalsIgnoreCase("friday")) jour = "vendredi";

        if (s.equalsIgnoreCase("saturday")) jour = "samedi";

        if (s.equalsIgnoreCase("sunday")) jour = "dimanche";


        ArrayList<String> r1 = new ArrayList<String>();
        r1.add(jour);

        TypeService type1 = new TypeService("piscine");
        TypeService type2 = new TypeService("ski");

        Service s1 = new Service(c1, 50, 1, d3, d1, t1, r1, "hell yeah", type1.getCode());
        Service s2 = new Service(c1, 50, 20, d3, d2, t1, r1, "hell yeah", type2.getCode());
        Service s3 = new Service(c2, 50, 20, d3, d2, t2, r1, "hell yeah", type2.getCode());
        c1.ajoutService(s1);
        ListeServices.ajouter(s1);
        c1.ajoutService(s2);
        ListeServices.ajouter(s2);
        c2.ajoutService(s3);
        ListeServices.ajouter(s3);
    }

    @AfterEach
    void after() {
        //pour reinitialiser les diffrents codes
        TypeService.reset();
        Client.reset();
    }

    @Test
    void testInscriptionSeanceCreation() {

        String userInputs1 = "1" + System.lineSeparator() +
                "n" + System.lineSeparator() +
                "confirmer" + System.lineSeparator();

        String userInputs2 = "2" + System.lineSeparator() +
                "y" + System.lineSeparator() +
                "allo" + System.lineSeparator() +
                "confirmer" + System.lineSeparator() +
                "2" + System.lineSeparator() +
                "N" + System.lineSeparator() +
                "confirmer" + System.lineSeparator() +
                "3" + System.lineSeparator() +
                "Y" + System.lineSeparator() +
                "bonjours" + System.lineSeparator() +
                "confirmer" + System.lineSeparator();

        String userInputs = userInputs1 + userInputs2;
        ByteArrayInputStream in = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(in);
        MenuPrincipal.action = new Scanner(System.in);


        assertEquals(ListeServices.listeServices.get(0).getSeance().get(0).getNbInscrit(), 0);
        assertEquals(ListeServices.listeServices.get(1).getSeance().get(0).getNbInscrit(), 0);
        assertEquals(ListeServices.listeServices.get(2).getSeance().get(0).getNbInscrit(), 0);
        //premiere inscription

        controleur.inscriptionSeance(m1);


        Inscription i1 = ListeServices.listeServices.get(0).getSeance().get(0).getInscriptions().get(0);

        assertEquals(ListeServices.listeServices.get(0).getSeance().get(0).getNbInscrit(), 1);
        assertEquals(i1.getCodeSeance(), "0010103");
        assertEquals(i1.getNumeroMembre(), "000000001");
        assertEquals(i1.getNumPro(), "000000003");
        assertEquals(i1.getSeance().getNomTypeService(), "piscine");

        // deuxieme inscription

        controleur.inscriptionSeance(m1);

        Inscription i2 = ListeServices.listeServices.get(1).getSeance().get(0).getInscriptions().get(0);

        //s'assurer que seulement le deuxieme service a ete modifier
        assertEquals(ListeServices.listeServices.get(1).getSeance().get(0).getNbInscrit(), 1);
        assertEquals(ListeServices.listeServices.get(0).getSeance().get(0).getNbInscrit(), 1);
        assertEquals(i2.getCodeSeance(), "0020103");
        assertEquals(i2.getCommentaire(), "allo");
        assertEquals(i2.getNumeroMembre(), "000000001");
        assertEquals(i2.getNumPro(), "000000003");
        assertEquals(i2.getSeance().getNomTypeService(), "ski");

        // troisieme inscription

        controleur.inscriptionSeance(m2);

        Inscription i3 = ListeServices.listeServices.get(1).getSeance().get(0).getInscriptions().get(1);

        //s'assurer que seulement le deuxieme service a ete modifier
        assertEquals(ListeServices.listeServices.get(1).getSeance().get(0).getNbInscrit(), 2);
        assertEquals(ListeServices.listeServices.get(0).getSeance().get(0).getNbInscrit(), 1);
        assertEquals(i3.getCodeSeance(), "0020103");
        assertEquals(i3.getNumeroMembre(), "000000002");
        assertEquals(i3.getNumPro(), "000000003");
        assertEquals(i3.getSeance().getNomTypeService(), "ski");

        // quatrieme incription

        controleur.inscriptionSeance(m2);

        Inscription i4 = ListeServices.listeServices.get(2).getSeance().get(0).getInscriptions().get(0);

        //s'assurer que seulement le troisieme service a ete modifier
        assertEquals(ListeServices.listeServices.get(2).getSeance().get(0).getNbInscrit(), 1);
        assertEquals(ListeServices.listeServices.get(1).getSeance().get(0).getNbInscrit(), 2);
        assertEquals(ListeServices.listeServices.get(0).getSeance().get(0).getNbInscrit(), 1);
        assertEquals(i4.getCodeSeance(), "0020104");
        assertEquals(i4.getNumeroMembre(), "000000002");
        assertEquals(i4.getNumPro(), "000000004");
        assertEquals(i4.getSeance().getNomTypeService(), "ski");

    }

    @Test
    void testInscriptionSeanceBonNombreSeanceAfficher() {

        String userInputs1 = "1" + System.lineSeparator() +
                "n" + System.lineSeparator() +
                "confirmer" + System.lineSeparator() +
                "2" + System.lineSeparator() +
                "N" + System.lineSeparator() +
                "confirmer" + System.lineSeparator() +
                "3" + System.lineSeparator() +
                "n" + System.lineSeparator() +
                "confirmer" + System.lineSeparator() +
                "3" + System.lineSeparator() +
                "n" + System.lineSeparator() +
                "confirmer" + System.lineSeparator();

        String userInputs = userInputs1;
        ByteArrayInputStream in = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(in);
        MenuPrincipal.action = new Scanner(System.in);

        assertEquals(ListeServices.listeServices.get(0).getSeance().get(0).getNbInscrit(), 0);
        assertEquals(ListeServices.listeServices.get(1).getSeance().get(0).getNbInscrit(), 0);
        assertEquals(ListeServices.listeServices.get(2).getSeance().get(0).getNbInscrit(), 0);


        //Pour utiliser le premier service affiche
        controleur.inscriptionSeance(m1);
        System.out.println(m1.getNumero());
        Inscription i1 = ListeServices.listeServices.get(0).getSeance().get(0).getInscriptions().get(0);

        assertEquals(ListeServices.listeServices.get(0).getSeance().get(0).getNbInscrit(), 1);
        assertEquals(i1.getCodeSeance(), "0010103");
        assertEquals(i1.getNumPro(), "000000003");
        assertEquals(i1.getSeance().getNomTypeService(), "piscine");

        //Pour utiliser le deuxieme service affiche
        controleur.inscriptionSeance(m1);

        Inscription i2 = ListeServices.listeServices.get(1).getSeance().get(0).getInscriptions().get(0);

        assertEquals(ListeServices.listeServices.get(1).getSeance().get(0).getNbInscrit(), 1);
        assertEquals(i2.getCodeSeance(), "0020103");
        assertEquals(i2.getNumPro(), "000000003");
        assertEquals(i2.getSeance().getNomTypeService(), "ski");
        //Pour utiliser le troisieme service affiche
        controleur.inscriptionSeance(m1);
        Inscription i3 = ListeServices.listeServices.get(2).getSeance().get(0).getInscriptions().get(0);

        assertEquals(ListeServices.listeServices.get(2).getSeance().get(0).getNbInscrit(), 1);
        assertEquals(i3.getCodeSeance(), "0020104");
        assertEquals(i3.getNumPro(), "000000004");
        assertEquals(i3.getSeance().getNomTypeService(), "ski");
        //Pour tenter d'utiliser une option inexistante (refuser et prend l'entrer suivante qui correspond au service 3)
        controleur.inscriptionSeance(m1);

        Inscription i4 = ListeServices.listeServices.get(2).getSeance().get(0).getInscriptions().get(0);

        assertEquals(ListeServices.listeServices.get(2).getSeance().get(0).getNbInscrit(), 2);
        assertEquals(i4.getCodeSeance(), "0020104");
        assertEquals(i4.getNumPro(), "000000004");
        assertEquals(i4.getSeance().getNomTypeService(), "ski");

    }

    @Test
    void testInscriptionSeanceInputVide() {

        String userInputs1 = "" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "y" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "commentaire" + System.lineSeparator() +
                "confirmer" + System.lineSeparator();

        String userInputs = userInputs1;
        ByteArrayInputStream in = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(in);
        MenuPrincipal.action = new Scanner(System.in);

        controleur.inscriptionSeance(m1);

        Inscription i1 = ListeServices.listeServices.get(0).getSeance().get(0).getInscriptions().get(0);

        assertEquals(ListeServices.listeServices.get(0).getSeance().get(0).getNbInscrit(), 1);
        assertEquals(i1.getCodeSeance(), "0010103");
        assertEquals(i1.getNumeroMembre(), "000000001");
        assertEquals(i1.getNumPro(), "000000003");
        assertEquals(i1.getCommentaire(), "commentaire");
        assertEquals(i1.getSeance().getNomTypeService(), "piscine");

    }

    @Test
    void testInscriptionSeanceInputNonValide() {

        String userInputs1 = "s" + System.lineSeparator() +
                "-" + System.lineSeparator() +
                "0" + System.lineSeparator() +
                "100" + System.lineSeparator() +
                "+" + System.lineSeparator() +
                //entrer attendue 1,2 ou 3
                "1" + System.lineSeparator() +
                "a" + System.lineSeparator() +
                "_" + System.lineSeparator() +
                "0" + System.lineSeparator() +
                // entrer attendu y ou n
                "n" + System.lineSeparator() +
                "confirmer" + System.lineSeparator();

        String userInputs = userInputs1;
        ByteArrayInputStream in = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(in);
        MenuPrincipal.action = new Scanner(System.in);

        controleur.inscriptionSeance(m1);

        Inscription i1 = ListeServices.listeServices.get(0).getSeance().get(0).getInscriptions().get(0);

        assertEquals(ListeServices.listeServices.get(0).getSeance().get(0).getNbInscrit(), 1);
        assertEquals(i1.getCodeSeance(), "0010103");
        assertEquals(i1.getNumeroMembre(), "000000001");
        assertEquals(i1.getNumPro(), "000000003");
        assertEquals(i1.getSeance().getNomTypeService(), "piscine");
    }

    @Test
    void testInscriptionCapaciteAtteinte() {

        String userInputs1 = "1" + System.lineSeparator() +
                "n" + System.lineSeparator() +
                "confirmer" + System.lineSeparator() +
                // la capacite du service 1 est de 1 donc deja atteinte
                "1" + System.lineSeparator() +
                "2" + System.lineSeparator() +
                "n" + System.lineSeparator() +
                "confirmer" + System.lineSeparator();

        String userInputs = userInputs1;
        ByteArrayInputStream in = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(in);
        MenuPrincipal.action = new Scanner(System.in);

        assertEquals(ListeServices.listeServices.get(0).getSeance().get(0).getNbInscrit(), 0);

        controleur.inscriptionSeance(m1);

        assertEquals(ListeServices.listeServices.get(0).getSeance().get(0).getNbInscrit(), 1);
        controleur.inscriptionSeance(m2);
        assertEquals(ListeServices.listeServices.get(0).getSeance().get(0).getNbInscrit(), 1);
        assertEquals(ListeServices.listeServices.get(1).getSeance().get(0).getNbInscrit(), 1);

    }


}
