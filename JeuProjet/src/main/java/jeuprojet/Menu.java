package jeuprojet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Scanner;

public class Menu {
	
	
	private int nameGame;
	private int nbLevel;

	static Scanner clavier = new Scanner(System.in);
	static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args)  {
		
		logger.info("Mode joueur activé !");
		String playAgain = "";
	do {
		Menu menu = new Menu();
		menu.gameChoice();
		menu.modeChoice();
		Jeux jeu; //on instancie une variable jeu sans connaitre le type et le mode de jeu
		// choix du jeu 
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
		System.out.println("Voulez-vous retourner au menu principal pour jouer à nouveau à un jeu ? Tapez O/N");
	    playAgain = clavier.next();
	    
	} while (playAgain.equalsIgnoreCase("O"));
	System.out.println("Merci d'avoir jouer ! A bientôt !!");
	}


	//choix du jeu
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
				System.out.println("Vous n'avez pas choisi de jeu parmi les choix proposés, veuillez recommencer en tapant 1 ou 2 !");
				throw new IllegalArgumentException();
			}
		} catch (Exception e) {
			logger.error("Erreur de choix de jeu !");
			this.gameChoice();
		}
	}

	//choix du mode de jeu
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
				System.out.println("Vous n'avez pas choisi de niveau parmi les choix proposés, veuillez réessayer ! ");
				System.out.println("Veuillez tapez 1 pour le mode Challenger, 2 pour le mode Defenseur ou 3 pour le mode Duel : ");
				throw new IllegalArgumentException();
			}

		} catch (Exception e) {
			logger.error("Erreur de choix du mode de jeu ! Merci de taper un chiffre entre 1 et 3 ! ");
			this.modeChoice();
		}
	}
	

}


