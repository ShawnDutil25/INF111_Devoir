package modele.reseau;

/**
 * Le gestionnaire réseau est responsable de gérer les connexions cellulaires et de relayer
 * les appels, messages et fin d'appel.
 * 
 * Dans le cadre de la simulation, c'est également le gestionnaire réseau qui instancie Antennes et
 * cellulaire et qui gère l'exécution par tour des cellulaires.
 * 
 * Le gestionnaire réseau est un singleton
 * 
 * @author Fred Simard
 * @version Hiver 2021
 */


import observer.MonObservable;

public class GestionnaireReseau extends MonObservable implements Runnable {

	private boolean mondeEnVie = true;
	private static GestionnaireReseau instance = new GestionnaireReseau();

	/**
	 * méthode permettant d'obtenir une référence sur le Gestionnaire Réseau
	 * @return instance
	 */
	public static GestionnaireReseau getInstance() {
		return instance;
	}
	
	private GestionnaireReseau() {}

	/**
	 * permet de mettre fin à la simulation
	 * @param mondeEnVie
	 */
	public void finDeSimulation() {
		this.mondeEnVie = false;
	}


	/**
	 * s'exécute en continue pour simuler le système
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
