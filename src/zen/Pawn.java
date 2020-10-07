package zen;

import java.io.Serializable;

/**
 * This class allows to create the pawns of a player
 *
 * @author Castella Mateo
 * @version 1.0.0
 */

public class Pawn implements Serializable {

    private final PawnType type;
    private int x;
    private int y;

    /**
     * This method allows to create a new pawn with his coordinates
     *
     * @param x - The x coordinate of the current pawn
     * @param y - The y coordinate of the current pawn
     */

    public Pawn(int x, int y, PawnType type) {

        this.x = x;
        this.y = y;
        this.type = type;

    }

    /**
     * This method allows to modify the x coordinate of the current pawn
     */

    protected void setX(int x) {

        this.x = x;

    }

    /**
     * This method allows to modify the y coordinate of the current pawn
     */

    protected void setY(int y) {

        this.y = y;

    }

    /**
     * This method allows to get the x coordinate of the current pawn
     *
     * @return the x coordinate of the current pawn
     */

    public int getX() {

        return this.x;

    }

    /**
     * This method allows to get the y coordinate of the current pawn
     *
     * @return the y coordinate of the current pawn
     */

    public int getY() {

        return this.y;

    }

    /**
     * This method allows to get the type of a pawn
     *
     * @return the type of the current pawn
     */

    public PawnType getType() {

        return this.type;

    }
}