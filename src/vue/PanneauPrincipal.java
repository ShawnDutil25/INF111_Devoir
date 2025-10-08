package vue;
/**
 * Panneau qui affiche l'état de la simulation.
 * 
 * Les antennes sont représentés par des cercles gris et les Cellulaire par des cercles bleus.
 * Lorsque deux cellulaires sont connectés, ils changent de couleur et prenne une couleur aléatoire
 * commune.
 * 
 * Le panneau se mets à jours suivant une notification du modèle (Observable)
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
	 * @param taille de la fenêtre
	 */
	public PanneauPrincipal(Dimension taille) {
		
		this.taille = taille; 
		rafraichirDessin();
		
		reseau.attacherObserver(this);
		
	}
	
	/**
	 * appelé pour mettre à jours le dessin
	 */
	private void rafraichirDessin(){
		
		validate();
		repaint();		

	}
	
	/**
	 * méthode hérité qui dessine à la fenêtre
	 */
	public void paintComponent(Graphics g) {

		// convertie en engin graphique 2D
		Graphics2D g2 = (Graphics2D) g;
		
		// appel au paint de base
		super.paintComponent(g);
		// efface l'Ã©cran
		g2.clearRect(0, 0, taille.width, taille.height);
		
		dessineCellulaires(g2);
		dessineAntennes(g2);

        //gets rid of the copy
        g2.dispose();
	}
	
	/**
	 * Dessine les cellulaires à l'écran
	 * @param g référence à engin graphique
	 */
	public void dessineCellulaires(Graphics2D g) {
		
		ArrayList<Cellulaire> cellulaires = reseau.getCellulaires();
		
		// dessine tous les cellulaires
		for(Cellulaire cellulaire : cellulaires) {
			
			Position position = cellulaire.getPosition();
			
			// si le cellulaire est connecté, choisi sa couleur à partir du numéro de connexion
			if(cellulaire.estConnecte()) {
				double test = cellulaire.getNumeroConnexion()/(double)Integer.MAX_VALUE;
				test *= 3;
				if(test<=1) {
					g.setColor(new Color((float)test%1,0.0f,0.0f));
				}else if(test<=2) {
					test -= 1.0;
					g.setColor(new Color(0.0f,(float)test%1,0.0f));
				}else {
					test -= 2.0;
					g.setColor(new Color((float)test%1,0.0f,(float)test%1));
				}
			}else {
				g.setColor(Color.BLUE);
			}
			
			g.fillOval((int)position.getX()-RAYON_INDIVIDU, (int)position.getY()-RAYON_INDIVIDU, 2*RAYON_INDIVIDU, 2*RAYON_INDIVIDU);	
			
		}
		
	}

	/**
	 * Dessine les antennes à l'écran
	 * @param g référence à engin graphique
	 */
	public void dessineAntennes(Graphics2D g) {

		ArrayList<Antenne> antennes = reseau.getAntennes();
		
		// dessine toutes les antennes selon les paramètres
		for(Antenne antenne : antennes) {

			Position position = antenne.getPosition();
			g.setColor(Color.DARK_GRAY);
			g.fillOval((int)position.getX()-RAYON_ANTENNE, (int)position.getY()-RAYON_ANTENNE, 2*RAYON_ANTENNE, 2*RAYON_ANTENNE);	
		}
	}

	@Override
	public void avertir() {
		rafraichirDessin();		
	}
	
}
