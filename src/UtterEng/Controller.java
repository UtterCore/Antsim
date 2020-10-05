package UtterEng;

import Simulator.*;
import Simulator.Organisms.Creatures.Creature;
import Simulator.Organisms.Creatures.Organism;
import org.jibble.pircbot.IrcException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    private int screenWidth;
    private int screenHeight;

    private boolean showDebug = false;

    private long fpsCounter;

    private BufferedImage screenImage;
    private View view;
    private boolean controlMouse;
    public Controller(Model model, int screenWidth, int screenHeight) {

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
        };

        Runnable updateView = () -> {

            //drawToScreen(model.getDrawables());
            drawToScreen(World.entities);

            SwingUtilities.invokeLater(()->view.refreshGUI(screenImage));
        };

        Runnable updateFPSCounter = () -> {
            fpsCounter = model.fps;
        };

        ScheduledExecutorService gameModelExec;
        gameModelExec = Executors.newScheduledThreadPool(3);
        gameModelExec.scheduleAtFixedRate(update, 0, 1000/100, TimeUnit.MILLISECONDS);
        gameModelExec.scheduleAtFixedRate(updateView, 0, 1000/100, TimeUnit.MILLISECONDS);
        gameModelExec.scheduleAtFixedRate(updateFPSCounter, 0, 1, TimeUnit.SECONDS);

        initTwitchBot(model);
    }

    private void initTwitchBot(Model model) {

        File passFile = new File("./src/twitchpass.txt");
        String pass = null;

        try {
            Scanner scanner = new Scanner(passFile);
            pass = scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        String channelName = "#utterc_bot";
        TwitchBot bot = new TwitchBot(this, model);

        bot.setVerbose(true);

        // Connect to the IRC server.

        try {
            bot.connect("irc.twitch.tv", 6667, pass);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IrcException e) {
            e.printStackTrace();
        }
        bot.setVerbose(true);

        // Join the #pircbot channel.
        bot.joinChannel(channelName);
    }

    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }


    private void drawHitbox(Graphics2D g, GameEntity entity) {
        g.fill3DRect((int)entity.getPosition().getX(), (int)entity.getPosition().getY(),
                entity.getDimension().getWidth(), entity.getDimension().getHeight(), true);
    }

    private void drawWanted(Graphics2D g, GameEntity entity) {


        if (entity instanceof Creature) {
            Creature creature = (Creature) entity;
            if (creature.getOldWanted() != null) {
                g.drawLine((int) creature.getCenterPosition().getX(), (int) creature.getCenterPosition().getY(),
                        (int) creature.getOldWanted().getCenterPosition().getX(), (int) creature.getOldWanted().getCenterPosition().getY());
            }
        }
    }
    private void drawTag(Graphics2D g, GameEntity entity) {

        drawString(g, entity.tag, (int)entity.getPosition().getX(), (int)entity.getPosition().getY() - 35);
    }

    private void drawName(Graphics2D g, Creature creature) {

        String name = creature.getName();
        if (creature.getIsDead()) {
            name = name + " (dead)";
        }
        drawString(g, name, (int)creature.getPosition().getX(), (int)creature.getPosition().getY() - 20);
    }

    private void drawEnergy(Graphics2D g, GameEntity entity) {

        if (entity instanceof Organism) {
            Organism organism = (Organism)entity;
            drawString(g, Integer.toString(organism.getEnergy()), (int) organism.getPosition().getX(), (int) organism.getPosition().getY() + (int) organism.getDimension().getHeight());

        }
    }

    public void toggleDebug() {
        showDebug = !showDebug;
    }

    public void drawEyeScan(Graphics2D g, GameEntity entity) {
        if (entity instanceof Creature) {
            Creature creature = (Creature) entity;
            if (creature.getEye() == null) {
                return;
            }
            if (creature.getEye().getEyeScan() == null) {
                return;
            }
            g.drawLine((int)creature.getCenterPosition().getX(), (int)creature.getCenterPosition().getY(),
                    (int)creature.getEye().getEyeScan().getX(), (int)creature.getEye().getEyeScan().getY());


            g.drawLine((int)creature.getCenterPosition().getX(), (int)creature.getCenterPosition().getY(),
                    (int)creature.getEye().getEye2Scan().getX(), (int)creature.getEye().getEye2Scan().getY());
        }
    }

    private void drawFPSCounter(Graphics2D g) {
        drawString(g, "FPS: " + Long.toString(fpsCounter), 40, 40);
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

            if (entity instanceof Creature) {
                if (!((Creature) entity).getName().equals("default")) {
                    drawName(g, (Creature) entity);
                }
            }

            if (showDebug) {
                drawHitbox(g, entity);
                drawTag(g, entity);
                drawWanted(g, entity);
                drawEyeScan(g, entity);
                drawEnergy(g, entity);
            }
            drawFPSCounter(g);

            drawString(g, MessageLog.getInstance().getMessagesString(), screenWidth - 200, 40);



            g.rotate(Math.toRadians((int)entity.getRotation()), (int)entity.getCenterPosition().getX(), (int)entity.getCenterPosition().getY());


            if (entity.getIsBackground()) {
                g.drawImage(entity.getSprite(), (int)entity.getPosition().getX(),
                        (int)entity.getPosition().getY(), entity.getDimension().getWidth(), entity.getDimension().getHeight(), null);
            } else {
                g.drawImage(entity.getSprite(), (int)entity.getCenterPosition().getX() - (entity.getDimension().getWidth() / 2),
                        (int)entity.getCenterPosition().getY() - (entity.getDimension().getHeight() / 2), entity.getDimension().getWidth(),
                        entity.getDimension().getHeight(),null);

            }

            g.dispose();
        }
    }

}
