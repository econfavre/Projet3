package jeuprojet;

import java.util.Scanner;

public class PlusOuMoinsGame extends Jeux {
	Scanner clavier = new Scanner(System.in);
	Scanner clavierString = new Scanner(System.in);
	static int oldComputerInput = 0; 
	int newComputerInput = 0;
	int tryNber = 0;
	int tryMax = 5;
	static int size = 4;
	static int maxDigit = 9;
	boolean isNumberValid;

	@Override
	public void challenger () throws IllegalArgumentException {
		int secretCode = 0;
		int userInput = 0;

		// Presentation
		gamePresentation(size, maxDigit, tryMax);

		// Secret code
		int[] tabSecretCode = putCombinaison(size, maxDigit);
		for(int i = 0; i < tabSecretCode.length; i++) 
			secretCode += Math.pow(10,i) * tabSecretCode[tabSecretCode.length - i - 1];

		do {
			tryNber++;
			do {
				// Proposition de l'utilisateur
				System.out.println("Veuillez taper un nombre : ");
				userInput = getClavierInt();
			} while (!isNumberValid);

			// Comparaison
			String resultat = compareTab(convertDigitToTab(userInput), tabSecretCode);
			System.out.println("Proposition : " + userInput + " -> Réponse : " + resultat);

			// Tests fin de boucle
			if (userInput == secretCode) {
				System.out.println("Bravo vous avez trouvé le code secret " + secretCode + "!");
				break;
			} 
			if (tryNber >= tryMax) {
				System.out.println("Vous n'avez plus d'essai : la reponse était " + secretCode + "!");
				break;
			}

		} while (secretCode != userInput);
	}

	/**
	 * Control de saisie utilisateur
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
		System.out.println("(Combinaison secréte : " + secretCode + ")");

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
		int secretCodeUser = 0;
		int secretCodeComputer = 0;
		int computerInput = 0;
		int userInput;
		String userTip = null;

		// Presentation
		gamePresentation(size, maxDigit, tryMax);

		// Code secret de l'utilisateur 
		do {
			// Proposition de l'utilisateur
			System.out.println("Veuillez définir un code secret : ");
			secretCodeUser = getClavierInt();
		} while (!isNumberValid);
		int[] tabSecretCodeUser = convertDigitToTab(secretCodeUser);

		// Code secret de l'ordinateur
		int[] tabSecretCodeComputer = putCombinaison(size, maxDigit);
		for(int i = 0; i < tabSecretCodeComputer.length; i++) 
			secretCodeComputer += Math.pow(10,i) * tabSecretCodeComputer[tabSecretCodeComputer.length - i - 1];

		do {
			tryNber++;

			// Proposition de l'ordinateur
			oldComputerInput = newComputerInput;
			newComputerInput = computerInput;
			computerInput = smartRandom(userTip, convertDigitToTab(newComputerInput), convertDigitToTab(oldComputerInput));

			// Comparaison
			userTip = compareTab(convertDigitToTab(computerInput), tabSecretCodeUser);

			System.out.println("La proposition de l'ordinateur : " + computerInput + " -> Aide de l'utilisateur: " + userTip);

			// Tests 
			if (secretCodeUser == computerInput) {
				System.out.println("L'ordinateur a trouvé le code secret " + secretCodeUser + "!");
				break;
			} 
			if (tryNber >= tryMax) {
				System.out.println("L'ordinateur n'a plus d'essai : la reponse était " + secretCodeUser + "!");
				break;
			} 

			// Proposition de l'utilisateur
			System.out.println("Veuillez taper une proposition pour trouver le code secret de l'ordinateur : ");
			userInput = clavier.nextInt();

			// Comparaison
			System.out.println("-> Aide de l'ordinateur : " + compareTab(convertDigitToTab(userInput), tabSecretCodeComputer));

			// Trouvé
			// Tests 
			if (secretCodeComputer == userInput) {
				System.out.println("Vous avez trouvé le code secret " + secretCodeComputer + "!");
				System.out.println("La partie est terminée : voulez-vous rejouer ?");
				break;
			} 
			if (tryNber >= tryMax) {
				System.out.println("Vous n'avez plus d'essai : la reponse était " + secretCodeComputer + "!");
				System.out.println("La partie est terminée : voulez-vous rejouer ?");
				break;
			} 

		} while (secretCodeUser != computerInput || secretCodeComputer != userInput);
	}

	/**
	 * Message de présentation du jeu 
	 * @param n
	 * @param m
	 * @param maxTry
	 */
	static void gamePresentation(int n, int m, int maxTry) {
		System.out.print("Pouvez vous trouver le code secret de " + n + " chiffres compris entre 1 et " + m + " en moins de " + maxTry + " coups ? ");
	}

	/**
	 * Fonction donnant un random int entre 0 et max
	 * 
	 * @param max : valeur max du int
	 * @return
	 */
	static int random(int max) {
		return (1 + (int) (Math.random() * max));
	}

	/**
	 * Fonction renvoyant un tableau de n chiffre random de 0 à m
	 * 
	 * @param n : max size tab
	 * @param m : max digit random
	 * 
	 */
	public int[] putCombinaison(int n, int m) {
		int[] combinaison = new int[n]; 
		for (int i = 0; i < n; i++) {
			combinaison [i] = random(m);
		}
		return combinaison;
	}

	/**
	 * Convertion entier en tableau de chiffre
	 * 
	 * @param num le nombre à tronquer
	 * @return
	 */
	public int[] convertDigitToTab(int num) {
		int index = 0;
		int[] tabInt = new int[size];
		int digits = (int) Math.log10(num);
		for (int i = (int) Math.pow(10, digits); i > 0; i /= 10) {
			tabInt[index] =num / i;
			num %= i;
			index++;
		}
		return tabInt;
	}

	/**
	 * Retourne un chiffre dans un nombre 
	 * 
	 * @param number : nombre
	 * @param base : 
	 * @param n : position du chiffre à extraire
	 * @return
	 */
	public int getNthDigit(int number, int base, int n) {    
		return (int) ((number / Math.pow(base, n - 1)) % base);
	}

	/**
	 * Comparaison de deux tableau d'entier
	 * 
	 * @param tabUserInput
	 * @param tabSecret
	 * 
	 * @return resultat de la comparaison en string
	 */
	private static String compareTab(int[] tabUserInput, int[] tabSecret) {
		String result = new String();
		for (int i = 0; i < tabUserInput.length; i++) {
			if (tabSecret[i] < tabUserInput[i]) {
				result = result + '-';
			} else if (tabSecret[i] > tabUserInput[i]) {
				result = result + '+';
			} else {
				result = result + '=';
			}
		}
		return result;
	}

	/**
	 * Dychotomie de l'ordinateur qui prend en compte le résultat de la comparaison
	 * 
	 * @param userTip             : resultat de la comparaison
	 * @param tabNewComputerInput : ancienne proposition de l'ordinateur
	 * @return
	 */
	private static int smartRandom(String userTip, int[] tabNewComputerInput, int[] tabOldComputerInput) {

		// Bornes digit initiales
		int borneMin = 0;
		int borneMax = maxDigit + 1;
		int result = 0;
		String tabResult = "";

		for (int i = 0; i < size; i++) {
			if (userTip == null) {
				tabResult = tabResult + Math.round((borneMin + borneMax) / 2);
			} else if ('=' == userTip.charAt(i)) {
				tabResult = tabResult + tabNewComputerInput[i];
			} else if ('+' == userTip.charAt(i)) {
				borneMin = tabNewComputerInput[i];
				if (oldComputerInput == 0 || tabOldComputerInput[i] < tabNewComputerInput[i]) {
					borneMax = 10;
				} else {
					borneMax = tabOldComputerInput[i];
				}
				tabResult = tabResult + Math.round((borneMin + borneMax) / 2);
			} else if ('-' == userTip.charAt(i)) {
				if (oldComputerInput == 0 || tabOldComputerInput[i] > tabNewComputerInput[i]) {
					borneMin = 0;
				} else {
					borneMin = tabOldComputerInput[i];
				}
				borneMax = tabNewComputerInput[i];
				tabResult = tabResult + Math.round((borneMin + borneMax) / 2);
			}
		}
		result = Integer.valueOf(tabResult);
		return result;
	}
}
