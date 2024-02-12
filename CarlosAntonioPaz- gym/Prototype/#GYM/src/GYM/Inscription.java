package GYM;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Classe inscription, cas concret d'{@link GYM.Enregistrement Enregistrement}.
 * Possède les attributs supplémentaires suivants:
 * <br> Seance,
 * <br> date de la séance,
 * <br> numéro du membre
 *
 * @author Daniel Polotski
 */
public class Inscription extends Enregistrement {

    private Seance seance;
    private LocalDate dateSeance;
    private String numeroMembre;

    /**
     * Constructeur.
     *
     * @param seance
     * @param dateSeance
     * @param numeroMembre
     * @param commentaires
     */
    public Inscription(Seance seance, LocalDate dateSeance, String numeroMembre, String commentaires) {
        super(seance.getNumPro(), seance.getCodeSeance(), commentaires);

        this.seance = seance;
        this.dateSeance = dateSeance;
        this.numeroMembre = numeroMembre;
    }


    /**
     * Renvoie l'inscription sous forme de String
     *
     * @return String
     */
    @Override
    public String toString() {

        String str = super.toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = dateSeance.format(formatter);

        str += "Date à laquelle le service sera fourni : " + date + "\n";
        str += "Numéro du membre : " + numeroMembre + "\n";

        return str;
    }


    // Getters
    public Seance getSeance() {
        return this.seance;
    }

    public LocalDate getDateSeance() {
        return this.dateSeance;
    }

    public String getNumeroMembre() {
        return this.numeroMembre;
    }

    // Setters
    public void setNumeroMembre(String numeroMembre) {
        this.numeroMembre = numeroMembre;
    }
}