package Simulator.Organisms;

import Simulator.*;

import java.util.Random;

public class Egg extends Creature {

    private boolean isHatched;
    private Legs legs;

    public Egg(Position position, Dimension dimension, String spritePath) {
        super(position, dimension, spritePath);
        legs = new Legs(this);

        Random random = new Random();

        if (random.nextBoolean()) {
            legs.rotate(RIGHT);
        }
    }

    public boolean isHatched() {
        return isHatched;
    }

    public void setHatched(boolean hatched) {
        isHatched = hatched;
    }

    public Organism hatch() {

        return null;
    }
}
