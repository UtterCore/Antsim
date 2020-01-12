package UtterEng;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    private int screenWidth;
    private int screenHeight;

    private BufferedImage screenImage;
    private View view;
    private boolean controlMouse;

    public Controller(UModel model, int screenWidth, int screenHeight) {

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        //create new game window with correct dimensions
        view = new View(screenWidth, screenHeight);

        //setting the mouse listener
        view.getGameWindow().addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.click(e.getX(), e.getY());
                controlMouse = !controlMouse;
            }
        });

        //the game update loop
        Runnable update = () -> {
            model.update();

            drawToScreen(model.getDrawables());

            SwingUtilities.invokeLater(()->view.refreshGUI(screenImage));
        };

        ScheduledExecutorService gameModelExec;
        gameModelExec = Executors.newScheduledThreadPool(1);
        gameModelExec.scheduleAtFixedRate(update, 0, 10, TimeUnit.MILLISECONDS);
    }

    /**
     * Uses the sprites, positions, dimensions and rotations of
     * a list of game entities and outputs them on the screen.
     *
     * @param entities a list of drawable game entities
     */
    private void drawToScreen(ArrayList<GameEntity> entities) {

        screenImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);

        if (entities == null) {
            return;
        }
        for (GameEntity entity : entities) {

            Graphics2D g = (Graphics2D)screenImage.getGraphics();

            g.rotate(Math.toRadians((int)entity.getRotation()), entity.getPosition().getX(), entity.getPosition().getY());

            if (entity.getIsBackground()) {
                g.drawImage(entity.getSprite(), (int)entity.getPosition().getX(),
                        (int)entity.getPosition().getY(), entity.getDimension().getWidth(), entity.getDimension().getHeight(), null);
            } else {
                g.drawImage(entity.getSprite(), (int)entity.getPosition().getX() - (entity.getDimension().getWidth() / 2),
                        (int)entity.getPosition().getY() - (entity.getDimension().getHeight() / 2), entity.getDimension().getWidth(),
                        entity.getDimension().getHeight(),null);

            }

            g.dispose();
        }
    }

}
