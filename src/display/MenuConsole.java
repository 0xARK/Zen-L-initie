package display;

import zen.SaveManager;
import zen.Zen;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class allows to display all the menus when the console version is loaded
 *
 * @author Castella Mateo
 * @version 1.0.0
 */

public class MenuConsole {

    private Zen zen;

    /**
     * The constructor : it allows to display the principal menu
     */

    public MenuConsole(Zen z) {

        this.zen = z;
        principal();

    }

    /**
     * This method allows to display the homepage menu in the console version
     */

    public void principal() {

        System.out.println("Que souhaitez-vous faire ?\n");

        System.out.println("1. Jouer");
        System.out.println("2. Charger une partie");
        System.out.println("3. Quitter\n");

        int action = 0;

        do {

            Scanner in = new Scanner(System.in);
            System.out.println("Choisissez une action :");
            String input = in.nextLine();

            String regex = "[1-3]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);

            if (!matcher.matches()) {

                action = 0;
                System.err.println("[-] Erreur - Veuillez choisir une action valide");

            } else {

                action = Integer.parseInt(input);

            }

        } while (action == 0);

        if (action == 1) {
            play();
        } else if (action == 2) {
            loadGame();
        } else if (action == 3) {
            quit();
        }

    }

    /**
     * This method allows to display the play menu in the console version
     */

    private void play() {

        System.out.println("Que souhaitez-vous faire ?\n");

        System.out.println("1. Jouer à deux");
        System.out.println("2. Jouer contre l'ordinateur");
        System.out.println("3. Revenir en arrière");
        System.out.println("4. Quitter\n");

        int action = 0;

        do {

            Scanner in = new Scanner(System.in);
            System.out.println("Choisissez une action :");
            String input = in.nextLine();

            String regex = "[1-4]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);

            if (!matcher.matches()) {

                action = 0;
                System.err.println("[-] Erreur - Veuillez choisir une action valide");

            } else {

                action = Integer.parseInt(input);

            }

        } while (action == 0);

        if (action == 1) {
            chooseNameAndStart(false);
        } else if (action == 2) {
            chooseNameAndStart(true);
        } else if (action == 3) {
            returnTo("principal");
        } else if (action == 4) {
            quit();
        }

    }

    /**
     * This method allows to display the loader menu in the console version
     */

    private void loadGame() {

        System.out.println("Choisissez la partie à charger :\n");

        File file = new File("saved/");
        File[] files = file.listFiles();

        int i = 0;

        if (files != null && files.length != 0) {

            while (i < files.length) {

                String name = files[i].getName();
                String[] nameSplit = name.split("-");

                String date = nameSplit[0] + "/" + nameSplit[1] + "/" + nameSplit[2];
                String hour = nameSplit[3] + ":" + nameSplit[4] + ":" + nameSplit[5];

                System.out.println((i + 1) + ". Charger la partie sauvegardée le " + date + " à " + hour);

                i++;

            }

        } else {
            System.out.println("Aucune partie à charger.\nSauvegardez des parties et elles s'afficheront ici.");
        }

        System.out.println((i + 1) + ". Revenir en arrière");
        i++;
        System.out.println((i + 1) + ". Quitter\n");

        int action = 0;

        do {

            Scanner in = new Scanner(System.in);
            System.out.println("Choisissez une action :");
            String input = in.nextLine();

            int range = i + 1;

            String regex = "[1-" + range + "]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);

            if (!matcher.matches()) {

                action = 0;
                System.err.println("[-] Erreur - Veuillez choisir une action valide");

            } else {

                action = Integer.parseInt(input);

            }

        } while (action == 0);

        if (action == i + 1) {

            quit();

        } else if (action == i) {

            returnTo("principal");

        } else {

            int nb = action - 1;
            String fileToLoad = "saved/" + files[nb].getName();

            SaveManager sm = new SaveManager();
            sm.loadGame(fileToLoad, this.zen);

        }

    }

    /**
     * This method allows to quit the application
     */

    private void quit() {

        System.exit(0);

    }

    /**
     * This method allows to return to the precedent menu
     *
     * @param returnTo - The menu to return
     */

    private void returnTo(String returnTo) {

        if (returnTo.equals("principal")) {

            principal();

        }

    }

    /**
     * This method allows to choose a name for the player(s) and start a game
     *
     * @param singlePlayer - True if the user choos to play against an auto player or false
     * @return true if the user start a game or false if he cancel
     */

    private void chooseNameAndStart(boolean singlePlayer) {

        if (singlePlayer) {

            String name = null;

            do {

                Scanner in = new Scanner(System.in);
                System.out.println("Choisissez un nom :");
                name = in.nextLine();

                String regex = "[a-zA-Z]{3,12}";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(name);

                if (!matcher.matches()) {

                    name = null;
                    System.err.println("[-] Erreur - Veuillez choisir un nom valide composé de 3 à 12 caractères");

                }

            } while (name == null);

            zen.startGame(name, "auto", true);

        } else {

            String nameOne = null;

            do {

                Scanner in = new Scanner(System.in);
                System.out.println("Choisissez un nom (joueur 1) :");
                nameOne = in.nextLine();

                String regex = "[a-zA-Z]{3,12}";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(nameOne);

                if (!matcher.matches()) {

                    nameOne = null;
                    System.err.println("[-] Erreur - Veuillez choisir un nom valide composé de 3 à 12 lettres");

                }

            } while (nameOne == null);

            String nameTwo = null;

            do {

                Scanner in = new Scanner(System.in);
                System.out.println("Choisissez un nom (joueur 2) :");
                nameTwo = in.nextLine();

                String regex = "[a-zA-Z]{3,12}";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(nameTwo);

                if (!matcher.matches()) {

                    nameTwo = null;
                    System.err.println("[-] Erreur - Veuillez choisir un nom valide composé de 3 à 12 lettres");

                } else if (nameOne.toUpperCase().equals(nameTwo.toUpperCase())) {

                    nameTwo = null;
                    System.err.println("[-] Erreur - Veuillez choisir un nom différent de celui du premier joueur");

                }

            } while (nameTwo == null);

            zen.startGame(nameOne, nameTwo, false);

        }

    }

}