package GYM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CreerServiceTest {

    Professionnel c1 = new Professionnel("Carlos", "1@moi.ca", "2 rue principale", "montreal", "QC", "L0L 1N0");
    ControleurService controleur = new ControleurService(c1);
    InputStream sysInBackup = System.in;


    @BeforeEach
    void before() {
        ListeServices.listeServices = new ArrayList<Service>();
    }

    @Test
    void testCreerService() {
        String userInputs1 = "0" + System.lineSeparator() +
                "Zumba" + System.lineSeparator() +
                "12-10-2020" + System.lineSeparator() +
                "12-11-2020" + System.lineSeparator() +
                "12:30" + System.lineSeparator() +
                "Lundi" + System.lineSeparator() +
                "20" + System.lineSeparator() +
                "25" + System.lineSeparator() +
                "N" + System.lineSeparator() +
                "1" + System.lineSeparator();

        String userInputs2 = "" + System.lineSeparator() +
                "0" + System.lineSeparator() +
                "Salsa" + System.lineSeparator() +
                // date deja passer
                "12-12-1900" + System.lineSeparator() +
                "12-11-2020" + System.lineSeparator() +
                // date deja passer
                "11-12-1900" + System.lineSeparator() +
                "11-12-2020" + System.lineSeparator() +
                //mauvais format
                "1100" + System.lineSeparator() +
                "11:00" + System.lineSeparator() +
                // erreur d'epellation
                "Muandi" + System.lineSeparator() +
                "Mardi" + System.lineSeparator() +
                // depasse limite permise
                "10000" + System.lineSeparator() +
                "30" + System.lineSeparator() +
                // depasse limite permise
                "10000" + System.lineSeparator() +
                "15" + System.lineSeparator() +
                "Y" + System.lineSeparator() +
                "intermediaire" + System.lineSeparator() +
                "11:00" + System.lineSeparator() +
                // mauvaise enter pour confirmer attend 1 pour confirmer ou 2 pour annuler
                "10" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "1" + System.lineSeparator();

        String userInputs = userInputs1 + userInputs2;
        ByteArrayInputStream in = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(in);
        MenuPrincipal.action = new Scanner(System.in);

        assertEquals(ListeServices.listeServices.size(), 0);

        LocalDate rep1 = LocalDate.of(2020, 10, 12);
        LocalDate rep2 = LocalDate.of(2020, 11, 12);
        LocalTime rep3 = LocalTime.of(12, 30);

        //premiere ajout
        controleur.creerService();


        assertEquals(ListeServices.listeServices.size(), 1);
        assertEquals(ListeServices.listeServices.get(0).getNom(), "Zumba");
        assertTrue(ListeServices.listeServices.get(0).getDateDebut().isEqual(rep1));
        assertTrue(ListeServices.listeServices.get(0).getDateFin().isEqual(rep2));
        assertTrue(ListeServices.listeServices.get(0).getHeure().equals(rep3));
        assertEquals(ListeServices.listeServices.get(0).getRec().get(0), "Lundi");
        assertEquals(ListeServices.listeServices.get(0).getFrais(), 20);
        assertEquals(ListeServices.listeServices.get(0).getCapacite(), 25);
        assertEquals(ListeServices.listeServices.get(0).getCom(), null);

        //deuxieme ajout
        controleur.creerService();
        LocalDate rep4 = LocalDate.of(2020, 11, 12);
        LocalDate rep5 = LocalDate.of(2020, 12, 11);
        LocalTime rep6 = LocalTime.of(11, 00);


        assertEquals(ListeServices.listeServices.size(), 2);
        assertEquals(ListeServices.listeServices.get(1).getNom(), "Salsa");
        assertTrue(ListeServices.listeServices.get(1).getDateDebut().isEqual(rep4));
        assertTrue(ListeServices.listeServices.get(1).getDateFin().isEqual(rep5));
        assertTrue(ListeServices.listeServices.get(1).getHeure().equals(rep6));
        assertEquals(ListeServices.listeServices.get(1).getRec().get(0), "Mardi");
        assertEquals(ListeServices.listeServices.get(1).getFrais(), 30);
        assertEquals(ListeServices.listeServices.get(1).getCapacite(), 15);
        assertEquals(ListeServices.listeServices.get(1).getCom(), "intermediaire");

    }

    @Test
    void testCreerServiceAnnulation() {

        String userInputs1 = "0" + System.lineSeparator() +
                "Zumba" + System.lineSeparator() +
                "12-10-2020" + System.lineSeparator() +
                "12-11-2020" + System.lineSeparator() +
                "12:30" + System.lineSeparator() +
                "Lundi" + System.lineSeparator() +
                "20" + System.lineSeparator() +
                "25" + System.lineSeparator() +
                "N" + System.lineSeparator() +
                // mauvaise entrer
                "12" + System.lineSeparator() +
                // 2  pour annuler
                "2" + System.lineSeparator();

        String userInputs = userInputs1;
        ByteArrayInputStream in = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(in);
        MenuPrincipal.action = new Scanner(System.in);

        assertEquals(ListeServices.listeServices.size(), 0);

        controleur.creerService();

        assertEquals(ListeServices.listeServices.size(), 0);


    }

    @Test
    void testCreerServiceEntrerVide() {

        String userInputs1 = "" + System.lineSeparator() +
                "0" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "Bachata" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "22-10-2020" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "22-12-2020" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "10:30" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "Jeudi" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "10" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "30" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "N" + System.lineSeparator() +
                "" + System.lineSeparator() +
                // 1  pour confirmer
                "1" + System.lineSeparator();

        String userInputs = userInputs1;
        ByteArrayInputStream in = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(in);
        MenuPrincipal.action = new Scanner(System.in);

        assertEquals(ListeServices.listeServices.size(), 0);

        controleur.creerService();

        LocalDate rep1 = LocalDate.of(2020, 10, 22);
        LocalDate rep2 = LocalDate.of(2020, 12, 22);
        LocalTime rep3 = LocalTime.of(10, 30);

        assertEquals(ListeServices.listeServices.size(), 1);
        assertEquals(ListeServices.listeServices.get(0).getNom(), "Bachata");
        assertTrue(ListeServices.listeServices.get(0).getDateDebut().isEqual(rep1));
        assertTrue(ListeServices.listeServices.get(0).getDateFin().isEqual(rep2));
        assertTrue(ListeServices.listeServices.get(0).getHeure().equals(rep3));
        assertEquals(ListeServices.listeServices.get(0).getRec().get(0), "Jeudi");
        assertEquals(ListeServices.listeServices.get(0).getFrais(), 10);
        assertEquals(ListeServices.listeServices.get(0).getCapacite(), 30);
        assertEquals(ListeServices.listeServices.get(0).getCom(), null);


    }

    @Test
    void testCreerServiceAjoutUneSeances() {

        String userInputs1 = "" + System.lineSeparator() +
                "0" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "Bachata" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "03-11-2020" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "07-11-2020" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "10:30" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "Jeudi" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "10" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "30" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "N" + System.lineSeparator() +
                "" + System.lineSeparator() +
                // 1  pour confirmer
                "1" + System.lineSeparator();

        String userInputs = userInputs1;
        ByteArrayInputStream in = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(in);
        MenuPrincipal.action = new Scanner(System.in);

        assertEquals(ListeServices.listeServices.size(), 0);

        controleur.creerService();

        LocalDate rep1 = LocalDate.of(2020, 11, 5);

        assertEquals(ListeServices.listeServices.get(0).getSeance().size(), 1);
        assertTrue(ListeServices.listeServices.get(0).getSeance().get(0).getDateSeance().isEqual(rep1));

    }

    @Test
    void testCreerServiceAjoutPlusieurSeances() {

        String userInputs1 = "" + System.lineSeparator() +
                "0" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "Bachata" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "03-12-2020" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "17-12-2020" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "10:30" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "Jeudi Lundi" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "10" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "30" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "N" + System.lineSeparator() +
                "" + System.lineSeparator() +
                // 1  pour confirmer
                "1" + System.lineSeparator();

        String userInputs = userInputs1;
        ByteArrayInputStream in = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(in);
        MenuPrincipal.action = new Scanner(System.in);

        assertEquals(ListeServices.listeServices.size(), 0);

        controleur.creerService();

        LocalDate rep1 = LocalDate.of(2020, 12, 3);
        LocalDate rep2 = LocalDate.of(2020, 12, 10);
        LocalDate rep3 = LocalDate.of(2020, 12, 17);
        LocalDate rep4 = LocalDate.of(2020, 12, 07);
        LocalDate rep5 = LocalDate.of(2020, 12, 14);

        assertEquals(ListeServices.listeServices.get(0).getSeance().size(), 5);
        assertTrue(ListeServices.listeServices.get(0).getSeance().get(0).getDateSeance().isEqual(rep1));
        assertTrue(ListeServices.listeServices.get(0).getSeance().get(1).getDateSeance().isEqual(rep2));
        assertTrue(ListeServices.listeServices.get(0).getSeance().get(2).getDateSeance().isEqual(rep3));
        assertTrue(ListeServices.listeServices.get(0).getSeance().get(3).getDateSeance().isEqual(rep4));
        assertTrue(ListeServices.listeServices.get(0).getSeance().get(4).getDateSeance().isEqual(rep5));
    }


}
