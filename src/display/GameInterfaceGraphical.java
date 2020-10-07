package display;

import zen.Game;
import zen.PawnType;
import zen.Player;
import zen.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * This class allows to create the game interface of the grid for the graphical version
 *
 * @author Castella Mateo
 * @version 1.0.0
 */

public class GameInterfaceGraphical implements Serializable {

    public static JTextArea info = new JTextArea("");

    protected static Square[][] grid;
    protected JTextArea gameStatus = new JTextArea("");
    protected static boolean choosePawnValid = false;
    protected static boolean chooseSquareValid = false;
    protected static PawnType actualPawnType;

    private Game g;
    private Player p;

    /**
     * The constructor : it allows to initialize the GUI in graphical mode
     */

    public GameInterfaceGraphical() {


    }

    /**
     * This method allows to set a game for this GUI interface
     *
     * @param g - the current game
     */

    public void setGame(Game g) {
        this.g = g;
    }

    public void setPlayer(Player p) {
        this.p = p;
    }

    /**
     * This method allows to display the grid in graphical mode
     *
     * @param grid - The grid to display
     */

    public void myGridInterface(Square[][] grid) {

        choosePawnValid = false;
        chooseSquareValid = false;

        GameInterfaceGraphical.grid = grid;

        JPanel frameContent = new JPanel(new BorderLayout());
        frameContent.setPreferredSize(new Dimension(1600, 850));
        frameContent.setOpaque(false);

        JPanel gridContent = new JPanel(new GridLayout(11, 11, 5, 5));
        gridContent.setPreferredSize(new Dimension(840, 820));
        gridContent.setOpaque(false);

        for (int i = 0; i < 11; i++) {

            for (int j = 0; j < 11; j++) {

                String[] yVal = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};

                JLabel myLabel = new JLabel(yVal[i] + "" + j);

                if (GameInterfaceGraphical.grid[i][j].isBusy() && GameInterfaceGraphical.grid[i][j].getType() == PawnType.WHITE) {
                    myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square_green.png")));
                } else if (GameInterfaceGraphical.grid[i][j].isBusy() && GameInterfaceGraphical.grid[i][j].getType() == PawnType.BLACK) {
                    myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square_blue.png")));
                } else if (GameInterfaceGraphical.grid[i][j].isBusy() && GameInterfaceGraphical.grid[i][j].getType() == PawnType.ZEN) {
                    myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square_zen.png")));
                } else {
                    myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square.png")));
                }

                myLabel.setForeground(new Color(255, 255, 255, 0));

                BtnListener.addPawnClickListener(myLabel, this.g, this.p);

                gridContent.add(myLabel);

            }

        }

        gridContent.setBorder(new EmptyBorder(5, 15, 0, 0));

        Font japanFont = null;

        try {
            japanFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/data/fonts/ChineseDragon.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(japanFont);
        } catch (IOException | FontFormatException e) {
            System.err.println(e.getMessage());
        }

        JPanel actionContent = new JPanel();
        actionContent.setLayout(new GridBagLayout());
        actionContent.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        BufferedImage myPicture = null;

        try {
            myPicture = ImageIO.read(getClass().getResourceAsStream("/data/images/zenLogo.png"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // Zen logo

        JPanel imageLogo = new JPanel();
        JLabel logo = new JLabel(new ImageIcon(myPicture));
        logo.setPreferredSize(new Dimension(300, 300));
        imageLogo.add(logo);
        imageLogo.setOpaque(false);

        gameStatus.setBackground(new Color(255, 255, 255, 150));
        gameStatus.setFont(new Font("Arial", Font.BOLD, 18));
        gameStatus.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        gameStatus.setPreferredSize(new Dimension(600, 75));
        gameStatus.setEditable(false);

        info.setBackground(new Color(255, 255, 255, 150));
        info.setFont(new Font("Arial", Font.BOLD, 18));
        info.setForeground(Color.RED);
        info.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        info.setPreferredSize(new Dimension(600, 75));
        info.setEditable(false);

        JButton saveAndQuit = new JButton("SAUVEGARDER ET QUITTER");
        saveAndQuit.setForeground(Color.WHITE);
        saveAndQuit.setBackground(Color.RED);
        saveAndQuit.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        saveAndQuit.setPreferredSize(new Dimension(600, 75));
        saveAndQuit.setFont(japanFont);
        saveAndQuit.setFocusPainted(false);

        BtnListener.addSaveGameListener(saveAndQuit, this.g);

        actionContent.add(imageLogo, gbc);
        actionContent.add(Box.createVerticalStrut(150));
        actionContent.add(gameStatus, gbc);
        actionContent.add(info, gbc);
        actionContent.add(Box.createVerticalStrut(150));
        actionContent.add(saveAndQuit, gbc);

        actionContent.setBorder(new EmptyBorder(0, 0, 0, 75));

        // add left and right panel to a global panel

        frameContent.add(gridContent, BorderLayout.WEST);
        frameContent.add(actionContent, BorderLayout.EAST);

        // add panel to the container, the container to the frame and repaint

        MenuGraphical.mf.container.removeAll();
        MenuGraphical.mf.container.add(frameContent);
        MenuGraphical.mf.add(MenuGraphical.mf.container);
        MenuGraphical.mf.container.revalidate();
        MenuGraphical.mf.container.repaint();

    }

    /**
     * This method allows to display the grid with the placement possible
     * @param depPossible - The list of all placement possible for the current selected pawn
     */

    public void drawGridWithPlacementPossible(int[][] depPossible, int[] pawnSelected) {

        choosePawnValid = false;
        chooseSquareValid = false;

        JPanel frameContent = new JPanel(new BorderLayout());
        frameContent.setPreferredSize(new Dimension(1600, 850));
        frameContent.setOpaque(false);

        JPanel gridContent = new JPanel(new GridLayout(11, 11, 5, 5));
        gridContent.setPreferredSize(new Dimension(840, 820));
        gridContent.setOpaque(false);

        for (int i = 0; i < 11; i++) {

            for (int j = 0; j < 11; j++) {

                String[] yVal = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};

                JLabel myLabel = new JLabel(yVal[i] + "" + j);

                boolean possible = false;

                if (grid[i][j].isBusy() && grid[i][j].getType() == PawnType.WHITE) {

                    for (int k = 0; k < depPossible.length; k++) {
                        if (depPossible[k][1] == i && depPossible[k][0] - 1 == j) {
                            possible = true;
                        }
                    }

                    if (possible) {
                        myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square_green_deplacement.png")));
                    } else if (pawnSelected[1] == i && pawnSelected[0] == j) {
                        myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square_green_selected.png")));
                    } else {
                        myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square_green.png")));
                    }

                } else if (grid[i][j].isBusy() && grid[i][j].getType() == PawnType.BLACK) {

                    for (int k = 0; k < depPossible.length; k++) {
                        if (depPossible[k][1] == i && depPossible[k][0] - 1 == j) {
                            possible = true;
                        }
                    }

                    if (possible) {
                        myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square_blue_deplacement.png")));
                    } else if (pawnSelected[1] == i && pawnSelected[0] == j) {
                        myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square_blue_selected.png")));
                    } else {
                        myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square_blue.png")));
                    }

                } else if (grid[i][j].isBusy() && grid[i][j].getType() == PawnType.ZEN) {

                    for (int k = 0; k < depPossible.length; k++) {
                        if (depPossible[k][1] == i && depPossible[k][0] - 1 == j) {
                            possible = true;
                        }
                    }

                    if (possible) {
                        myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square_zen_deplacement.png")));
                    } else if (pawnSelected[1] == i && pawnSelected[0] == j) {
                        myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square_zen_selected.png")));
                    } else {
                        myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square_zen.png")));
                    }

                } else {

                    for (int k = 0; k < depPossible.length; k++) {
                        if (depPossible[k][1] == i && depPossible[k][0] - 1 == j) {
                            possible = true;
                        }
                    }

                    if (possible) {
                        myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square_deplacement.png")));
                    } else {
                        myLabel.setIcon(new ImageIcon(getClass().getResource("/data/images/square.png")));
                    }

                }

                myLabel.setForeground(new Color(255, 255, 255, 0));

                BtnListener.addSquareClickListener(myLabel, this.g, this.p, depPossible);

                gridContent.add(myLabel);

            }

        }

        gridContent.setBorder(new EmptyBorder(5, 15, 0, 0));

        Font japanFont = null;

        try {
            japanFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/data/fonts/ChineseDragon.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(japanFont);
        } catch (IOException | FontFormatException e) {
            System.err.println(e.getMessage());
        }

        JPanel actionContent = new JPanel();
        actionContent.setLayout(new GridBagLayout());
        actionContent.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        BufferedImage myPicture = null;

        try {
            myPicture = ImageIO.read(getClass().getResourceAsStream("/data/images/zenLogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Zen logo

        JPanel imageLogo = new JPanel();
        JLabel logo = new JLabel(new ImageIcon(myPicture));
        logo.setPreferredSize(new Dimension(300, 300));
        imageLogo.add(logo);
        imageLogo.setOpaque(false);

        gameStatus.setBackground(new Color(255, 255, 255, 150));
        gameStatus.setFont(new Font("Arial", Font.BOLD, 18));
        gameStatus.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        gameStatus.setPreferredSize(new Dimension(600, 75));
        //gameStatus.setIcon(new ImageIcon("data/images/background_infos.png"));
        gameStatus.setEditable(false);

        info.setBackground(new Color(255, 255, 255, 150));
        info.setFont(new Font("Arial", Font.BOLD, 18));
        info.setForeground(Color.RED);
        info.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        info.setPreferredSize(new Dimension(600, 75));
        //info.setIcon(new ImageIcon("data/images/background_infos.png"));
        info.setEditable(false);

        JButton saveAndQuit = new JButton("SAUVEGARDER ET QUITTER");
        saveAndQuit.setForeground(Color.WHITE);
        saveAndQuit.setBackground(Color.RED);
        saveAndQuit.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        saveAndQuit.setPreferredSize(new Dimension(600, 75));
        saveAndQuit.setFont(japanFont);
        saveAndQuit.setFocusPainted(false);

        BtnListener.addSaveGameListener(saveAndQuit, this.g);

        actionContent.add(imageLogo, gbc);
        actionContent.add(Box.createVerticalStrut(150));
        actionContent.add(gameStatus, gbc);
        actionContent.add(info, gbc);
        actionContent.add(Box.createVerticalStrut(150));
        actionContent.add(saveAndQuit, gbc);

        actionContent.setBorder(new EmptyBorder(0, 0, 0, 75));

        // add left and right panel to a global panel

        frameContent.add(gridContent, BorderLayout.WEST);
        frameContent.add(actionContent, BorderLayout.EAST);

        // add panel to the container, the container to the frame and repaint

        MenuGraphical.mf.container.removeAll();
        MenuGraphical.mf.container.add(frameContent);
        MenuGraphical.mf.add(MenuGraphical.mf.container);
        MenuGraphical.mf.container.revalidate();
        MenuGraphical.mf.container.repaint();

    }

    /**
     * This method allows to display a message
     *
     * @param msg - the message to display
     */

    public void displayMsg(String msg) {

        this.gameStatus.setText(msg);

    }

    /**
     * This method allows to authorize to choose a square to place a pawn in the graphical mode
     * @param pt - the pawn type of the current player
     */

    public int[] choosePawn(PawnType pt) {

        int[] selectedPawn = new int[2];

        actualPawnType = pt;

        chooseSquareValid = false;

        choosePawnValid = true;

        return selectedPawn;

    }

    /**
     * This method allows to authorize to choose a square for move a pawn in the graphical mode
     * @param depPossible - the list of the coordinates where the deplacement is possible
     */

    public int[] chooseSquare(String[] depPossible) {

        int[] selectedSquare = new int[2];

        choosePawnValid = false;

        chooseSquareValid = true;

        return selectedSquare;

    }

    /**
     * This method allows to show the winner in graphical mode
     */

    public void win(String name) {

        Font japanFont = null;

        try {
            japanFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/data/fonts/ChineseDragon.ttf")).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(japanFont);
        } catch (IOException | FontFormatException e) {
            System.err.println(e.getMessage());
        }

        JPanel menuContent = new JPanel();

        menuContent.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Creating menu buttons

        BufferedImage myPicture = null;

        try {
            myPicture = ImageIO.read(getClass().getResourceAsStream("/data/images/zenLogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Zen logo

        JPanel imageLogo = new JPanel();
        JLabel logo = new JLabel(new ImageIcon(myPicture));
        logo.setPreferredSize(new Dimension(300, 300));
        imageLogo.add(logo);
        imageLogo.setOpaque(false);
        menuContent.add(imageLogo, gbc);

        JPanel buttons = new JPanel(new GridBagLayout());

        JLabel gratification = new JLabel("BRAVO", SwingConstants.CENTER);
        gratification.setForeground(Color.YELLOW);
        gratification.setFont(japanFont);

        JLabel winner = new JLabel("Le joueur " + name + " a gagné !", SwingConstants.CENTER);
        winner.setForeground(Color.YELLOW);
        winner.setFont(japanFont);

        JButton exit = new JButton("QUITTER");
        exit.setForeground(Color.WHITE);
        exit.setBackground(Color.RED);
        exit.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        exit.setPreferredSize(new Dimension(600, 75));
        exit.setFont(japanFont);
        exit.setFocusPainted(false);

        // Creating menu buttons listener

        BtnListener.addReturnToHomeListener(exit);

        // Adding menu buttons

        buttons.add(gratification, gbc);
        buttons.add(Box.createVerticalStrut(150));
        buttons.add(winner, gbc);
        buttons.add(exit, gbc);
        buttons.add(Box.createVerticalStrut(150));
        buttons.setOpaque(false);

        int top = (900 - buttons.getHeight() - 600) / 2;
        menuContent.setBorder(new EmptyBorder(top, 0, 0, 0));

        gbc.weighty = 1;
        menuContent.add(buttons, gbc);

        // Adding content to the frame

        menuContent.setOpaque(false);

        // add panel to the container, the container to the frame and repaint

        MenuGraphical.mf.container.removeAll();
        MenuGraphical.mf.container.add(menuContent);
        MenuGraphical.mf.add(MenuGraphical.mf.container);
        MenuGraphical.mf.container.revalidate();
        MenuGraphical.mf.container.repaint();

    }

}