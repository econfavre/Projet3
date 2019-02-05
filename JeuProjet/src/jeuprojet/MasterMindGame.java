package jeuprojet;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MasterMindGame extends Jeux {
	Scanner clavier = new Scanner(System.in);

	static int oldComputerInput = 0; 
	int newComputerInput = 0;
	// Configuration du nombre d'essai
	private static int tryNber = 0;
	private static int tryMax = 20;


	// Parametrage de la combinaison
	static int size = 4;
	static int maxDigit = 9;

	boolean isNumberValid;


	@Override
	public void challenger () throws IllegalArgumentException{
		int userInput=0;
		int secretCode = 0;
		int [] userInputTab;
		int[] tabSecretCode = new int[size];

		// 	Presentation du jeu
		gamePresentation(size, maxDigit, tryMax);

		// Secret Code
		putCombinaison(tabSecretCode, size, maxDigit);

		// Convertir le tableau code secret en un int
		for(int i = 0; i < tabSecretCode.length; i++) 
			secretCode += Math.pow(10,i) * tabSecretCode[tabSecretCode.length - i - 1];

		do {
			tryNber++;
			do {
				// Proposition de l'utilisateur
				System.out.println(" Veuillez taper un chiffre : ");
				userInput = getClavierInt();
			} while (!isNumberValid);

			// Conversion du l'entree utilisateur en tableau
			userInputTab = convertDigit(userInput);

			// Comparaison
			compareTab(userInputTab, tabSecretCode);

			// Tests fin de boucle
			if (secretCode == userInput ) {
				System.out.println("Bravo vous avez trouvé le code secret en " + tryNber + " essai(s) !");
				break;
			} else if (tryNber >= tryMax) {
				System.out.println("Vous n'avez plus d'essai : la reponse était " + String.format("%04d", secretCode) + "!");
				break;
			}
		} while (userInputTab != tabSecretCode);
	}

	/**
	 * Controle de saisie utilisateur
	 * 
	 * @return
	 */
	public int getClavierInt() {
		int result = 0;
		if (clavier.hasNextInt()) {
			result = clavier.nextInt();
			isNumberValid = true;
			// Test longueur
			if ((int) Math.log10(result) + 1 != size) {
				isNumberValid = false;
				System.out.println("Merci de taper 4 chiffres !");
				clavier.next();
			} 
		} else {
			System.out.println("Veuillez réessayer : merci de taper une suite de chiffre : ");
			isNumberValid = false;
			clavier.next(); 
		} 
		return result;
	}
	

	@Override
	public void defenseur() {
		int secretCode;

		// Code secret de l'utilisateur
		do {
			// Proposition de l'utilisateur
			System.out.println("Veuillez définir un code secret : ");
			secretCode = getClavierInt();
		} while (!isNumberValid);

		int result = 0;

		String userTip = null;
		System.out.println("(Combinaison secrète : " + secretCode + ")");

		String[] inputs = {"1", "2", "3", "4", "5", "6"};
		int maxLength = 4;

		// le nombre total de toutes les combinaisons
		int total = (int) Math.pow(inputs.length, maxLength);

		ArrayList<String[]> allPossibleCombi = new ArrayList<>();


		int [] index = new int [maxLength];
		// tous les elements de index equivalent a 0
		Arrays.fill(index, 0);

		for (int i = 0; i < total; i++)
		{
	
		    String[] subSets = new String[maxLength];
		    for (int j = 0; j < maxLength; j++)
		    {
		        subSets[j] = inputs[index[j]];
		    }
		    allPossibleCombi.add(subSets);
		    if (i != (total - 1))
		        index = nextIndex (index, maxLength, inputs.length);
		}
		
		String computerInput = Arrays.toString(allPossibleCombi.get(0));
		
		do {
			tryNber++;
			// Proposition d l'ordinateur
			

						oldComputerInput = newComputerInput;
						newComputerInput = result;
						result = smartRandom(userTip, convertDigitToTab(newComputerInput), convertDigitToTab(oldComputerInput));
						System.out.println("Proposition : " + result);

						// Comparaison manuelle utilisateur
						userTip = clavierString.nextLine();
						System.out.println("Réponse : " + userTip);

			// Tests fin de boucle
			if (result == secretCode) {
				System.out.println("L'ordinateur a trouvé le code secret " + secretCode + "!");
				break;
			} 
			if (tryNber >= tryMax) {
				System.out.println("L'ordinateur n'a plus d'essai : la reponse était " + secretCode + "!");
				break;
			} 

		} while (secretCode != result);
	}

	@Override
	public void duel() {
	}


	static void gamePresentation(int n, int m, int maxTry) {
		System.out.print("Pouvez vous trouver ma combinaison de " + n + " chiffres compris entre 1 et " + m + " en moins de " + maxTry + " coups ?");
	}

	static int random(int max) {
		return (1 + (int) (Math.random() * max));
	}


	static void putCombinaison(int[] combinaison, int n, int m) {
		for (int i = 0; i < n; i++) {
			combinaison [i] = random(m);
		}
	}

	public static void compareTab (int [] userInputTab, int [] tabSecretCode) {
		int wellPlaced = 0;
		int badPlaced = 0; 
		int [] temp =  new  int [4] ;  
		System.arraycopy(tabSecretCode,  0, temp,  0,  4) ;


		for (int i = 0; i < 4; i++) {
			// si l'element est à la bonne place
			if (tabSecretCode[i] == userInputTab[i]) {
				wellPlaced++;
				temp[i] = -1;
			}
		}
		for (int i = 0; i < tabSecretCode.length; i++) {
			for (int j = 0; j < tabSecretCode.length; j++) {
				if (userInputTab[i] == temp[j]) {
					badPlaced++; 
					temp[j]= -1;
				}
			}
		}
		System.out.println("Bien place(s) : " + wellPlaced + " , Mal place(s) : " + badPlaced);
	}


	public static boolean contains(int[] array, int v) {
		boolean result = false;
		for (int i : array) {
			if (i == v) {
				result = true;
				break;
			}
		}		
		return result;
	}

	private static int[] convertDigit(int num) {

		// Completion
		String temp = String.format("%04d", num);

		// test de longueur
		if (temp.length() != 4) {
			// Gestionn d'erreur à revoir
			throw new IllegalArgumentException();
		}

		// transcription
		int[] tabInt = new int[temp.length()];
		for (int i = 0; i < temp.length(); i++) {
			tabInt[i] = Character.getNumericValue(temp.charAt(i));
		}
		return tabInt;
	}

	/**
	 * Dychotomie de l'ordinateur qui prend en compte le résultat de la comparaison
	 * 
	 * @param userTip             : resultat de la comparaison
	 * @param tabNewComputerInput : ancienne proposition de l'ordinateur
	 * @return
	 */
		
	
	// Get the index of the next possible combination
	public static int[] nextIndex (int[] index, int maxLength, int size)
	{   
		for (int i = (maxLength - 1); i > 0; i--)
		{
			if (index[i] == (size - 1))
			{
				index[i] = 0;
				if(index[i-1] == (size - 1)){
					continue;
				}
				index[i - 1]++;
				break;
			}else{
				index[i]++;
				break;
			}

		}
		return index;
	}
}


	
	

	
	