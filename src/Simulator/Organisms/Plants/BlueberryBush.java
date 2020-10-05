package Simulator.Organisms.Plants;

import Simulator.EntityClass;
import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;
import Simulator.Wall;

import java.util.ArrayList;
import java.util.Random;

public class BlueberryBush extends Plant {
    public BlueberryBush(Position position) {
        super(position, new Dimension(40, 40), "./resources/bbb.png");
        tag = "blueberrybush";
        setEnergy(20);
        setEdible(true);
    }

    @Override
    public void update() {
        super.update();

        if (getDropTimer() <= 0 && getEnergy() >= getMaxEnergy() + 30) {
            Berry newBerry = new Berry(getRandomDropPoint());
            //satte detta till 20
            giveEnergy(newBerry, 20);


            boolean touchesWall = false;
            ArrayList<GameEntity> touchedObjects = newBerry.getTouch().getTouchedObjects();

            for (GameEntity touchedEntity : touchedObjects) {
                if (touchedEntity instanceof Wall
                || (touchedEntity instanceof Plant && touchedEntity != this)) {
                    touchesWall = true;
                    break;
                }
            }
            if (!touchesWall) {
                dropFruit(newBerry);

                Random random = new Random();
                if (random.nextInt(10) == 10) {
                    dropFruit(new CanBerry(getRandomDropPoint()));
                }
            }

            setDropTimer(DROP_TIMER_MAX);
        }
    }
}
