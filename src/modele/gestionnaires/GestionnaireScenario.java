package modele.gestionnaires;

/**
 * Le gestionnaire de scénario est un module utilitaire gérant:
 * 	* la création de numéro de téléphone
 *  * les messages
 *  
 *  Les fonctionnalités sont offertes pour les numéros normales et les numéros
 *  de criminels.
 *  
 *  @author Fred Simard | ETS
 *  @revision hiver 2021
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import modele.physique.Carte;
import modele.reseau.Antenne;
import modele.reseau.GestionnaireReseau;
import tda.FileSChainee;

public class GestionnaireScenario {

	public static final String FICHIER_CONVERSATION = "ressources/conversations.txt";
	public static final String PREFIX = "514-";
	
	GestionnaireReseau reseau = GestionnaireReseau.getInstance();
	FileSChainee<String> file = new FileSChainee<String>();
	ArrayList<String> numeroCriminel = new ArrayList<String>(GestionnaireReseau.NB_CRIMINELS);
	ArrayList<String> numeroStandard = new ArrayList<String>(GestionnaireReseau.NB_CELLULAIRES);

    Random rand = new Random();

	private static GestionnaireScenario instance = new GestionnaireScenario();

	private GestionnaireScenario() {
		// charge les conversations
		chargementDuFichier();
	}
	
	/**
	 * méthode qui charge le fichier de conversation
	 */
	private void chargementDuFichier() {
		
		try {
			Scanner scanner = new Scanner(new File(FICHIER_CONVERSATION));
			while (scanner.hasNextLine()) {
				file.enfiler(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode utilitaire pour générer une chaîne de caractères aléatoire
	 * @return String aléatoire
	 * @ref: https://www.baeldung.com/java-random-string
	 */
	private static String generatingRandomAlphabeticString() {
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = instance.rand.nextInt(100)+1;

	    String generatedString = instance.rand.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    return generatedString;
	}
	
	/**
	 * Méthode retournant un message. Le message renvoyé dépend de 
	 * si le numéro est standard ou criminel. Si criminel, le message est tiré
	 * du scénario. Si standard, chaine de caractère aléatoire
	 * @param numero utilisé pour envoyer le message
	 * @return le message à envoyer
	 */
	public static String obtenirMessage(String numero) {
		
		if(instance.numeroCriminel.contains(numero)) {
			try {
				return instance.file.defiler();
			}
			catch(Exception exception){
				instance.reseau.finDeSimulation();
				return null;
			}
		} else {
			return generatingRandomAlphabeticString();
		}
	}

	/**
	 * Méthode qui retourne un numéro choisi aléatoirement parmis la
	 * liste des numéros criminels, à l'exception de celui reçu en
	 * paramètre
	 * @param exclus le numéro a exclure des possibilités
	 * @return le numéro appartenant aux numéros criminels
	 */
	public static String obtenirNumeroCriminelAlea(String exclus) {
		int index = instance.rand.nextInt(instance.numeroCriminel.size());
		String numero = instance.numeroCriminel.get(index);
		
		while(numero.equals(exclus)){
			index = instance.rand.nextInt(instance.numeroCriminel.size());
			numero = instance.numeroCriminel.get(index);
		}
		return numero;
	}

	/**
	 * Méthode qui retourne un numéro choisi aléatoirement parmis la
	 * liste des numéros standard, à l'exception de celui reçu en
	 * paramètre
	 * @param exclus le numéro a exclure des possibilités
	 * @return le numéro appartenant aux numéros standards
	 */
	public static String obtenirNumeroStandardAlea(String exclus) {
		int index = instance.rand.nextInt(instance.numeroStandard.size());
		String numero = instance.numeroStandard.get(index);
		
		while(numero.equals(exclus)){
			index = instance.rand.nextInt(instance.numeroStandard.size());
			numero = instance.numeroStandard.get(index);
		}
		return numero;
	}

	/**
	 * Méthode qui retourne un numéro de téléphone aléatoire
	 * après l'avoir ajouté à la liste des numéros criminels
	 * @return le numéro sous forme the String
	 */
	public static String obtenirNouveauNumeroCriminel() {
		String numero = obtenirNouveauNumeroAlea();
		instance.numeroCriminel.add(numero);
		return numero;
	}

	/**
	 * Méthode qui retourne un numéro de téléphone aléatoire
	 * après l'avoir ajouté à la liste des numéros standards
	 * @return le numéro sous forme the String
	 */
	public static String obtenirNouveauNumeroStandard() {
		String numero = obtenirNouveauNumeroAlea();
		instance.numeroStandard.add(numero);
		return numero;
	}

	/**
	 * Méthode qui construit un numéro de téléphone aléatoire
	 * avec un préfix constant, tel que PPP-XXX-YYYY
	 * @return le numéro sous forme the String
	 */
	private static String obtenirNouveauNumeroAlea() {
		
		String numero = PREFIX;
		for(int i=0;i<3;i++) {
			numero += instance.rand.nextInt(10);
		}
		
		numero += "-";
		
		for(int i=0;i<4;i++) {
			numero += instance.rand.nextInt(10);
		}
		
		return numero;
		
	}
}
