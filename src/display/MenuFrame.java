package display;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * THis class allows to create the GUI of the menus
 *
 * @author Castella Matéo
 * @version 1.0.0
 */

public class MenuFrame extends JFrame {

    private boolean frameAlreadyPaint = false;
    private Font japanFont;

    protected JPanel container = new JPanel();
    protected JTextField pn1 = new JTextField("Nom du joueur 1");
    protected JTextField pn2 = new JTextField("Nom du joueur 2");
    protected JLabel error = new JLabel("", SwingConstants.CENTER);
    protected static MenuGraphical mg;

    /**
     * This method allows to construct the GUI principal menu
     *
     * @param mg - The instance of the graphical menu
     */

    public void drawPrincipalMenu(MenuGraphical mg) {

        MenuFrame.mg = mg;

        this.container.removeAll();
        this.container.revalidate();
        this.container.repaint();

        // Background image

        if (!this.frameAlreadyPaint) {

            // custom font

            try {
                this.japanFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/data/fonts/ChineseDragon.ttf")).deriveFont(30f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(japanFont);
            } catch (IOException | FontFormatException e) {
                System.err.println(e.getMessage());
            }

            // background image

            this.setContentPane(new GifPanel("/data/images/zen_menu.gif"));
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/data/images/zenLogo.png")));
            //this.setContentPane(new ImagePanel(new ImageIcon("data/images/menu_resized.jpeg").getImage()));
            this.setLayout(new BorderLayout());
        }

        // Menu content

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

        JLabel recommanded = new JLabel("CASQUE RECOMMANDE", SwingConstants.CENTER);
        recommanded.setForeground(Color.WHITE);
        recommanded.setFont(this.japanFont);
        recommanded.setBackground(Color.BLACK);

        JButton play = new JButton("JOUER");
        play.setForeground(Color.WHITE);
        play.setBackground(Color.RED);
        play.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        play.setPreferredSize(new Dimension(600, 75));
        play.setFont(this.japanFont);
        play.setFocusPainted(false);

        JButton load = new JButton("CHARGER UNE PARTIE");
        load.setForeground(Color.WHITE);
        load.setBackground(Color.RED);
        load.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        load.setPreferredSize(new Dimension(600, 75));
        load.setFont(this.japanFont);
        load.setFocusPainted(false);

        JButton exit = new JButton("QUITTER");
        exit.setForeground(Color.WHITE);
        exit.setBackground(Color.RED);
        exit.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        exit.setPreferredSize(new Dimension(600, 75));
        exit.setFont(this.japanFont);
        exit.setFocusPainted(false);

        // Creating menu buttons listener

        BtnListener.addPrincipalMenuListener(play, mg, "play");
        BtnListener.addPrincipalMenuListener(load, mg, "load");
        BtnListener.addPrincipalMenuListener(exit, mg, "quit");

        // Adding menu buttons

        buttons.add(recommanded, gbc);
        buttons.add(Box.createVerticalStrut(150));
        buttons.add(play, gbc);
        buttons.add(load, gbc);
        buttons.add(Box.createVerticalStrut(150));
        buttons.add(exit, gbc);
        buttons.setOpaque(false);

        int top = (900 - buttons.getHeight() - 750) / 2;
        menuContent.setBorder(new EmptyBorder(top, 0, 0, 0));

        gbc.weighty = 1;
        menuContent.add(buttons, gbc);

        // Adding content to the frame

        menuContent.setOpaque(false);
        this.container.add(menuContent);
        this.container.setOpaque(false);

        if (!this.frameAlreadyPaint) {

            // Create frame

            this.add(this.container);
            this.setTitle("Zen l'Initié");
            this.setSize(1600, 900);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(true);

            this.frameAlreadyPaint = true;

        }

    }

    /**
     * This method allows to construct the GUI play menu
     *
     * @param mg - The instance of the graphical menu
     */

    public void drawPlayMenu(MenuGraphical mg) {

        this.container.removeAll();
        this.container.revalidate();
        this.container.repaint();

        // Menu content

        JPanel menuContent = new JPanel();

        menuContent.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Creating menu buttons

        JPanel buttons = new JPanel(new GridBagLayout());

        JButton playOne = new JButton("JOUER CONTRE L'ORDINATEUR");
        playOne.setForeground(Color.WHITE);
        playOne.setBackground(Color.RED);
        playOne.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        playOne.setPreferredSize(new Dimension(600, 75));
        playOne.setFont(this.japanFont);
        playOne.setFocusPainted(false);

        JButton playTwo = new JButton("JOUER A DEUX");
        playTwo.setForeground(Color.WHITE);
        playTwo.setBackground(Color.RED);
        playTwo.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        playTwo.setPreferredSize(new Dimension(600, 75));
        playTwo.setFont(this.japanFont);
        playTwo.setFocusPainted(false);

        JButton back = new JButton("RETOUR");
        back.setForeground(Color.WHITE);
        back.setBackground(Color.RED);
        back.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        back.setPreferredSize(new Dimension(600, 75));
        back.setFont(this.japanFont);
        back.setFocusPainted(false);

        JButton exit = new JButton("QUITTER");
        exit.setForeground(Color.WHITE);
        exit.setBackground(Color.RED);
        exit.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        exit.setPreferredSize(new Dimension(600, 75));
        exit.setFont(this.japanFont);
        exit.setFocusPainted(false);

        // Creating menu buttons listener

        BtnListener.addPlayMenuListener(playOne, mg, "playOne");
        BtnListener.addPlayMenuListener(playTwo, mg, "playTwo");
        BtnListener.addPlayMenuListener(back, mg, "back");
        BtnListener.addPlayMenuListener(exit, mg, "quit");

        // Adding menu buttons

        buttons.add(playOne, gbc);
        buttons.add(Box.createVerticalStrut(150));
        buttons.add(playTwo, gbc);
        buttons.add(back, gbc);
        buttons.add(Box.createVerticalStrut(150));
        buttons.add(exit, gbc);
        buttons.setOpaque(false);

        int top = (900 - buttons.getHeight() - 450) / 2;
        menuContent.setBorder(new EmptyBorder(top, 0, 0, 0));

        gbc.weighty = 1;
        menuContent.add(buttons, gbc);

        // Adding content to the frame

        menuContent.setOpaque(false);
        this.container.add(menuContent);
        this.container.setOpaque(false);

    }

    /**
     * This method allows to construct the GUI play menu
     *
     * @param mg - The instance of the graphical menu
     */

    public void drawLoadMenu(MenuGraphical mg) {

        this.container.removeAll();
        this.container.revalidate();
        this.container.repaint();

        // Menu content

        JPanel menuContent = new JPanel();

        menuContent.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Creating menu buttons

        JPanel buttons = new JPanel(new GridBagLayout());

        JLabel info = new JLabel("CLIQUEZ POUR CHARGER LA SAUVEGARDE", SwingConstants.CENTER);
        info.setForeground(Color.WHITE);
        info.setFont(this.japanFont);

        buttons.add(info, gbc);
        buttons.add(Box.createVerticalStrut(150));

        // list and display all saved game

        File file = new File("saved/");
        File[] files = file.listFiles();

        int i = 0;

        if (files != null && files.length != 0) {

            while (i < files.length) {

                String name = files[i].getName();
                String[] nameSplit = name.split("-");

                String date = nameSplit[0] + "/" + nameSplit[1] + "/" + nameSplit[2];
                String hour = nameSplit[3] + ":" + nameSplit[4] + ":" + nameSplit[5];

                String buttonText = date + " " + hour;

                JButton loadSave = new JButton((i+1) + ". Sauvegarde du " + buttonText);
                loadSave.setForeground(Color.WHITE);
                loadSave.setBackground(Color.RED);
                loadSave.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
                loadSave.setPreferredSize(new Dimension(600, 75));
                loadSave.setFont(new Font("Arial", Font.BOLD, 18));
                loadSave.setFocusPainted(false);

                BtnListener.addLoadSaveMenuListener(loadSave, mg, "loadSave", files, this);

                buttons.add(loadSave, gbc);
                if (i == 1 || i == 3) {
                    buttons.add(Box.createVerticalStrut(150));
                }

                i++;

            }

        }

        JButton back = new JButton("RETOUR");
        back.setForeground(Color.WHITE);
        back.setBackground(Color.RED);
        back.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        back.setPreferredSize(new Dimension(600, 75));
        back.setFont(this.japanFont);
        back.setFocusPainted(false);

        JButton exit = new JButton("QUITTER");
        exit.setForeground(Color.WHITE);
        exit.setBackground(Color.RED);
        exit.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        exit.setPreferredSize(new Dimension(600, 75));
        exit.setFont(this.japanFont);
        exit.setFocusPainted(false);

        // Creating menu buttons listener

        BtnListener.addLoadSaveMenuListener(back, mg, "back", files, this);
        BtnListener.addLoadSaveMenuListener(exit, mg, "quit", files, this);

        // Adding menu buttons


        buttons.add(back, gbc);
        if (files.length != 2 && files.length != 4) {
            buttons.add(Box.createVerticalStrut(150));
        }
        buttons.add(exit, gbc);
        buttons.setOpaque(false);

        int top = (900 - buttons.getHeight() - 850) / 2;
        menuContent.setBorder(new EmptyBorder(top, 0, 0, 0));

        gbc.weighty = 1;
        menuContent.add(buttons, gbc);

        // Adding content to the frame

        menuContent.setOpaque(false);
        this.container.add(menuContent);
        this.container.setOpaque(false);

    }

    /**
     * This method allows to construct the GUI Name menu
     *
     * @param  singlePlayer - true if the player will play against another player or false if he will play against the computer
     * @param mg - The instance of the graphical menu
     */

    public void drawNameMenu(Boolean singlePlayer, MenuGraphical mg) {

        this.container.removeAll();
        this.container.revalidate();
        this.container.repaint();

        this.error.setText("");

        // Menu content

        JPanel menuContent = new JPanel();

        menuContent.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Creating menu actions

        JPanel actions = new JPanel(new GridBagLayout());

        this.pn1.setPreferredSize(new Dimension(600, 75));
        this.pn1.setFont(this.japanFont);

        this.pn2.setPreferredSize(new Dimension(600, 75));
        this.pn2.setFont(this.japanFont);

        JButton start = new JButton("COMMENCER");
        start.setForeground(Color.WHITE);
        start.setBackground(Color.RED);
        start.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        start.setPreferredSize(new Dimension(600, 75));
        start.setFont(this.japanFont);
        start.setFocusPainted(false);

        JButton back = new JButton("RETOUR");
        back.setForeground(Color.WHITE);
        back.setBackground(Color.RED);
        back.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        back.setPreferredSize(new Dimension(600, 75));
        back.setFont(this.japanFont);
        back.setFocusPainted(false);

        JButton exit = new JButton("QUITTER");
        exit.setForeground(Color.WHITE);
        exit.setBackground(Color.RED);
        exit.setBorder(new MatteBorder(2, 2, 2, 2, Color.YELLOW));
        exit.setPreferredSize(new Dimension(600, 75));
        exit.setFont(this.japanFont);
        exit.setFocusPainted(false);

        this.error.setForeground(Color.RED);
        this.error.setFont(this.japanFont);

        // Creating menu buttons listener

        BtnListener.addChooseNameMenuListener(start, mg, "start", this, singlePlayer);
        BtnListener.addChooseNameMenuListener(back, mg, "back", this, singlePlayer);
        BtnListener.addChooseNameMenuListener(exit, mg, "quit", this, singlePlayer);

        // Adding menu buttons

        int top = 0;

        actions.add(this.pn1, gbc);
        actions.add(Box.createVerticalStrut(150));

        if (!singlePlayer) {
            actions.add(this.pn2, gbc);
        }

        actions.add(start, gbc);

        if (!singlePlayer) {
            actions.add(Box.createVerticalStrut(150));
            top = (900 - actions.getHeight() - 550) / 2;
        }

        actions.add(back, gbc);

        if (singlePlayer) {
            actions.add(Box.createVerticalStrut(150));
            top = (900 - actions.getHeight() - 450) / 2;
        }

        actions.add(exit, gbc);
        actions.add(Box.createVerticalStrut(150));
        actions.add(this.error, gbc);
        actions.setOpaque(false);

        menuContent.setBorder(new EmptyBorder(top, 0, 0, 0));

        gbc.weighty = 1;
        menuContent.add(actions, gbc);

        // Adding content to the frame

        menuContent.setOpaque(false);
        this.container.add(menuContent);
        this.container.setOpaque(false);

    }

}
