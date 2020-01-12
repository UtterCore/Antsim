package UtterEng;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameEntity {
    private Position position;
    private Dimension dimension;
    private BufferedImage sprite;
    private float rotation;
    private boolean isBackground;
    private boolean isTransparent;

    public String tag;

    public GameEntity(Position position, Dimension dimension, String spritePath) {
        tag = "unassigned";

        this.position = position;
        this.dimension = dimension;
        this.sprite = loadSprite(spritePath);
        rotation = 0;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public boolean collidesWith(GameEntity entity) {

        if (this == entity) {
            return false;
        }

        if (getPosition().getX() + getDimension().getWidth() >= entity.getPosition().getX()
                && getPosition().getX()  <= entity.getPosition().getX() + entity.getDimension().getWidth()
                && getPosition().getY() + getDimension().getHeight() >= entity.getPosition().getY()
                && getPosition().getY() <= entity.getPosition().getY() + entity.getDimension().getHeight()
                ) {
            return true;
        } else {
            return false;
        }
    }

    public Position getCenterPosition() {

        return new Position(
                getPosition().getX() + (float)getDimension().getWidth() / 2,
                getPosition().getY() + (float)getDimension().getHeight() / 2);
    }

    public double distanceTo(GameEntity entity) {

        if (entity == null) {
            return 0;
        }
        return (Math.hypot(getCenterPosition().getX() - entity.getCenterPosition().getX(),
                getCenterPosition().getY() - entity.getCenterPosition().getY()));

    }



    public boolean getIsTransparent() {
        return isTransparent;
    }

    public void setIsTransparent(boolean b) {
        isTransparent = b;
    }

    public void setIsBackground(boolean b) {
        isBackground = b;
    }

    public boolean getIsBackground() {
        return isBackground;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {

        while (rotation >= 360) {
            rotation -= 360;
        }

        while (rotation < 0) {
            rotation += 360;
        }

        this.rotation = rotation;
    }

    public BufferedImage loadSprite(String spritePath) {

        BufferedImage sprite = null;

        File f = new File(spritePath);
        try {
            sprite = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sprite;
    }
}
