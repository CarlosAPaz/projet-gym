package GYM;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe service.
 * Possede les attributs suivants:
 * <br> Professionnel,
 * <br> seances,
 * <br> code de service,
 * <br> frais.
 * Possede un attribut et une methode statique pour gerer les codes de service.
 *
 * @author Daniel Polostki
 */
public class Service {

    private Client pro;
    private ArrayList<Seance> seances = new ArrayList<Seance>();
    private TypeService typeService;
    private String codeService;
    private int fraisService;
    private int capacite;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalTime heureService;
    private ArrayList<String> recurrenceH;
    private String commentaires;
    private int prochainCode = 1;


    /**
     * Constructeur.
     * Initialise une liste vide de {@link GYM.Seance séances} et genere un code unique.
     *
     * @param pro
     * @param fraisService
     */
    public Service(Client pro, int fraisService, int capacite, LocalDate dateDebut,
                   LocalDate dateFin, LocalTime heureService, ArrayList<String> recurrenceH, String commentaires, String type) {
        this.pro = pro;
        this.codeService = Service.genererCode(type, pro.getNumero());
        this.fraisService = fraisService;
        this.capacite = capacite;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heureService = heureService;
        this.recurrenceH = recurrenceH;
        this.commentaires = commentaires;

        for (TypeService typeService : TypeService.listeTypes) {
            if (typeService.getCode().equals(type)) {
                this.typeService = typeService;
            }
        }

        ajoutInitial();
    }


    /**
     * Création des {@link GYM.Seance séances} aux bons jours entre la date de début et de fin
     */
    private void ajoutInitial() {
        LocalDate prochaine = dateDebut.minusDays(1);
        String s;
        Iterator<String> i = recurrenceH.iterator();
        DayOfWeek jour = null;
        Seance seance = null;

        while (i.hasNext()) {

            s = (String) i.next();

            if (s.equalsIgnoreCase("lundi")) jour = DayOfWeek.MONDAY;

            if (s.equalsIgnoreCase("mardi")) jour = DayOfWeek.TUESDAY;

            if (s.equalsIgnoreCase("mercredi")) jour = DayOfWeek.WEDNESDAY;

            if (s.equalsIgnoreCase("jeudi")) jour = DayOfWeek.THURSDAY;

            if (s.equalsIgnoreCase("vendredi")) jour = DayOfWeek.FRIDAY;

            if (s.equalsIgnoreCase("samedi")) jour = DayOfWeek.SATURDAY;

            if (s.equalsIgnoreCase("dimanche")) jour = DayOfWeek.SUNDAY;

            while (prochaine.with(TemporalAdjusters.next(jour)).isBefore(dateFin) || prochaine.with(TemporalAdjusters.next(jour)).isEqual(dateFin)) {

                prochaine = prochaine.with(TemporalAdjusters.next(jour));
                seance = new Seance(
                        this.pro.getNumero(),
                        this.genererCodeSeance(this.codeService),
                        this.typeService.getNom(),
                        this.commentaires
                );

                seance.setDateSeance(prochaine);
                seance.setFrais(fraisService);
                seance.setCapacite(capacite);
                seance.setHeure(heureService);

                seances.add(seance);

            }

            // Reinitialiser la variable
            prochaine = dateDebut.minusDays(1);
        }
    }

    /**
     * Création/suppression des {@link GYM.Seance séances} pour correspondre aux changements, entre la date de début
     * et de fin
     */
    public void modifierSeances(LocalDate dateDebut, LocalDate dateFin, ArrayList<String> recurrenceH) {

        LocalDate prochaine = dateDebut.minusDays(1);
        String s;
        Iterator<String> i = recurrenceH.iterator();
        DayOfWeek jour = null;
        Seance seance = null;

        while (i.hasNext()) {

            s = (String) i.next();

            if (s.equalsIgnoreCase("lundi")) jour = DayOfWeek.MONDAY;

            if (s.equalsIgnoreCase("mardi")) jour = DayOfWeek.TUESDAY;

            if (s.equalsIgnoreCase("mercredi")) jour = DayOfWeek.WEDNESDAY;

            if (s.equalsIgnoreCase("jeudi")) jour = DayOfWeek.THURSDAY;

            if (s.equalsIgnoreCase("vendredi")) jour = DayOfWeek.FRIDAY;

            if (s.equalsIgnoreCase("samedi")) jour = DayOfWeek.SATURDAY;

            if (s.equalsIgnoreCase("dimanche")) jour = DayOfWeek.SUNDAY;

            while (prochaine.with(TemporalAdjusters.next(jour)).isBefore(dateFin) || prochaine.with(TemporalAdjusters.next(jour)).isEqual(dateFin)) {

                prochaine = prochaine.with(TemporalAdjusters.next(jour));
                seance = new Seance(
                        this.pro.getNumero(),
                        genererCodeSeance(this.codeService),
                        this.typeService.getNom(),
                        this.commentaires
                );
                seance.setDateSeance(prochaine);
                seance.setFrais(this.fraisService);
                seance.setCapacite(this.capacite);
                seance.setHeure(this.heureService);

                if (seance != null) seances.add(seance);

            }

            // Reinitialiser la variable
            prochaine = dateDebut.minusDays(1);
        }
    }


    /**
     * Generateur de code unique.</br>
     * !!! Ce code de service est pour usage interne du programme uniquement. !!!
     *
     * @return code de service en String
     */
    public static String genererCode(String type, String pro) {
        String code = "";
        String last2Digits = pro.substring(7);
        code = type + "-" + last2Digits;
        return code;
    }


    /**
     * Generateur de code unique.
     *
     * @return code d'une seance en String
     */
    public String genererCodeSeance(String codeService) {

        String code = Integer.toString(this.prochainCode);

        for (int i = code.length(); i < 2; i++) {
            code = "0" + code;
        }

        prochainCode++;

        String[] serviceCodeArr = codeService.split("-");

        code = serviceCodeArr[0] + code + serviceCodeArr[1];
        return code;
    }


    /**
     * Retourne la {@link GYM.Seance séance} du service qui a lieu aujourd'hui
     *
     * @return séance
     */
    public Seance getSeanceJour() {

        LocalDate now = LocalDate.now();

        for (Seance seance : seances) {
            if (seance.getDateSeance().isEqual(now)) {
                return seance;
            }
        }

        return null;
    }


    /**
     * Retourne les {@link GYM.Seance séances} de la semaine précédante du service
     *
     * @return les séances de la semaine
     */
    public ArrayList<Seance> getSeancesH() {
        ArrayList<Seance> seancesH = new ArrayList<Seance>();

        LocalDate debut = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        LocalDate fin = LocalDate.now();

        for (Seance seance : seances) {
            LocalDate date = seance.getDateSeance();
            boolean limiteDebut = date.isAfter(debut) || date.isEqual(debut);
            boolean limiteFin = date.isBefore(fin) || date.isEqual(fin);

            // Vérifie si la séance a eu lieu cette semaine
            if (limiteDebut && limiteFin) {
                seancesH.add(seance);
            }
        }

        return seancesH;
    }

    /**
     * Retourne les {@link GYM.Seance séances} de la semaine à venir du service
     *
     * @return les séances de la semaine
     */
    public ArrayList<Seance> getSeancesHVenir() {
        ArrayList<Seance> seancesH = new ArrayList<Seance>();

        LocalDate debut = LocalDate.now();
        LocalDate fin = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));


        for (Seance seance : seances) {
            LocalDate date = seance.getDateSeance();
            boolean limiteDebut = date.isAfter(debut) || date.isEqual(debut);
            boolean limiteFin = date.isBefore(fin) || date.isEqual(fin);

            // Vérifie si la séance a eu lieu cette semaine
            if (limiteDebut && limiteFin) {
                seancesH.add(seance);
            }
        }

        return seancesH;
    }

    /**
     * Retourne le nombre de {@link GYM.Seance séances} de la semaine précédante du service
     *
     * @return nombre de séances de la semaine
     */
    public int getNbSeancesH() {
        return getSeancesH().size();
    }


    /**
     * Retourne le montant généré par les {@link GYM.Seance séances} de la semaine précédante du service,
     * moins les frais du gym (30% du total)
     *
     * @return montant généré de la semaine
     */
    public double getMontantGenereH() {

        double montant = 0;
        ArrayList<Seance> seancesH = getSeancesH();

        for (Seance seance : seancesH) {
            // nombre inscrit * frais
            montant += seance.getNbInscrit() * seance.getFrais();
        }

        return montant * 0.70;
    }


    /**
     * Calcule et retourne le nombre de services donnés dans la semaine, qui
     * correspond au nombre de personnes à qui le service a été fourni
     *
     * @return nombre de services donnés de la semaine
     */
    public int getServicesDonnesH() {
        int total = 0;
        ArrayList<Seance> seancesH = getSeancesH();

        for (Seance seance : seancesH) {
            total += seance.getNbInscrit();
        }

        return total;
    }


    // Getters
    public String getNumeroPro() {
        return this.pro.getNumero();
    }

    public String getCode() {
        return this.codeService;
    }

    public String getNom() {
        return this.typeService.getNom();
    }

    public int getFrais() {
        return this.fraisService;
    }

    public ArrayList<Seance> getSeance() {
        return this.seances;
    }

    public int getCapacite() {
        return this.capacite;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public LocalTime getHeure() {
        return this.heureService;
    }

    public ArrayList<String> getRec() {
        return this.recurrenceH;
    }

    public String getCom() {
        return this.commentaires;
    }

    // Setters
    public void setFrais(int fraisService) {
        this.fraisService = fraisService;
        LocalDate auj = LocalDate.now();

        for (Seance seance : seances) {

            //seulement modifier si la date n'est pas deja passee

            if (!(seance.getDateSeance().isBefore(auj))) {
                seance.setFrais(fraisService);
            }
        }
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
        LocalDate auj = LocalDate.now();

        for (Seance seance : seances) {
            //seulement modifier si la date n'est pas deja passee

            if (!(seance.getDateSeance().isBefore(auj))) {
                seance.setCapacite(capacite);
            }
        }
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setHeure(LocalTime heureService) {
        this.heureService = heureService;
        LocalDate auj = LocalDate.now();

        for (Seance seance : seances) {

            if (!(seance.getDateSeance().isBefore(auj))) {
                seance.setHeure(heureService);
            }
        }
    }

    public void setRec(ArrayList<String> recurrenceH) {
        this.recurrenceH = recurrenceH;
    }

    public void setCom(String commentaires) {
        this.commentaires = commentaires;
    }
}