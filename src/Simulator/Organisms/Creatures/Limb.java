package Simulator.Organisms.Creatures;

public abstract class Limb {
    private Creature owner;

    public Limb(Creature owner) {
        this.owner = owner;
    }

    public void setOwner(Creature owner) {
        this.owner = owner;
    }

    public Creature getOwner() {
        return owner;
    }
}
