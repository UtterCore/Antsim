package Simulator.Organisms;

import UtterEng.Dimension;
import UtterEng.Position;

import java.util.Random;

public class Egg extends Creature {

    private boolean isHatched;

    public Egg(Position position, Dimension dimension, String spritePath) {
        super(position, dimension, spritePath);

        Random random = new Random();

        if (random.nextBoolean()) {
            getLegs().rotate(RIGHT);
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
