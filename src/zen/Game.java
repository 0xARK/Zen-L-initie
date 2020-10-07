package zen;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class allows to instantiate a zen game loop
 *
 * @author Castella Mateo
 * @version 1.0.0
 */

public class Game implements Serializable {

    private Square[][] grid;
    private Player current;
    private Player spectator;
    private Pawn zen;
    private boolean aligned = false;

    protected String playerName1;
    protected String playerName2;
    protected String savedFile = null;
    protected boolean graphical;

    /**
     * The constructor : it allows to create a new Game with the player's name
     *
     * @param playerName1 - The name of the player 1
     * @param playerName2 - The name of the player 2
     * @param graphical   - True if the user choose to play in graphical mode or false
     */

    public Game(String playerName1, String playerName2, boolean graphical, boolean singlePlayer) {

        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
        this.graphical = graphical;

        initializeGrid();

        if (singlePlayer) {

            this.current = new HumanPlayer(this.playerName1, this.grid, PawnType.BLACK, graphical, this, false);
            this.spectator = new AutoPlayer(this.playerName2, this.grid, PawnType.WHITE, graphical, this, true);

        } else {

            this.current = new HumanPlayer(playerName1, this.grid, PawnType.BLACK, graphical, this, false);
            this.spectator = new HumanPlayer(playerName2, this.grid, PawnType.WHITE, graphical, this, false);

        }

        this.current.myPawns.add(zen);
        this.spectator.myPawns.add(zen);

        start();

    }

    /**
     * This method allows to change the mode of the game to graphical mode
     */

    protected void setGraphical() {
        this.graphical = true;
        this.current.setGraphical();
        this.spectator.setGraphical();
    }

    /**
     * This method allows to change the mode of the game to console mode
     */

    protected void setConsole() {
        this.graphical = false;
        this.current.setConsole();
        this.spectator.setConsole();
    }

    /**
     * This method allows to stop the game, because we have a winner !
     */

    private void endOfGame() {

        if (this.savedFile != null) {

            File mySavedFile = new File("saved/" + this.savedFile);
            mySavedFile.delete();

            if (this.graphical) {

                this.current.GUIgraphical.win(this.current.name);

            } else {
                System.out.println("\033[1;31m" + "Bravo ! Le joueur " + this.current.name + " a gagné !" + "\033[0;37m");
            }

        }

    }

    /**
     * This method allows to save the current game
     */

    public boolean saveMyGame() {

        boolean saved = true;

        try {

            File file = new File("saved/");
            File[] files = file.listFiles();

            if (files != null && files.length == 5 && this.savedFile == null) {

                files[0].delete();

            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
            LocalDateTime now = LocalDateTime.now();
            String filename = dtf.format(now);

            if (this.savedFile != null) {

                File lastSave = new File("saved/" + this.savedFile);
                lastSave.delete();
                this.savedFile = filename;

            }

            SaveManager sm = new SaveManager();
            sm.saveGame(this, filename);

        } catch (IOException e) {

            saved = false;
            if (graphical) {

                System.err.println(e.getMessage());

            } else {

                this.current.GUIconsole.displayMsg("\033[1;31m" + "[-] Sauvegarde échouée : " + "\033[0;37m" + e.getMessage());

            }

        }

        return saved;

    }

    /**
     * This method allows to initialize the grid of the current game
     */

    private void initializeGrid() {

        this.grid = new Square[11][11];

        for (int i = 0; i < 11; i++) {

            for (int j = 0; j < 11; j++) {

                this.grid[i][j] = new Square(i, j);

            }

        }

        this.zen = new Pawn(5, 5, PawnType.ZEN);
        this.grid[5][5].setBusy();
        this.grid[5][5].setType(PawnType.ZEN);

    }

    /**
     * This method allows to change the current player
     */

    private void changePlayer() {

        Player temp = this.current;

        this.current = this.spectator;

        this.spectator = temp;

    }

    /**
     * This method allows to start the game loop : it allows to display messages to the user and allows to the current player to choose a pawn to deplace
     */

    public void start() {

        this.current.drawGrid();

        if (this.graphical) {

            String msg = "";

            if (this.current.zenIsMine) {

                if (this.current.type == PawnType.BLACK) {
                    msg += "Le pion Zen appartient à " + this.current.name + " (pions bleus)";
                } else if (this.current.type == PawnType.WHITE) {
                    msg += "Le pion Zen appartient à " + this.current.name + " (pions verts)";
                }

            } else if (this.spectator.zenIsMine) {

                if (this.spectator.type == PawnType.BLACK) {
                    msg += "Le pion Zen appartient à " + this.spectator.name + " (pions bleus)";
                } else if (this.spectator.type == PawnType.WHITE) {
                    msg += "Le pion Zen appartient à " + this.spectator.name + " (pions verts)";
                }

            } else {
                msg += "Le pion Zen n'appartient à personne";
            }

            if (this.current.type == PawnType.BLACK) {
                msg += "\nC'est au tour du joueur " + this.current.name + " (pions bleus)";
            } else if (this.current.type == PawnType.WHITE) {
                msg += "\nC'est au tour du joueur " + this.current.name + " (pions verts)";
            }

            this.current.GUIgraphical.displayMsg(msg);

        } else {

            if (this.current.zenIsMine) {

                if (this.current.type == PawnType.BLACK) {
                    this.current.GUIconsole.displayMsg("\n\033[1;34m" + "Le pion Zen appartient à " + this.current.name + " (pions B)" + "\033[0;37m");
                } else if (this.current.type == PawnType.WHITE) {
                    this.current.GUIconsole.displayMsg("\n\033[1;32m" + "Le pion Zen appartient à " + this.current.name + " (pions W)" + "\033[0;37m");
                }

            } else if (this.spectator.zenIsMine) {

                if (this.spectator.type == PawnType.BLACK) {
                    this.current.GUIconsole.displayMsg("\n\033[1;34m" + "Le pion Zen appartient à " + this.spectator.name + " (pions B)" + "\033[0;37m");
                } else if (this.spectator.type == PawnType.WHITE) {
                    this.current.GUIconsole.displayMsg("\n\033[1;32m" + "Le pion Zen appartient à " + this.spectator.name + " (pions W)" + "\033[0;37m");
                }

            } else {
                this.current.GUIconsole.displayMsg("\n\033[1;31m" + "Le pion Zen n'appartient à personne" + "\033[0;37m");
            }

            if (this.current.type == PawnType.BLACK) {
                this.current.GUIconsole.displayMsg("\033[1;34m" + "C'est au tour du joueur " + this.current.name + " (pions B)" + "\033[0;37m\n");
            } else if (this.current.type == PawnType.WHITE) {
                this.current.GUIconsole.displayMsg("\033[1;32m" + "C'est au tour du joueur " + this.current.name + " (pions W)" + "\033[0;37m\n");
            }

            this.current.GUIconsole.displayMsg("\033[1;37m" + "Saisissez 'x' à la place d'une coordonnée pour sauvegarder et quitter la partie" + "\033[0;37m\n");

        }

        int[] pawnSelected = this.current.selectPawn();

        if (!this.graphical || this.current.auto) {
            startStep2(pawnSelected);
        }

    }

    /**
     * This method is the second step of the game loop : the player choose the square where he want deplace his selected pawn
     * @param pawnSelected - the coordinates of the selected pawn
     */

    public void startStep2(int[] pawnSelected) {

        if (pawnSelected[0] == -1 && pawnSelected[1] == 0 && !graphical) {

            this.current.GUIconsole.displayMsg("\033[1;32m" + "[+] Sauvegarde de la partie en cours..." + "\033[0;37m");

            boolean quit = saveMyGame();

            if (quit) {

                System.out.println("\033[1;32m" + "[+] Sauvegarde réussie" + "\033[0;37m");
                System.exit(0);

            } else {

                this.current.GUIconsole.saveFail();

            }

        } else {

            if (this.current.selectedPawn.getType() == PawnType.ZEN) {
                this.current.zenIsMine = true;
                this.spectator.zenIsMine = false;
            }

            int[][] depPossible = this.current.placementPossible(pawnSelected[0], pawnSelected[1]);

            if (this.graphical) {
                this.current.GUIgraphical.drawGridWithPlacementPossible(depPossible, pawnSelected);
            }

            int[] squareSelected = this.current.selectSquare(depPossible);

            if (!this.graphical || this.current.auto) {
                startStep3(squareSelected);
            }

        }

    }

    /**
     * This method is the third stepp of the game loop : it allows to move the selected pawn on the selected square
     * @param squareSelected - the square where the player want to deplace the selected pawn
     */

    public void startStep3(int[] squareSelected) {

        if (squareSelected[0] == -1 && squareSelected[1] == 0 && !graphical) {

            this.current.GUIconsole.displayMsg("\033[1;32m" + "[+] Sauvegarde de la partie en cours..." + "\033[0;37m");

            boolean quit = saveMyGame();

            if (quit) {

                System.out.println("\033[1;32m" + "[+] Sauvegarde réussie" + "\033[0;37m");
                System.exit(0);

            } else {

                this.current.GUIconsole.saveFail();

            }

        } else {

            this.current.movePawn(squareSelected[0], squareSelected[1], this.spectator);

            this.current.verified = "";

            this.aligned = this.current.allAligned(squareSelected[1], squareSelected[0], this.current.type);

            startStep4();

        }

    }

    /**
     * This method is the end of the game loop : we change the current player and start is called if the pawns of the current player are aligned, else, we call endOfGame
     */

    private void startStep4() {

        if (!this.aligned) {

            changePlayer();
            start();

        } else {

            endOfGame();

        }

    }

}