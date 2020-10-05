package Simulator.Organisms.Plants;

import Simulator.EntityClass;
import Simulator.Organisms.Creatures.Creature;
import UtterEng.Dimension;
import UtterEng.Position;

import java.util.Random;

public class Berry extends Fruit {

    public Berry(Position position) {
        super(position, new Dimension(20, 20), "./resources/berry.png");
        tag = "berry";
    }

    @Override
    public void decayTrigger() {
        if (hasGrownInto() == null) {

            Random random = new Random();

            if (random.nextInt(5) >= 4) {
                growInto(new BlueberryBush(getPosition()));
            }
        }
    }

    @Override
    public void eatTrigger(Creature creature) {

    }
}
