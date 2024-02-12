package GYM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ControleurClientTest {

    ControleurClient controleur = new ControleurClient();
    InputStream sysInBackup = System.in;


    @BeforeEach
    void after() {
        ListeClients.listeClients = new ArrayList<Client>();
    }


    @Test
    void testAjouterMembre() {
        // inputs corrects
        String userInputs1 = "Premier" + System.lineSeparator() +
                "1@membre.ca" + System.lineSeparator() +
                "qqpart" + System.lineSeparator() +
                "ville" + System.lineSeparator() +
                "qq" + System.lineSeparator() +
                "M1M1M1" + System.lineSeparator() +
                "confirmer" + System.lineSeparator();

        // inputs incorrects
        String userInputs2 = "n0m 1nval1d3" + System.lineSeparator() +
                "00000" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "nömVàlîde" + System.lineSeparator() +

                "" + System.lineSeparator() +
                "123123" + System.lineSeparator() +
                "courriel@invalide" + System.lineSeparator() +
                "2@membre.ca" + System.lineSeparator() +

                "" + System.lineSeparator() +
                "123 rue adresse trop longue" + System.lineSeparator() +
                "qqpart2" + System.lineSeparator() +

                "" + System.lineSeparator() +
                "Nom de la ville dépasse les 14 caractères" + System.lineSeparator() +
                "ville2" + System.lineSeparator() +

                "" + System.lineSeparator() +
                "0" + System.lineSeparator() +
                "Québec" + System.lineSeparator() +
                "QC" + System.lineSeparator() +

                "" + System.lineSeparator() +
                "code postal invalide" + System.lineSeparator() +
                "111 111" + System.lineSeparator() +
                "HHH HHH" + System.lineSeparator() +
                "H1H H1H" + System.lineSeparator() +
                "M2M 2M2" + System.lineSeparator() +

                "confirmer" + System.lineSeparator();

        String userInputs = userInputs1 + userInputs2;
        ByteArrayInputStream in = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(in);
        MenuPrincipal.action = new Scanner(System.in);

        assertEquals(ListeClients.listeClients.size(), 0);

        // Ajout du 1er membre
        controleur.ajouterMembre();
        assertEquals(ListeClients.listeClients.size(), 1);

        // Ajout du 2ème membre
        controleur.ajouterMembre();
        assertEquals(ListeClients.listeClients.size(), 2);

        // Teste les inputs (tous valides) du 1er membre
        assertEquals(ListeClients.listeClients.get(0).getNom(), "Premier");
        assertEquals(ListeClients.listeClients.get(0).getCourriel(), "1@membre.ca");
        assertEquals(ListeClients.listeClients.get(0).getAdresse(), "qqpart");
        assertEquals(ListeClients.listeClients.get(0).getVille(), "ville");
        assertEquals(ListeClients.listeClients.get(0).getProvince(), "qq");
        assertEquals(ListeClients.listeClients.get(0).getCodePostal(), "M1M1M1");

        // Teste les inputs invalides du 2e membre
        assertNotEquals(ListeClients.listeClients.get(1).getNom(), "n0m 1nval1d3");
        assertNotEquals(ListeClients.listeClients.get(1).getProvince(), "");
        assertNotEquals(ListeClients.listeClients.get(1).getCodePostal(), "111 111");
        assertNotEquals(ListeClients.listeClients.get(1).getCourriel(), "");
        assertNotEquals(ListeClients.listeClients.get(1).getCourriel(), "courriel@invalide");

        // Teste les inputs valides du 2e membre
        assertEquals(ListeClients.listeClients.get(1).getNom(), "nömVàlîde");
        assertEquals(ListeClients.listeClients.get(1).getCourriel(), "2@membre.ca");
        assertEquals(ListeClients.listeClients.get(1).getAdresse(), "qqpart2");
        assertEquals(ListeClients.listeClients.get(1).getVille(), "ville2");
        assertEquals(ListeClients.listeClients.get(1).getProvince(), "QC");
        assertEquals(ListeClients.listeClients.get(1).getCodePostal(), "M2M 2M2");

        System.setIn(sysInBackup);
    }


    @Test
    void testAjouterPro() {
        // inputs corrects
        String userInputs1 = "ProNumeroUn" + System.lineSeparator() +
                "1@test.ca" + System.lineSeparator() +
                "qqpart" + System.lineSeparator() +
                "ville" + System.lineSeparator() +
                "on" + System.lineSeparator() +
                "A1A1A1" + System.lineSeparator();

        // inputs incorrects
        String userInputs2 = "n0m___1nval1d3" + System.lineSeparator() +
                "00000" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "ProNuméroDeux" + System.lineSeparator() +

                "" + System.lineSeparator() +
                "123123" + System.lineSeparator() +
                "courriel@invalide" + System.lineSeparator() +
                "2@test.ca" + System.lineSeparator() +

                "" + System.lineSeparator() +
                "123 rue adresse trop longue" + System.lineSeparator() +
                "Adresse" + System.lineSeparator() +

                "" + System.lineSeparator() +
                "Nom de la ville dépasse les 14 caractères" + System.lineSeparator() +
                "Montréal" + System.lineSeparator() +

                "" + System.lineSeparator() +
                "0" + System.lineSeparator() +
                "Province_Invalide" + System.lineSeparator() +
                "QC" + System.lineSeparator() +

                "" + System.lineSeparator() +
                "code postal invalide" + System.lineSeparator() +
                "111 111" + System.lineSeparator() +
                "HHH HHH" + System.lineSeparator() +
                "H1H H1H" + System.lineSeparator() +
                "M2M 2M2" + System.lineSeparator();

        String userInputs = userInputs1 + userInputs2;
        ByteArrayInputStream in = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(in);
        MenuPrincipal.action = new Scanner(System.in);

        assertEquals(ListeClients.listeClients.size(), 0);

        // Ajout du 1er pro
        controleur.ajouterPro();
        assertEquals(ListeClients.listeClients.size(), 1);

        // Ajout du 2ème pro
        controleur.ajouterPro();
        assertEquals(ListeClients.listeClients.size(), 2);

        // Teste les inputs (tous valides) du 1er pro
        assertEquals(ListeClients.listeClients.get(0).getNom(), "ProNumeroUn");
        assertEquals(ListeClients.listeClients.get(0).getCourriel(), "1@test.ca");
        assertEquals(ListeClients.listeClients.get(0).getAdresse(), "qqpart");
        assertEquals(ListeClients.listeClients.get(0).getVille(), "ville");
        assertEquals(ListeClients.listeClients.get(0).getProvince(), "on");
        assertEquals(ListeClients.listeClients.get(0).getCodePostal(), "A1A1A1");

        // Teste les inputs invalides du 2e pro
        assertNotEquals(ListeClients.listeClients.get(1).getNom(), "n0m___1nval1d3");
        assertNotEquals(ListeClients.listeClients.get(1).getProvince(), "");
        assertNotEquals(ListeClients.listeClients.get(1).getCodePostal(), "111 111");
        assertNotEquals(ListeClients.listeClients.get(1).getCourriel(), "");
        assertNotEquals(ListeClients.listeClients.get(1).getCourriel(), "courriel@invalide");

        // Teste les inputs valides du 2e pro
        assertEquals(ListeClients.listeClients.get(1).getNom(), "ProNuméroDeux");
        assertEquals(ListeClients.listeClients.get(1).getCourriel(), "2@test.ca");
        assertEquals(ListeClients.listeClients.get(1).getAdresse(), "Adresse");
        assertEquals(ListeClients.listeClients.get(1).getVille(), "Montréal");
        assertEquals(ListeClients.listeClients.get(1).getProvince(), "QC");
        assertEquals(ListeClients.listeClients.get(1).getCodePostal(), "M2M 2M2");

        System.setIn(sysInBackup);
    }


    @Test
    void testValiderNumero() {
        Membre membre1 = new Membre("nom", "courriel@test.ca", "adresse", "ville", "qc", "A1A1A1");
        Membre membre2 = new Membre("nom2", "courriel2@test.ca", "adresse", "ville", "qc", "A1A 1A1");
        Professionnel pro = new Professionnel("pro", "pro@test.ca", "3 rue pro", "montreal", "qc", "H1H1H1");

        membre1.initialiser();
        membre2.initialiser();

        String numMb1 = membre1.getNumero();
        String numMb2 = membre2.getNumero();
        String numPro = pro.getNumero();

        ListeClients.ajouter(membre1);
        ListeClients.ajouter(membre2);
        ListeClients.ajouter(pro);

        String inputs = numMb1 + System.lineSeparator() +
                numMb2 + System.lineSeparator() +
                numPro + System.lineSeparator();

        ByteArrayInputStream in = new ByteArrayInputStream(inputs.getBytes());
        System.setIn(in);
        MenuPrincipal.action = new Scanner(System.in);

        Client membre1Bis = controleur.validerNumero();
        Client membre2Bis = controleur.validerNumero();
        Client proBis = controleur.validerNumero();

        assertEquals(membre1Bis.getNumero(), numMb1);
        assertNotEquals(membre1Bis.getNumero(), numMb2);
        assertNotEquals(membre1Bis.getNumero(), numPro);
        assertTrue(membre1Bis instanceof Membre);
        assertFalse(membre1Bis instanceof Professionnel);

        assertEquals(membre2Bis.getNumero(), numMb2);
        assertNotEquals(membre2Bis.getNumero(), numMb1);
        assertNotEquals(membre2Bis.getNumero(), numPro);
        assertTrue(membre2Bis instanceof Membre);
        assertFalse(membre2Bis instanceof Professionnel);

        assertEquals(proBis.getNumero(), numPro);
        assertNotEquals(proBis.getNumero(), numMb1);
        assertNotEquals(proBis.getNumero(), numMb2);
        assertTrue(proBis instanceof Professionnel);
        assertFalse(proBis instanceof Membre);

        System.setIn(sysInBackup);
    }

}
