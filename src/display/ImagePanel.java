package display;

import java.awt.*;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private Image img;

    public ImagePanel(Image img) {
        this.img = img;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(this.img, 0, 0, null);
    }
}