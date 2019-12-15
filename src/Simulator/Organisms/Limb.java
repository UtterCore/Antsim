package Simulator.Organisms;

public abstract class Limb {
    private Organism owner;

    public Limb(Organism owner) {
        this.owner = owner;
    }

    public void setOwner(Organism owner) {
        this.owner = owner;
    }

    public Organism getOwner() {
        return owner;
    }
}
