package modele.gestionnaires;

/**
 * Le gestionnaire de sc�nario est un module utilitaire g�rant:
 * 	* la cr�ation de num�ro de t�l�phone
 *  * les messages
 *  
 *  Les fonctionnalit�s sont offertes pour les num�ros normales et les num�ros
 *  de criminels.
 *  
 *  @author Fred Simard | ETS
 *  @revision hiver 2021
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
	 * m�thode qui charge le fichier de conversation
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
	 * M�thode utilitaire pour g�n�rer une cha�ne de caract�res al�atoire
	 * @return String al�atoire
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
	 * M�thode retournant un message. Le message renvoy� d�pend de 
	 * si le num�ro est standard ou criminel. Si criminel, le message est tir�
	 * du sc�nario. Si standard, chaine de caract�re al�atoire
	 * @param numero utilis� pour envoyer le message
	 * @return le message � envoyer
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
	 * M�thode qui retourne un num�ro choisi al�atoirement parmis la
	 * liste des num�ros criminels, � l'exception de celui re�u en
	 * param�tre
	 * @param exclus le num�ro a exclure des possibilit�s
	 * @return le num�ro appartenant aux num�ros criminels
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
	 * M�thode qui retourne un num�ro choisi al�atoirement parmis la
	 * liste des num�ros standard, � l'exception de celui re�u en
	 * param�tre
	 * @param exclus le num�ro a exclure des possibilit�s
	 * @return le num�ro appartenant aux num�ros standards
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
	 * M�thode qui retourne un num�ro de t�l�phone al�atoire
	 * apr�s l'avoir ajout� � la liste des num�ros criminels
	 * @return le num�ro sous forme the String
	 */
	public static String obtenirNouveauNumeroCriminel() {
		String numero = obtenirNouveauNumeroAlea();
		instance.numeroCriminel.add(numero);
		return numero;
	}

	/**
	 * M�thode qui retourne un num�ro de t�l�phone al�atoire
	 * apr�s l'avoir ajout� � la liste des num�ros standards
	 * @return le num�ro sous forme the String
	 */
	public static String obtenirNouveauNumeroStandard() {
		String numero = obtenirNouveauNumeroAlea();
		instance.numeroStandard.add(numero);
		return numero;
	}

	/**
	 * M�thode qui construit un num�ro de t�l�phone al�atoire
	 * avec un pr�fix constant, tel que PPP-XXX-YYYY
	 * @return le num�ro sous forme the String
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
