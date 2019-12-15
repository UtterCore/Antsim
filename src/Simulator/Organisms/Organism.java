package Simulator.Organisms;
import Simulator.*;
import Simulator.Entity.GameEntity;

public class Organism extends GameEntity {

    private TouchSensor touch;
    private int energy;

    public Organism(Position position, Dimension dimension, String spritePath) {
        super(position, dimension, spritePath);

        setIsTransparent(true);
        touch = new TouchSensor(this);
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
