package zen;

import display.MenuConsole;
import display.MenuGraphical;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class allows to create a new zen game with the mode that the user have choose (console or graphical)
 *
 * @author Castella Mateo
 * @version 1.0.0
 */

public class Zen {

    private boolean graphical;
    private Clip gameMusicClip;
    private Clip gameSoundClip;

    /**
     * The constructor : it allows to display the menus
     *
     * @param graphical - True if the user choose to play in graphical mode or false
     */

    public Zen(boolean graphical) {

        this.graphical = graphical;

        startMusicGame();

        if (this.graphical) {

            MenuGraphical menu = new MenuGraphical(this);

        } else {

            MenuConsole menu = new MenuConsole(this);

        }

    }

    /**
     * This method allows to start the game music
     */

    private void startMusicGame() {

        try {

            InputStream audioSrc = getClass().getResourceAsStream("/data/sounds/zen_japan.wav");
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            this.gameMusicClip = clip;
            clip.open(audioIn);
            clip.start();
            clip.loop(-1);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            System.err.println(e.getMessage());

        }

    }

    /**
     * This method allows to start the game sound
     */

    private void startGameSound() {

        try {

            InputStream audioSrc = getClass().getResourceAsStream("/data/sounds/rain.wav");
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            this.gameSoundClip = clip;
            clip.open(audioIn);
            clip.start();
            clip.loop(-1);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            System.err.println(e.getMessage());

        }

    }

    /**
     * This method allows to stop the game sound
     */

    public void endGameSound() {

        this.gameSoundClip.stop();

    }

    /**
     * This method allows to start a game
     * @param pn1 - player name 1
     * @param pn2 - player name 2
     * @param singlePlayer - true if player want to play against the computer or false
     */

    public void startGame(String pn1, String pn2, boolean singlePlayer) {

        if (this.graphical) {
            startGameSound();
        }

        Game game = new Game(pn1, pn2, this.graphical, singlePlayer);

    }

    /**
     * This method allows to start a saved game
     * @param g - the game saved o start
     */

    protected void startSavedGame(Game g) {

        Game game = g;

        if (this.graphical) {
            startGameSound();
        }

        if (!this.graphical && g.graphical) {
            g.setConsole();
        } else if (this.graphical && !g.graphical) {
            g.setGraphical();
        }

        game.start();

    }

}