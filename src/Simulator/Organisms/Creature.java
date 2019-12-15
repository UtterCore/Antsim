package Simulator.Organisms;
import Simulator.*;
import Simulator.Entity.GameEntity;

import java.util.ArrayList;
import java.util.Random;

public abstract class Creature extends Organism {

    public static final float LEFT = -90;
    public static final float RIGHT = 90;
    public static final int CHOICE_TIMER_MAX = 50;
    private static final int SCAN_TIMER_MAX = 1;

    private ArrayList<GameEntity> visibleObjects;

    private boolean isDead;
    private int age;
    private Legs legs;
    private Eye eye;
    private Mouth mouth;

    private int scanTimer;
    private int choiceTimer;

    public Creature(Position position, Dimension dimension, String spritePath) {
        super(position, dimension, spritePath);

        legs = new Legs(this);
        eye = new Eye(this);
        mouth = new Mouth(this);

        setIsDead(false);
    }

    public ArrayList<GameEntity> getVisibleObjects() {
        return visibleObjects;
    }

    public void setVisibleObjects(ArrayList<GameEntity> visibleObjects) {
        this.visibleObjects = visibleObjects;
    }

    public Legs getLegs() {
        return legs;
    }

    public Eye getEye() {
        return eye;
    }

    public Mouth getMouth() {
        return mouth;
    }

    public void setIsDead(boolean b) {
        isDead = b;
        tag = "berry";
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }


    protected  ArrayList<GameEntity> useEye() {
        scanTimer--;
        if (scanTimer <= 0) {
            scanTimer = SCAN_TIMER_MAX;
            return getEye().getVisualData();
        } else {
            return getVisibleObjects();
        }
    }

    protected void wander() {


        boolean stay = false;

        //if creature is stuck choicetimer will be negative and
        //they will wait for a while to avoid spinning around
        if (choiceTimer < 0) {
            choiceTimer++;
            stay = true;
        }

        if (choiceTimer > 0) {
            choiceTimer--;
        }

        if (choiceTimer == 0) {
            Random random = new Random();

            int choice = random.nextInt(10);

            switch (choice) {
                case 1: {
                    getLegs().rotate(LEFT);
                    break;
                }
                case 2: {
                    getLegs().rotate(LEFT);
                    break;
                }
                case 3: {
                    getLegs().rotate(RIGHT);
                    break;
                }
                case 4: {
                    getLegs().rotate(RIGHT);
                    break;
                }
                case 5: {
                    getLegs().rotate(RIGHT);
                    getLegs().rotate(RIGHT);
                    break;
                }
                default: {
                    if (choice > 7) {
                        stay = true;
                    }
                    break;
                }
            }
            choiceTimer = CHOICE_TIMER_MAX;
        }

        if (!stay) {
            getLegs().moveForward();
        }
    }

    protected void watchOut() {
        if (getVisibleObjects() != null) {
            if (getVisibleObjects().size() > 0) {
                if (distanceTo(getVisibleObjects().get(0)) < (int)getDimension().getWidth()) {

                    if (getVisibleObjects().get(0).tag.equals("wall") || !getVisibleObjects().get(0).getIsTransparent()) {
                        getLegs().moveBackward();
                        getLegs().moveBackward();

                        //if cant move
                        choiceTimer = -10;
                        Random random = new Random();

                        if (random.nextBoolean()) {
                          //  getLegs().rotate(LEFT);
                        } else {
                            //getLegs().rotate(RIGHT);
                        }
                    }
                }
            }
        }
    }

    protected void creatureBehavior() {
    }

    protected void updateCreatureStatus() {

    }

    @Override
    public void update() {
        updateCreatureStatus();

        if (!getIsDead()) {
            creatureBehavior();
        }
    }
}
