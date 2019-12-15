package Simulator.Organisms.Plants;

import Simulator.Dimension;
import Simulator.Entity.GameEntity;
import Simulator.Position;
import Simulator.Wall;

import java.util.ArrayList;

public class BlueberryBush extends Plant {
    public BlueberryBush(Position position) {
        super(position, new Dimension(40, 40), "./bbb.png");
    }

    @Override
    public void update() {
        super.update();

        if (getDropTimer() <= 0 && getEnergy() >= getMaxEnergy() + 30) {
            Berry newBerry = new Berry(getRandomDropPoint());
            giveEnergy(newBerry, 30);


            boolean touchesWall = false;
            ArrayList<GameEntity> touchedObjects = newBerry.getTouch().getTouchedObjects();

            for (GameEntity touchedEntity : touchedObjects) {
                if (touchedEntity instanceof Wall
                || (touchedEntity instanceof Plant && touchedEntity != this)) {
                    touchesWall = true;
                    break;
                }
            }
            if (touchesWall) {
            } else {
                dropFruit(newBerry);
            }

            setDropTimer(DROP_TIMER_MAX);
        }
    }
}