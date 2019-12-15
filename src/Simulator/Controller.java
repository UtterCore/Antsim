package Simulator;

import Simulator.Entity.GameEntity;

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

    private BufferedImage screenImage;
    private Model model;
    private View view = new View();
    private ScheduledExecutorService gameModelExec;
    private boolean controlMouse;

    public Controller(Model model) {
        this.model = model;


        view.getGameWindow().addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.displayInfo(e.getX(), e.getY());
                controlMouse = !controlMouse;
            }
        });

        gameModelExec = Executors.newScheduledThreadPool(1);
        gameModelExec.scheduleAtFixedRate(update, 0, 10, TimeUnit.MILLISECONDS);
    }

    private Runnable update = new Runnable() {

        @Override
        public void run() {
            //drawToScreen(model.getCurrentImage());
            model.update();

            drawToScreen(model.getDrawables());

            SwingUtilities.invokeLater(()->view.refreshGUI(screenImage));
        }
    };

    private void drawToScreen(ArrayList<GameEntity> entities) {

        screenImage = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

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
