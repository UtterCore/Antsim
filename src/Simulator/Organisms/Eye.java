package Simulator.Organisms;

import Simulator.*;
import UtterEng.GameEntity;
import UtterEng.Position;

import java.util.ArrayList;

public class Eye extends Limb {
    private int length;

    public Eye(Organism owner) {
        super(owner);
        length = 200;
    }

    public ArrayList<GameEntity> getVisualData() {
        ArrayList<GameEntity> visibleObjects = new ArrayList<>();

            for (int i = 0; i < length; i++) {
                boolean visionIsBlocked = false;
                Position eyeScan = new Position(getOwner().getCenterPosition().getX(), getOwner().getCenterPosition().getY());

                //right
                if (getOwner().getRotation() == 90) {
                    eyeScan.setX(eyeScan.getX() + i);

                    //down
                } else if (getOwner().getRotation() == 180) {
                    eyeScan.setY(eyeScan.getY() + i);

                    //left
                } else if (getOwner().getRotation() == 270) {
                    eyeScan.setX(eyeScan.getX() - i);

                    //up
                } else if (getOwner().getRotation() == 0) {
                    eyeScan.setY(eyeScan.getY() - i);

                }

                for (GameEntity entity : World.entities) {
                    if (eyeScan.getX() >= entity.getPosition().getX() && eyeScan.getX() <= entity.getPosition().getX() + entity.getDimension().getWidth()
                            && eyeScan.getY() >= entity.getPosition().getY() && eyeScan.getY() <= entity.getPosition().getY() + entity.getDimension().getHeight()) {

                        if (entity instanceof Background) {

                        } else {
                            if (!visibleObjects.contains(entity) && entity != getOwner()) {
                                visibleObjects.add(entity);

                                if (!entity.getIsTransparent()) {
                                    visionIsBlocked = true;
                                }
                            }
                        }
                    }
                }
            if (visionIsBlocked) {
                    break;
            }
        }

        if (visibleObjects.size() > 0) {
           // System.out.println("Objects: ");
            for (GameEntity entity : visibleObjects) {
                if (entity.tag != "unassigned") {
                 //   System.out.println(entity.tag);
                }
            }
        }
        return visibleObjects;
    }
}
