package modele.physique;

import java.util.Random;

/**
 * Cette classe carte est modélisée comme une surface rectangulaire de dimensions fixes
 * (1920x1080). Elle offre des méthodes utilitaires pour générer des positions
 * aléatoires et ajuster les positions qui sortent des limites de la carte selon
 * une logique de toroïde — c’est-à-dire que les objets qui sortent d’un côté
 * réapparaissent de l’autre.
 *
 * @author Shawn Dutil
 * @version Automne 2025
 */
public final class Carte {


    public static final Position TAILLE_CARTE = new Position(1920, 1080);
    public static final Random RANDOM = new Random();


    /**
     * Génère une position aléatoire comprise à l’intérieur des limites de la carte.
     *
     * @return une instance de la position contenant des coordonnées aléatoires entre 0 et la taille maximale de la carte.
     */
    public static Position genererPositionAleatoire() {

        double aleatoireX = RANDOM.nextDouble() * TAILLE_CARTE.getPositionX();
        double aleatoireY = RANDOM.nextDouble() * TAILLE_CARTE.getPositionY();

        Position position = new Position(aleatoireX,aleatoireY);

        return position;
    }

    /**
     * Ajuste la position donnée selon la logique du toroïde.
     * Si la position sort à droite, elle réapparaît à gauche (X = 0).
     * Si elle sort à gauche, elle réapparaît à droite (X = largeur de la carte).
     * Si elle sort en bas, elle réapparaît en haut (Y = 0).
     * Si elle sort en haut, elle réapparaît en bas (Y = hauteur de la carte).
     *
     * @param position ajuste les coordonnées s'elles sortent des limites.
     */
    public static void ajusterPosition(Position position) {

        // Vérification de la coordonnée X
        if (position.getPositionX() > TAILLE_CARTE.getPositionX()) {
            position.setPositionX(0);
        } else if (position.getPositionX() < 0) {
            position.setPositionX(TAILLE_CARTE.getPositionX());
        }

        // Vérification de la coordonnée Y
        if (position.getPositionY() > TAILLE_CARTE.getPositionY()) {
            position.setPositionY(0);
        } else if (position.getPositionY() < 0) {
            position.setPositionY(TAILLE_CARTE.getPositionY());
        }
    }

    /**
     * Routine de test intégrée pour Carte
     */
    public static void run() {

        System.out.println(Thread.currentThread().getName());

        Position p = genererPositionAleatoire();
        System.out.println("Point généré : " + p);


        p.setPositionX(p.getPositionX() + TAILLE_CARTE.getPositionX() + 50);
        p.setPositionY(p.getPositionY() - 50);
        System.out.println("Point déplacé hors carte : " + p);

        ajusterPosition(p);

        System.out.println("Point après ajustement : " + p);
        System.out.println("------------");
    }

}
