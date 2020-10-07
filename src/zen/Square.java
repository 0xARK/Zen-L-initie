package zen;

import java.io.Serializable;

/**
 * This class allows to create all the squares of the grid of the zen game
 * @author Castella Mateo
 * @version 1.0.0
 */

public class Square implements Serializable {

	private int x;
	private int y;
	private boolean busy;
	private boolean placeMyPawnPossible;
	private PawnType type;

	/**
	 * The constructor : it allows to create a square with his coordinates
	 * @param x - The x coordinate of the current square
	 * @param y - The y coordinate of the current square
	 */

	public Square(int x, int y) {

		this.x = x;
		this.y = y;
		this.busy = false;
		this.type = null;

	}

	/**
	 * This method allows to set a square to busy
	 */

	protected void setBusy() {
		this.busy = true;
	}

	/**
	 * This method allows to set a square to free
	 */

	protected void setFree() {
		this.busy = false;
	}

	/**
	 * This method allows to set the type of the pawn on this square
	 * @param t - the pawn type, or null if the square is empty
	 */

	protected void setType(PawnType t) {
		this.type = t;
	}

	/**
	 * This method allows to get the type of the pawn that occupe this square
	 * @return The type of the pawn if the square is busy or null
	 */

	public PawnType getType() {
		return this.type;
	}

	/**
	 * This method allows to verify if a square is busy
	 * @return true if the current square is busy or false
	 */

	public boolean isBusy() {

		return busy;

	}

	/**
	 * This method allows to get the x coordinates of a square
	 * @return the x coordinate of the cuurent square
	 */

	protected int getX() {

		return 0;

	}

	/**
	 * This method allows to create the y coordinate of a square
	 * @return the y coordinate of the cuurent square
	 */

	protected int getY() {

		return 0;

	}

}