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


import modele.communication.Connexion;
import modele.communication.Message;
import modele.gestionnaires.GestionnaireScenario;
import modele.physique.Carte;
import modele.physique.Position;
import observer.MonObservable;
import tda.TDAStatique;

import java.util.ArrayList;

public class GestionnaireReseau extends MonObservable implements Runnable {

	public static final ArrayList<String> NB_CRIMINELS = new ArrayList<>();
	public static final ArrayList<String> NB_CELLULAIRES = new ArrayList<>();
	public static final int PERIODE_SIMULATION_MS = 100;
	public static final double VITESSE = 10.0;
	public static final double DEVIATION_STANDARD = 0.05;
	public static final int NB_CELLULAIRE = 30;
	public static final int NB_ANTENNES = 10;
	public static final int CODE_NON_CONNECTE = -1;


	private boolean mondeEnVie = true;
	private static GestionnaireReseau instance = new GestionnaireReseau();

	private ArrayList<Antenne> antennes = new ArrayList<>();
	private ArrayList<Cellulaire> cellulaires = new ArrayList<>();
	private static TDAStatique<Connexion> listOrdonne = new TDAStatique<>();
	private int compteurConnexion = 0;

	/**
	 * m�thode permettant d'obtenir une r�f�rence sur le Gestionnaire R�seau
	 * @return instance
	 */
	public static GestionnaireReseau getInstance() {
		return instance;
	}


	/**
	 * permet de mettre fin � la simulation
	 */
	public void finDeSimulation() {
		this.mondeEnVie = false;
	}

	/**
	 * Crée et ajoute les antennes dans la simulation.
	 * Positionne chaque antenne aléatoirement.
	 */
	private void creeAntennes(){
		for (int i = 0; i < NB_ANTENNES; i++) {

			Position position = Carte.genererPositionAleatoire();
			Antenne antenne = new Antenne(position);
			antennes.add(antenne);
		}
	}
	/**
	 * Crée et ajoute les cellulaires dans la simulation.
	 * Chaque cellulaire reçoit un numéro unique et une position aléatoire.
	 *
	 */
	private void creeCellulaires(){
		for (int i = 0; i < NB_CELLULAIRE; i++) {

			Position position = Carte.genererPositionAleatoire();
			Cellulaire cellulaire = new Cellulaire(GestionnaireScenario.obtenirNouveauNumeroStandard(),position,VITESSE,DEVIATION_STANDARD);
			cellulaires.add(cellulaire);
		}
	}
	/**
	 * Met à jour l'antenne d'une connexion existante.
	 * Remplace la référence 'ancienne' par 'nouvelle' dans la Connexion correspondante.
	 *
	 * @param numeroConnexion numéro de la connexion à mettre à jour
	 * @param ancienneAntenne référence à l'ancienne antenne
	 * @param nouvelleAntenne référence à la nouvelle antenne
	 */
	public void mettreAJourConnexion(int numeroConnexion, Antenne ancienneAntenne, Antenne nouvelleAntenne){

		Connexion connexion = getConnexion(numeroConnexion);

		if(connexion == null){
			return;
		}
		connexion.mettreAJourAntenne(ancienneAntenne,nouvelleAntenne);
	}

	/**
	 * Retourne l'antenne la plus proche d'une position donnée.
	 *
	 * @param position position du cellulaire
	 * @return antenne la plus proche
	 */
	public Antenne getAntenneProche(Position position) {
		Antenne antenneProche = null;
		double distanceMin = Double.MAX_VALUE;

		for (Antenne antenne : antennes) {
			double distance = antenne.distance(position);
			if (distance < distanceMin) {
				distanceMin = distance;
				antenneProche = antenne;
			}
		}

		return antenneProche;
	}


	/**
	 * Recherche une connexion dans la liste ordonnée par numéro de connexion.
	 * Utilise la recherche binaire pour que sa soit plus rapide.
	 * Insipiration de ce vidéo documentative : https://www.youtube.com/watch?v=gsaQRO0cU7Q
	 *
	 * @param numeroConnexion numéro de la connexion recherchée
	 * @return la connexion correspondante ou null si non trouvée
	 */
	private static Connexion getConnexion(int numeroConnexion) {
		int indiceDebut = 0;
		int indiceFin = listOrdonne.getNbElements() -1;

		while(indiceDebut <= indiceFin){
			int indiceMilieu = (indiceDebut + indiceFin) / 2;

			if(listOrdonne.getElement(indiceMilieu).getNumeroConnexion() == numeroConnexion){
				return listOrdonne.getElement(indiceMilieu);
			} else if (listOrdonne.getElement(indiceMilieu).getNumeroConnexion() < numeroConnexion) {
				indiceDebut = indiceMilieu + 1;
			} else {
				indiceFin = indiceMilieu - 1;
			}
		}
		return null;
	}

	/**
	 * Insère une connexion dans la liste ordonnée en conservant l'ordre croissant
	 * selon le numéro de connexion.
	 *
	 * @param connexion connexion à insérer
	 */
	public void insererConnexion(Connexion connexion){
		int compteur = 0;


		while (true) {
			// Si on est arrivé à la fin de la liste, insérer à la fin
			if (compteur >= listOrdonne.getNbElements()) {
				listOrdonne.ajouter(connexion);
				break;
			}

			// Si le numéro actuel est plus grand ou égal, on insère ici
			if (listOrdonne.getElement(compteur).getNumeroConnexion() >= connexion.getNumeroConnexion()) {
				listOrdonne.ajouterParIndice(compteur, connexion);
				break;
			}

			compteur++;
		}
	}
	/**
	 * Relaye la demande d’appel à travers toutes les antennes du réseau.
	 * Si une antenne trouve le cellulaire destinataire, une Connexion est créée.
	 *
	 * @param antenneSource antenne de l’appelant
	 * @param numeroDestination numéro du cellulaire appelé
	 * @param numeroAppelant numéro du cellulaire appelant
	 * @return un identifiant de connexion (>0) si succès, CODE_NON_CONNECTE sinon
	 */
	public int relayerAppel(Antenne antenneSource, String numeroDestination, String numeroAppelant) {
		int numeroConnexion = ++compteurConnexion;


		for (Antenne antenne : antennes) {
			Cellulaire cible = antenne.repondre(numeroDestination, numeroAppelant, numeroConnexion);
			if (cible != null) {

				Connexion connexion = new Connexion(numeroConnexion, antenneSource, antenne);
				insererConnexion(connexion);

				return numeroConnexion;
			}
		}
		return CODE_NON_CONNECTE;
	}
	/**
	 * Relaye un message à travers le réseau vers l'antenne de destination associée à une connexion.
	 * Cette méthode récupère la connexion correspondant au numéro fourni. Si la connexion existe,
	 * elle obtient l'antenne de destination et lui transmet le message.
	 *
	 * @param message le message à relayer à travers le réseau
	 * @param numeroConnexion le numéro unique identifiant la connexion en cours
	 */
	public static void relayerMessage(Message message, int numeroConnexion) {
		Connexion connexion = getConnexion(numeroConnexion);
		if (connexion == null){
			return;
		}
		Antenne antenneDestination = connexion.getAntenneDestination();
		if (antenneDestination != null) {
			antenneDestination.recevoir(message, numeroConnexion);
		}
	}

	/**
	 * Relaye la fin d’un appel à travers le réseau et supprime la connexion associée.
	 * Cette méthode récupère la connexion à partir du numéro fourni. Si elle existe,
	 * elle notifie l’antenne de destination,puis retire la connexion de la liste ordonnée
	 * des connexions actives.
	 *
	 * @param numeroAppele le numéro à raccrocher
	 * @param numeroConnexion le numéro unique de la connexion à terminer
	 */
	public void relayerFinAppel(String numeroAppele,int numeroConnexion) {

		Connexion connexion = getConnexion(numeroConnexion);
		if (connexion == null) return;


		Antenne antenneDest = connexion.getAntenneDestination();

		if (antenneDest != null) {
			antenneDest.finAppelDistant(numeroAppele, numeroConnexion);
		}

		if(listOrdonne.estVide()){
			throw new ArrayIndexOutOfBoundsException("[GestionnaireReseau]-[relayerFinAppel] On ne peut pas retirer d'une liste vide.");
		}


		listOrdonne.retirer(connexion);
	}

	/**
	 * Obtient le tableau d'antennes.
	 * @return la liste des antennes
	 * */
	public ArrayList<Antenne> getAntennes() {
		return antennes;
	}
	/**
	 * Obtient la liste des cellulaires
	 * @return la liste des cellulaires
	 * */
	public ArrayList<Cellulaire> getCellulaires() {
		return cellulaires;
	}
	/**
	 * s'ex�cute en continue pour simuler le syst�me
	 */
	@Override
	public void run() {

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
		}
	}


}
