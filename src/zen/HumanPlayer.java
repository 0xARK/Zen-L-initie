package zen;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class allows to create a human player
 *
 * @author Castella Mateo
 * @version 1.0.0
 */

public class HumanPlayer extends Player implements Serializable {

    /**
     * The constructor : it allows to create a new human player with his name
     *
     * @param name - The name of the current player
     */

    public HumanPlayer(String name, Square[][] grid, PawnType type, boolean graphical, Game g, boolean auto) {

        super(name, grid, type, graphical, g, auto);

    }

    /**
     * This method allows to select a square where we want move the selected pawn
     *
     * @param depPossible - The deplacements possible for this pawn
     * @return the coordinates of the selected square
     */

    protected int[] selectSquare(int[][] depPossible) {

        String[] dep = new String[8];

        String[] yVal = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};

        for (int i = 0; i < depPossible.length; i++) {
            if (depPossible[i][0] != -5 && depPossible[i][1] != -5) {
                dep[i] = depPossible[i][0] + "" + yVal[depPossible[i][1]];
            }
        }

        int[] selectedSquare = new int[2];

        if (this.graphical) {

            selectedSquare = this.GUIgraphical.chooseSquare(dep);

        } else {

            selectedSquare = this.GUIconsole.chooseSquare(dep);

        }

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
        this.selectedPawn.setY(newX);

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

        int[] coordPawn = null;

        if (this.graphical) {

            coordPawn = this.GUIgraphical.choosePawn(this.type);

        } else {

            coordPawn = this.GUIconsole.choosePawn(this.type);

        }

        if (coordPawn[0] != -1) {

            if (this.actualGrid[coordPawn[1]][coordPawn[0]].getType() == PawnType.ZEN) {

                for (Pawn p : this.myPawns) {
                    if (p.getType() == PawnType.ZEN) {
                        this.selectedPawn = p;
                    }
                }

            } else {

                for (Pawn p : this.myPawns) {
                    if (coordPawn[1] == p.getX() && coordPawn[0] == p.getY()) {
                        this.selectedPawn = p;
                    }
                }

            }

        }

        return coordPawn;

    }

}