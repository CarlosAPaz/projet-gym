package GYM;

import java.util.ArrayList;

/**
 * Classe professionnel, {@link GYM.Client client} de #GYM fournisseur de services.
 * Possede une ArrayList de services.
 *
 * @author Daniel Polotski
 */
public class Professionnel extends Client {

    private ArrayList<Service> services;

    /**
     * Constructeur.
     * Assigne d'emblée nom et numéro, et initialise une liste de services vide.
     *
     * @param nom
     * @see GYM.Client#Client(String, String, String, String, String, String, String)
     */
    public Professionnel(String nom, String courriel, String adresse, String ville, String province, String codePostal) {
        super(nom, courriel, adresse, ville, province, codePostal, Client.genererNum());
        this.services = new ArrayList<Service>();
    }


    /**
     * Ajoute un {@link GYM.Service service} à la liste de services.
     *
     * @param service service à ajouter
     */
    public void ajoutService(Service service) {
        this.services.add(service);
    }


    /**
     * Supprime un {@link GYM.Service service} de la liste de services.
     *
     * @param service service à supprimer
     * @return true si supprime avec succès, false si une erreur s'est produite
     */
    public boolean supprimerService(Service service) {
        try {
            this.services.remove(service);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // Getter
    public ArrayList<Service> getServices() {
        return this.services;
    }

    /**
     * Trouver le {@link GYM.Service service} desiré
     */
    public Service trouverService(String code) {
        for (Service service : services) {
            if (service.getCode().equals(code)) return service;
        }

        return null;
    }
}