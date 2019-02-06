package jeuprojet;

import java.util.Scanner;

public class Menu {

	private int nameGame;
	private int nbLevel;

	static Scanner clavier = new Scanner(System.in);

	public static void main(String[] args) {

	
		String playAgain = "";
	do {
		Menu menu = new Menu();
		menu.gameChoice();
		menu.modeChoice();
		Jeux jeu; //on instancie une variable jeu sans connaitre le type et le mode de jeu
		if (menu.nameGame == 1) { // c'est que a partir de l'objet menu qu'on va creer l'objet jeu (avec le type de jeu et de mode choisi en entree clavier),c'est grace aux donnees recoltees dans l'objet menu qu'on va creer un objet jeu
			
			jeu = new PlusOuMoinsGame();
			if (menu.nbLevel == 1) {
				jeu.challenger();
			} else if (menu.nbLevel == 2) {
				jeu.defenseur();
			} else if (menu.nbLevel == 3) {
				jeu.duel();
			}
		}
		else if (menu.nameGame == 2) {
			jeu = new MasterMindGame();
			if (menu.nbLevel == 1) {
				jeu.challenger();
			} else if (menu.nbLevel == 2) {
				jeu.defenseur();
			} else if (menu.nbLevel == 3) {
				jeu.duel();
			}
		}
		System.out.println("PARTIE TERMINEE, voulez-vous rejouer? O/N");
	    playAgain = clavier.next();
	    
	} while (playAgain.equalsIgnoreCase("O"));
	System.out.println("Merci d'avoir jouer ! A bientôt !!");
	}



	public void gameChoice() {
		clavier = new Scanner(System.in);
		try {
			System.out.println("Choix jeu \n1 - Recherche +/- \n2 - Mastermind \nA quel jeu souhaitez-vous jouer ?");
			nameGame = clavier.nextInt();
			if (nameGame == 1) {
				System.out.println("Vous avez choisi comme jeu : Recherche +/-");
			} else if (nameGame == 2) {
				System.out.println("Vous avez choisi comme jeu : Mastermind");
			} else {
				System.out.println("Vous n'avez pas choisi de jeu parmi les choix propos�s");
				throw new IllegalArgumentException();
			}
		} catch (Exception e) {
			this.gameChoice();
		}
	}

	public void modeChoice() {
		clavier = new Scanner(System.in);
		try {
			System.out.println("Veuillez choisir à présent le niveau du jeu selectionné : ");
			nbLevel = clavier.nextInt();

			switch (nbLevel) {
			case 1:
				System.out.println("Vous avez choisi le niveau : Challenger");
				break;
			case 2:
				System.out.println("Vous avez choisi le niveau : Défenseur");
				break;
			case 3:
				System.out.println("Vous avez choisi le niveau : Duel");
				break;
			default:
				System.out.println("Vous n'avez pas choisi de niveau parmi les choix proposés, veuillez réessayer :");
				throw new IllegalArgumentException();
			}

		} catch (Exception e) {
			this.modeChoice();
		}
	}

}


