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
     * Retourne le numéro de la connexion.
     *
     * @return le numéro unique de la connexion
     */
    public int getNumeroConnexion() {
        return numeroConnexion;
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

        // Vérifie que l’objet n’est pas null et du bon type
        if (!(obj instanceof Connexion)) {
            return false;
        }

         Connexion connexion = (Connexion) obj;

        return this.numeroConnexion == connexion.numeroConnexion;
    }


}
