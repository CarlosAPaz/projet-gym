package GYM;

/**
 * @inheritDoc Cas spécifique d'un {@link GYM.Enregistrement Enregistrement}, utilisé pour confirmer une séance.</br>
 * Une liste d'enregistrements est stockée dans chaque instance de {@link GYM.Seance seance}.
 */
public class Confirmation extends Enregistrement {

    private String numeroMembre;

    /**
     * Constructeur de la confirmation
     *
     * @param seance       séance à laquelle on confirme l'inscription
     * @param numeroMembre numéro du membre qui confirme l'inscription
     * @param commentaires
     */
    public Confirmation(Seance seance, String numeroMembre, String commentaires) {
        super(seance.getNumPro(), seance.getCodeSeance(), commentaires);

        this.numeroMembre = numeroMembre;
    }


    /**
     * Renvoie la confirmation sous forme de String
     *
     * @return String
     */
    @Override
    public String toString() {

        String str = super.toString();

        str += "Numéro du membre : " + numeroMembre + "\n";

        return str;
    }


    // Getter
    public String getNumeroMembre() {
        return this.numeroMembre;
    }

    // Setter
    public void setNumeroMembre(String numeroMembre) {
        this.numeroMembre = numeroMembre;
    }
}