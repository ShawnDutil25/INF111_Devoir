package modele.reseau;

import modele.communication.Message;
import modele.physique.ObjetPhysique;
import modele.physique.Position;
import tda.TDAStatique;
/**
 * Représente une antenne du réseau cellulaire.
 *
 * Une antenne est un objet physique fixe qui peut communiquer avec des
 * cellulaires à proximité. Elle maintient une liste statique des
 * cellulaires actuellement connectés et interagit avec le gestionnaire
 * de réseau centralisé.
 *
 * @author Shawn Dutil
 * @version Automne 2025
 */
public class Antenne extends ObjetPhysique implements UniteCellulaire {

    private final GestionnaireReseau gestionnaireReseau = GestionnaireReseau.getInstance();
    private final TDAStatique<Cellulaire> listeCellulaires = new TDAStatique<>();

    /**
     * Constructeur paramétré permettant d'initialiser la position de l'objet.
     * @param position la position initiale de l'objet physique.
     */
    public Antenne(Position position) {
        super(position);
    }

    /**
     * Retourne la distance entre cette antenne et une position donnée.
     * Utilise la méthode distance de la classe Position.
     * @param position position à comparer
     * @return la distance en double
     */
    public double distance(Position position){

        return this.getPosition().distance(position.getPositionX(),position.getPositionY(),this.position.getPositionX(),this.position.getPositionY());
    }

    /**
     * Ajoute un cellulaire à la collection de l'antenne.
     * @param cellulaire le cellulaire à ajouter
     */
    public void ajouterCellulaire(Cellulaire cellulaire) {
        listeCellulaires.ajouter(cellulaire);
    }

    /**
     * Appelée sur la nouvelle antenne lors d'un handoff :
     * elle délègue au GestionnaireReseau pour mettre à jour la connexion.
     *
     * @param numeroConnexion numéro de la connexion à mettre à jour
     * @param ancienne antenne à remplacer
     */
    public void mettreAJourConnexion(int numeroConnexion, Antenne ancienne){
        GestionnaireReseau.getInstance().mettreAJourConnexion(numeroConnexion, ancienne, this);
    }

    /**
     * Retire un cellulaire de la collection de l'antenne.
     * @param cellulaire le cellulaire à retirer
     */
    public void retirerCellulaire(Cellulaire cellulaire) {
        if(listeCellulaires.estVide()){
            throw new ArrayIndexOutOfBoundsException("[Antenne]-[retirerCellulaire] On ne peut pas retirer d'une liste vide.");
        }
        listeCellulaires.retirer(cellulaire);
    }


    /**
     * Retourne une représentation textuelle de l’antenne.
     * @return une chaîne contenant la position de l’antenne
     */
    @Override
    public String toString(){
        return "Position Antenne : " + this.position;
    }

    @Override
    public int appeler(String numeroAppele, String numeroAppelant, Antenne antenne) {

        GestionnaireReseau gestionnaireReseau = GestionnaireReseau.getInstance();

        return gestionnaireReseau.relayerAppel(this, numeroAppele, numeroAppelant);
    }

    @Override
    public Cellulaire repondre(String numeroAppele, String numeroAppelant, int numeroConnexion) {

        for (Cellulaire cellulaire : listeCellulaires.getListeClone()) {

            if (cellulaire.comparerNumero(numeroAppele)) {

                cellulaire.repondre(numeroAppele, numeroAppelant, numeroConnexion);
                return cellulaire;
            }
        }
        return null;
    }

    @Override
    public void finAppelLocal(String numeroAppele, int numeroConnexion) {
        gestionnaireReseau.relayerFinAppel(numeroAppele, numeroConnexion);
    }

    @Override
    public void finAppelDistant(String numeroAppele, int numeroConnexion) {
        for (Cellulaire cellulaire : listeCellulaires.getListeClone()) {
            if (cellulaire.getNumeroConnexion() == numeroConnexion) {
                cellulaire.finAppelDistant(numeroAppele, numeroConnexion);
                break;
            }
        }
    }

    @Override
    public void envoyer(Message message, int numeroConnexion) {
        GestionnaireReseau.relayerMessage(message,numeroConnexion);
    }

    @Override
    public void recevoir(Message message) {
        for (Cellulaire cellulaire : listeCellulaires.getListeClone()) {
            if (cellulaire.comparerNumero(message.getNumeroDestination())) {
                cellulaire.recevoir(message);
                break;
            }
        }
    }

    /**
     * s'exécute en continue pour simuler le système
     */
    public void run() {
        System.out.println(Thread.currentThread().getName());

        Cellulaire c1 = new Cellulaire("555-0001", new Position(10, 10), 0, 0);
        Cellulaire c2 = new Cellulaire("555-0002", new Position(20, 20), 0, 0);

        // Test ajout de cellulaires
        System.out.println("Ajout du cellulaire1 et cellulaire2");
        ajouterCellulaire(c1);
        ajouterCellulaire(c2);
        System.out.println("Nb cellulaires : " + listeCellulaires.getNbElements());

        // Test les erreurs
        // Affiche leur qui est supposer d'afficher
        try {
            ajouterCellulaire(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception attendue : " + e.getMessage());
        }


        System.out.println("Supprimer cellulaire1");
        retirerCellulaire(c1);
        System.out.println("Nb cellulaires après la suppression : " + listeCellulaires.getNbElements());

        // Test les erreurs
        // Affiche leur qui est supposer d'afficher
        try {
            retirerCellulaire(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception attendue : " + e.getMessage());
        }

        // Test de getElement
        try {
            Cellulaire test = listeCellulaires.getElement(0);
            System.out.println("Cellulaire en indice 0 : " + test.getNumeroLocal());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Exception getElement : " + e.getMessage());
        }

        System.out.println("--------");
    }


}
