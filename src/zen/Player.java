package zen;

import display.GameInterfaceConsole;
import display.GameInterfaceGraphical;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

/**
 * This class allows to create a player in the current game and allow it to make action
 *
 * @author Castella Mateo
 * @version 1.0.0
 */

public abstract class Player implements Serializable {

    public Pawn selectedPawn;
    public ArrayList<Pawn> myPawns;

    protected Square[][] actualGrid;
    protected PawnType type;
    protected String verified = "";
    protected boolean zenIsMine = false;

    protected String name;

    protected boolean graphical;
    protected GameInterfaceConsole GUIconsole = new GameInterfaceConsole();
    protected GameInterfaceGraphical GUIgraphical = new GameInterfaceGraphical();

    protected boolean auto;

    /**
     * The constructor : it allows to create a new player with his name and his pawn list
     *
     * @param name - The name of the player
     * @param grid - The grid of the current game
     */

    public Player(String name, Square[][] grid, PawnType type, boolean graphical, Game g, boolean auto) {

        this.name = name;
        this.type = type;
        this.graphical = graphical;
        this.actualGrid = grid;
        this.auto = auto;

        this.GUIgraphical.setGame(g);
        this.GUIgraphical.setPlayer(this);

        this.myPawns = new ArrayList<Pawn>();

        if (type == PawnType.BLACK) {

            int[][] initBlack = {
                    {0, 5}, {0, 10}, {2, 3}, {2, 7}, {4, 1}, {4, 9}, {6, 1}, {6, 9}, {8, 3}, {8, 7}, {10, 0}, {10, 5}
            };

            for (int i = 0; i < 12; i++) {
                Pawn p = new Pawn(initBlack[i][0], initBlack[i][1], PawnType.BLACK);
                this.actualGrid[initBlack[i][0]][initBlack[i][1]].setBusy();
                this.actualGrid[initBlack[i][0]][initBlack[i][1]].setType(PawnType.BLACK);
                this.myPawns.add(p);
            }

        } else if (type == PawnType.WHITE) {

            int[][] initWhite = {
                    {0, 0}, {1, 4}, {1, 6}, {3, 2}, {3, 8}, {5, 0}, {5, 10}, {7, 2}, {7, 8}, {9, 4}, {9, 6}, {10, 10}
            };

            for (int i = 0; i < 12; i++) {
                Pawn p = new Pawn(initWhite[i][0], initWhite[i][1], PawnType.WHITE);
                this.actualGrid[initWhite[i][0]][initWhite[i][1]].setBusy();
                this.actualGrid[initWhite[i][0]][initWhite[i][1]].setType(PawnType.WHITE);
                myPawns.add(p);
            }

        }

    }

    /**
     * This method allows to change the mode of the game to graphical mode
     */

    protected void setGraphical() {
        this.graphical = true;
    }

    /**
     * This method allows to change the mode of the game to console mode
     */

    protected void setConsole() {
        this.graphical = false;
    }

    /**
     * This method allows to move the selected pawn
     */

    protected abstract void movePawn(int newX, int newY, Player opponent);

    /**
     * This method allows to select a pawn
     */

    protected abstract int[] selectPawn();

    /**
     * This method allows to select a square
     */

    protected abstract int[] selectSquare(int[][] depPossible);

    /**
     * This method allows to know where we can move the selected pawn
     * @param x - the x coordinate of the pawn to move
     * @param y - the y coordinate of the pawn to move
     * @return an array with the coordinates of the squares where we can move the selected pawn
     */

    protected int[][] placementPossible(int x, int y) {

        int horizontalDep = 1;
        int verticalDep = 1;
        int diagonalDepRight = 1;
        int diagonalDepLeft = 1;

        int i = x - 1;

        // line left
        while (i >= 0) {

            if (this.actualGrid[y][i].isBusy()) {
                horizontalDep++;
            }

            i--;
        }

        i = x + 1;

        // line right
        while (i <= 10) {

            if (this.actualGrid[y][i].isBusy()) {
                horizontalDep++;
            }

            i++;
        }

        int j = y - 1;

        // column high
        while (j >= 0) {

            if (this.actualGrid[j][x].isBusy()) {
                verticalDep++;
            }

            j--;
        }

        j = y + 1;

        // column bottom
        while (j <= 10) {

            if (this.actualGrid[j][x].isBusy()) {
                verticalDep++;
            }

            j++;
        }

        i = x - 1;
        j = y - 1;

        // diagonal left high
        while (i >= 0 && j >= 0) {

            if (this.actualGrid[j][i].isBusy()) {
                diagonalDepLeft++;
            }

            i--;
            j--;
        }

        i = x + 1;
        j = y + 1;

        // diagonal left bottom
        while (i <= 10 && j <= 10) {

            if (this.actualGrid[j][i].isBusy()) {
                diagonalDepLeft++;
            }

            i++;
            j++;
        }

        i = x + 1;
        j = y - 1;

        // diagonal right high
        while (i <= 10 && j >= 0) {

            if (this.actualGrid[j][i].isBusy()) {
                diagonalDepRight++;
            }

            i++;
            j--;
        }

        i = x - 1;
        j = y + 1;

        // diagonal right bottom
        while (i >= 0 && j <= 10) {

            if (this.actualGrid[j][i].isBusy()) {
                diagonalDepRight++;
            }

            i--;
            j++;
        }

        int[][] deplacements = new int[8][2];

        for (int a = 0; a < deplacements.length; a++) {
            deplacements[a][0] = -5;
            deplacements[a][1] = -5;
        }

        // horizontal right
        if (x + horizontalDep <= 10 && this.actualGrid[y][x + horizontalDep].getType() != this.type) {

            boolean blocked = false;

            i = x;

            while (i < x + horizontalDep && !blocked) {

                if (this.actualGrid[y][i].isBusy() && this.actualGrid[y][i].getType() != this.type && this.actualGrid[y][i].getType() != PawnType.ZEN) {
                    blocked = true;
                } else if (this.actualGrid[y][i].isBusy() && this.actualGrid[y][i].getType() == PawnType.ZEN && !this.zenIsMine) {
                    blocked = true;
                }

                i++;

            }

            if (!blocked) {
                int newX = x + horizontalDep + 1;
                deplacements[0][0] = newX;
                deplacements[0][1] = y;
            }

        }

        // horizontal left
        if (x - horizontalDep >= 0 && this.actualGrid[y][x - horizontalDep].getType() != this.type) {

            boolean blocked = false;

            i = x;

            while (i > x - horizontalDep && !blocked) {

                if (this.actualGrid[y][i].isBusy() && this.actualGrid[y][i].getType() != this.type && this.actualGrid[y][i].getType() != PawnType.ZEN) {
                    blocked = true;
                } else if (this.actualGrid[y][i].isBusy() && this.actualGrid[y][i].getType() == PawnType.ZEN && !this.zenIsMine) {
                    blocked = true;
                }

                i--;

            }

            if (!blocked) {
                int newX = x - horizontalDep + 1;
                deplacements[1][0] = newX;
                deplacements[1][1] = y;
            }

        }

        // vertical bottom
        if (y + verticalDep <= 10 && this.actualGrid[y + verticalDep][x].getType() != this.type) {

            boolean blocked = false;

            j = y;

            while (j < y + verticalDep && !blocked) {

                if (this.actualGrid[j][x].isBusy() && this.actualGrid[j][x].getType() != this.type && this.actualGrid[j][x].getType() != PawnType.ZEN) {
                    blocked = true;
                } else if (this.actualGrid[j][x].isBusy() && this.actualGrid[j][x].getType() == PawnType.ZEN && !this.zenIsMine) {
                    blocked = true;
                }

                j++;

            }

            if (!blocked) {
                int newX = x + 1;
                int newY = y + verticalDep;
                deplacements[2][0] = newX;
                deplacements[2][1] = newY;
            }

        }

        // vertical high
        if (y - verticalDep >= 0 && this.actualGrid[y - verticalDep][x].getType() != this.type) {

            boolean blocked = false;

            j = y;

            while (j > y - verticalDep && !blocked) {

                if (this.actualGrid[j][x].isBusy() && this.actualGrid[j][x].getType() != this.type && this.actualGrid[j][x].getType() != PawnType.ZEN) {
                    blocked = true;
                } else if (this.actualGrid[j][x].isBusy() && this.actualGrid[j][x].getType() == PawnType.ZEN && !this.zenIsMine) {
                    blocked = true;
                }

                j--;

            }

            if (!blocked) {
                int newX = x + 1;
                int newY = y - verticalDep;
                deplacements[3][0] = newX;
                deplacements[3][1] = newY;
            }

        }

        // diagonal left high
        if (x - diagonalDepLeft >= 0 && y - diagonalDepLeft >= 0 && this.actualGrid[y - diagonalDepLeft][x - diagonalDepLeft].getType() != this.type) {

            boolean blocked = false;

            i = x;
            j = y;

            while (i > x - diagonalDepLeft && j > y - diagonalDepLeft && !blocked) {

                if (this.actualGrid[j][i].isBusy() && this.actualGrid[j][i].getType() != this.type && this.actualGrid[j][i].getType() != PawnType.ZEN) {
                    blocked = true;
                } else if (this.actualGrid[j][i].isBusy() && this.actualGrid[j][i].getType() == PawnType.ZEN && !this.zenIsMine) {
                    blocked = true;
                }

                i--;
                j--;

            }

            if (!blocked) {
                int newX = x - diagonalDepLeft + 1;
                int newY = y - diagonalDepLeft;
                deplacements[4][0] = newX;
                deplacements[4][1] = newY;
            }

        }

        // diagonal left bottom
        if (x + diagonalDepLeft <= 10 && y + diagonalDepLeft <= 10 && this.actualGrid[y + diagonalDepLeft][x + diagonalDepLeft].getType() != this.type) {

            boolean blocked = false;

            i = x;
            j = y;

            while (i < x + diagonalDepLeft && j < y + diagonalDepLeft && !blocked) {

                if (this.actualGrid[j][i].isBusy() && this.actualGrid[j][i].getType() != this.type && this.actualGrid[j][i].getType() != PawnType.ZEN) {
                    blocked = true;
                } else if (this.actualGrid[j][i].isBusy() && this.actualGrid[j][i].getType() == PawnType.ZEN && !this.zenIsMine) {
                    blocked = true;
                }

                i++;
                j++;

            }

            if (!blocked) {
                int newX = x + diagonalDepLeft + 1;
                int newY = y + diagonalDepLeft;
                deplacements[5][0] = newX;
                deplacements[5][1] = newY;
            }

        }

        // diagonal right high
        if (x + diagonalDepRight <= 10 && y - diagonalDepRight >= 0 && this.actualGrid[y - diagonalDepRight][x + diagonalDepRight].getType() != this.type) {

            boolean blocked = false;

            i = x;
            j = y;

            while (i < x + diagonalDepRight && j > y - diagonalDepRight && !blocked) {

                if (this.actualGrid[j][i].isBusy() && this.actualGrid[j][i].getType() != this.type && this.actualGrid[j][i].getType() != PawnType.ZEN) {
                    blocked = true;
                } else if (this.actualGrid[j][i].isBusy() && this.actualGrid[j][i].getType() == PawnType.ZEN && !this.zenIsMine) {
                    blocked = true;
                }

                i++;
                j--;

            }

            if (!blocked) {
                int newX = x + diagonalDepRight + 1;
                int newY = y - diagonalDepRight;
                deplacements[6][0] = newX;
                deplacements[6][1] = newY;
            }

        }

        // diagonal right bottom
        if (x - diagonalDepRight >= 0 && y + diagonalDepRight <= 10 && this.actualGrid[y + diagonalDepRight][x - diagonalDepRight].getType() != this.type) {

            boolean blocked = false;

            i = x;
            j = y;

            while (i > x - diagonalDepRight && j < y + diagonalDepRight && !blocked) {

                if (this.actualGrid[j][i].isBusy() && this.actualGrid[j][i].getType() != this.type && this.actualGrid[j][i].getType() != PawnType.ZEN) {
                    blocked = true;
                } else if (this.actualGrid[j][i].isBusy() && this.actualGrid[j][i].getType() == PawnType.ZEN && !this.zenIsMine) {
                    blocked = true;
                }

                i--;
                j++;

            }

            if (!blocked) {
                int newX = x - diagonalDepRight + 1;
                int newY = y + diagonalDepRight;
                deplacements[7][0] = newX;
                deplacements[7][1] = newY;
            }

        }

        return deplacements;

    }

    /**
     * This method allows to draw the grid after a move
     */

    protected void drawGrid() {

        if (this.graphical) {

            this.GUIgraphical.myGridInterface(this.actualGrid);

        } else {

            this.GUIconsole.myGridInterface(this.actualGrid);

        }

    }

    /**
     * This method allows to verify if all the pawns of a player are aligned
     *
     * @return true if all the pawns of the current player are aligned or false
     */

    protected boolean allAligned(int currentX, int currentY, PawnType playerPawnType) {

        boolean aligned = false;

        verified += currentX + "" + currentY + " ";

        if (currentX == 0 && currentY != 0 && currentY != 10) {

            if (!verified.contains((currentX) + "" + (currentY - 1) + " ") && this.actualGrid[currentX][currentY - 1].isBusy() && (this.actualGrid[currentX][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX, currentY - 1, playerPawnType);
            }

            if (!verified.contains((currentX) + "" + (currentY + 1) + " ") && this.actualGrid[currentX][currentY + 1].isBusy() && (this.actualGrid[currentX][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX, currentY + 1, playerPawnType);
            }

            if (!verified.contains((currentX + 1) + "" + (currentY - 1) + " ") && this.actualGrid[currentX + 1][currentY - 1].isBusy() && (this.actualGrid[currentX + 1][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX + 1][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY - 1, playerPawnType);
            }

            if (!verified.contains((currentX + 1) + "" + (currentY + 1) + " ") && this.actualGrid[currentX + 1][currentY + 1].isBusy() && (this.actualGrid[currentX + 1][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX + 1][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY + 1, playerPawnType);
            }

            if (!verified.contains((currentX + 1) + "" + (currentY) + " ") && this.actualGrid[currentX + 1][currentY].isBusy() && (this.actualGrid[currentX + 1][currentY].getType() == playerPawnType || (this.actualGrid[currentX + 1][currentY].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY, playerPawnType);
            }

        } else if (currentY == 0 && currentX != 0 && currentX != 10) {

            if (!verified.contains((currentX - 1) + "" + (currentY) + " ") && this.actualGrid[currentX - 1][currentY].isBusy() && (this.actualGrid[currentX - 1][currentY].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY, playerPawnType);
            }

            if (!verified.contains((currentX + 1) + "" + (currentY) + " ") && this.actualGrid[currentX + 1][currentY].isBusy() && (this.actualGrid[currentX + 1][currentY].getType() == playerPawnType || (this.actualGrid[currentX + 1][currentY].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY, playerPawnType);
            }

            if (!verified.contains((currentX - 1) + "" + (currentY + 1) + " ") && this.actualGrid[currentX - 1][currentY + 1].isBusy() && (this.actualGrid[currentX - 1][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY + 1, playerPawnType);
            }

            if (!verified.contains((currentX + 1) + "" + (currentY + 1) + " ") && this.actualGrid[currentX + 1][currentY + 1].isBusy() && (this.actualGrid[currentX + 1][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX + 1][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY + 1, playerPawnType);
            }

            if (!verified.contains((currentX) + "" + (currentY + 1) + " ") && this.actualGrid[currentX][currentY + 1].isBusy() && (this.actualGrid[currentX][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX, currentY + 1, playerPawnType);
            }

        } else if (currentX == 0 && currentY == 0) {

            if (!verified.contains((currentX + 1) + "" + (currentY) + " ") && this.actualGrid[currentX + 1][currentY].isBusy() && (this.actualGrid[currentX + 1][currentY].getType() == playerPawnType || (this.actualGrid[currentX + 1][currentY].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY, playerPawnType);
            }

            if (!verified.contains((currentX) + "" + (currentY + 1) + " ") && this.actualGrid[currentX][currentY + 1].isBusy() && (this.actualGrid[currentX][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX, currentY + 1, playerPawnType);
            }

            if (!verified.contains((currentX + 1) + "" + (currentY + 1) + " ") && this.actualGrid[currentX + 1][currentY + 1].isBusy() && (this.actualGrid[currentX + 1][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX + 1][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY + 1, playerPawnType);
            }

        } else if (currentX == 0 && currentY == 10) {

            if (!verified.contains((currentX) + "" + (currentY - 1) + " ") && this.actualGrid[currentX][currentY - 1].isBusy() && (this.actualGrid[currentX][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX, currentY - 1, playerPawnType);
            }

            if (!verified.contains((currentX + 1) + "" + (currentY) + " ") && this.actualGrid[currentX + 1][currentY].isBusy() && (this.actualGrid[currentX + 1][currentY].getType() == playerPawnType || (this.actualGrid[currentX + 1][currentY].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY, playerPawnType);
            }

            if (!verified.contains((currentX + 1) + "" + (currentY - 1) + " ") && this.actualGrid[currentX + 1][currentY - 1].isBusy() && (this.actualGrid[currentX + 1][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX + 1][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY - 1, playerPawnType);
            }

        } else if (currentX == 10 && currentY == 0) {

            if (!verified.contains((currentX - 1) + "" + (currentY) + " ") && this.actualGrid[currentX - 1][currentY].isBusy() && (this.actualGrid[currentX - 1][currentY].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY, playerPawnType);
            }

            if (!verified.contains((currentX) + "" + (currentY + 1) + " ") && this.actualGrid[currentX][currentY + 1].isBusy() && (this.actualGrid[currentX][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX, currentY + 1, playerPawnType);
            }

            if (!verified.contains((currentX - 1) + "" + (currentY + 1) + " ") && this.actualGrid[currentX - 1][currentY + 1].isBusy() && (this.actualGrid[currentX - 1][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY + 1, playerPawnType);
            }

        } else if (currentX == 10 && currentY != 0 && currentY != 10) {

            if (!verified.contains((currentX) + "" + (currentY - 1) + " ") && this.actualGrid[currentX][currentY - 1].isBusy() && (this.actualGrid[currentX][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX, currentY - 1, playerPawnType);
            }

            if (!verified.contains((currentX - 1) + "" + (currentY - 1) + " ") && this.actualGrid[currentX - 1][currentY - 1].isBusy() && (this.actualGrid[currentX - 1][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY - 1, playerPawnType);
            }

            if (!verified.contains((currentX - 1) + "" + (currentY) + " ") && this.actualGrid[currentX - 1][currentY].isBusy() && (this.actualGrid[currentX - 1][currentY].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY, playerPawnType);
            }

            if (!verified.contains((currentX - 1) + "" + (currentY + 1) + " ") && this.actualGrid[currentX - 1][currentY + 1].isBusy() && (this.actualGrid[currentX - 1][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY + 1, playerPawnType);
            }

            if (!verified.contains((currentX) + "" + (currentY + 1) + " ") && this.actualGrid[currentX][currentY + 1].isBusy() && (this.actualGrid[currentX][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX, currentY + 1, playerPawnType);
            }

        } else if (currentY == 10 && currentX != 0 && currentX != 10) {

            if (!verified.contains((currentX - 1) + "" + (currentY) + " ") && this.actualGrid[currentX - 1][currentY].isBusy() && (this.actualGrid[currentX - 1][currentY].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY, playerPawnType);
            }

            if (!verified.contains((currentX - 1) + "" + (currentY - 1) + " ") && this.actualGrid[currentX - 1][currentY - 1].isBusy() && (this.actualGrid[currentX - 1][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY - 1, playerPawnType);
            }

            if (!verified.contains((currentX) + "" + (currentY - 1) + " ") && this.actualGrid[currentX][currentY - 1].isBusy() && (this.actualGrid[currentX][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX, currentY - 1, playerPawnType);
            }

            if (!verified.contains((currentX + 1) + "" + (currentY - 1) + " ") && this.actualGrid[currentX + 1][currentY - 1].isBusy() && (this.actualGrid[currentX + 1][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX + 1][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY - 1, playerPawnType);
            }

            if (!verified.contains((currentX + 1) + "" + (currentY) + " ") && this.actualGrid[currentX + 1][currentY].isBusy() && (this.actualGrid[currentX][currentY].getType() == playerPawnType || (this.actualGrid[currentX][currentY].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY, playerPawnType);
            }

        } else if (currentX == 10 && currentY == 10) {

            if (!verified.contains((currentX) + "" + (currentY - 1) + " ") && this.actualGrid[currentX][currentY - 1].isBusy() && (this.actualGrid[currentX][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX, currentY - 1, playerPawnType);
            }

            if (!verified.contains((currentX - 1) + "" + (currentY) + " ") && this.actualGrid[currentX - 1][currentY].isBusy() && (this.actualGrid[currentX - 1][currentY].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY, playerPawnType);
            }

            if (!verified.contains((currentX - 1) + "" + (currentY - 1) + " ") && this.actualGrid[currentX - 1][currentY - 1].isBusy() && (this.actualGrid[currentX - 1][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY -1, playerPawnType);
            }

        } else {

            if (!verified.contains((currentX - 1) + "" + (currentY - 1) + " ") && this.actualGrid[currentX - 1][currentY - 1].isBusy() && (this.actualGrid[currentX - 1][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY - 1, playerPawnType);
            }

            if (!verified.contains((currentX) + "" + (currentY - 1) + " ") && this.actualGrid[currentX][currentY - 1].isBusy() && (this.actualGrid[currentX][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX, currentY - 1, playerPawnType);
            }

            if (!verified.contains((currentX + 1) + "" + (currentY - 1) + " ") && this.actualGrid[currentX + 1][currentY - 1].isBusy() && (this.actualGrid[currentX + 1][currentY - 1].getType() == playerPawnType || (this.actualGrid[currentX + 1][currentY - 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY - 1, playerPawnType);
            }

            if (!verified.contains((currentX + 1) + "" + (currentY) + " ") && this.actualGrid[currentX + 1][currentY].isBusy() && (this.actualGrid[currentX + 1][currentY].getType() == playerPawnType || (this.actualGrid[currentX + 1][currentY].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY, playerPawnType);
            }

            if (!verified.contains((currentX + 1) + "" + (currentY + 1) + " ") && this.actualGrid[currentX + 1][currentY + 1].isBusy() && (this.actualGrid[currentX + 1][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX + 1][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX + 1, currentY + 1, playerPawnType);
            }

            if (!verified.contains((currentX) + "" + (currentY + 1) + " ") && this.actualGrid[currentX][currentY + 1].isBusy() && (this.actualGrid[currentX][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX, currentY + 1, playerPawnType);
            }

            if (!verified.contains((currentX - 1) + "" + (currentY + 1) + " ") && this.actualGrid[currentX - 1][currentY + 1].isBusy() && (this.actualGrid[currentX - 1][currentY + 1].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY + 1].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY + 1, playerPawnType);
            }

            if (!verified.contains((currentX - 1) + "" + (currentY) + " ") && this.actualGrid[currentX - 1][currentY].isBusy() && (this.actualGrid[currentX - 1][currentY].getType() == playerPawnType || (this.actualGrid[currentX - 1][currentY].getType() == PawnType.ZEN && this.zenIsMine))) {
               
                aligned = allAligned(currentX - 1, currentY, playerPawnType);
            }

        }

        int spaceNb = 0;

        for (int i = 0; i < verified.length(); i++) {
            if (verified.charAt(i) == ' ') {
                spaceNb++;
            }
        }

        if (this.zenIsMine && verified.length() == 3*this.myPawns.size()) {
            aligned = true;
        } else if (!this.zenIsMine && verified.length() == 3*this.myPawns.size() - 3) {
            aligned = true;
        }

        return aligned;

    }

}