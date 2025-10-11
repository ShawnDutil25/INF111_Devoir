package programme;

import modele.reseau.GestionnaireReseau;
import vue.CadrePrincipal;

public class ProgrammePrincipal {

	public static void main(String[] args){

    	Thread t2 = new Thread(GestionnaireReseau.getInstance());
    	t2.start();
		
    	Thread t = new Thread(new CadrePrincipal());
    	t.start();
	}
}
