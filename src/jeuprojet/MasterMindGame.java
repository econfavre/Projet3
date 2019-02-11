package jeuprojet;


import java.util.ArrayList;
import java.util.Scanner;

public class MasterMindGame extends Jeux {
	Scanner clavier = new Scanner(System.in);

	static int oldComputerInput = 0; 
	int newComputerInput = 0;
	// Configuration du nombre d'essai
	private static int tryNber = 0;
	private static int tryMax = 20;

	//static String resultat;
	static int badPlaced;
	static int wellPlaced;

	// Parametrage de la combinaison
	static int size = 4;
	static int maxDigit = 9;

	boolean isNumberValid;
	
	String[] validDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    String lastMove;
    ArrayList<String> possibleTokens;


	@Override
	public void challenger () throws IllegalArgumentException{
		int userInput=0;
		int secretCode = 0;
		int [] userInputTab;
		int[] tabSecretCode = new int[size];
		String resultat;
		// 	Presentation du jeu
		gamePresentation(size, maxDigit, tryMax);

		// Secret Code
		putCombinaison(tabSecretCode, size, maxDigit);


		// Convertir le tableau code secret en un int
		for(int i = 0; i < tabSecretCode.length; i++) 
			secretCode += Math.pow(10,i) * tabSecretCode[tabSecretCode.length - i - 1];
		System.out.println(secretCode);
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
			resultat = compareTab(userInputTab, tabSecretCode);
			System.out.println(resultat);

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
		String computerInput;

		// Code secret de l'utilisateur
			// Proposition de l'utilisateur
			System.out.println("Veuillez définir un code secret : ");
			secretCode = clavier.nextInt();
			int[] tabSecretCode = convertDigitToTab(secretCode);
	
		System.out.println("(Combinaison secrète : " + secretCode + ")");
		
		ArrayList<String> possibleCombi = AllPossibleCombinaisons();
		//do {
			tryNber++;
			// Proposition d l'ordinateur
			computerInput = possibleCombi.get(0);
			System.out.println(" Proposition de l'ordinateur : " + computerInput);
			String[] computerInputS = computerInput.split(" "); 
			int[] computerInputTab = new int[computerInputS.length]; 
			for (int i = 0; i < computerInputTab.length; i++){
			    computerInputTab[i] = Integer.parseInt(computerInputS[i]); 
			String resultat = compareTab(tabSecretCode, computerInputTab);
			System.out.println(String.format("%02x", resultat));
			}
		
			
			//Tests fin de boucle
//			for (int i = 0; i <=computerInputTab.length; i++) {
//			if (computerInputTab[i] == tabSecretCode[i]) {
//			System.out.println("L'ordinateur a trouvé le code secret " + secretCode + "!");
//			break; 
//			}
//			}
//			if (tryNber >= tryMax) {
//			System.out.println("L'ordinateur n'a plus d'essai : la reponse était " + secretCode + "!");
//			break;
//			} 

	//} while (secretCode != computerInput);
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

	public static String compareTab (int [] userInputTab, int [] tabSecretCode) {
		wellPlaced = 0;
		badPlaced = 0; 
		int [] temp =  new  int [4] ;  
		System.arraycopy(tabSecretCode,  0, temp,  0,  4) ;


		for (int i = 0; i < 4; i++) {
			// si l'element est à la bonne place
			if (tabSecretCode[i] == userInputTab[i]) {
				wellPlaced++;
				temp[i] = -1;
				userInputTab[i]= -1;
			} 
		} 
		for (int i = 0; i < tabSecretCode.length; i++) {
			for (int j = 0; j < tabSecretCode.length; j++) {
				if (userInputTab[i] == temp[j] && temp[j] != -1) {
					badPlaced++; 
					temp[j]= -1;
				} 
			}
		}
		return String.valueOf(wellPlaced) + String.valueOf(badPlaced);
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
	
	//donne toutes les combinaisons possibles (avec doublons)
	private ArrayList<String> AllPossibleCombinaisons() {
        ArrayList<String> combinaison = new ArrayList<String>();
        for (int d1 = 0; d1 < validDigits.length; d1++)
            for (int d2 = 0; d2 < validDigits.length; d2++)
                for (int d3 = 0; d3 < validDigits.length; d3++)
                    for (int d4 = 0; d4 < validDigits.length; d4++)
                        {
         combinaison.add((validDigits[d1].toString() + validDigits[d2].toString() + validDigits[d3].toString() + validDigits[d4].toString()));
                        }
        return combinaison;
    }
}