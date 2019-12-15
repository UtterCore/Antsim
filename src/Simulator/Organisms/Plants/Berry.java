package Simulator.Organisms.Plants;

import Simulator.*;
import Simulator.Organisms.Consumable;
import Simulator.Organisms.Organism;

import java.util.Random;

public class Berry extends Fruit implements Consumable {

    public Berry(Position position) {
        super(position, new Dimension(20, 20), "./berry.png");
        tag = "berry";
        setIsTransparent(true);
    }

    @Override
    public void update() {
        super.update();
        if (getDecayTimer() <= 0 && hasGrownInto() == null) {

            Random random = new Random();

            if (random.nextInt(5) >= 4) {
                growInto(new BlueberryBush(getPosition()));
            }
        }
    }
}
