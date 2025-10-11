package modele.physique.tests;

import modele.physique.ObjetMobile;
import modele.physique.Position;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

/**
 * Classe de test JUnit pour la classe abstraite de ObjetMobile.

 * Ce test vérifie que la méthode seDeplacer() modifie correctement
 * la position de l'objet mobile, même lorsqu'on utilise une classe anonyme
 * pour instancier la classe abstraite.
 * @author Shawn Dutil
 * @version Autonme 2025
 */
public class TestObjetMobile{


    /**
     * Teste le déplacement d'un objet mobile.
     *
     * Étapes du test :
     * 1. Création d'une position initiale.
     * 2. Instanciation d'une classe anonyme héritant de ObjetMobile.
     * 3. Affichage de la position initiale.
     * 4. Appel de seDeplacer() pour mettre à jour la position.
     * 5. Affichage de la nouvelle position après déplacement.
     * 6. Vérification avec assertNotEquals que la position X et Y a changé.
     * @author Shawn Dutil
     * @version Autonme 2025
     */
    @Test
    public void testDeplacementObjetMobile(){
        Position position = new Position(1000, 1200);

        // Instanciation d'ObjetMobile via une classe anonyme
        ObjetMobile mobile = new ObjetMobile(position, 5,0.2){

        };

        double xAvant = mobile.getPosition().getPositionX();
        double yAvant = mobile.getPosition().getPositionY();

        System.out.println("Position initiale : " + mobile.getPosition());

        mobile.seDeplacer();
        System.out.println("Position après déplacement : " + mobile.getPosition());

        assertNotEquals("La position X doit changer",xAvant, mobile.getPosition().getPositionX());
        assertNotEquals("La position Y doit changer",yAvant, mobile.getPosition().getPositionY());
    }
}
