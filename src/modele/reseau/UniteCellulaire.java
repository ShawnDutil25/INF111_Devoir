package modele.reseau;

import modele.communication.Message;


/**
 * Interface définissant les comportements fondamentaux d'une unité cellulaire
 * au sein du réseau de communication simulé.
 *
 * Cette interface est implémentée par les entités capables de participer à des connexions cellulaires,
 * comme les Cellulaires et les Antennes.
 * Elle définit les opérations liées aux appels, à leur terminaison et à la transmission de messages.
 *
 * @author Shawn Dutil
 * @version Automne 2025
 */
public interface UniteCellulaire {

    /**
     * Démarre un appel entre un cellulaire appelant et un cellulaire destinataire.
     * Cette méthode est généralement invoquée par un cellulaire initiant la communication.
     *
     * @param numeroAppele   le numéro du cellulaire à appeler
     * @param numeroAppelant le numéro du cellulaire initiant l’appel
     * @param antenne        l’antenne utilisée pour relayer l’appel
     * @return un identifiant unique de connexion si la communication est établie, sinon un code d’erreur
     */
    int appeler(String numeroAppele, String numeroAppelant, Antenne antenne);

    /**
     * Répond à une tentative d’appel provenant d’un autre cellulaire.
     * Cette méthode permet à l’unité cellulaire appelée d’accepter ou de rejeter la connexion.
     *
     * @param numeroAppele     le numéro du cellulaire destinataire
     * @param numeroAppelant   le numéro du cellulaire qui initie l’appel
     * @param numeroConnexion  le numéro unique attribué à la connexion
     * @return le cellulaire ayant accepté l’appel, ou {@code null} si aucun n’a répondu
     */
    Cellulaire repondre(String numeroAppele, String numeroAppelant, int numeroConnexion);

    /**
     * Termine localement un appel en cours.
     * Cette méthode est appelée lorsque le cellulaire décide de mettre fin à la communication.
     *
     * @param numeroAppele     le numéro de l’autre participant à l’appel
     * @param numeroConnexion  le numéro unique de la connexion à terminer
     */
    void finAppelLocal(String numeroAppele, int numeroConnexion);

    /**
     * Termine un appel distant, c’est-à-dire initié par l’autre participant.
     * Cette méthode est appelée lorsqu’un signal de fin d’appel provient du réseau ou de l’autre cellulaire.
     *
     * @param numeroAppele     le numéro du cellulaire distant
     * @param numeroConnexion  le numéro unique de la connexion à fermer
     */
    void finAppelDistant(String numeroAppele, int numeroConnexion);

    /**
     * Envoie un message à travers une connexion active.
     * Le message est transmis via l’antenne associée à la connexion.
     *
     * @param message          le message à envoyer
     * @param numeroConnexion  le numéro de la connexion utilisée pour la transmission
     */
    void envoyer(Message message, int numeroConnexion);

    /**
     * Reçoit un message provenant d’une autre unité cellulaire.
     *
     * @param message le message reçu
     */
    void recevoir(Message message);
}
