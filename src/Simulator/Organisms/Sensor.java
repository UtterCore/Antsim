package Simulator.Organisms;

import Simulator.Organisms.Creatures.Organism;

public class Sensor {

    private Organism owner;

    public Sensor(Organism owner) {
        this.owner = owner;

    }

    public Organism getOwner() {
        return owner;
    }

    public void setOwner(Organism owner) {
        this.owner = owner;
    }
}
