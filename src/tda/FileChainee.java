package tda;

/**
 * Classe représentant une file chaînée générique (FIFO : premier entré, premier sorti).
 *
 * @param <S> le type des éléments stockés dans la file
 */
public class FileChainee<S> {


    /**
     * Classe interne représentant un nœud de la file.
     *
     * @param <S> le type de la valeur contenue dans le nœud
     */
    public static class Noeud<S>{
        String valeur;
        Noeud<String> suivant;

        /**
         * Constructeur d'un nœud.
         *
         * @param valeur la valeur à stocker
         */
        Noeud(String valeur){
            this.valeur = valeur;
            this.suivant = null;
        }
    }

    private Noeud<String> debut = null;
    private Noeud<String> fin = null;

    /**
     * Constructeur de la liste chainée
     */
    public FileChainee(){
        this.debut = null;
        this.fin = null;
    }

    /**
     * Ajoute un élément à la fin de la file.
     *
     * @param element l'élément à ajouter
     */
    public void enfiler(String element){
        Noeud<String> nouveau = new Noeud<>(element);

        if(fin == null){
            debut = nouveau;
            fin = nouveau;
        }

        fin.suivant = nouveau;
        fin = nouveau;
    }

    /**
     * Retire et retourne l'élément au début de la file.
     *
     * @return l'élément retiré, ou null si la file est vide
     */
    public String defiler(){
        if(estVide()){
            return null;
        }

        String valeur = debut.valeur;
        debut = debut.suivant;

        if(estVide()){
            fin = null;
        }

        return valeur;
    }

    /**
     * Vérifie si la file est vide.
     *
     * @return true si la file est vide, false sinon
     */
    public boolean estVide() {
        return debut == null;
    }

}
