package GYM;

import java.util.ArrayList;

/**
 * Représente le type d'un {@link GYM.Service service}.</br>
 * Contient un nom et un code à trois chiffres.</br>
 */
public class TypeService {

    private String nom;
    private String codeType;

    private static int prochainCode = 1;
    public static ArrayList<TypeService> listeTypes = new ArrayList<TypeService>();

    /**
     * Constructeur
     *
     * @param nom nom du type de service
     */
    public TypeService(String nom) {

        this.nom = nom;
        this.codeType = TypeService.genererCode();
        TypeService.listeTypes.add(this);

    }

    // Getters
    public String getNom() {

        return this.nom;

    }

    public String getCode() {

        return codeType;

    }

    /**
     * Génère un code à trois chiffres unique
     *
     * @return code à trois chiffres
     */
    public static String genererCode() {
        String code = Integer.toString(prochainCode);
        for (int i = code.length(); i < 3; i++) {
            code = "0" + code;
        }
        prochainCode++;
        return code;
    }

    /**
     * !! Pour tests seulement !!</br>
     * Ré-initialise le compteur des codes
     */
    public static void reset() {

        TypeService.prochainCode = 1;
    }


}
