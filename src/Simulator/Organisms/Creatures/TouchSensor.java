package Simulator.Organisms.Creatures;

import Simulator.Organisms.Sensor;
import UtterEng.GameEntity;
import Simulator.World;

import java.util.ArrayList;

public class TouchSensor extends Sensor {
    public TouchSensor(Organism owner) {
        super(owner);
    }

    public ArrayList<GameEntity> getTouchedObjects() {
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
