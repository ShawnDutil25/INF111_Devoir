package modele.physique;

/**
 * Classe abstraite représentant un objet physique dans l'espace de la carte.
 *
 * Un objet physique possède une position dans la carte, mais cette
 * classe ne définit pas de comportement concret. Elle sert de base à d'autres
 * entités physiques.
 *
 * @author Shawn Dutil
 * @version Automne 2025
 */
public abstract class ObjetPhysique {

    protected Position position;

    /**
     * Constructeur paramétré permettant d'initialiser la position de l'objet.
     * @param position la position initiale de l'objet physique.
     */
    public ObjetPhysique(Position position){
        this.position = position;
    }

    /**
     * Retourne la position actuelle de l'objet.
     * @return la position de cet objet physique.
     */
    public Position getPosition() {
        return position;
    }

}
