package GYM;

/**
 * Généralise un rapport de services fournis.</br>
 * Possède un attribut contenu.
 */
public abstract class Rapport {
    protected String contenu;

    /**
     * Enregistre le rapport sur le disque
     */
    public abstract void enregistrer();


    /**
     * Affiche le contenu du rapport
     */
    public void imprimer() {
        System.out.println(this.contenu);
    }
}