package Simulator.Organisms.Creatures;

import Simulator.*;
import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

import java.util.Random;


public class Ant extends Creature {
//TODO: döda myrors energi ska gå ner så de försvinner

    //Constants
    private static final int FEED_TIMER_MAX = 80;

    private static final int ANT_INITIAL_ENERGY = 30;


    private static final int DANCE_TIMER_MAX = 1000;

    //Timers
    private int feedTimer;
    private int danceTimer;

    private boolean shouldWait;



    private int id;
    private int searchPriority;



    public Ant(Position position) {
        super(position, new Dimension(20, 20), "./resources/antU.png");
        tag = "ant";

        id = World.ants++;

        setEnergy(ANT_INITIAL_ENERGY);

        searchPriority = 10;

        setAge(0);

        feedTimer = FEED_TIMER_MAX;
        danceTimer = DANCE_TIMER_MAX;

        setName("ant#" + id);

        MessageLog.getInstance().addMessage(getName() + " was born");

    }

    public Ant(Position position, String spritePath) {
        super(position, new Dimension(20, 20), spritePath);
        tag = "ant";

        id = World.ants++;

        setEnergy(ANT_INITIAL_ENERGY);
        currentBehavior = NONE;

        searchPriority = 10;

        setAge(0);

        feedTimer = FEED_TIMER_MAX;
        danceTimer = DANCE_TIMER_MAX;

        MessageLog.getInstance().addMessage(getName() + " was born");
    }


    public void dance() {
        danceTimer = 0;
    }

    public int getId() {
        return id;
    }




    public int getCurrentBehavior() {
        return currentBehavior;
    }




    @Override
    protected void deathTrigger() {
        super.deathTrigger();
        tag = "dead_ant";
        setSprite(loadSprite("./resources/antDeadU.png"));
    }

    @Override
    protected void agingTrigger() {
        if (getDimension().getWidth() != 20 + getAge()) {
            setDimension(new Dimension(20 + getAge(), 20 + getAge()));
        }

        Random random = new Random();

        if (random.nextInt(getAge()) >= 15) {

            if (random.nextBoolean()) {
                die();
                MessageLog.getInstance().addMessage(getName() + " died of age (" + getAge() + ")");
            }
        }
    }

    public void idle() {
        shouldWait = true;
    }

    private void mateWith(Ant partner) {

        MessageLog.getInstance().addMessage(getName() + " mated with " + partner.getName());
        partner.setHasChild(true);
        partner.setEnergy(partner.getEnergy() - ANT_INITIAL_ENERGY);
        setMatingTimer(MATING_TIMER_MAX);
        partner.setMatingTimer(MATING_TIMER_MAX);
    }

    void handleMating() {
        if (getMatingTimer() <= 0) {
            currentBehavior = MATE;
        }
    }

    void doMatingCall(Ant partner) {
        partner.idle();
    }


    /**
     * The ant can feed another ant
     */
    private void feed(Ant ant) {

        feedTimer--;

        if (feedTimer <= 0) {
            int amountToFeed = 0;
            amountToFeed = 10;

            ant.setEnergy(ant.getEnergy() + amountToFeed);
            setEnergy(getEnergy() - amountToFeed);


            MessageLog.getInstance().addMessage(getName() + " has fed " + ant.getName());

            feedTimer = FEED_TIMER_MAX;
        }
    }


    private void handleHunger() {

        if (getEnergy() < 60) {
            currentBehavior = EAT;
        }

        if (getEnergy() < 30) { //override all other desires
            currentBehavior = EAT;
            searchPriority = 7;
        }

        if (getEnergy() < 10) {
            die();
            MessageLog.getInstance().addMessage(getName() + " died of hunger");
        }
    }

    @Override
    protected void updateCreatureStatus() {
        currentBehavior = NONE;

        if (!getIsDead()) {
            if (getAge() > MATURITY_AGE) {
                handleMating();
            }
            handleHunger();
        }
    }

    @Override
    protected void creatureBehavior() {

        if (danceTimer < DANCE_TIMER_MAX) {
            getLegs().rotate(90);
            danceTimer++;
            return;
        }
        setVisibleObjects(useEye());

        if (shouldWait) {
            //if crisis, do not wait
            shouldWait = false;
            if (!isStarving()) {
                return;
            }
        }

        GameEntity wantedItem = null;
        for (GameEntity entity : getVisibleObjects()) {

            //feed
            if (!isHungry() && currentBehavior == NONE) {
                if (entity instanceof Ant && ((Ant) entity).isHungry()
                        && (!((Creature) entity).getIsDead())) {
                    wantedItem = entity;
                    currentBehavior = FEED;

                    if (entity instanceof Ant) {
                        doMatingCall((Ant) entity);
                    }
                }
                //if is hungry
            } else {
                if (entity instanceof Organism) {
                    if (currentBehavior == EAT) {
                        if (((Organism) entity).isEdible()) {
                            wantedItem = entity;
                            //  System.out.println(entity);
                        }
                    }
                }
            }

            if (currentBehavior == MATE) {
                if (entity instanceof Ant) {
                    if (((Ant) entity).canMate()) {
                    wantedItem = entity;
                        doMatingCall((Ant) entity);
                    }
                }
            }
        }

        if (wantedItem != null) {
            if (distanceTo(wantedItem) < getDimension().getWidth() + 10) {
            //if (collidesWith(wantedItem)) {

                if (wantedItem instanceof Organism
                        && currentBehavior == EAT
                        && ((Organism) wantedItem).isEdible()) {
                    eatItem((Organism) wantedItem);
                }
                if (wantedItem instanceof Ant) {
                    if (currentBehavior == FEED) {
                        feed((Ant) wantedItem);
                    }
                    if (currentBehavior == MATE) {
                        if (((Ant) wantedItem).canMate()) {
                            mateWith((Ant) wantedItem);
                        }
                    }
                }
                    //if doesnt collide, go towards
                } else {
                goTowards(wantedItem);
            }
        } else {
            wander();
        }

        oldWanted = wantedItem;
        watchOut();

        //TODO: FIX so they cant crash into things
        /*
        if (getTouch().getTouchedObjects().size() > 0) {
        setIsDead(true);
        }
        */
    }
}
