package Simulator.Organisms.Creatures;

import Simulator.EntityClass;
import UtterEng.Dimension;
import UtterEng.Position;

import java.util.Random;

public abstract class Egg extends Creature {

    private boolean isHatched;
    private int hatchTimer;

    public Egg(Position position, Dimension dimension, String spritePath) {
        super(position, dimension, spritePath);


        Random random = new Random();
        if (random.nextBoolean()) {
            getLegs().rotate(RIGHT);
        }

        hatchTimer = 4000;
        setEnergy(30);

        isHatched = false;
        hasHunger = false;
        hasMating = false;

        addEntityClass(EntityClass.Egg);
    }

    public int getHatchTimer() {
        return hatchTimer;
    }


    public void setHatchTimer(int hatchTimer) {
        this.hatchTimer = hatchTimer;
    }

    public boolean isHatched() {
        return isHatched;
    }

    public void setHatched(boolean hatched) {
        isHatched = hatched;
    }

    @Override
    protected void updateCreatureStatus() {
        if (hatchTimer <= 0) {
            setHatched(true);
        } else {
            hatchTimer--;
        }
    }

    public abstract Organism hatch();
}
