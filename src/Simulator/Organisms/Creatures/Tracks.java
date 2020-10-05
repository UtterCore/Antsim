package Simulator.Organisms.Creatures;

import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

public class Tracks extends GameEntity {

    private Creature owner;
    private boolean isInWorld;

    public Tracks(Creature owner) {
        super(new Position(owner.getPosition().getX(), owner.getPosition().getY()),
                new Dimension(owner.getDimension().getWidth(), owner.getDimension().getHeight()),
                "./resources/tracks.png");

        setRotation(owner.getRotation());
        setIsTransparent(true);
        this.owner = owner;
    }


    public boolean isInWorld() {
        return isInWorld;
    }

    public void setInWorld(boolean inWorld) {
        isInWorld = inWorld;
    }


    public Creature getOwner() {
        return owner;
    }
}
