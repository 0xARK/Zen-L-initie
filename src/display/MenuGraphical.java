package display;

import zen.Zen;

/**
 * This class allows to display all the menus when the graphical version is loaded
 *
 * @author Castella Mateo
 * @version 1.0.0
 */

public class MenuGraphical {

    protected static Zen zen;
    protected static MenuFrame mf = new MenuFrame();

    /**
     * This method allows to display the homepage menu in the graphical version
     */

    public MenuGraphical(Zen z) {

        this.zen = z;
        principal();

    }

    /**
     * This method alllows to stop the rain sound when we return to the menu
     */

    public static void endGameSoudClip() {

        zen.endGameSound();

    }

    /**
     * This method allows to display the homepage menu in the graphical version
     */

    public void principal() {

        this.mf.drawPrincipalMenu(this);

    }

    /**
     * This method allows to display the play menu in the graphical version
     */

    protected void play() {

        this.mf.drawPlayMenu(this);

    }

    /**
     * This method allows to display the loader menu in the graphical version
     */

    protected void loadGame() {

        this.mf.drawLoadMenu(this);

    }

    /**
     * This method allows to quit the application
     */

    protected void quit() {

        System.exit(0);

    }

    /**
     * This method allows to return to the precedent menu
     *
     * @param returnTo - The menu to return
     */

    protected void returnTo(String returnTo) {

        if (returnTo.equals("principal")) {
            this.mf.drawPrincipalMenu(this);
        } else if (returnTo.equals("play")) {
            this.mf.drawPlayMenu(this);
        }

    }

    /**
     * This method allows to choose a name for the player(s)
     *
     * @param singlePlayer - true if the user choose to play against an auto player or false
     */

    protected void chooseNames(boolean singlePlayer) {

        this.mf.drawNameMenu(singlePlayer, this);

    }

    /**
     * This method allows to start a game
     *
     * @param singlePlayer - true if the user choose to play against an auto player or false
     * @param pn1 - the player name 1
     * @param pn2 - the player name 2
     */

    protected void start(boolean singlePlayer, String pn1, String pn2) {

        if (singlePlayer) {
            zen.startGame(pn1, "auto", singlePlayer);
        } else {
            zen.startGame(pn1, pn2, singlePlayer);
        }

    }

}