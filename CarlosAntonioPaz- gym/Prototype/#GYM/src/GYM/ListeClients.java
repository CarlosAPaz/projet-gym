package GYM;

import java.util.ArrayList;

/**
 * Classe à instance unique.</br>
 * Contient la liste de tous les {@link GYM.Client clients} de #GYM.
 */
public class ListeClients {

    public static ArrayList<Client> listeClients = new ArrayList<Client>();


    /**
     * Recherche le {@link GYM.Client client} parmi la liste de clients.
     * La recherche se fait par numéro, ou par courriel.
     *
     * @param attribut attribut utilisé pour trouver le client
     * @param param    paramètre de recherche (1 : numero unique, 2 : courriel)
     * @return client
     */
    public static Client rechercheClient(String attribut, int param) {
        for (Client client : listeClients) {
            if (param == 1) {
                if (client.getNumero().equals(attribut)) return client;
            } else if (param == 2) {
                if (client.getCourriel().equals(attribut)) return client;
            }
        }

        return null;
    }

    /**
     * Suppression d'un {@link GYM.Client client} dans la liste de clients
     *
     * @param client client à supprimer
     */
    public static void supprimer(Client client) {
        listeClients.remove(client);
        System.out.println(client.getNom() + " a été supprimé avec succès");
    }


    /**
     * Ajout d'un {@link GYM.Client client} dans la liste de clients
     *
     * @param client client à ajouter
     */
    public static void ajouter(Client client) {
        listeClients.add(client);

        if (client instanceof Membre) {
            System.out.println("Le membre a été ajouté avec succès");
        } else {
            System.out.println("Le professionnel a été ajouté avec succès");
        }
    }
}