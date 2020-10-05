package UtterEng;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageBank {

    private static ImageBank imageBank;
    private ArrayList<Image> images;

    private ImageBank() {
        images = new ArrayList<>();
    }

    public static ImageBank getInstance() {
        if (imageBank == null) {
            imageBank = new ImageBank();
        }
        return imageBank;
    }

    public boolean spriteExists(String spritePath) {
        for (Image image : images) {
            if (image.getPath().equals(spritePath)) {
                return true;
            }
        }
        return false;
    }
    public void createSprite(String spritePath) {
        images.add(new Image(spritePath));
        System.out.println("Creating sprite " + spritePath);
    }

    public BufferedImage getSprite(String spritePath) {
        for (Image image : images) {
            if (image.getPath().equals(spritePath)) {
                return image.getImage();
            }
        }
        return null;
    }
}
