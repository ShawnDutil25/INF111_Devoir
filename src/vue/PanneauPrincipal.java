package vue;
/**
 * Panneau qui affiche l'�tat de la simulation.
 *
 * Les antennes sont repr�sent�s par des cercles gris et les Cellulaire par des cercles bleus.
 * Lorsque deux cellulaires sont connect�s, ils changent de couleur et prenne une couleur al�atoire
 * commune.
 *
 * Le panneau se mets � jours suivant une notification du mod�le (Observable)
 *
 * @author Fred Simard
 * @version Hiver 2021
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import modele.physique.Position;
import modele.reseau.Antenne;
import modele.reseau.Cellulaire;
import modele.reseau.GestionnaireReseau;
import observer.MonObserver;

public class PanneauPrincipal extends JPanel implements MonObserver{

	private static int RAYON_ANTENNE = 20;
	private static int RAYON_INDIVIDU = 10;

	Dimension taille;
	GestionnaireReseau reseau = GestionnaireReseau.getInstance();

	/**
	 * Constructeur
	 * @param taille de la fen�tre
	 */
	public PanneauPrincipal(Dimension taille) {

		this.taille = taille;
		rafraichirDessin();

		reseau.attacherObserver(this);

	}

	/**
	 * appel� pour mettre � jours le dessin
	 */
	private void rafraichirDessin(){

		validate();
		repaint();

	}

	/**
	 * m�thode h�rit� qui dessine � la fen�tre
	 */
	public void paintComponent(Graphics g) {

		// convertie en engin graphique 2D
		Graphics2D g2 = (Graphics2D) g;

		// appel au paint de base
		super.paintComponent(g);
		// efface l'écran
		g2.clearRect(0, 0, taille.width, taille.height);

		dessineCellulaires(g2);
		dessineAntennes(g2);

        //gets rid of the copy
        g2.dispose();
	}

	/**
	 * Dessine les cellulaires � l'�cran
	 * @param g r�f�rence � engin graphique
	 */
	public void dessineCellulaires(Graphics2D g) {

		ArrayList<Cellulaire> cellulaires = reseau.getCellulaires();

		// dessine tous les cellulaires
		for(Cellulaire cellulaire : cellulaires) {

			Position position = cellulaire.getPosition();

			// si le cellulaire est connect�, choisi sa couleur � partir du num�ro de connexion
			if(cellulaire.estConnecte()) {

				int numeroConnexion = Math.abs(cellulaire.getNumeroConnexion());

				// Génère une teinte unique et bien répartie
				float hue = ((numeroConnexion * 53) % 360) / 360f;

				// Crée une couleur selon le hue (numeroConnexion).
				Color couleur = Color.getHSBColor(hue, 0.85f, 0.95f);
				g.setColor(couleur);
			}else {
				g.setColor(Color.BLUE);
			}

			g.fillOval((int)position.getPositionX()-RAYON_INDIVIDU, (int)position.getPositionY()-RAYON_INDIVIDU, 2*RAYON_INDIVIDU, 2*RAYON_INDIVIDU);

		}

	}

	/**
	 * Dessine les antennes � l'�cran
	 * @param g r�f�rence � engin graphique
	 */
	public void dessineAntennes(Graphics2D g) {

		ArrayList<Antenne> antennes = reseau.getAntennes();

		// dessine toutes les antennes selon les param�tres
		for(Antenne antenne : antennes) {

			Position position = antenne.getPosition();
			g.setColor(Color.DARK_GRAY);
			g.fillOval((int)position.getPositionX()-RAYON_ANTENNE, (int)position.getPositionY()-RAYON_ANTENNE, 2*RAYON_ANTENNE, 2*RAYON_ANTENNE);
		}
	}

	@Override
	public void avertir() {
		rafraichirDessin();
	}

}
