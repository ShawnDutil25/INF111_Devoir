package programme;

import modele.physique.Carte;
import modele.physique.ObjetMobile;
import modele.physique.Position;
import modele.reseau.Antenne;
import modele.reseau.GestionnaireReseau;
import vue.CadrePrincipal;

public class ProgrammePrincipal {

	public static void main(String[] args){

		//testCarte();
		//testObjetMobile();
		//testAntenne();

		Thread t2 = new Thread(GestionnaireReseau.getInstance());
    	t2.start();

    	Thread t = new Thread(new CadrePrincipal());
    	t.start();
	}

	/**
	 * Méthode qui test la classe Carte avec un thread
	 */
	private static void testCarte(){
		Thread testCarte = new Thread(Carte::run, "[Test carte]");
		testCarte.start();
	}
	/**
	 * Méthode qui test la classe ObjetMobile avec un thread
	 */
	private static void testObjetMobile(){
		Thread testObjetMobile = new Thread(ObjetMobile::run, "[Test objet mobile]");
		testObjetMobile.start();
	}
	/**
	 * Méthode qui test la classe Antenne avec un thread
	 */
	private static void testAntenne(){
		Position p = Carte.genererPositionAleatoire();
		Antenne antenne = new Antenne(p);
		Thread testAntenne = new Thread(antenne::run,"[Test Antenne]");
		testAntenne.start();
	}
}
