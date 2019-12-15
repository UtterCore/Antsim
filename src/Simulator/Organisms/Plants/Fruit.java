package Simulator.Organisms.Plants;

import Simulator.Dimension;
import Simulator.Organisms.Organism;
import Simulator.Position;

public class Fruit extends Organism {

    protected static final int DECAY_TIMER_MAX = 2 * 60 * 60;

    private int decayTimer;
    private Plant plant;

    public Fruit(Position position, Dimension dimension, String spritePath) {
        super(position, dimension, spritePath);

        setEdible(true);
        decayTimer = DECAY_TIMER_MAX;
    }

    public int getDecayTimer() {
        return decayTimer;
    }

    protected void growInto(Plant plant) {
        this.plant = plant;
    }

    public Plant hasGrownInto() {
        return plant;
    }

    @Override
    public void update() {
        if (decayTimer > 0) {
            decayTimer--;
        }

    }
}
