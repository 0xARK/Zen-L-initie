package display;

import zen.PawnType;
import zen.Square;

import java.io.Serializable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class allows to create the game interface of the grid for the console version
 * @author Castella Mateo
 * @version 1.0.0
 */

public class GameInterfaceConsole implements Serializable {

	private Square[][] grid;

	/**
	 * The constructor : it allows to initialize the GUI in console mode
	 */

	public GameInterfaceConsole() {



	}

	/**
	 * This method allows to display the grid in console mode
	 * @param grid - The actual grid of the game with the current pawns placements
	 */
	
	public void myGridInterface(Square[][] grid) {

		/*for (int clear = 0; clear < 50; clear++) {
			System.out.println();
		}*/

		this.grid = grid;

		System.out.println("\033[0;37m");
		System.out.println("    1   2   3   4   5   6   7   8   9  10  11");
		System.out.println("  --------------------------------------------");

		for (int line = 0; line < 11; line++) {

			String[] lineNb = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};
			String drawLine = lineNb[line] + " |";

			for (int col = 0; col < 11; col++) {

				if (this.grid[line][col].isBusy() && this.grid[line][col].getType() == PawnType.WHITE) {
					drawLine += "\033[1;32m" + " W " + "\033[0;37m" + "|";
				} else if (this.grid[line][col].isBusy() && this.grid[line][col].getType() == PawnType.BLACK) {
					drawLine += "\033[1;34m" + " B " + "\033[0;37m" + "|";
				} else if (this.grid[line][col].isBusy() && this.grid[line][col].getType() == PawnType.ZEN) {
					drawLine += "\033[1;31m" + " Z " + "\033[0;37m" + "|";
				} else {
					drawLine += "   |";
				}

			}

			System.out.println(drawLine + " " + lineNb[line]);
			System.out.println("  --------------------------------------------");

		}

		System.out.println("    1   2   3   4   5   6   7   8   9  10  11");

	}

	/**
	 * This method allows to display a message
	 *
	 * @param msg - the message to display
	 */

	public void displayMsg(String msg) {

		System.out.println(msg);

	}

	/**
	 * This method allows to choose a pawn to move in the console mode
	 */

	public int[] choosePawn(PawnType pt) {

		String cord = null;

		int[] selectedPawn = new int[2];

		boolean exit = false;

		int x = -1;
		int y = -1;

		do {

			Scanner in = new Scanner(System.in);
			System.out.println("Choisissez un pion à déplacer :");
			cord = in.nextLine();

			String regex = "[1-9][a-kA-K]";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(cord);

			String regex2 = "[1][0-1][a-kA-K]";
			Pattern pattern2 = Pattern.compile(regex2);
			Matcher matcher2 = pattern2.matcher(cord);

			String regex3 = "[x]";
			Pattern pattern3 = Pattern.compile(regex3);
			Matcher matcher3 = pattern3.matcher(cord);

			boolean l = true;

			String[] yVal = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};

			if (cord.length() == 2 && matcher.matches()) {

				x = Integer.parseInt(cord.substring(0,1)) - 1;
				String yString = cord.substring(1).toUpperCase();

				for (int i = 0; i < yVal.length; i++) {
					if (yVal[i].equals(yString)) {
						y = i;
					}
				}

			} else if (cord.length() == 3 && matcher2.matches()) {

				x = Integer.parseInt(cord.substring(0,2)) - 1;
				String yString = cord.substring(2).toUpperCase();

				for (int i = 0; i < yVal.length; i++) {
					if (yVal[i].equals(yString)) {
						y = i;
					}
				}

			} else if (cord.length() == 1 && matcher3.matches()) {

				exit = true;

			} else {
				l = false;
			}

			if (!exit && (!l || x == -1 || y == -1)) {

				System.err.println("[-] Erreur - Veuillez entrer une coordonnée au format XY (exemple : 9A)");
				cord = null;
				x = -1;
				y = -1;

			} else if (!exit && !grid[y][x].isBusy()) {

				System.err.println("[-] Erreur - Aucun pion n'est sur cette case");
				cord = null;
				x = -1;
				y = -1;

			} else if (!exit && pt != this.grid[y][x].getType() && this.grid[y][x].getType() != PawnType.ZEN) {

				System.err.println("[-] Erreur - Ce pion ne vous appartient pas");
				cord = null;
				x = -1;
				y = -1;

			}

		} while (cord == null);

		if (exit) {

			selectedPawn[0] = -1;

		} else {

			selectedPawn[0] = x;
			selectedPawn[1] = y;

		}

		return selectedPawn;

	}

	/**
	 * This method allows to choose a square for place a pawn in the console mode
	 */

	public int[] chooseSquare(String[] depPossible) {

		int[] selectedSquare = new int[2];

		int newX = -1;
		int newY = -1;
		String cord = null;

		boolean exit = false;

		do {

			System.out.print("Déplacements possibles : ");

			for (int i = 0; i < depPossible.length; i++) {
				if (depPossible[i] != null) {
					System.out.print(depPossible[i] + " ");
				}
			}

			System.out.println();

			Scanner in = new Scanner(System.in);
			System.out.println("Choisissez la case où vous souhaitez déplacer votre pion :");
			cord = in.nextLine();

			String regex = "[1-9][a-kA-K]";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(cord);

			String regex2 = "[1][0-1][a-kA-K]";
			Pattern pattern2 = Pattern.compile(regex2);
			Matcher matcher2 = pattern2.matcher(cord);

			String regex3 = "[x]";
			Pattern pattern3 = Pattern.compile(regex3);
			Matcher matcher3 = pattern3.matcher(cord);

			if (matcher.matches() || matcher2.matches() || matcher3.matches()) {

				int j = 0;
				boolean depValid = false;

				if (cord.length() == 1) {

					exit = true;

				}

				while (j < depPossible.length && !depValid && !exit) {
					if (cord.toUpperCase().equals(depPossible[j])) {

						depValid = true;

						String[] yVal = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};

						if (depPossible[j].length() == 2) {

							newX = Integer.parseInt(depPossible[j].substring(0, 1)) - 1;
							String yString = depPossible[j].substring(1);

							for (int i = 0; i < yVal.length; i++) {
								if (yVal[i].equals(yString)) {
									newY = i;
								}
							}

						} else if (depPossible[j].length() == 3) {

							newX = Integer.parseInt(depPossible[j].substring(0, 2)) - 1;
							String yString = depPossible[j].substring(2);

							for (int i = 0; i < yVal.length; i++) {
								if (yVal[i].equals(yString)) {
									newY = i;
								}
							}

						}

					}
					j++;
				}

				if (!exit && !depValid) {
					System.err.println("[-] Erreur - Vous ne pouvez pas déplacer votre pion sur cette case." +
							" Choisissez une case présente dans la liste des déplacements possibles");
					cord = null;
				}

			} else if (!exit) {

				System.err.println("[-] Erreur - Veuillez entrer une coordonnée au format XY (exemple : 9A)");
				cord = null;

			}

		} while (cord == null);

		if (exit) {

			selectedSquare[0] = -1;

		} else {

			selectedSquare[0] = newX;
			selectedSquare[1] = newY;

		}

		return selectedSquare;

	}

	/**
	 * This method allows to know if the player want to exit without save the game or want to continue to play when the save fail
	 */

	public void saveFail() {

		System.out.println("Que souhaitez-vous faire ?\n");

		System.out.println("1. Continuer la partie");
		System.out.println("2. Quitter sans sauvegarder\n");

		int action = 0;

		do {

			Scanner in = new Scanner(System.in);
			System.out.println("Choisissez une action :");
			String input = in.nextLine();

			String regex = "[1-2]";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(input);

			if (!matcher.matches()) {

				action = 0;
				System.err.println("[-] Erreur - Veuillez choisir une action valide");

			} else {

				action = Integer.parseInt(input);

			}

		} while (action == 0);

		if (action == 2) {
			System.exit(0);
		}

	}

}