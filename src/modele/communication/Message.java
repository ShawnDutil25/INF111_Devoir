package modele.communication;

/**
 * Représente un message transmis entre deux cellulaires du réseau.
 * Contient le numéro de destination et le contenu du message.
 *
 * @author Shawn Dutil
 * @version Automne 2025
 */
public class Message {



    private String numeroDestination;
    private String message;

    /**
     * Constructeur du message.
     * Initialise un message avec un numéro de destination et un contenu.
     *
     * @param numeroDestination le numéro de téléphone du destinataire
     * @param message le texte du message
     */
    public Message(String numeroDestination, String message){
        this.message = message;
        this.numeroDestination = numeroDestination;
    }

    /**
     * Retourne le contenu textuel du message.
     *
     * @return le message sous forme de chaîne de caractères
     */
    public String getMessage() {
        return message;
    }


    /**
     * Retourne le numéro de téléphone de destination.
     *
     * @return le numéro de destination sous forme de chaîne
     */
    public String getNumeroDestination() {
        return numeroDestination;
    }
}
