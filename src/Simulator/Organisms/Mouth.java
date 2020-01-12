package Simulator.Organisms;
import UtterEng.GameEntity;
import UtterEng.Position;

public class Mouth extends Limb {

    private int biteSize;
    private int carryTimer;

    public Mouth(Creature owner) {
        super(owner);
        carryTimer = 100;

        biteSize = 10 * (owner.getAge() + 1);
        if (biteSize > 50) {
            biteSize = 50;
        }
    }

    public void carryObject(GameEntity entity) {
        carryTimer--;

        if (carryTimer > 0) {
            entity.setPosition(getOwner().getPosition());
        } else if (carryTimer == 0) {
            putDownObject(entity);
            carryTimer = 50;
        }


    }
    public void putDownObject(GameEntity entity) {
        entity.setPosition(new Position(getOwner().getCenterPosition().getX(), getOwner().getCenterPosition().getY()));
    }

    public void eatObject(Organism entity) {
        int energy = 0;

        if (entity.getEnergy() >= biteSize) {
            energy = biteSize;

            entity.setEnergy(entity.getEnergy() - biteSize);
        } else if (entity.getEnergy() == 0) {
            getOwner().setEnergy(getOwner().getEnergy() + energy);
            return;
        } else {
            energy = entity.getEnergy();
            entity.setEnergy(0);
        }

        getOwner().setEnergy(getOwner().getEnergy() + energy);
    }
}
