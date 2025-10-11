package modele.reseau;

import modele.communication.Message;
import modele.physique.ObjetPhysique;
import modele.physique.Position;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private static ArrayList<Cellulaire> tdaCellulaire = new ArrayList<>();

    /**
     * Constructeur paramétré permettant d'initialiser la position de l'objet.
     *
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
        if (!tdaCellulaire.contains(cellulaire)) {
            tdaCellulaire.add(cellulaire);
        }
    }

    /**
     * Retire un cellulaire de la collection de l'antenne.
     * @param cellulaire le cellulaire à retirer
     */
    public void retirerCellulaire(Cellulaire cellulaire) {
        tdaCellulaire.remove(cellulaire);
    }


    /**
     * Retourne une représentation textuelle de l’antenne.
     *
     * @return une chaîne contenant la position de l’antenne
     */
    @Override
    public String toString(){
        return "Position Antenne : " + this.position;
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
