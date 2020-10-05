package UtterEng;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {

    private String path;
    private BufferedImage image;

    public Image(String path) {

        this.path = path;

        File f = new File(path);
        try {
            image = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getPath() {
        return path;
    }

    public BufferedImage getImage() {
        return image;
    }
}
