package GYM;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * @inheritDoc Cas spécifique d'un enregistrement qui représente une séance de service.
 */
public class Seance extends Enregistrement {


    private LocalDate dateSeance;
    private LocalTime heureService;
    private int capacite;
    private int frais;
    private String nomTypeService;
    private ArrayList<Inscription> inscriptions = new ArrayList<Inscription>();
    private ArrayList<Confirmation> confirmations = new ArrayList<Confirmation>();


    /**
     * Constructeur.
     *
     * @param numeroPro
     * @param codeSeance
     */
    public Seance(String numeroPro, String codeSeance, String nomTypeService, String commentaires) {
        super(numeroPro, codeSeance, commentaires);
        this.nomTypeService = nomTypeService;
    }


    /**
     * Crée une {@link GYM.Inscription inscription} et l'ajoute à la liste des inscriptions
     *
     * @param numeroMembre
     * @param commentaires
     * @return inscription
     */
    public Inscription ajouterInscription(String numeroMembre, String commentaires) {
        Inscription inscription = new Inscription(this, LocalDate.now(), numeroMembre, commentaires);
        this.inscriptions.add(inscription);
        return inscription;
    }


    /**
     * Vérifie si le numéro du membre figure dans la liste des inscriptions
     *
     * @param numero numéro du membre
     * @return booléen
     */
    public boolean validiteInscription(String numero) {
        for (Inscription inscription : inscriptions) {
            if (inscription.getNumeroMembre().equals(numero)) {
                System.out.println("\nL'inscription est valide. Vous pouvez procéder à la confirmation.\n");
                return true;
            }
        }

        System.out.println("Le client n'est pas inscrit à la séance");
        return false;
    }

    public boolean confirmationDupliquee(Confirmation confirmation) {
        for (Confirmation confirmationEnregistree : confirmations) {
            if (confirmationEnregistree.getNumeroMembre().equals(confirmation.getNumeroMembre())) {
                return true;
            }
        }
        this.confirmations.add(confirmation);
        return false;
    }

    /**
     * Retourne s'il y a des places disponibles dans la séance
     *
     * @return true si oui, false sinon
     */
    public boolean placesDisponibles() {
        return inscriptions.size() == capacite;
    }

    // Getters
    public LocalDate getDateSeance() {
        return this.dateSeance;
    }

    public LocalTime getHeure() {
        return this.heureService;
    }

    public int getCapacite() {
        return this.capacite;
    }

    public int getFrais() {
        return this.frais;
    }

    public String getNomTypeService() {
        return nomTypeService;
    }

    public ArrayList<Inscription> getInscriptions() {
        return this.inscriptions;
    }

    public ArrayList<Confirmation> getConfirmations() {
        return this.confirmations;
    }

    public int getNbInscrit() {
        return inscriptions.size();
    }


    // Setters
    public void setDateSeance(LocalDate date) {
        this.dateSeance = date;
    }

    public void setHeure(LocalTime heureService) {
        this.heureService = heureService;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void setFrais(int frais) {
        this.frais = frais;
    }
}