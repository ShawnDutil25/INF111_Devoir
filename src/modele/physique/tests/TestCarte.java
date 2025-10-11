package modele.physique.tests;

import modele.physique.Carte;
import modele.physique.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * Classe de test JUnit pour la classe Carte.
 *
 * Ce test vérifie le comportement de la méthode ajusterPosition(Position)
 * qui applique la logique toroïdale à une position.
 *
 * Le test déplace volontairement une position en dehors de la carte,
 * puis vérifie que la position est correctement ajustée à l'intérieur de la carte.
 *
 * Les vérifications se font à l'aide d'assertions avec tolérance pour les valeurs en double
 * et d'affichages System.out.println pour observer les positions avant et après ajustement.
 *
 * @author Shawn Dutil
 * @version Automne 2025
 */
public class TestCarte {
    /**
     * Teste l'ajustement d'une position selon la logique toroïdale.
     *
     * Étapes du test :
     * 1. Génération d'une position aléatoire.
     * 2. Déplacement volontaire du point à l'extérieur de la carte.
     * 3. Affichage de la position avant ajustement.
     * 4. Ajustement via Carte.ajusterPosition().
     * 5. Affichage de la position après ajustement.
     * 6. Vérification que la position X et Y ont été correctement ramenées dans la carte.
     */
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

        assertEquals("La position X doit être ramenée à 0",0.0, position.getPositionX(),0.01);
        assertEquals("La position Y doit être ramenée à la hauteur maximale de la carte", Carte.TAILLE_CARTE.getPositionY(), position.getPositionY(), 0.01); // tolérance pour double

    }

}
