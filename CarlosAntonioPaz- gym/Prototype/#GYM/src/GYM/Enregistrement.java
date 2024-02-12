package GYM;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Generalise un enregistrement sur le disque.
 * Possede les attributs suivants:
 * <br> date actuelle,
 * <br> numero du professionnel,
 * <br> code de seance,
 * <br> commentaires
 *
 * @author Daniel Polotski
 */
public abstract class Enregistrement {

    private LocalDate dateActuelle;
    private String heureActuelle;
    private String numeroPro;
    private String codeSeance;
    private String commentaires;

    /**
     * Constructeur.
     *
     * @param numeroPro
     * @param codeSeance
     * @param commentaires
     */
    public Enregistrement(String numeroPro, String codeSeance, String commentaires) {

        this.numeroPro = numeroPro;
        this.codeSeance = codeSeance;
        this.commentaires = commentaires;
        dateActuelle = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        heureActuelle = LocalTime.now().format(formatter);
    }


    /**
     * Renvoie l'enregistrement sous forme de String
     *
     * @return String
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = dateActuelle.format(formatter);

        String str = "\nDate et heure actuelles : " + date + " " + heureActuelle + "\n";
        str += "Numéro du professionnel : " + numeroPro + "\n";
        str += "Code de seance : " + codeSeance + "\n";
        if (commentaires != null) str += "Commentaires : " + commentaires + "\n";

        return str;
    }


    // Getters
    public LocalDate getDateActu() {
        return this.dateActuelle;
    }

    public String getHeureActuelle() {
        return this.heureActuelle;
    }

    public String getNumPro() {
        return this.numeroPro;
    }

    public String getCodeSeance() {
        return this.codeSeance;
    }

    public String getCommentaire() {
        return this.commentaires;
    }

    // Setters
    public void setDateActu(LocalDate dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public void setNumeroPro(String numeroPro) {
        this.numeroPro = numeroPro;
    }

    public void setCodeSeance(String codeSeance) {
        this.codeSeance = codeSeance;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }
}