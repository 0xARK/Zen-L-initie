package zen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class allows to create an automatic player
 * @author Castella Mateo
 * @version 1.0.0
 */

public class AutoPlayer extends Player implements Serializable {

	/**
	 * The constructor : it allows to create a new automatic player with his name
	 * @param name - the name of the current player
	 */

	public AutoPlayer(String name, Square[][] grid, PawnType type, boolean graphical, Game g, boolean auto) {

		super(name, grid, type, graphical, g, auto);

	}

	/**
	 * This method allows to select a square where we want move the selected pawn
	 * @param depPossible - The deplacements possible for this pawn
	 * @return the coordinates of the selected square
	 */

	protected int[] selectSquare(int[][] depPossible) {

		int[] selectedSquare = new int[2];
		selectedSquare[0] = -5;
		selectedSquare[1] = -5;

		Random r = new Random();

		do {

			int randomInt = r.nextInt(depPossible.length);

			selectedSquare[0] = depPossible[randomInt][0];
			selectedSquare[1] = depPossible[randomInt][1];

		} while (selectedSquare[0] == -5 && selectedSquare[1] == -5);

		return selectedSquare;

	}

	/**
	 * This method allows to move the selected pawn
	 * @param newX - the new x coordinate of the selected pawn
	 * @param newY - the new y coordinate of the selected pawn
	 * @param opponent - the opponent of the current player
	 */

	protected void movePawn(int newX, int newY, Player opponent) {

		this.actualGrid[this.selectedPawn.getX()][this.selectedPawn.getY()].setFree();
		this.actualGrid[this.selectedPawn.getX()][this.selectedPawn.getY()].setType(null);

		this.selectedPawn.setX(newY);
		this.selectedPawn.setY(newX - 1);

		if (this.actualGrid[this.selectedPawn.getX()][this.selectedPawn.getY()].isBusy()) {

			ArrayList<Pawn> opponentPawns = new ArrayList<Pawn>();

			for (int i = 0; i < opponent.myPawns.size(); i++) {

				opponentPawns.add(opponent.myPawns.get(i));

			}

			for (Pawn p : opponentPawns) {

				if (p.getX() == this.selectedPawn.getX() && p.getY() == this.selectedPawn.getY()) {

					opponent.myPawns.remove(p);

				}

			}

		}

		if (this.actualGrid[this.selectedPawn.getX()][this.selectedPawn.getY()].getType() == PawnType.ZEN) {

			ArrayList<Pawn> playerPawns = new ArrayList<Pawn>();

			for (int i = 0; i < this.myPawns.size(); i++) {

				playerPawns.add(this.myPawns.get(i));

			}

			for (Pawn p : playerPawns) {

				if (p.getType() == PawnType.ZEN) {

					this.myPawns.remove(p);
					this.zenIsMine = false;
					opponent.zenIsMine = false;

				}

			}

		}

		this.actualGrid[this.selectedPawn.getX()][this.selectedPawn.getY()].setBusy();
		this.actualGrid[this.selectedPawn.getX()][this.selectedPawn.getY()].setType(this.selectedPawn.getType());

	}

	/**
	 * This method allows to select a pawn
	 */

	protected int[] selectPawn() {

		int[] coordPawn = new int[2];

		Random r = new Random();

		int randomInt = r.nextInt(this.myPawns.size());

		Pawn p = this.myPawns.get(randomInt);

		this.selectedPawn = p;

		coordPawn[0] = p.getY();
		coordPawn[1] = p.getX();

		return coordPawn;

	}

}