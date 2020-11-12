package Simulator.Organisms.Creatures;
import Simulator.Gravity;
import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

public class Organism extends GameEntity {

    private TouchSensor touch;
    private int energy;
    private boolean isEdible;
    private Gravity gravity;

    public Organism(Position position, Dimension dimension, String spritePath) {
        super(position, dimension, spritePath);

        setIsTransparent(true);
        touch = new TouchSensor(this);
        gravity = new Gravity(this);
    }

    public Gravity getGravity() {
        return gravity;
    }


    public boolean isEdible() {
        return isEdible;
    }

    public void setEdible(boolean edible) {
        isEdible = edible;
    }

    public TouchSensor getTouch() {
        return touch;
    }

    public void giveEnergy(Organism organism, int energy) {

        if (energy == -1) {
            energy = getEnergy();
        }
        organism.setEnergy(organism.getEnergy() + energy);
        setEnergy(getEnergy() - energy);
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

    public void update() {
    }
}
