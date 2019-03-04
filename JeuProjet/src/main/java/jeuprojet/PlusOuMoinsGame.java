package jeuprojet;

import java.io.IOException;
import java.util.Scanner;

public class PlusOuMoinsGame extends Jeux {
	
	public PlusOuMoinsGame() {
		GetPropertyValues prop = new GetPropertyValues();
		try {
			prop.getPropValues();
			size = prop.getSize();
			tryMax = prop.getTryMax();
			maxDigit = prop.getMaxDigit();
			devMode = prop.getDevMode();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	Scanner clavier = new Scanner(System.in);
	Scanner clavierString = new Scanner(System.in);
	
	int devMode;
	int oldComputerInput = 0;
	int newComputerInput = 0;
	int tryNber = 0;
	int tryMax;
	int size;
	int maxDigit;
	boolean isNumberValid;

	@Override
	public void challenger() throws IllegalArgumentException {
		String playAgain = "";
		
		do {
			
			int secretCode = 0;
			int userInput = 0;

			// Game explanation
			gamePresentation(size, maxDigit, tryMax);

			// Computer secret code
			int[] tabSecretCode = putCombinaison(size, maxDigit);
			
			for (int i = 0; i < tabSecretCode.length; i++)
				secretCode += Math.pow(10, i) * tabSecretCode[tabSecretCode.length - i - 1];
			if (devMode == 1) {
				System.out.println("\nLe code secret de l'ordinateur est " + secretCode);
			}

			do {
				tryNber++;
				do {
					// User guess
					System.out.println("\nVeuillez taper une proposition de combinaison : ");
					userInput = getClavierInt();
				} while (!isNumberValid);

				// Comparison
				String resultat = compareTab(convertDigitToTab(userInput), tabSecretCode);
				System.out.println("Proposition : " + userInput + " -> Réponse : " + resultat);

				// User secret code found
				// Tests
				if (userInput == secretCode) {
					System.out.println("Bravo vous avez trouvé le code secret " + secretCode + " en " + tryNber + " essai(s) !");
					break;
				}
				if (tryNber >= tryMax) {
					System.out.println("Vous n'avez plus d'essai : la réponse était " + secretCode + "!");
					break;
				}

			} while (secretCode != userInput);
			System.out.println("PARTIE TERMINEE, voulez-vous rejouer au jeu PlusOuMoins mode Challenger ? Tapez O/N");
			playAgain = clavier.next();
			tryNber = 0;

		} while (playAgain.equalsIgnoreCase("O"));
		System.out.println("Merci d'avoir jouer au jeu PlusOuMoins mode Challenger ! A bientôt !!");
	}


	@Override
	public void defenseur() {
		String playAgain = "";
		do {
			int secretCode;

			// User secret code
			do {
				System.out.println("Veuillez définir un code secret : ");
				secretCode = getClavierInt();
			} while (!isNumberValid);

			int result = 0;

			String userTip = null;
			System.out.println("(Combinaison secrète : " + secretCode + ")");
			
			// Game explanation
			gamePresentation(size, maxDigit, tryMax);
			do {
				tryNber++;

				// Computer guess
				oldComputerInput = newComputerInput;
				newComputerInput = result;
				result = smartRandom(userTip, convertDigitToTab(newComputerInput), convertDigitToTab(oldComputerInput));
				System.out.println("\nProposition de l'ordinateur : " + result);

				// User advice 
				System.out.println(" Indice pour l'ordinateur : = : chiffre correct, - : le bon chiffre est inférieur et + : le bon chiffre est supérieur ");
				userTip = clavierString.nextLine();
				System.out.println("Indices : " + userTip);

				// User secret code found
				// Tests
				if (result == secretCode) {
					System.out.println("L'ordinateur a trouvé le code secret " + secretCode + " en " + tryNber + " essai(s) !");
					break;
				}
				if (tryNber >= tryMax) {
					System.out.println("L'ordinateur n'a plus d'essai : la réponse était " + secretCode + "!");
					break;
				}

			} while (secretCode != result);
			System.out.println("PARTIE TERMINEE, voulez-vous rejouer au jeu PlusOuMoins mode Defenseur ? Tapez O/N");
			playAgain = clavier.next();
			tryNber = 0;

		} while (playAgain.equalsIgnoreCase("O"));
		System.out.println(
				"Merci d'avoir jouer au jeu PlusOuMoins mode Defenseur ! A bientôt !!");
	}

	@Override
	public void duel() {
		String playAgain = "";
		
		do {
			int secretCodeUser = 0;
			int secretCodeComputer = 0;
			int computerInput = 0;
			int userInput;
			String userTip = null;

			// Game explanation
			gamePresentation(size, maxDigit, tryMax);

			// User secret code
			do {
				System.out.println("\nVeuillez définir un code secret : ");
				secretCodeUser = getClavierInt();
			} while (!isNumberValid);
			int[] tabSecretCodeUser = convertDigitToTab(secretCodeUser);

			// Computer secret code
			int[] tabSecretCodeComputer = putCombinaison(size, maxDigit);
			for (int i = 0; i < tabSecretCodeComputer.length; i++)
				secretCodeComputer += Math.pow(10, i) * tabSecretCodeComputer[tabSecretCodeComputer.length - i - 1];
			if (devMode == 1) {
				System.out.println("\nLe code secret de l'ordinateur est " + secretCodeComputer);
			}

			do {
				tryNber++;
				// Computer guess
				oldComputerInput = newComputerInput;
				newComputerInput = computerInput;
				computerInput = smartRandom(userTip, convertDigitToTab(newComputerInput),
						convertDigitToTab(oldComputerInput));

				// Comparison
				userTip = compareTab(convertDigitToTab(computerInput), tabSecretCodeUser);

				System.out.println(
						"La proposition de l'ordinateur : " + computerInput + " -> Indices de l'utilisateur: " + userTip);

				// User secret code found
				// Tests
				if (secretCodeUser == computerInput) {
					System.out.println("L'ordinateur a trouvé le code secret " + secretCodeUser + " en " + tryNber + " essai(s) !");
					break;
				}
				if (tryNber >= tryMax) {
					System.out.println("L'ordinateur n'a plus d'essai : la réponse était " + secretCodeUser + "!");
					break;
				}

				// User guess
				System.out.println("Veuillez taper une proposition pour trouver le code secret de l'ordinateur : ");
				do {
				userInput = clavier.nextInt();
				} while (!isNumberValid);
				// Comparison
				System.out.println(
						"-> Aide de l'ordinateur : " + compareTab(convertDigitToTab(userInput), tabSecretCodeComputer));

				// Computer secret code found
				// Tests
				if (secretCodeComputer == userInput) {
					System.out.println("Vous avez trouvé le code secret " + secretCodeComputer + " en " + tryNber + " essai(s) !");
					break;
				}
				if (tryNber >= tryMax) {
					System.out.println("Vous n'avez plus d'essai : la réponse était " + secretCodeComputer + "!");
					break;
				}

			} while (secretCodeUser != computerInput || secretCodeComputer != userInput);
			System.out.println("PARTIE TERMINEE, voulez-vous rejouer au jeu PlusOuMoins mode Duel ? Tapez O/N");
			playAgain = clavier.next();
			tryNber = 0;

		} while (playAgain.equalsIgnoreCase("O"));
		System.out.println(
				"Merci d'avoir jouer au jeu PlusOuMoins mode Duel ! A bientôt !!");
	}

	/**
	 * Game presentation message
	 * 
	 * @param n : number of elements in the combination
	 * @param m : maximum digit of an element
	 * @param maxTry : maximum number of guesses
	 */
	static void gamePresentation(int n, int m, int maxTry) {
		System.out.print("Pouvez vous trouver le code secret de " + n + " chiffres compris entre 1 et " + m
				+ " en moins de " + maxTry + " coups ? ");
	}
	
	/**
	 * User Input Control
	 * 
	 * @return
	 */
	public int getClavierInt() {
		int input = 0;
		if (clavier.hasNextInt()) {
			input = clavier.nextInt();
			isNumberValid = true;
			// Test longueur
			if ((int) Math.log10(input) + 1 != size) {
				isNumberValid = false;
				System.out.println("Merci de taper " + size + " chiffres !");
				//clavier.next();
			}
		} else {
			System.out.println("Veuillez réessayer : merci de taper une suite de chiffre : ");
			isNumberValid = false;
			clavier.next();
		}
		return input;
	}

	/**
	 * Function for giving a random element between 0 and max digit
	 * 
	 * @param max : max value of an element
	 * @return
	 */
	static int random(int max) {
		return (1 + (int) (Math.random() * max));
	}

	/**
	 * Function for giving a tab of n random from 0 to max digit 	 * 
	 * @param n : max size tab
	 * @param m : max digit random
	 * @return
	 * 
	 */
	public int[] putCombinaison(int n, int m) {
		int[] combinaison = new int[n];
		for (int i = 0; i < n; i++) {
			combinaison[i] = random(m);
		}
		return combinaison;
	}

	/**
	 * Conversion of an int to a int tab
	 * 
	 * @param num : the number to transform
	 * @return
	 */
	public int[] convertDigitToTab(int num) {
		int index = 0;
		int[] tabInt = new int[size];
		int digits = (int) Math.log10(num);
		for (int i = (int) Math.pow(10, digits); i > 0; i /= 10) {
			tabInt[index] = num / i;
			num %= i;
			index++;
		}
		return tabInt;
	}

	/**
	 * Comparison of 2 int tab
	 * 
	 * @param tabInput : tab of the gamer guess
	 * @param tabSecret : secret code tab
	 * 
	 * @return resultat de la comparaison en string
	 */
	private static String compareTab(int[] tabInput, int[] tabSecret) {
		String result = new String();
		for (int i = 0; i < tabInput.length; i++) {
			if (tabSecret[i] < tabInput[i]) {
				result = result + '-';
			} else if (tabSecret[i] > tabInput[i]) {
				result = result + '+';
			} else {
				result = result + '=';
			}
		}
		return result;
	}
	
	//FUNCTION IN THE DEFENSER AND DUAL MODES OF PLUSOUMOINS GAME

	/**
	 * Computer dichotomy who take into account the result of the comparison
	 * 
	 * @param userTip             : comparison result
	 * @param tabNewComputerInput : new computer guess
	 * @param tabOldComputerInput : old computer guess
	 * @return
	 */
	private int smartRandom(String userTip, int[] tabNewComputerInput, int[] tabOldComputerInput) {

		// initialization of the boundaries of elements
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
