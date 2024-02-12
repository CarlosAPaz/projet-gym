package GYM;

import java.util.ArrayList;

/**
 * Classe à instance unique.
 * Contient la liste de tous les {@link GYM.Service services} offerts chez #GYM.
 */
public class ListeServices {

    public static ArrayList<Service> listeServices = new ArrayList<Service>();

    /**
     * Trouve le {@link GYM.Service service} à l'aide de son code de service parmi la liste des
     * services
     *
     * @param code code de service
     * @return Service
     */
    public static Service trouverService(String code) {
        for (Service service : listeServices) {
            if (service.getCode().equals(code)) return service;
        }

        System.out.println("Le service au code " + code + " n'a pas été trouvé...");
        return null;
    }

    /**
     * Trouve une {@link GYM.Seance séance} à l'aide de son code de séance parmi les services dans la liste de services
     *
     * @param codeSeance code de la séance recherchés
     * @return Séance, ou null si introuvée
     */
    public static Seance trouverSeance(String codeSeance) {
        for (Service service : listeServices) {
            Seance seance = service.getSeanceJour();
            if (seance.getCodeSeance().equals(codeSeance)) return seance;
        }

        System.out.println("La seance au code " + codeSeance + " n'a pas été trouvée...");
        return null;
    }


    /**
     * Suppression du {@link GYM.Service service} de la liste des services
     *
     * @param service service à supprimer
     */
    public static void supprimer(Service service) {
        listeServices.remove(service);
        System.out.println("Le service a été supprimé avec succès");
    }


    /**
     * Ajout d'un {@link GYM.Service service} dans la liste des services
     *
     * @param service service à ajouter
     */
    public static void ajouter(Service service) {
        listeServices.add(service);
        System.out.println("Le service a été ajouté avec succès");
    }


    /**
     * Cherche les {@link GYM.Service services} qui possèdent des {@link GYM.Seance séances} à la date d'aujourd'hui
     *
     * @return liste des séances d'aujourd'hui
     */
    public static ArrayList<Seance> getServicesDuJour() {

        ArrayList<Seance> seancesDuJour = new ArrayList<Seance>();

        for (Service service : listeServices) {
            Seance seance = service.getSeanceJour();

            if (seance != null) {
                seancesDuJour.add(seance);
            }
        }

        return seancesDuJour;
    }
}