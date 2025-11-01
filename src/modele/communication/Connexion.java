package modele.communication;

import modele.reseau.Antenne;

/**
 * Représente une connexion entre deux antennes dans le réseau cellulaire.
 * Chaque connexion relie deux antennes et est identifiée par un numéro unique.
 * Elle permet la communication entre cellulaires via les antennes connectées.
 *
 * @author Shawn Dutil
 * @version Automne 2025
 */
public class Connexion {

    private static final int NB_ANTENNES =2;
    private int numeroConnexion;
    private Antenne[] antennes = new Antenne[NB_ANTENNES];

    /**
     * Constructeur d'une connexion entre deux antennes.
     *
     * @param numeroConnexion le numéro unique de la connexion
     * @param antenne1 la première antenne connectée
     * @param antenne2 la deuxième antenne connectée
     */
    public Connexion(int numeroConnexion, Antenne antenne1, Antenne antenne2){
        this.numeroConnexion = numeroConnexion;
        this.antennes[0] = antenne1;
        this.antennes[1] = antenne2;
    }

    /**
     * Obtient le numéro de la connexion.
     *
     * @return le numéro unique de la connexion
     */
    public int getNumeroConnexion() {
        return numeroConnexion;
    }
    /**
     * Obtient l'antenne de destination.
     *
     * @return le numéro unique de la connexion
     */
    public Antenne getAntenneDestination() {
        return antennes[1];
    }
    /**
     * Remplace une ancienne antenne par une nouvelle dans la connexion.
     *
     * @param antenneAncienne l'ancienne antenne connectée
     * @param antenneNouvelle la nouvelle antenne à connecter
     */
    public void mettreAJourAntenne(Antenne antenneAncienne, Antenne antenneNouvelle){
        for (int i = 0; i < NB_ANTENNES; i++) {
            if (antennes[i] == antenneAncienne) {
                antennes[i] = antenneNouvelle;
                return;
            }
        }

    }

    /**
     * Vérifie si l'objet courant est égal à un autre objet donné.
     * Deux connexions sont considérées égales si elles possèdent le même numéro de connexion.
     *
     * @param obj l'objet à comparer avec cette connexion
     * @return true si les deux objets représentent la même connexion sinon false
     */
    @Override
    public boolean equals(Object obj){

        if(this == obj){
            return true;
        }
        if (!(obj instanceof Connexion connexion)) {
            return false;
        }

        return this.numeroConnexion == connexion.numeroConnexion;
    }


}
