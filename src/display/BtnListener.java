package display;

import zen.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class allows to create listener and event when a button is clicked
 *
 * @author Castella Matéo
 * @version 1.0.0
 */

public class BtnListener {

    /**
     * This method allows to create all event when a button is clicked on the principal menu
     *
     * @param btn    - the btn clicked
     * @param mg     - The menuGraphical who allows to display the GUI
     * @param action - The action required by the user
     */

    public static void addPrincipalMenuListener(JButton btn, MenuGraphical mg, String action) {

        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                switch (action) {

                    case ("play"):
                        mg.play();
                        break;
                    case ("load"):
                        mg.loadGame();
                        break;
                    case ("quit"):
                        mg.quit();
                        break;
                    default:
                        break;

                }

            }
        });

    }

    /**
     * This method allows to create all event when a button is clicked on the play menu
     *
     * @param btn    - the btn clicked
     * @param mg     - The menuGraphical who allows to display the GUI
     * @param action - The action required by the user
     */

    public static void addPlayMenuListener(JButton btn, MenuGraphical mg, String action) {

        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                switch (action) {

                    case ("playOne"):
                        mg.chooseNames(true);
                        break;
                    case ("playTwo"):
                        mg.chooseNames(false);
                        break;
                    case ("back"):
                        mg.returnTo("principal");
                        break;
                    case ("quit"):
                        mg.quit();
                        break;
                    default:
                        break;

                }

            }
        });

    }

    /**
     * This method allows to create all event when a button is clicked on the play menu
     *
     * @param btn    - the btn clicked
     * @param mg     - The menuGraphical who allows to display the GUI
     * @param action - The action required by the user
     * @param files  - The list of saved files game
     */

    public static void addLoadSaveMenuListener(JButton btn, MenuGraphical mg, String action, File[] files, MenuFrame mf) {

        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                switch (action) {

                    case ("loadSave"):

                        mf.container.removeAll();
                        mf.setContentPane(new GifPanel("/data/images/tokyo_rain_resized.gif"));
                        mf.add(mf.container);
                        mf.container.revalidate();
                        mf.container.repaint();

                        int nb = Integer.parseInt(btn.getText().substring(0, 1)) - 1;
                        String fileToLoad = "saved/" + files[nb].getName();

                        SaveManager sm = new SaveManager();
                        sm.loadGame(fileToLoad, mg.zen);

                        break;
                    case ("back"):
                        mg.returnTo("principal");
                        break;
                    case ("quit"):
                        mg.quit();
                        break;
                    default:
                        break;

                }

            }
        });

    }

    /**
     * This method allows to create all event when a button is clicked on the name menu
     *
     * @param btn          - the btn clicked
     * @param mg           - The menuGraphical who allows to display the GUI
     * @param action       - The action required by the user
     * @param mf           - The menuFrame where errors will be displayed
     * @param singlePlayer - true if the game is launch in single player mode or false
     */

    public static void addChooseNameMenuListener(JButton btn, MenuGraphical mg, String action, MenuFrame mf, boolean singlePlayer) {

        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                switch (action) {

                    case ("start"):

                        String pn1 = null;
                        String pn2 = null;

                        if (!mf.pn1.getText().toUpperCase().equals("NOM DU JOUEUR 1")) {

                            String regex = "[a-zA-Z]{3,12}";
                            Pattern pattern = Pattern.compile(regex);
                            Matcher matcher = pattern.matcher(mf.pn1.getText());

                            if (matcher.matches()) {

                                mf.error.setText("");
                                pn1 = mf.pn1.getText();
                                if (singlePlayer) {

                                    mf.container.removeAll();
                                    mf.setContentPane(new GifPanel("/data/images/tokyo_rain_resized.gif"));
                                    mf.add(mf.container);
                                    mf.container.revalidate();
                                    mf.container.repaint();

                                    mg.start(true, pn1, pn2);
                                }
                            } else {
                                mf.error.setText("Le nom des joueurs doit\ncontenir entre 3 et 12 caractères");
                                break;
                            }

                        } else {
                            mf.error.setText("Veuillez choisir un nom de\njoueur valide");
                            break;
                        }

                        if (!singlePlayer && !mf.pn2.getText().toUpperCase().equals("NOM DU JOUEUR 2")) {

                            String regex = "[a-zA-Z]{3,12}";
                            Pattern pattern = Pattern.compile(regex);
                            Matcher matcher = pattern.matcher(mf.pn2.getText());

                            if (matcher.matches()) {
                                mf.error.setText("");
                                pn2 = mf.pn2.getText();

                                if (!pn1.equals(pn2)) {

                                    mf.container.removeAll();
                                    mf.setContentPane(new GifPanel("/data/images/tokyo_rain_resized.gif"));
                                    mf.add(mf.container);
                                    mf.container.revalidate();
                                    mf.container.repaint();

                                    mg.start(false, pn1, pn2);
                                } else {
                                    mf.error.setText("Veuillez choisir deux noms differents.");
                                }

                            } else {
                                mf.error.setText("Le nom des joueurs doit contenir entre 3 et 12 caractères.");
                                break;
                            }

                        } else {
                            mf.error.setText("Veuillez choisir un nom de joueur valide");
                            break;
                        }

                        break;

                    case ("back"):
                        mg.returnTo("play");
                        break;
                    case ("quit"):
                        mg.quit();
                        break;
                    default:
                        break;

                }

            }
        });

    }

    /**
     * This method allows to select a pawn in a JLabel square
     *
     * @param label - The JLabel to listen
     * @param g     - the game to continue if the pawn selected is valid
     * @param p     - the current player
     */

    public static void addPawnClickListener(JLabel label, Game g, Player p) {

        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (GameInterfaceGraphical.choosePawnValid) {

                    String[] yVal = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};

                    int x = -1;
                    int y = -1;

                    if (label.getText().length() == 2) {
                        x = Integer.parseInt(label.getText().substring(1));
                    } else if (label.getText().length() == 3) {
                        x = Integer.parseInt(label.getText().substring(1, 3));
                    }

                    for (int i = 0; i < 11; i++) {
                        if (label.getText().substring(0, 1).equals(yVal[i])) {
                            y = i;
                        }
                    }

                    if (GameInterfaceGraphical.grid[y][x].isBusy()) {

                        GameInterfaceGraphical.info.setText("");

                        if (GameInterfaceGraphical.grid[y][x].getType() == PawnType.ZEN || GameInterfaceGraphical.grid[y][x].getType() == GameInterfaceGraphical.actualPawnType) {

                            GameInterfaceGraphical.info.setText("");

                            int[] pawnSelected = new int[2];

                            pawnSelected[0] = x;
                            pawnSelected[1] = y;

                            for (Pawn pawn : p.myPawns) {
                                if (pawn.getX() == y && pawn.getY() == x) {
                                    p.selectedPawn = pawn;
                                }
                            }

                            g.startStep2(pawnSelected);

                        } else {

                            GameInterfaceGraphical.info.setText("Ce pion ne vous appartient pas");

                        }

                    } else {

                        GameInterfaceGraphical.info.setText("La case sélectionnée est vide.");

                    }

                }

            }
        });

    }

    /**
     * This method allows to select a pawn in a JLabel square
     *
     * @param label       - The JLabel to listen
     * @param g           - the game to continue if the square selected is valid
     * @param p           - the current player
     * @param depPossible - the list of deplacement possible
     */

    public static void addSquareClickListener(JLabel label, Game g, Player p, int[][] depPossible) {

        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (GameInterfaceGraphical.chooseSquareValid) {

                    String[] yVal = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};

                    int x = -1;
                    int y = -1;

                    if (label.getText().length() == 2) {
                        x = Integer.parseInt(label.getText().substring(1));
                    } else if (label.getText().length() == 3) {
                        x = Integer.parseInt(label.getText().substring(1, 3));
                    }

                    for (int i = 0; i < 11; i++) {
                        if (label.getText().substring(0, 1).equals(yVal[i])) {
                            y = i;
                        }
                    }

                    boolean isDepValid = false;
                    int k = 0;

                    while (k < depPossible.length && !isDepValid) {
                        if (depPossible[k][0] - 1 == x && depPossible[k][1] == y) {
                            isDepValid = true;
                        }
                        k++;
                    }

                    if (isDepValid) {

                        GameInterfaceGraphical.info.setText("");

                        int[] squareSelected = new int[2];

                        squareSelected[0] = x;
                        squareSelected[1] = y;

                        g.startStep3(squareSelected);

                    } else if (GameInterfaceGraphical.grid[y][x].isBusy() && GameInterfaceGraphical.grid[y][x].getType() == GameInterfaceGraphical.actualPawnType) {

                        GameInterfaceGraphical.info.setText("");

                        g.start();

                    } else {

                        GameInterfaceGraphical.info.setText("Vous ne pouvez pas déplacer votre pion ici.");

                    }

                }

            }
        });

    }

    /**
     * This method allows to save and quit a game
     *
     * @param btn - the button that allows to save
     * @param g   - the game to save
     */

    public static void addSaveGameListener(JButton btn, Game g) {

        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                boolean saved = g.saveMyGame();

                if (saved) {

                    MenuGraphical.endGameSoudClip();
                    MenuGraphical.mf.container.removeAll();
                    MenuGraphical.mf.setContentPane(new GifPanel("/data/images/zen_menu.gif"));
                    MenuGraphical.mf.add(MenuGraphical.mf.container);
                    MenuGraphical.mf.container.revalidate();
                    MenuGraphical.mf.container.repaint();

                    MenuFrame.mg.principal();

                } else {
                    GameInterfaceGraphical.info.setText("La sauvegarde a échouée.\nVérifiez que le dossier saved existe");
                }

            }
        });

    }

    public static void addReturnToHomeListener(JButton btn) {

        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                MenuGraphical.endGameSoudClip();
                MenuGraphical.mf.container.removeAll();
                MenuGraphical.mf.setContentPane(new GifPanel("/data/images/zen_menu.gif"));
                MenuGraphical.mf.add(MenuGraphical.mf.container);
                MenuGraphical.mf.container.revalidate();
                MenuGraphical.mf.container.repaint();

                MenuFrame.mg.principal();

            }
        });

    }

}