package modele.physique;

/**
 * Représente une position dans un plan à deux dimensions.
 * Cette classe permet de stocker les coordonnées X et Y d'un point
 * ainsi que de calculer la distance euclidienne entre deux points.
 *
 * @author Shawn Dutil
 * @version Automne 2025
 */
public class Position {

    private double positionX;
    private double positionY;


    /**
     * Crée une nouvelle position à partir des coordonnées données.
     *
     * @param positionX la coordonnée X du point
     * @param positionY la coordonnée Y du point
     */
    public Position(double positionX, double positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Calcule la distance euclidienne entre deux points (x1, y1) et (x2, y2).
     *
     * @param x1 coordonnée X du premier point
     * @param y1 coordonnée Y du premier point
     * @param x2 coordonnée X du second point
     * @param y2 coordonnée Y du second point
     * @return la distance euclidienne entre les deux points
     */
    public double distanceEucledienne(double x1, double y1 , double x2, double y2){
        return Math.sqrt(Math.pow((x2 - x1),2) + Math.pow((y2 - y1),2));
    }

    /**
     * Retourne la coordonnée Y de la position.
     *
     * @return la valeur actuelle de positionY
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * Modifie la coordonnée Y de la position.
     *
     * @param positionY la nouvelle valeur de Y
     */
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    /**
     * Retourne la coordonnée X de la position.
     *
     * @return la valeur actuelle de positionX
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * Modifie la coordonnée X de la position.
     *
     * @param positionX la nouvelle valeur de X
     */
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    /**
     * Retourne une représentation textuelle de la position.
     *
     * @return une chaîne de caractères au format "Position (x, y)"
     */
    @Override
    public String toString() {
        return "Position (" + positionX + ", " + positionY + ")";
    }



}
