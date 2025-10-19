package modele.reseau;

import modele.communication.Message;
import modele.gestionnaires.GestionnaireScenario;
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
    public Antenne antenneConnecte;
    private Random random = new Random();
    private GestionnaireReseau gestionnaireReseau = GestionnaireReseau.getInstance();

    /**
     * Constructeur de l'objet mobile.
     * La direction initiale est fixée à 0.
     *
     * @param position position initiale de l'objet
     * @param vitesse vitesse de déplacement en pixels par itération
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
     * Effectue un tour de simulation pour ce cellulaire.
     */
    public void effectuerTour(){

        this.seDeplacer();


        Antenne antenneProche = gestionnaireReseau.getAntenneProche(this.position);
        if (antenneProche != null && antenneProche != this.antenneConnecte) {
            if (this.antenneConnecte != null) {
                this.antenneConnecte.retirerCellulaire(this);
            }
            antenneProche.ajouterCellulaire(this);


            if (this.estConnecte()) {
                antenneProche.mettreAJourConnexion(this.numeroConnexion, this.antenneConnecte);
            }

            this.antenneConnecte = antenneProche;
        }


        if (this.estConnecte()) {

            if (random.nextDouble() < PROB_ENVOI_MSG) {
                String texte = GestionnaireScenario.obtenirMessage(this.numeroLocal);
                if (texte != null) {
                    Message msg = new Message(this.numeroConnecte, texte);
                    envoyer(msg, this.numeroConnexion);
                }
            }
            else if (random.nextDouble() < PROB_DECONNEXION) {
                System.out.printf("Cellulaire %s est deconnecter avec le numero de connexion %s \n",this.numeroLocal,this.numeroConnexion);
                finAppelLocal(this.numeroConnecte, this.numeroConnexion);
            }
        }
        else if (random.nextDouble() < PROB_APPELER) {
            String numeroAlea;
            do {
                numeroAlea = GestionnaireScenario.obtenirNumeroStandardAlea("2");
            } while (numeroAlea.equals(this.numeroLocal));

            appeler(numeroAlea, this.numeroLocal, antenneProche);
        }
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
        if(antenne == null){
            return NON_CONNECTE;
        }

        int numeroConnexion = antenne.appeler(numeroAppele,numeroAppelant, antenne);

        if(numeroConnexion != NON_CONNECTE){
            this.setNumeroConnecte(numeroAppele);
            this.setNumeroConnexion(numeroConnexion);


            String texte = GestionnaireScenario.obtenirMessage(this.numeroLocal);
            Message nouveauMessage = null;

            if(texte != null && this.antenneConnecte != null){
                nouveauMessage = new Message(numeroAppele, texte);

                System.out.println("==================================Debut=============================================");
                System.out.println("Connexion entre deux cellulaires");
                System.out.println("Numéro de connexion : " + this.numeroConnexion);
                System.out.println("Cellulaire : " + numeroAppelant + " - Message envoye : "+ nouveauMessage.getMessage() + " Destination " + nouveauMessage.getNumeroDestination());
                System.out.print("Cellulaire : " + numeroAppele + " - Message recu : ");
                antenneConnecte.envoyer(nouveauMessage, this.numeroConnexion);
                System.out.println("===================================Fin==============================================");
            }

            return numeroConnexion;
        }

        return NON_CONNECTE;
    }

    @Override
    public Cellulaire repondre(String numeroAppele, String numeroAppelant, int numeroConnexion) {
        if(this.numeroConnexion == NON_CONNECTE){
            this.numeroConnecte = numeroAppelant;
            this.numeroConnexion = numeroConnexion;
            return this;
        }
        return null;
    }

    @Override
    public void finAppelLocal(String numeroAppele, int numeroConnexion) {
        if (this.numeroConnexion != numeroConnexion) return;


        if (this.antenneConnecte != null) {
            this.antenneConnecte.finAppelLocal(this.numeroLocal, numeroConnexion);
        }


        this.numeroConnexion = NON_CONNECTE;
        this.numeroConnecte = null;
    }

    @Override
    public void finAppelDistant(String numeroAppele, int numeroConnexion) {
        if (this.numeroConnexion != numeroConnexion) return;

        this.numeroConnexion = NON_CONNECTE;
        this.numeroConnecte = null;
    }

    @Override
    public void envoyer(Message message, int numeroConnexion) {
        if(!estConnecte()){
            return;
        }

        if(this.numeroConnexion != numeroConnexion){
            return;
        }

        String texte = GestionnaireScenario.obtenirMessage(this.numeroLocal);


        if(texte == null){
            return;
        }


        Message nouveauMessage = new Message(this.numeroConnecte,texte);

    }

    @Override
    public void recevoir(Message message) {
        System.out.println(message.getMessage());
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
    /**
     * Définit le numéro de connexion (utilisé par le gestionnaire/antenne).
     */
    public void setNumeroConnexion(int numeroConnexion) {
        this.numeroConnexion = numeroConnexion;
    }

    /**
     * Définit le numéro du correspondant (numéro connecté).
     */
    public void setNumeroConnecte(String numeroConnecte) {
        this.numeroConnecte = numeroConnecte;
    }
}
