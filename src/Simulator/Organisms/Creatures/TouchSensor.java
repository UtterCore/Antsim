package Simulator.Organisms.Creatures;

import Simulator.EntityClass;
import Simulator.Organisms.Sensor;
import UtterEng.GameEntity;
import Simulator.World;

import javax.swing.text.html.parser.Entity;
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

    //TODO fix this
    public boolean isOnGround() {

        return true;
    }


    public ArrayList<GameEntity> getTouchedObjects(ArrayList<EntityClass> entityClasses) {
        ArrayList<GameEntity> allTouchedObjects = getTouchedObjects();


        ArrayList<GameEntity> correctTouched = new ArrayList<>();


        for (GameEntity touchedObject : allTouchedObjects) {
            for (EntityClass entityClass : entityClasses) {
                if (touchedObject.getEntityClasses().contains(entityClass)) {
                    correctTouched.add(touchedObject);
                }
            }
        }

        return correctTouched;
    }

    public ArrayList<GameEntity> getTouchedObjects(EntityClass entityClass) {
        ArrayList<GameEntity> allTouchedObjects = getTouchedObjects();
        ArrayList<GameEntity> correctTouched = new ArrayList<>();

        for (GameEntity touchedObject : allTouchedObjects) {
            if (touchedObject.getEntityClasses().contains(entityClass)) {
                correctTouched.add(touchedObject);
            }
        }

        return correctTouched;
    }

}
