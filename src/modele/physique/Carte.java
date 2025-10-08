package modele.physique;

import java.util.Random;

public final class Carte {

    public static final Position TAILLE_CARTE = new Position(1920, 1080);
    public static final Random RANDOM = new Random();


    public static Position genererPositionAleatoire() {

        double aleatoireX = RANDOM.nextDouble() * TAILLE_CARTE.getPositionX();
        double aleatoireY = RANDOM.nextDouble() * TAILLE_CARTE.getPositionY();

        Position position = new Position(aleatoireX,aleatoireY);

        return position;
    }

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



}
