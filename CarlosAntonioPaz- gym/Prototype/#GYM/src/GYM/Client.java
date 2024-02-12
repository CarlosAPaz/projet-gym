package GYM;

/**
 * Classe client, generalise un utilisateur de #GYM.
 * Possede un nom et un numero d'id, qui est gere par une methode statique.
 *
 * @author Daniel Polotski
 */
public class Client {

    private String nom;
    private String courriel;
    private String adresse;
    private String ville;
    private String province;
    private String codePostal;
    protected String numeroUnique;

    private static int prochainNum = 1;


    /**
     * Constructeur avec nom uniquement. Assignation de numero retardée, utilise pour {@link GYM.Membre}
     *
     * @param nom      nom du client
     * @param courriel addresse courriel associee au compte Facebook du client
     */
    public Client(String nom, String courriel, String adresse, String ville, String province, String codePostal) {
        this.nom = nom;
        this.courriel = courriel;
        this.adresse = adresse;
        this.ville = ville;
        this.province = province;
        this.codePostal = codePostal;
    }

    /**
     * Constructeur avec nom et numero, utilise pour {@link GYM.Professionnel}
     *
     * @param nom          nom du client
     * @param courriel     addresse courriel associee au compte Facebook du client
     * @param numeroUnique numero unique du client
     */
    public Client(String nom, String courriel, String adresse, String ville, String province, String codePostal, String numeroUnique) {
        this.nom = nom;
        this.courriel = courriel;
        this.adresse = adresse;
        this.ville = ville;
        this.province = province;
        this.codePostal = codePostal;
        this.numeroUnique = numeroUnique;
    }


    /**
     * Generateur de numéro unique d'id
     *
     * @return numéro en String
     */
    public static String genererNum() {
        String numero = Integer.toString(prochainNum++);

        for (int i = numero.length(); i < 9; i++) {
            numero = "0" + numero;
        }

        return numero;
    }


    // Getters
    public String getNom() {
        return this.nom;
    }

    public String getNumero() {
        return this.numeroUnique;
    }

    public String getCourriel() {
        return this.courriel;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public String getVille() {
        return this.ville;
    }

    public String getProvince() {
        return this.province;
    }

    public String getCodePostal() {
        return this.codePostal;
    }


    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    //utiliser dans les test
    public static void reset() {

        Client.prochainNum = 1;

    }
}