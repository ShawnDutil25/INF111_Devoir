package tda;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe générique représentant une structure de données statique basée sur une Liste.
 *
 * Cette classe permet de gérer une collection d'éléments génériques de manière sécurisée,
 * en empêchant l'insertion de valeurs nulles et en offrant des opérations de base
 * telles que l’ajout, la suppression, l’accès par indice et la duplication de la liste.
 *
 * @param <T> le type d'éléments stockés dans cette structure
 *
 * @author Shawn Dutil
 * @version Automne 2025
 */
public class TDAStatique<T> {

    protected final List<T> listeElements;

    /**
     * Constructeur par défaut.
     *
     * Initialise une nouvelle structure vide.
     */
    public TDAStatique() {
        listeElements = new ArrayList<>();
    }

    /**
     * Retourne le nombre d’éléments actuellement présents dans la structure.
     *
     * @return le nombre d’éléments
     */
    public int getNbElements() {
        return listeElements.size();
    }

    /**
     * Vérifie si la structure est vide.
     *
     * @return true si la structure ne contient aucun élément, sinon false
     */
    public boolean estVide(){
        return listeElements.isEmpty();
    }

    /**
     * Ajoute un élément à la fin de la structure, si celui-ci n’est pas déjà présent.
     *
     * @param element l’élément à ajouter
     * @throws IllegalArgumentException si l’élément est null
     */
    public void ajouter(T element) {
        if (element == null) {
            // Façon de pouvoir aller chercher le nom de la classe même si l'élèment est null avec un thread.
            String classe = Thread.currentThread().getStackTrace()[2].getClassName();

            throw new IllegalArgumentException("[TDA statique]-[ajouter]-[" + classe + "] Impossible d'ajouter un élément null");
        }
        if (!listeElements.contains(element)) {
            listeElements.add(element);
        }
    }

    /**
     * Ajoute un élément à un indice précis dans la structure.
     * Si l’indice est invalide, une exception est levée. L’élément ne doit pas être null.
     *
     * @param indice position où insérer l’élément
     * @param element l’élément à ajouter
     * @throws IllegalArgumentException si l’élément est null
     * @throws IndexOutOfBoundsException si l’indice est en dehors des bornes valides
     */
    public void ajouterParIndice(int indice, T element){
        if (element == null) {
            // Façon de pouvoir aller chercher le nom de la classe même si l'élèment est null avec un thread.
            String classe = Thread.currentThread().getStackTrace()[2].getClassName();

            throw new IllegalArgumentException("[TDA statique]-[ajouter]-[" + classe + "] Impossible d'ajouter un élément null");
        }
        if (indice < 0 || indice >= listeElements.size()) {
            throw new IndexOutOfBoundsException("[TDA Statique]-[getElement]-["+getClass().getName()+"] Indice invalide : " + indice);
        }

        listeElements.add(indice,element);
    }

    /**
     * Retire un élément de la structure.
     *
     * @param element l’élément à retirer
     * @throws IllegalArgumentException si l’élément est null
     */
    public void retirer(T element) {
        if (element == null) {
            // Façon de pouvoir aller chercher le nom de la classe même si l'élèment est null avec un thread.
            String classe = Thread.currentThread().getStackTrace()[2].getClassName();

            throw new IllegalArgumentException("[TDA statique]-[retirer]-[" + classe + "] Impossible de retirer un élément null");
        }

        listeElements.remove(element);
    }

    /**
     * Retourne une copie (clone) de la liste d’éléments.
     * Les modifications apportées à la copie n’affectent pas la structure originale.
     *
     * @return une nouvelle liste contenant les mêmes éléments
     */
    public List<T> getListeClone() {
        return new ArrayList<>(listeElements);
    }

    /**
     * Retourne l’élément se trouvant à un indice donné.
     *
     * @param indice la position de l’élément à récupérer
     * @return l’élément situé à cet indice
     * @throws IndexOutOfBoundsException si l’indice est en dehors des bornes valides
     */
    public T getElement(int indice) {
        if (indice < 0 || indice >= listeElements.size()) {
            throw new IndexOutOfBoundsException("[TDA Statique]-[getElement]-["+getClass().getName()+"] Indice invalide : " + indice);
        }
        return listeElements.get(indice);
    }
}
