package UtterEng;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {


    private BufferedImage image;
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(image != null){
            g.drawImage(image, 0, 0, this);
        }
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
