package modele.reseau;

import modele.communication.Message;

public interface UniteCellulaire {

    int appeler(String numeroAppele, String numeroAppelant, Antenne antenne);

    Cellulaire repondre(String numeroAppele, String numeroAppelant, int numeroConnexion);

    void finAppelLocal(String numeroAppele, int numeroConnexion);

    void finAppelDistant(String numeroAppele, int numeroConnexion);

    void envoyer(Message message, int numeroConnexion);

    void recevoir(Message message);
}
