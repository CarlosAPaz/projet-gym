package GYM;

/**
 * Classe membre, de #GYM recevant des services.
 * Possede un attribut suspendu.
 *
 * @author Daniel Polotski
 */
public class Membre extends Client {

    private boolean suspendu;

    /**
     * Constructeur.
     * Membre suspendu dès le debut.
     *
     * @param nom
     * @see GYM.Client#Client(String, String, String, String, String, String)
     */
    public Membre(String nom, String courriel, String adresse, String ville, String province, String codePostal) {
        super(nom, courriel, adresse, ville, province, codePostal);
        this.suspendu = true;
    }


    /**
     * Initialise le membre en assignant un numéro et en mettant suspendu = false.
     */
    public void initialiser() {
        this.suspendu = false;
        this.numeroUnique = Client.genererNum();
    }


    // Getter
    public boolean getSuspendu() {
        return this.suspendu;
    }

    // Setter
    public void setSuspendu(boolean suspendu) {
        this.suspendu = suspendu;
    }
}