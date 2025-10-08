package modele.physique.tests;

import modele.physique.Carte;
import modele.physique.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCarte {
    @Test
    public void testAjusterPositionToroide() {
        // Génère une position aléatoire
        Position position = Carte.genererPositionAleatoire();
        System.out.println("Point aléatoire : " + position);

        // Déplace le point à l'extérieur
        position.setPositionX(Carte.TAILLE_CARTE.getPositionX() + 200);
        position.setPositionY(-30);
        System.out.println("Point à l'extérieur : " + position);

        // Ajuste la position selon la logique du toroïde
        Carte.ajusterPosition(position);
        System.out.println("Position après ajustement: " + position);
        
        assertEquals("La position X doit être ramenée à 0",0.0, position.getPositionX(),0.01); // tolérance pour double

        assertEquals("La position Y doit être ramenée à la hauteur maximale de la carte", Carte.TAILLE_CARTE.getPositionY(), position.getPositionY(), 0.01); // tolérance pour double

    }

}
