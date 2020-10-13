package Simulator.Organisms.Creatures;
import Simulator.*;
import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

import java.util.ArrayList;
import java.util.Random;

public abstract class Creature extends Organism {

    public static final int NONE = -1;
    public static final int EAT = 1;
    public static final int FEED = 2;
    public static final int MATE = 3;
    public static final int WAIT = 4;

    public static final float LEFT = -90;
    public static final float RIGHT = 90;
    public static final int CHOICE_TIMER_MAX = 50;
    private static final int SCAN_TIMER_MAX = 4;
    private static final int AGE_TIMER_MAX = 1300;
    public static final int EAT_TIMER_MAX = 100;
    public static final int STARVE_TIMER_MAX = 120;
    public static final int MATING_TIMER_MAX = 3000;

    public static final int MATURITY_AGE = 3;

    private ArrayList<GameEntity> visibleObjects;

    private boolean isDead;
    private int age;
    private Legs legs;
    private Eye eye;
    private Mouth mouth;

    private ArrayList<EntityClass> foodPreferences;

    protected int currentBehavior;

    private int scanTimer;
    private int eatTimer;
    private int starveTimer;
    private int choiceTimer;
    private int matingTimer;
    private int ageTimer;

    protected boolean hasHunger;
    protected boolean hasMating;

    private boolean hasChild;

    private String name;

    protected GameEntity oldWanted;

    private ArrayList<Tracks> tracks;

    public Creature(Position position, Dimension dimension, String spritePath) {
        super(position, dimension, spritePath);

        legs = new Legs(this);
        eye = new Eye(this);
        mouth = new Mouth(this);

        currentBehavior = NONE;
        ageTimer = AGE_TIMER_MAX;
        eatTimer = EAT_TIMER_MAX;
        matingTimer = MATING_TIMER_MAX;
        starveTimer = STARVE_TIMER_MAX;

        setEdible(false);
        isDead = false;

        hasHunger = true;
        hasMating = true;

        name = "default";
        tag = "default";


        foodPreferences = new ArrayList<EntityClass>();
        tracks = new ArrayList<>();
        addEntityClass(EntityClass.Fruit);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<EntityClass> getFoodPreferences() {
        return foodPreferences;
    }

    public void addFoodPreference(EntityClass entityClass) {
        if (!getEntityClasses().contains(entityClass)) {
            foodPreferences.add(entityClass);
        }
    }

    public void removeFoodPreference(EntityClass entityClass) {
        if (foodPreferences.contains(entityClass)) {
            foodPreferences.remove(entityClass);
        }
    }
    public void clearFoodPreferences() {
        foodPreferences = new ArrayList<EntityClass>();
    }

    public boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean b) {
        hasChild = b;
    }

    public void leaveTracks() {

        if (tracks.size() >= 10) {
            tracks.get(0).setShouldRemove(true);
            tracks.remove(0);

           // tracks.remove(tracks.size() - 1);
        }

        tracks.add(new Tracks(this));
    }

    public ArrayList<Tracks> getTracks() {
        return tracks;
    }

    protected void goTowards(GameEntity entity) {
        getLegs().moveForward();
    }

    private void retrace() {

        if (getTracks().size() == 0) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            setPosition(new Position(getTracks().get(getTracks().size()-1).getPosition().getX(), getTracks().get(getTracks().size()-1).getPosition().getY()));
            choiceTimer = -10;
            getLegs().rotate(LEFT);
            getLegs().rotate(LEFT);
        }
    }

    public ArrayList<GameEntity> getVisibleObjects() {
        return visibleObjects;
    }

    public void setVisibleObjects(ArrayList<GameEntity> visibleObjects) {
        this.visibleObjects = visibleObjects;
    }

    public GameEntity getOldWanted() {
        return oldWanted;
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

    public boolean isHungry() {
        return getEnergy() < 40;
    }

    public boolean isStarving() {
        return getEnergy() < 20;
    }

    public boolean needsToMate() {

        return (matingTimer <= 0 && age >= MATURITY_AGE);
    }

    public boolean canMate() {

        return (!getIsDead() && getAge() > MATURITY_AGE);
        //return (!getIsDead() && getAge() > MATURITY_AGE && getEnergy() > 30 + 20);
    }

    protected void mateWith(Creature partner) {

        MessageLog.getInstance().addMessage(getName() + " mated with " + partner.getName());
        partner.setHasChild(true);
        //partner.setEnergy(partner.getEnergy() - 30);
        setMatingTimer(MATING_TIMER_MAX);
        partner.setMatingTimer(MATING_TIMER_MAX);
    }

    public int getMatingTimer() {
        return matingTimer;
    }

    public void setMatingTimer(int matingTimer) {
        this.matingTimer = matingTimer;
    }

    public void setCurrentBehavior(int currentBehavior) {
        this.currentBehavior = currentBehavior;
    }

    public int getCurrentBehavior() {
        return currentBehavior;
    }

    protected GameEntity lookFor(ArrayList<EntityClass> entityClasses) {

        if (visibleObjects == null) {
            return null;
        }
        for (GameEntity entity : visibleObjects) {
            for (EntityClass entityClass : entity.getEntityClasses()) {
                if (entityClasses.contains(entityClass)) {
                    return entity;
                }
            }
        }
        return null;
    }
    protected GameEntity lookFor(EntityClass entityClass) {
        if (visibleObjects == null) {
            return null;
        }
        for (GameEntity entity : visibleObjects) {
            if (entity.getEntityClasses().contains(entityClass)) {
                return entity;
            }
        }
        return null;
    }

    protected GameEntity lookFor(String tag) {
        if (visibleObjects == null) {
            return null;
        }
        for (GameEntity entity : visibleObjects) {
            if (entity.tag.equals(tag)) {
                return entity;
            }
        }
        return null;
    }

    protected void eatItem(Organism entity) {
        eatTimer--;
        if (eatTimer <= 0) {

            getMouth().eatObject(entity);

            eatTimer = EAT_TIMER_MAX;
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
            boolean isTouchingWall = false;
            for (GameEntity entity : getTouch().getTouchedObjects()) {
                if (!entity.getIsTransparent()) {
                    isTouchingWall = true;
                }
            }
            if (!isTouchingWall) {
                getLegs().moveForward();
            } else {
                getLegs().moveBackward();
                getLegs().rotate(LEFT);
            }
        }
    }

    protected void watchOut() {

        //eyes
        if (getVisibleObjects() != null) {
            if (getVisibleObjects().size() > 0) {

                if (collidesWith(getVisibleObjects().get(0))) {

                    if (getVisibleObjects().get(0) instanceof Wall || !getVisibleObjects().get(0).getIsTransparent()) {
                        retrace();
                    }
                }
            }
        }
    }

    private void aging() {
        ageTimer--;
        if (ageTimer <= 0) {
            setAge(getAge() + 1);
            ageTimer = AGE_TIMER_MAX;
            agingTrigger();
        }
    }


    public void die() {
        isDead = true;
        deathTrigger();
        getEye().removeVisualData();
        currentBehavior = NONE;
        oldWanted = null;
    }

    private void mating() {
        if (matingTimer > 0) {
            matingTimer--;
        }
    }

    private void hunger() {
        starveTimer--;

        if (starveTimer < 0) {
            setEnergy(getEnergy() - 1);
            starveTimer = STARVE_TIMER_MAX;
        }
    }

    @Override
    public synchronized void update() {

        if (getEnergy() <= 0) {
            die();
        }
        aging();

        if (hasHunger && !isDead) {
            hunger();
        }
        if (hasMating && !isDead) {
            mating();
        }

        if (!getIsDead()) {
            aging();
            updateCreatureStatus();
            try {
                creatureBehavior();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void setFoodpreferences() {

    }

    protected void creatureBehavior() {
    }

    protected void updateCreatureStatus() {
    }

    protected void agingTrigger() {
    }

    protected void deathTrigger() {
        addEntityClass(EntityClass.Carcass);
    }

    public Organism giveBirth() {

        return null;
    }
}
