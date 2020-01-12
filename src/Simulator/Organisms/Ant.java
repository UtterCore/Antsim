package Simulator.Organisms;

import Simulator.*;
import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

import java.util.Random;


public class Ant extends Creature {
//TODO: döda myrors energi ska gå ner så de försvinner

    //Constants
    private static final int EAT_TIMER_MAX = 100;
    private static final int FEED_TIMER_MAX = 80;
    private static final int STARVE_TIMER_MAX = 120;

    private static final int AGE_TIMER_MAX = 1300;
    private static final int ANT_INITIAL_ENERGY = 30;

    private static final int MATING_TIMER_MAX = 3000;
    private static final int MATURITY_AGE = 3;

    //Timers
    private int ageTimer;
    private int matingTimer;
    private int eatTimer;
    private int feedTimer;
    private int starveTimer;

    private boolean shouldWait;
    private boolean hasChild;

    private String currentBehavior;
    private String soughtItem;

    private int id;
    private int searchPriority;



    public Ant(Position position) {
        super(position, new Dimension(20, 20), "./resources/antU.png");
        tag = "blep";

        id = World.ants++;

        setEnergy(ANT_INITIAL_ENERGY);
        currentBehavior = null;

        searchPriority = 10;

        setAge(0);

        ageTimer = AGE_TIMER_MAX;
        //choiceTimer = CHOICE_TIMER_MAX;
        matingTimer = MATING_TIMER_MAX;
        eatTimer = EAT_TIMER_MAX;
        feedTimer = FEED_TIMER_MAX;
        starveTimer = STARVE_TIMER_MAX;

        System.out.println("ant #" + id + " was born");

    }

    public int getId() {
        return id;
    }

    public boolean canMate() {

        return (!getIsDead() && getAge() > MATURITY_AGE && getEnergy() > 30 + 20);
    }


    public String getCurrentBehavior() {
        return currentBehavior;
    }

    public boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean b) {
        hasChild = b;
    }

    public int getMatingTimer() {
        return matingTimer;
    }

    public void setMatingTimer(int matingTimer) {
        this.matingTimer = matingTimer;
    }



    private void goTowards(GameEntity entity) {
        getLegs().moveForward();
    }

    private void handleAging() {

        if (getDimension().getWidth() != 20 + getAge()) {
            setDimension(new Dimension(20 + getAge(), 20 + getAge()));
        }

       // setSpeed(getMaxSpeed() / (getAge() + 1));
        ageTimer--;
        if (ageTimer <= 0) {

            Random random = new Random();

            setAge(getAge() + 1);
            ageTimer = AGE_TIMER_MAX;

            if (random.nextInt(getAge()) >= 10) {

                if (random.nextBoolean()) {
                    setIsDead(true);
                    System.out.println("ant #" + id + " died of age (" + getAge() + ")");
                    tag = "berry";
                }
            }
        }
    }

    @Override
    public void setIsDead(boolean b) {
        super.setIsDead(b);
        if (b) {
            setSprite(loadSprite("./resources/antDeadU.png"));
        }
    }

    public void idle() {
        shouldWait = true;
    }

    private void mateWith(Ant partner) {

        System.out.println("ant #" + id + " mated with ant #" + partner.id);
        partner.setHasChild(true);
        partner.setEnergy(partner.getEnergy() - ANT_INITIAL_ENERGY);
        setMatingTimer(MATING_TIMER_MAX);
        partner.setMatingTimer(MATING_TIMER_MAX);
    }

    void handleMating() {
        if (getMatingTimer() > 0) {
            setMatingTimer(getMatingTimer() - 1);
        }
        if (getMatingTimer() <= 0) {
            currentBehavior = "mate";
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


            System.out.println("ant #" + id + " has fed ant #" + ant.id);

            feedTimer = FEED_TIMER_MAX;
        }
    }

    private void eatItem(Organism entity) {
        eatTimer--;
        if (eatTimer <= 0) {

            getMouth().eatObject(entity);

            if (entity.getEnergy() <= 0) {
                soughtItem = null;
            }
            eatTimer = EAT_TIMER_MAX;
        }
    }

    void handleHunger() {

        starveTimer--;

        if (starveTimer < 0) {
            setEnergy(getEnergy() - 1);
            starveTimer = STARVE_TIMER_MAX;
        }

        if (getEnergy() < 60) {
            currentBehavior = "eat";
        }

        if (getEnergy() < 30) { //override all other desires
            currentBehavior = "eat";
            searchPriority = 7;
        }

        if (getEnergy() < 10) {
            setIsDead(true);
            System.out.println("ant #" + id + " died of hunger");
        }
    }

    @Override
    protected void updateCreatureStatus() {
        currentBehavior = null;

        if (!getIsDead()) {
            if (getAge() > MATURITY_AGE) {
                handleMating();
            }
            handleHunger();
            handleAging();
        }
    }

    @Override
    protected void creatureBehavior() {

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
            if (!isHungry() && currentBehavior == null) {
                if (entity instanceof Ant && ((Ant) entity).isHungry()
                        && (!((Creature) entity).getIsDead())) {
                    wantedItem = entity;
                    currentBehavior = "feed";

                    if (entity instanceof Ant) {
                        doMatingCall((Ant) entity);
                    }
                }
                //if is hungry
            } else {
                if (entity instanceof Organism) {
                    if (currentBehavior == "eat") {
                        if (((Organism) entity).isEdible()) {
                            wantedItem = entity;
                            //  System.out.println(entity);
                        }
                    }
                }
            }

            if (currentBehavior == "mate") {
                if (entity instanceof Ant) {
                    if (((Ant) entity).canMate()) {
                    wantedItem = entity;
                        doMatingCall((Ant) entity);
                    }
                }
            }
        }

        if (wantedItem != null) {
            if (collidesWith(wantedItem)) {

                if (wantedItem instanceof Organism
                        && currentBehavior == "eat"
                        && ((Organism) wantedItem).isEdible()) {
                    eatItem((Organism) wantedItem);
                }
                if (wantedItem instanceof Ant) {
                    if (currentBehavior.equals("feed")) {
                        feed((Ant) wantedItem);
                    }
                    if (currentBehavior == "mate") {
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
        watchOut();

        //TODO: FIX so they cant crash into things
        /*
        if (getTouch().getTouchedObjects().size() > 0) {
        setIsDead(true);
        }
        */
    }
}
