package jeuprojet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MasterMindGame extends Jeux {
	
	public MasterMindGame() {
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

	int devMode;
	
	int oldComputerInput = 0;
	int newComputerInput = 0;
	
	// try number configuration
	private int tryNber = 0;
	private int tryMax;

	int badPlaced;
	int wellPlaced;
	String resultat;

	// combination parameters
	int size;
	int maxDigit;

	boolean isNumberValid;

	int[] validDigits = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	String lastMove;
	ArrayList<String> possibleTokens;

	@Override
	public void challenger() throws IllegalArgumentException {
		String playAgain = "";

		do {
			int userInput = 0;
			int secretCode = 0;
			int[] userInputTab;
			int[] tabSecretCode = new int[size];
			String resultat;
			
			// Game explanation
			gamePresentation(size, maxDigit, tryMax);

			// Computer Secret Code
			putCombinaison(tabSecretCode, size, maxDigit);

			// Convert the int array to an int
			for (int i = 0; i < tabSecretCode.length; i++)
				secretCode += Math.pow(10, i) * tabSecretCode[tabSecretCode.length - i - 1];
			if (devMode == 1) {
				System.out.println("\nLe code secret de l'ordinateur est " + secretCode);
			}
			do {
				
				do {
					tryNber++;
					// User guess
					System.out.println("Veuillez taper une proposition : ");
					userInput = getClavierInt();
				} while (!isNumberValid);

				// Convert the user guess to an int tab
				userInputTab = convertDigitToTab(userInput);

				// Comparison
				resultat = compareTab(userInputTab, tabSecretCode);
				
				System.out.println("Indice : 1er chiffre = nombre de chiffre(s) bien placé(s) et 2eme chiffre = nombre d'élement(s) mal placé(s) : " + resultat);

				// Computer secret code found
				// Tests
				if (secretCode == userInput) {
					System.out.println("Bravo vous avez trouvé le code secret en " + tryNber + " essai(s) !");
					break;
				} else if (tryNber >= tryMax) {
					System.out.println("Vous n'avez plus d'essai : la reponse était " + secretCode + " !");
					break;
				}
				// Ask for playing again the game
			} while (userInputTab != tabSecretCode);
			System.out.println("PARTIE TERMINEE, voulez-vous rejouer au jeu MasterMind mode Challenger ? Tapez O/N");
			tryNber = 0;
			playAgain = clavier.next();
			
			// Ask for playing again to an other game and come back to the main menu
		} while (playAgain.equalsIgnoreCase("O"));
		System.out.println(
				"Merci d'avoir jouer au jeu MasterMind mode Challenger ! A bientôt !!");
	}

	@Override
	public void defenseur() {
		String playAgain = "";
		int secretCode;
		int total;

		// User secret code
		do {
		System.out.println("Veuillez définir un code secret : ");
		secretCode = getClavierInt();
		} while (!isNumberValid);
		int[] tabSecretCode = convertDigitToTab(secretCode);

		System.out.println(("Combinaison secrète : " + secretCode));
		

		// List of all the possible combination of n elements from 0 to max digit
		List<int[]> possibleCombi = AllPossibleCombinaisons();
		do {

			for (int i = 0; i < tryMax; i++) {
				tryNber++;
				// Computer guess
				System.out.println("Proposition de l'ordinateur : " + Arrays.toString(possibleCombi.get(0)));
				System.out.println("Indice : nombre de chiffres en commun avec le code secret : ");
				total = generateValue(tabSecretCode, possibleCombi.get(0));
				System.out.println(total);
				List<Integer> tmp = new ArrayList<>();
				for (int v : possibleCombi.get(0)) {
					tmp.add(v);
				}
				if (total == 0) {
					possibleCombi = removeUselessCombi(possibleCombi);
				} else if (total == 1) {
					possibleCombi = resizedList(extractSingle(tmp), possibleCombi, 1);
				} else if (total == 2) {
					possibleCombi = resizedList(extractPair(tmp), possibleCombi, 2);
				} else if (total == 3) {
					possibleCombi = resizedList(extractTrio(tmp), possibleCombi, 3);
				} else if (total == 4) {
					possibleCombi = resizedList(extractQuatuor(tmp), possibleCombi, 4);
				} else if (total == 5) {
					possibleCombi = resizedList(extract5(tmp), possibleCombi, 5);
				} else if (total == 6) {
					possibleCombi = resizedList(extract6(tmp), possibleCombi, 6);
				} else if (total == 7) {
					possibleCombi = resizedList(extract7(tmp), possibleCombi, 7);
				} else if (total == 8) {
					possibleCombi = resizedList(extract8(tmp), possibleCombi, 8);
				} else if (total == 9) {
					possibleCombi = resizedList(extract9(tmp), possibleCombi, 9);
				} else if (total == size) {
					System.out.println("L'ordinateur a trouvé le code secret en " + tryNber + " essai(s) !");
					break;
				}
				if (tryMax <= tryNber) {
					System.out.println("L'ordinateur n'a plus d'essai : la reponse était " + secretCode + " !");
					break;

				}
			}
			System.out.println("PARTIE TERMINEE, voulez-vous rejouer au jeu MasterMind mode Defenseur ? Tapez O/N");
			playAgain = clavier.next();
			tryNber = 0;

		} while (playAgain.equalsIgnoreCase("O"));
		System.out.println(
				"Merci d'avoir jouer au jeu MasterMind mode Defenseur ! A bientôt !!");
	}

	@Override
	public void duel() {
		String playAgain = "";
		do {

			int secretCodeUser = 0;
			int secretCodeComputer = 0;
			int[] tabSecretCodeComputer = new int[size];
			int computerInput = 0;
			int userInput;
			int total;

			// Game explanation
			gamePresentation(size, maxDigit, tryMax);

			// User secret code
			System.out.println("\nVeuillez définir un code secret : ");
			do {
			secretCodeUser = getClavierInt();
			} while (!isNumberValid);
			int[] tabSecretCodeUser = convertDigitToTab(secretCodeUser);

			System.out.println(("Combinaison secrète de l'utilisateur : " + secretCodeUser));

			// Computer secret code
			putCombinaison(tabSecretCodeComputer, size, maxDigit);

			for (int i = 0; i < tabSecretCodeComputer.length; i++) {
				secretCodeComputer += Math.pow(10, i) * tabSecretCodeComputer[tabSecretCodeComputer.length - i - 1];
			}
			if (devMode == 1) {
			System.out.println("\nLe code secret de l'ordinateur est " + secretCodeComputer);
			}

			List<int[]> possibleCombi = AllPossibleCombinaisons();
			do {
				tryNber++;

				// Computer guess
				System.out.println("Proposition de l'ordinateur : " + Arrays.toString(possibleCombi.get(0)));

				// Comparison
				total = generateValue(tabSecretCodeUser, possibleCombi.get(0));
				List<Integer> tmp = new ArrayList<>();
				for (int v : possibleCombi.get(0)) {
					tmp.add(v);
				}
					if (total == 0) {
						possibleCombi = removeUselessCombi(possibleCombi);
					} else if (total == 1) {
						possibleCombi = resizedList(extractSingle(tmp), possibleCombi, 1);

					} else if (total == 2) {
						possibleCombi = resizedList(extractPair(tmp), possibleCombi, 2);

					} else if (total == 3) {
						possibleCombi = resizedList(extractTrio(tmp), possibleCombi, 3);
					} else if (total == 4) {
						possibleCombi = resizedList(extractQuatuor(tmp), possibleCombi, 4);
					} else if (total == 5) {
						possibleCombi = resizedList(extract5(tmp), possibleCombi, 5);
					} else if (total == 6) {
						possibleCombi = resizedList(extract6(tmp), possibleCombi, 6);
					} else if (total == 7) {
						possibleCombi = resizedList(extract7(tmp), possibleCombi, 7);
					} else if (total == 8) {
						possibleCombi = resizedList(extract8(tmp), possibleCombi, 8);
					} else if (total == 9) {
						possibleCombi = resizedList(extract9(tmp), possibleCombi, 9);
					} else if (total == size) {
						System.out.println("Bravo l'ordinateur a trouvé le code secret en " + tryNber + " essai(s) !");
						break;
					}
					if (tryMax <= tryNber) {
						System.out.println("L'ordinateur n'a plus d'essai : la reponse était " + secretCodeUser + " !");
						break;
				}

				// User guess
				System.out.println("Veuillez taper une proposition pour trouver le code secret de l'ordinateur : ");
				userInput = getClavierInt();

				// Comparison
				System.out.println(
						"-> Aide de l'ordinateur : " + compareTab(convertDigitToTab(userInput), tabSecretCodeComputer));

				// Tests
				if (secretCodeComputer == userInput) {
					System.out.println("Vous avez trouvé le code secret " + secretCodeComputer + "!");
					break;
				}
				if (tryNber >= tryMax) {
					System.out.println("Vous n'avez plus d'essai : la reponse était " + secretCodeComputer + "!");
					break;
				}

			} while (secretCodeUser != computerInput || secretCodeComputer != userInput);
			System.out.println("PARTIE TERMINEE, voulez-vous rejouer au jeu MasterMind mode Duel ? Tapez O/N");
			playAgain = clavier.next();
			tryNber = 0;

		} while (playAgain.equalsIgnoreCase("O"));
		System.out.println("Merci d'avoir jouer au jeu MasterMind mode Defenseur ! A bientôt !!");
	}

	//FUNCTIONS USED IN ALL THE MASTERMIND GAME MODES

	/**
	 * Game presentation message
	 * 
	 * @param n : number of elements in the combination
	 * @param m : maximum digit of an element
	 * @param maxTry : maximum number of guesses
	 */
	static void gamePresentation(int n, int m, int maxTry) {
		System.out.print("Pouvez vous trouver la combinaison secrete de " + n + " chiffres compris entre 1 et " + m
				+ " en moins de " + maxTry + " coups ?");
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

	//FUNCTIONS USED IN MASTERMIND CHALLENGER AND DUAL MODES

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
	 * @param int [] combination : tab of int to have a combination
	 * @return
	 * 
	 */
	static void putCombinaison(int[] combination, int n, int m) {
		for (int i = 0; i < n; i++) {
			combination[i] = random(m);
		}
	}

	/**
	 * Function to compare the guess and the secret code
	 * @param int [] inputTab : the tab representing the guess
	 * @param int [] tabSecretCode : the tab of the secret code
	 * @return
	 */
	public String compareTab(int[] inputTab, int[] tabSecretCode) {
		int wellPlaced = 0;
		int badPlaced = 0;
		int[] temp = new int[size];
		System.arraycopy(tabSecretCode, 0, temp, 0, size);

		for (int i = 0; i < 4; i++) {
			// if good place
			if (tabSecretCode[i] == inputTab[i]) {
				wellPlaced++;
				temp[i] = -1;
				inputTab[i] = -1;
			}
		}
		for (int i = 0; i < tabSecretCode.length; i++) {
			for (int j = 0; j < tabSecretCode.length; j++) {
				// if bad place
				if (inputTab[i] == temp[j] && temp[j] != -1) {
					badPlaced++;
					temp[j] = -1;
				}
			}
		}
		return String.valueOf(wellPlaced) + String.valueOf(badPlaced);
	}

	// FUNCTION USED IN THE MASTERMIND CHALLENGER MODE	
	/**
	 * User Input Control
	 * 
	 * @return
	 */
	public int getClavierInt() {
		int result = 0;
		if (clavier.hasNextInt()) {
			result = clavier.nextInt();
			isNumberValid = true;
			// Length test
			if ((int) Math.log10(result) + 1 != size) {
				isNumberValid = false;
				System.out.println("Merci de taper " + size + " chiffre(s) !");
				clavier.next();
			}
		} else {
			System.out.println("Veuillez réessayer : merci de taper une suite de " + size + " chiffre(s) : ");
			isNumberValid = false;
			clavier.next();
		}
		return result;
	}

	//FUNCTIONS USED IN THE MASTERMIND DEFENSER AND DUAL MODES 

	/**
	 * Function to remove the useless combinations
	 * @param List<int [] > lst : the list of remaining possible combinations
	 * @return
	 */
	private List<int[]> removeUselessCombi(List<int[]> lst) {
		int[] cmp = copyTab(lst.get(0));
		lst.remove(0);
		List<int[]> ret = new ArrayList<>();
		for (int i = 0; i < lst.size(); i++) {
			boolean flag = true;
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++) {
					if (cmp[k] == lst.get(i)[j]) {
						flag = false;
					}
				}
			}
			if (flag) {
				ret.add(lst.get(i));
			}
		}
		return ret;
	}

	/**
	 * Function to extract the combination with one element in common with the computer guess
	 * @param List <Integer> l : the list of the combination
	 * @return
	 */
	private List<List<Integer>> extractSingle(List<Integer> l) {
		List<List<Integer>> ret = new ArrayList<>();
		List<Integer> v = new ArrayList<>();

		for (Integer a : l) {
			v.add(a);
		}

		ret.add(v);
		return ret;
	}

	/**
	 * Function to extract the combination with two elements in common with the computer guess
	 * @param List <Integer> lst : the list of the combination
	 * @return
	 */
	public List<List<Integer>> extractPair(List<Integer> lst) {
		List<List<Integer>> val = new ArrayList<>();

		for (int i1 = 0; i1 < lst.size(); i1++) {
			for (int i2 = i1 + 1; i2 < lst.size(); i2++) {
				List<Integer> add = new ArrayList<>();
				add.add(lst.get(i1));
				add.add(lst.get(i2));
				val.add(add);
			}
		}

		return val;
	}

	/**
	 * Function to extract the combination with three elements in common with the computer guess
	 * @param List <Integer> lst : the list of the combination
	 * @return
	 */
	public List<List<Integer>> extractTrio(List<Integer> lst) {
		List<List<Integer>> val = new ArrayList<>();

		for (int i1 = 0; i1 < lst.size(); i1++) {
			for (int i2 = i1 + 1; i2 < lst.size(); i2++) {
				for (int i3 = i2 + 1; i3 < lst.size(); i3++) {
					List<Integer> add = new ArrayList<>();
					add.add(lst.get(i1));
					add.add(lst.get(i2));
					add.add(lst.get(i3));
					val.add(add);
				}
			}
		}
		return val;
	}

	/**
	 * Function to extract the combination with four element in common with the computer guess
	 * @param List <Integer> lst : the list of the combination
	 * @return
	 */
	public List<List<Integer>> extractQuatuor(List<Integer> lst) {
		List<List<Integer>> val = new ArrayList<>();

		for (int i1 = 0; i1 < lst.size(); i1++) {
			for (int i2 = i1 + 1; i2 < lst.size(); i2++) {
				for (int i3 = i2 + 1; i3 < lst.size(); i3++) {
					for (int i4 = i3 + 1; i4 < lst.size(); i4++) {
						List<Integer> add = new ArrayList<>();
						add.add(lst.get(i1));
						add.add(lst.get(i2));
						add.add(lst.get(i3));
						add.add(lst.get(i4));

						val.add(add);
					}
				}
			}
		}
		return val;
	}

	public List<List<Integer>> extract5(List<Integer> lst) {
		List<List<Integer>> val = new ArrayList<>();

		for (int i1 = 0; i1 < lst.size(); i1++) {
			for (int i2 = i1 + 1; i2 < lst.size(); i2++) {
				for (int i3 = i2 + 1; i3 < lst.size(); i3++) {
					for (int i4 = i3 + 1; i4 < lst.size(); i4++) {
						for (int i5 = i4 + 1; i5 < lst.size(); i5++) {
							List<Integer> add = new ArrayList<>();
							add.add(lst.get(i1));
							add.add(lst.get(i2));
							add.add(lst.get(i3));
							add.add(lst.get(i4));
							add.add(lst.get(i5));
							val.add(add);
						}
					}
				}
			}
		}

		return val;
	}

	public List<List<Integer>> extract6(List<Integer> lst) {
		List<List<Integer>> val = new ArrayList<>();

		for (int i1 = 0; i1 < lst.size(); i1++) {
			for (int i2 = i1 + 1; i2 < lst.size(); i2++) {
				for (int i3 = i2 + 1; i3 < lst.size(); i3++) {
					for (int i4 = i3 + 1; i4 < lst.size(); i4++) {
						for (int i5 = i4 + 1; i5 < lst.size(); i5++) {
							for (int i6 = i5 + 1; i6 < lst.size(); i6++) {
								List<Integer> add = new ArrayList<>();
								add.add(lst.get(i1));
								add.add(lst.get(i2));
								add.add(lst.get(i3));
								add.add(lst.get(i4));
								add.add(lst.get(i5));
								add.add(lst.get(i6));
								val.add(add);
							}
						}
					}
				}
			}
		}

		return val;
	}

	public List<List<Integer>> extract7(List<Integer> lst) {
		List<List<Integer>> val = new ArrayList<>();

		for (int i1 = 0; i1 < lst.size(); i1++) {
			for (int i2 = i1 + 1; i2 < lst.size(); i2++) {
				for (int i3 = i2 + 1; i3 < lst.size(); i3++) {
					for (int i4 = i3 + 1; i4 < lst.size(); i4++) {
						for (int i5 = i4 + 1; i5 < lst.size(); i5++) {
							for (int i6 = i5 + 1; i6 < lst.size(); i6++) {
								for (int i7 = i6 + 1; i7 < lst.size(); i7++) {
									List<Integer> add = new ArrayList<>();
									add.add(lst.get(i1));
									add.add(lst.get(i2));
									add.add(lst.get(i3));
									add.add(lst.get(i4));
									add.add(lst.get(i5));
									add.add(lst.get(i6));
									add.add(lst.get(i7));
									val.add(add);
								}
							}
						}
					}
				}
			}
		}

		return val;
	}

	public List<List<Integer>> extract8(List<Integer> lst) {
		List<List<Integer>> val = new ArrayList<>();

		for (int i1 = 0; i1 < lst.size(); i1++) {
			for (int i2 = i1 + 1; i2 < lst.size(); i2++) {
				for (int i3 = i2 + 1; i3 < lst.size(); i3++) {
					for (int i4 = i3 + 1; i4 < lst.size(); i4++) {
						for (int i5 = i4 + 1; i5 < lst.size(); i5++) {
							for (int i6 = i5 + 1; i6 < lst.size(); i6++) {
								for (int i7 = i6 + 1; i7 < lst.size(); i7++) {
									for (int i8 = i7 + 1; i8 < lst.size(); i8++) {
										List<Integer> add = new ArrayList<>();
										add.add(lst.get(i1));
										add.add(lst.get(i2));
										add.add(lst.get(i3));
										add.add(lst.get(i4));
										add.add(lst.get(i5));
										add.add(lst.get(i6));
										add.add(lst.get(i7));
										add.add(lst.get(i8));
										val.add(add);
									}
								}
							}
						}
					}
				}
			}
		}

		return val;
	}

	public List<List<Integer>> extract9(List<Integer> lst) {
		List<List<Integer>> val = new ArrayList<>();

		for (int i1 = 0; i1 < lst.size(); i1++) {
			for (int i2 = i1 + 1; i2 < lst.size(); i2++) {
				for (int i3 = i2 + 1; i3 < lst.size(); i3++) {
					for (int i4 = i3 + 1; i4 < lst.size(); i4++) {
						for (int i5 = i4 + 1; i5 < lst.size(); i5++) {
							for (int i6 = i5 + 1; i6 < lst.size(); i6++) {
								for (int i7 = i6 + 1; i7 < lst.size(); i7++) {
									for (int i8 = i7 + 1; i8 < lst.size(); i8++) {
										for (int i9 = i8 + 1; i9 < lst.size(); i9++) {
											List<Integer> add = new ArrayList<>();
											add.add(lst.get(i1));
											add.add(lst.get(i2));
											add.add(lst.get(i3));
											add.add(lst.get(i4));
											add.add(lst.get(i5));
											add.add(lst.get(i6));
											add.add(lst.get(i7));
											add.add(lst.get(i8));
											add.add(lst.get(i9));
											val.add(add);
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return val;
	}

	/**
	 * Function to actualize the liste of possible combinations according to the guesses of the computer
	 * @param List<List<Integer>> toSearch :
	 * @param List<int[]> lst : 
	 * @param int size : the size of the combination
	 * @return
	 */
	private List<int[]> resizedList(List<List<Integer>> toSearch, List<int[]> lst, int size) {
		List<int[]> temp = new ArrayList<>();
		lst.remove(0);
		for (int i = 0; i < lst.size(); i++) {
			int count = 0;
			int[] cmp = copyTab(lst.get(i));
			for (List<Integer> l : toSearch) {
				for (Integer j : l) {
					for (int k = 0; k < cmp.length; k++) {
						if (j == cmp[k]) {
							count++;
							cmp[k] = -1;
						}
					}
				}
			}
			if (count >= size && !temp.contains(lst.get(i))) {
				temp.add(copyTab(lst.get(i)));
			}
		}
		return temp;
	}

	/**
	 * Function to know how many elements are presents in the computer guess and the secret code
	 * @param : int [] sol : the tab of the secret code
	 * @param : int [] combination : the tab of the computer guess
	 * 	  
	 * @return
	 */
	private int generateValue(int[] sol, int[] combination) {
		int[] cpy = new int[size];
		int present = 0;

		for (int i = 0; i < sol.length; i++) {
			cpy[i] = sol[i];
		}

		// good place
		for (int i = 0; i < size; i++) {
			if (sol[i] == combination[i]) {
				present++;
				cpy[i] = -1;
			}
		}

		for (int i = 0; i < sol.length; i++) {
			for (int j = 0; j < sol.length; j++) {
				if (combination[i] == cpy[j]) {
					present++;
					cpy[j] = -1;
					break;
				}
			}
		}
		return present;
	}

	/**
	 * Function to know if an element of the secret code is present in the computer guess
	 * @param : int [] array : the tab of computer guess
	 * @param : the element of the secret code
	 * @return
	 */
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

	/**
	 * Function to generate a tab
	 * @return
	 */
	private int[] generateTab() {
		int[] ret = new int[size];
		for (int i = 0; i < size; i++) {
			ret[i] = 0;
		}
		return ret;
	}

	/**
	 * Function to copy a tab
	 * @param int [] sol : tab of secret code
	 * @return
	 */
	private int[] copyTab(int[] sol) {
		int[] ret = new int[size];
		for (int i = 0; i < size; i++) {
			ret[i] = sol[i];
		}
		return ret;
	}

	/**
	 * Function to know if the tab is completed
	 * @param : int [] sol : tab of secret code
	 * @return
	 */
	private boolean isCompletedTab(int[] sol) {
		for (int i = 0; i < sol.length; i++) {
			if (sol[i] != maxDigit) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Function to reaffect 
	 * @param : int [] sol : tab of secret code
	 * @return
	 */
	private int[] reaffect(int[] sol) {
		for (int i = sol.length - 1; i > 0; i--) {
			if (sol[i] == maxDigit + 1) {
				sol[i] = 0;
				sol[i - 1]++;
			}
		}
		return sol;
	}

	// Function giving all the possible combinations (with duplication)
		private ArrayList<int[]> AllPossibleCombinaisons() {
		ArrayList<int[]> lst = new ArrayList<>(); // on cree une liste de tableaux de int
		int[] sol = generateTab(); // on genere un tableau
		lst.add(copyTab(sol)); // on rajoute dans la liste un tableau de int qui est la copie de int [] sol
		while (isCompletedTab(sol)) { // tant qu'on a pas des 9 a chaque element de la combi on continue la boucle
			sol[sol.length - 1]++;
			if (sol[sol.length - 1] == maxDigit + 1) {
				sol = reaffect(sol);
			}
			lst.add(copyTab(sol));
		}
		return lst;
	}
}


