package display;

import javax.swing.*;
import java.awt.*;

public class GifPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private Image img;

    public GifPanel(String img) {
        this.img = Toolkit.getDefaultToolkit().createImage(getClass().getResource(img));
    }

    public void paintComponent(Graphics g) {
        g.drawImage(this.img, 0, 0, this);
    }

}
