package GYM;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GetSeanceHTest {

    Professionnel c1 = new Professionnel("Carlos", "1@moi.ca", "2 rue principale", "montreal", "QC", "L0L 1N0");


    @Test
    void testAucunceSeance() {

        // date deja passer
        LocalDate d1 = LocalDate.of(2019, 7, 20);
        LocalDate d2 = LocalDate.of(2019, 9, 20);
        LocalTime t1 = LocalTime.of(12, 12);
        ArrayList<String> r = new ArrayList<String>();
        r.add("mercredi");
        TypeService type1 = new TypeService("piscine");
        Service s1 = new Service(c1, 50, 20, d1, d2, t1, r, "hell yeah", type1.getCode());

        ArrayList<Seance> liste = s1.getSeancesH();

        assertEquals(0, liste.size());

    }

    @Test
    void testAucunceSeance2() {

        // date Future
        LocalDate d1 = LocalDate.of(2021, 7, 20);
        LocalDate d2 = LocalDate.of(2021, 9, 20);
        LocalTime t1 = LocalTime.of(12, 12);
        ArrayList<String> r = new ArrayList<String>();
        r.add("mercredi");
        TypeService type1 = new TypeService("piscine");
        Service s1 = new Service(c1, 50, 20, d1, d2, t1, r, "hell yeah", type1.getCode());

        ArrayList<Seance> liste = s1.getSeancesH();

        assertEquals(0, liste.size());

    }

    @Test
    void test1Seance() {

        // une seance a ajouter.
        LocalDate d1 = LocalDate.of(2020, 7, 20);
        LocalDate d2 = LocalDate.of(2020, 9, 20);
        LocalTime t1 = LocalTime.of(12, 12);
        ArrayList<String> r = new ArrayList<String>();
        String s = LocalDate.now().getDayOfWeek().toString();
        String jour = "";
        if (s.equalsIgnoreCase("monday")) jour = "lundi";

        if (s.equalsIgnoreCase("tuesday")) jour = "mardi";

        if (s.equalsIgnoreCase("wednesday")) jour = "mercredi";

        if (s.equalsIgnoreCase("thursday")) jour = "jeudi";

        if (s.equalsIgnoreCase("friday")) jour = "vendredi";

        if (s.equalsIgnoreCase("saturday")) jour = "samedi";

        if (s.equalsIgnoreCase("sunday")) jour = "dimanche";
        r.add(jour);
        TypeService type1 = new TypeService("piscine");
        Service s1 = new Service(c1, 50, 20, d1, d2, t1, r, "hell yeah", type1.getCode());

        ArrayList<Seance> liste = s1.getSeancesH();
        LocalDate reponse = LocalDate.now();
        LocalDate aVerifier = liste.get(0).getDateSeance();
        assertEquals(1, liste.size());
        assertTrue(aVerifier.isEqual(reponse));


    }

    @Test
    void testPlusieurSeances() {


        LocalDate d1 = LocalDate.of(2020, 7, 20);
        LocalDate d2 = LocalDate.of(2020, 9, 20);
        LocalTime t1 = LocalTime.of(12, 12);
        ArrayList<String> r = new ArrayList<String>();
        String s = LocalDate.now().getDayOfWeek().toString();

        // pour obtenir aujourdhui et hier
        for (int i = 0; i < 2; i++) {
            String jour = "";
            if (s.equalsIgnoreCase("monday")) jour = "lundi";

            if (s.equalsIgnoreCase("tuesday")) jour = "mardi";

            if (s.equalsIgnoreCase("wednesday")) jour = "mercredi";

            if (s.equalsIgnoreCase("thursday")) jour = "jeudi";

            if (s.equalsIgnoreCase("friday")) jour = "vendredi";

            if (s.equalsIgnoreCase("saturday")) jour = "samedi";

            if (s.equalsIgnoreCase("sunday")) jour = "dimanche";
            r.add(jour);
            s = LocalDate.now().minusDays(1).getDayOfWeek().toString();
        }

        TypeService type1 = new TypeService("piscine");
        Service s1 = new Service(c1, 50, 20, d1, d2, t1, r, "hell yeah", type1.getCode());

        ArrayList<Seance> liste = s1.getSeancesH();

        // si on est dimanche, la semaine vient de commencer donc impossible d<avoir plus que une seance
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY) {
            LocalDate reponse = LocalDate.now();
            LocalDate aVerifier = liste.get(0).getDateSeance();
            assertEquals(1, liste.size());
            assertTrue(aVerifier.isEqual(reponse));

        } else {
            LocalDate reponse1 = LocalDate.now();
            LocalDate aVerifier1 = liste.get(0).getDateSeance();
            LocalDate reponse2 = LocalDate.now().minusDays(1);
            LocalDate aVerifier2 = liste.get(1).getDateSeance();

            assertEquals(2, liste.size());
            assertTrue(aVerifier1.isEqual(reponse1));
            assertTrue(aVerifier2.isEqual(reponse2));

        }
    }

}
