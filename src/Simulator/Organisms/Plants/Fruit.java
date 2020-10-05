package Simulator.Organisms.Plants;

import Simulator.EntityClass;
import Simulator.Organisms.Creatures.Creature;
import UtterEng.Dimension;
import Simulator.Organisms.Creatures.Organism;
import UtterEng.Position;

public abstract class Fruit extends Organism {

    protected static final int DECAY_TIMER_MAX = 2 * 60 * 60;

    private int decayTimer;
    private Plant plant;

    public Fruit(Position position, Dimension dimension, String spritePath) {
        super(position, dimension, spritePath);

        addEntityClass(EntityClass.Fruit);
        setIsTransparent(true);

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
        } else {
            decayTrigger();
        }
    }

    public abstract void decayTrigger();

    public abstract void eatTrigger(Creature creature);
}
