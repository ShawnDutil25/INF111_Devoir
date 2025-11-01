package modele.physique;

import java.util.Random;

import static org.junit.Assert.assertNotEquals;

/**
 * Classe abstraite représentant un objet mobile dans la carte.
 *
 * Un objet mobile est un objet physique qui peut se déplacer de manière dirigée
 * avec une vitesse et une déviation aléatoire. La direction initiale est en radian et
 * est mise à jour à chaque déplacement selon une distribution gaussienne.
 *
 * @author Shawn Dutil
 * @version Automne 2025
 */
public abstract class ObjetMobile extends ObjetPhysique {

    protected double direction = 0.0;
    protected double vitesse = 0.0;
    protected double deviationStandard = 0.0;
    protected static final Random RANDOM = new Random();

    /**
     * Constructeur de l'objet mobile.
     * La direction initiale est fixée à 0.
     *
     * @param position position initiale de l'objet
     * @param vitesse vitesse de déplacement en pixels par itération
     * @param deviationStandard déviation standard pour la direction en radian
     */
    public ObjetMobile(Position position, double vitesse, double deviationStandard){
        super(position);
        this.vitesse = vitesse;
        this.deviationStandard = deviationStandard;
        this.direction = 0.0;
    }

    /**
     * Déplace l'objet mobile selon sa vitesse et sa direction.
     *
     * La direction est modifiée par un nombre tiré aléatoirement selon une distribution
     * gaussienne et multiplié par la déviation standard.
     * La nouvelle position est ensuite ajustée par la logique toroïdale de la carte.
     */
    public void seDeplacer(){
        direction += RANDOM.nextGaussian() *deviationStandard;

        double posX = position.getPositionX() + vitesse * Math.cos(direction);
        double posY = position.getPositionY() + vitesse * Math.sin(direction);

        position.setPositionX(posX);
        position.setPositionY(posY);

        Carte.ajusterPosition(position);
    }

    /**
     * Routine de test intégrée pour ObjetMobile
     */
    public static void run(){

        System.out.println(Thread.currentThread().getName());

        Position position = new Position(Carte.genererPositionAleatoire().getPositionX(), Carte.genererPositionAleatoire().getPositionY());

        ObjetMobile mobile = new ObjetMobile(position,200,3){};

        System.out.println("Position initiale : " + mobile.getPosition());

        mobile.seDeplacer();

        System.out.println("Position après déplacement : " + mobile.getPosition());
    }
}
