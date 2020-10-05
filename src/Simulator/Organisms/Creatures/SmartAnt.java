package Simulator.Organisms.Creatures;

import Simulator.EntityClass;
import Simulator.MessageLog;
import Simulator.World;
import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

import java.util.Random;

public class SmartAnt extends Creature {

    public SmartAnt(Position position) {
        super(position, new Dimension(20, 20), "./resources/linaant.png");

        tag = "smart_ant";

        setAge(0);
        setEnergy(100);

        currentBehavior = EAT;

        setName(tag + "#" + World.ants++);
        addEntityClass(EntityClass.Meat);

        addFoodPreference(EntityClass.Carcass);
        addFoodPreference(EntityClass.Fruit);
        addFoodPreference(EntityClass.Plant);

        setHasChild(false);

    }

    @Override
    protected void updateCreatureStatus() {
        super.updateCreatureStatus();
    }

    @Override
    protected void agingTrigger() {
        if (getDimension().getWidth() <= 20 + getAge()) {
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

    @Override
    public Organism giveBirth() {
        setHasChild(false);
        return new SmartAntEgg(new Position(getPosition().getX(), getPosition().getY()));
    }

    @Override
    protected void deathTrigger() {
        super.deathTrigger();
        tag = "dead_ant";
    }

    @Override
    protected void creatureBehavior() {

        if (getEnergy() <= 10) {
            die();
        }
        //setName(Integer.toString(getEnergy()));
        setVisibleObjects(useEye());

        if (currentBehavior == WAIT) {
            currentBehavior = NONE;
            return;
        }

        if (isHungry()) {
            currentBehavior = EAT;
        } else if (needsToMate()){
            currentBehavior = MATE;
        } else if (getEnergy() < 100) {
            currentBehavior = EAT;
        } else {
            currentBehavior = NONE;
        }

        switch (currentBehavior) {
            case NONE: {
                wander();
                watchOut();
                break;
            }
            case EAT: {
                //Look for any type of food that is in the list of food prefs
                GameEntity entity = lookFor(getFoodPreferences());

                if (entity == null) {
                    wander();
                } else {
                    if (collidesWith(entity)) {
                        eatItem((Organism)entity);
                    } else {
                        getLegs().moveForward();
                        if (entity instanceof Creature) {
                            if (!((Creature) entity).getIsDead()) {
                                System.out.println("SEES MEAAT");
                            }
                        }
                    }
                }
                watchOut();
                break;
            }
            case MATE: {

                //Look for entity with same tag as this ant
                GameEntity entity = lookFor(tag);

                if (entity == null) {
                    wander();
                    watchOut();
                    break;
                } else {

                    SmartAnt ant = (SmartAnt) entity;

                    if (!ant.canMate() || ant.isStarving()) {
                        wander();
                        watchOut();
                        break;
                    }
                    ant.setCurrentBehavior(WAIT);

                    if (collidesWith(ant)) {
                        mateWith(ant);
                        MessageLog.getInstance().addMessage("SMART ANT BAZZADE");
                        currentBehavior = NONE;
                        ant.setCurrentBehavior(NONE);
                    } else {
                        getLegs().moveForward();
                    }
                }
                watchOut();
                break;
            }
            case WAIT: {

                break;
            }
        }
    }
}
