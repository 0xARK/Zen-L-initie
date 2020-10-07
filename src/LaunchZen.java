import zen.Zen;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * This class allows to launch the zen game
 *
 * @author Castella Mateo
 * @version 1.0.0
 */

public class LaunchZen {

    /**
     * The constructor : it allows to call the Zen constructor with the command line args
     *
     * @param args - the command line args
     */

    public static void main(String[] args) {

        if (args.length == 1) {

            boolean graphical = true;

            if (args[0].equals("c")) {
                graphical = false;
            }

            Zen zen = new Zen(graphical);

        } else {

            throw new IllegalArgumentException("\n[-] Usage : java -jar zen.jar c => launch the game in console mode\n"
                    + "[-] Usage : java -jar zen.jar g => launch the game in graphical mode");

        }

    }

}