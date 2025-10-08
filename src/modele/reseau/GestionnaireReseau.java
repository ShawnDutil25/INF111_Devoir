package modele.reseau;

/**
 * Le gestionnaire r�seau est responsable de g�rer les connexions cellulaires et de relayer
 * les appels, messages et fin d'appel.
 * 
 * Dans le cadre de la simulation, c'est �galement le gestionnaire r�seau qui instancie Antennes et
 * cellulaire et qui g�re l'ex�cution par tour des cellulaires.
 * 
 * Le gestionnaire r�seau est un singleton
 * 
 * @author Fred Simard
 * @version Hiver 2021
 */


import observer.MonObservable;

import java.util.ArrayList;

public class GestionnaireReseau extends MonObservable implements Runnable {

	public static final ArrayList<String> NB_CRIMINELS = new ArrayList<>();
	public static final ArrayList<String> NB_CELLULAIRES = new ArrayList<>();
	private boolean mondeEnVie = true;
	private static GestionnaireReseau instance = new GestionnaireReseau();

	/**
	 * m�thode permettant d'obtenir une r�f�rence sur le Gestionnaire R�seau
	 * @return instance
	 */
	public static GestionnaireReseau getInstance() {
		return instance;
	}
	
	private GestionnaireReseau() {}

	/**
	 * permet de mettre fin � la simulation
	 */
	public void finDeSimulation() {
		this.mondeEnVie = false;
	}


	/**
	 * s'ex�cute en continue pour simuler le syst�me
	 */
	@Override
	public void run() {
		
		/*
		creeAntennes();
		creeCellulaires();
		this.avertirLesObservers();

		while(this.mondeEnVie) {	
			
			for(Cellulaire cell : cellulaires) {
				cell.effectuerTour();
			}
			
			this.avertirLesObservers();
			
			
			try {
				Thread.sleep(PERIODE_SIMULATION_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
	}
	
}
