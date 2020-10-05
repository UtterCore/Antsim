package Simulator.Organisms.Creatures;

import Simulator.*;
import UtterEng.GameEntity;
import UtterEng.Position;

import java.util.ArrayList;

public class Eye extends Limb {
    public final static int SCAN_TIMER_MAX = 0;
    private int length;
    private Position eyeScan;
    private Position eye2Scan;
    private int scanTimer;
    private ArrayList<GameEntity> visualData;

    public Eye(Creature owner) {
        super(owner);
        length = 400;
        scanTimer = 0;
        eye2Scan = null;
        eyeScan = null;
    }

    public Position getEyeScan() {
        return eyeScan;
    }

    public Position getEye2Scan() {
        return eye2Scan;
    }

    public void removeVisualData() {
        getVisualData().clear();
        eyeScan = null;
        eye2Scan = null;
    }

    public ArrayList<GameEntity> getVisualData() {
        //ArrayList<GameEntity> visibleObjects = new ArrayList<>();

        int eyeDist = 4;

        visualData = new ArrayList<>();

            for (int i = 0; i < length; i++) {
                boolean visionIsBlocked = false;
                eyeScan = new Position(getOwner().getCenterPosition().getX(), getOwner().getCenterPosition().getY());
                eye2Scan = new Position(getOwner().getCenterPosition().getX(), getOwner().getCenterPosition().getY());

                //right
                if (getOwner().getRotation() == 90) {
                    eyeScan.setY(getOwner().getCenterPosition().getY() - getOwner().getDimension().getHeight() / eyeDist);
                    eye2Scan.setY(getOwner().getCenterPosition().getY() + getOwner().getDimension().getHeight() / eyeDist);

                    eyeScan.setX(eyeScan.getX() + i);
                    eye2Scan.setX(eye2Scan.getX() + i);
                    //down
                } else if (getOwner().getRotation() == 180) {

                    eyeScan.setX(getOwner().getCenterPosition().getX() - getOwner().getDimension().getWidth() / eyeDist);
                    eye2Scan.setX(getOwner().getCenterPosition().getX() + getOwner().getDimension().getWidth() / eyeDist);

                    eyeScan.setY(eyeScan.getY() + i);
                    eye2Scan.setY(eye2Scan.getY() + i);

                    //left
                } else if (getOwner().getRotation() == 270) {

                    eyeScan.setY(getOwner().getCenterPosition().getY() + getOwner().getDimension().getHeight() / eyeDist);
                    eye2Scan.setY(getOwner().getCenterPosition().getY() - getOwner().getDimension().getHeight() / eyeDist);

                    eyeScan.setX(eyeScan.getX() - i);
                    eye2Scan.setX(eye2Scan.getX() - i);

                    //up
                } else if (getOwner().getRotation() == 0) {

                    eyeScan.setX(getOwner().getCenterPosition().getX() + getOwner().getDimension().getWidth() / eyeDist);
                    eye2Scan.setX(getOwner().getCenterPosition().getX() - getOwner().getDimension().getWidth() / eyeDist);

                    eyeScan.setY(eyeScan.getY() - i);
                    eye2Scan.setY(eye2Scan.getY() - i);

                }

                try {
                    for (GameEntity entity : World.entities) {
                        if ((eyeScan.getX() >= entity.getPosition().getX() && eyeScan.getX() <= entity.getPosition().getX() + entity.getDimension().getWidth()
                                && eyeScan.getY() >= entity.getPosition().getY() && eyeScan.getY() <= entity.getPosition().getY() + entity.getDimension().getHeight()) ||
                                (eye2Scan.getX() >= entity.getPosition().getX() && eye2Scan.getX() <= entity.getPosition().getX() + entity.getDimension().getWidth()
                                        && eye2Scan.getY() >= entity.getPosition().getY() && eye2Scan.getY() <= entity.getPosition().getY() + entity.getDimension().getHeight())) {

                            if (entity instanceof Background) {

                            } else {
                                if (!visualData.contains(entity) && entity != getOwner()) {
                                    visualData.add(entity);

                                    if (!entity.getIsTransparent()) {
                                        visionIsBlocked = true;
                                    }
                                }
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    return null;
                }
            if (visionIsBlocked) {
                    break;
            }
        }

        return visualData;
    }
}
