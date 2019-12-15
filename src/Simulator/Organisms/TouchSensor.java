package Simulator.Organisms;

import Simulator.Entity.GameEntity;
import Simulator.World;

import java.util.ArrayList;

public class TouchSensor extends Limb {
    public TouchSensor(Organism owner) {
        super(owner);
    }

    public ArrayList getTouchedObjects() {
        ArrayList<GameEntity> touchedObjects = new ArrayList<>();

        for (GameEntity entity : World.entities) {
            if (getOwner().collidesWith(entity)) {
                if (!entity.equals(this)) {
                    touchedObjects.add(entity);
                }
            }
        }
        return touchedObjects;
    }

}
