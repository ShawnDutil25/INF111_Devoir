package modele.reseau;

import modele.communication.Message;
import modele.physique.ObjetMobile;
import modele.physique.Position;

import java.util.Random;

/**
 * Représente un téléphone cellulaire mobile dans le réseau.
 * Hérite d'ObjetMobile et implémente l'interface UniteCellulaire.
 * Contient les informations de connexion, de position et de comportement réseau.
 *
 * @author Shawn Dutil
 * @version Automne 2025
 */
public class Cellulaire extends ObjetMobile implements UniteCellulaire {


    // Constantes
    public static final int NON_CONNECTE = -1;
    public static final double PROB_APPELER = 0.05;
    public static final double PROB_ENVOI_MSG = 0.2;
    public static final double PROB_DECONNEXION = 0.1;

    // Attributs
    private String numeroLocal;
    private int numeroConnexion = NON_CONNECTE;
    private String numeroConnecte = null;

    public Antenne getAntenneConnecte() {
        return antenneConnecte;
    }

    public void setAntenneConnecte(Antenne antenneConnecte) {
        this.antenneConnecte = antenneConnecte;
    }

    public Antenne antenneConnecte;
    private Random random = new Random();
    private GestionnaireReseau gestionnaireReseau = GestionnaireReseau.getInstance();


    /**
     * Constructeur de l'objet mobile.
     * La direction initiale est fixée à 0.
     *
     * @param position          position initiale de l'objet
     * @param vitesse           vitesse de déplacement en pixels par itération
     * @param deviationStandard déviation standard pour la direction en radian
     */
    public Cellulaire(String numeroLocal, Position position, double vitesse, double deviationStandard) {
        super(position, vitesse, deviationStandard);
        this.numeroLocal = numeroLocal;

        // Initialise l'antenne connectée dès la création
        this.antenneConnecte = GestionnaireReseau.getInstance().getAntenneProche(this.getPosition());
        if (this.antenneConnecte != null) {
            this.antenneConnecte.ajouterCellulaire(this);
        }
    }


    /**
     * Vérifie si le cellulaire est actuellement connecté.
     * @return true si le cellulaire est connecté, false sinon
     */
    public boolean estConnecte(){
        return this.numeroConnexion != NON_CONNECTE;
    }

    /**
     * Compare le numéro local du cellulaire à un numéro donné.
     * @param numeroRecu numéro à comparer
     * @return true si les deux numéros sont identiques, false sinon
     */
    public boolean comparerNumero(String numeroRecu){
        return this.numeroLocal.equals(numeroRecu);
    }

    /**
     * Retourne le numéro de connexion actuel.
     * @return le numéro de connexion
     */
    public int getNumeroConnexion() {
        return numeroConnexion;
    }

    /**
     * Retourne le numéro local du cellulaire.
     * @return le numéro local
     */
    public String getNumeroLocal() {
        return numeroLocal;
    }

    public void effectuerTour() {

        this.seDeplacer();

        //Trouver l'antenne la plus proche via le gestionnaire réseau
        Antenne antenneProche = gestionnaireReseau.getAntenneProche(this.position);

        //Changement d'antenne si nécessaire
        if (antenneProche != null && antenneProche != this.antenneConnecte) {

            if (this.antenneConnecte != null) {
                this.antenneConnecte.retirerCellulaire(this);
            }

            antenneProche.ajouterCellulaire(this);
            this.antenneConnecte = antenneProche;
        }

        // Ici tu pourras ajouter plus tard logique d'appels/messages
    }


    /**
     * Retourne une représentation textuelle du cellulaire.
     * @return une chaîne contenant le numéro local et la position actuelle
     */
    @Override
    public String toString(){
        return "Numéro local : " + this.numeroLocal + " Position : " + this.position;
    }

    @Override
    public int appeler(String numeroAppele, String numeroAppelant, Antenne antenne) {
        return 0;
    }

    @Override
    public Cellulaire repondre(String numeroAppele, String numeroAppelant, int numeroConnexion) {
        return null;
    }

    @Override
    public void finAppelLocal(String numeroAppele, int numeroConnexion) {

    }

    @Override
    public void finAppelDistant(String numeroAppele, int numeroConnexion) {

    }

    @Override
    public void envoyer(Message message, int numeroConnexion) {

    }

    @Override
    public void recevoir(Message message) {

    }
}
