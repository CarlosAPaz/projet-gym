package GYM;

/**
 * Interface qui permet de gérer les options et choix offerts à l'utilisateur.
 */
public interface Menu {

    // Méthode abstraites à implémenter

    /**
     * Affiche les différentes actions possibles et permet à l'utilisateur de
     * choisir l'action qu'il désire effectuer
     */
    public abstract void afficheMenu();

    /**
     * Demande à l'utilisateur de choisir l'action à effectuer
     *
     * @return int numéro associé à l'action choisie
     */
    public abstract int choixAction();

}
