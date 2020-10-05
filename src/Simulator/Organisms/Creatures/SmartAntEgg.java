package Simulator.Organisms.Creatures;

import UtterEng.Dimension;
import UtterEng.Position;

public class SmartAntEgg extends Egg {

    public SmartAntEgg(Position position) {
        super(position, new Dimension(10, 20), "./resources/egg.png");

        tag = "smartant_egg";
    }
    @Override
    public Organism hatch() {
        die();
        setEnergy(0);
        return new SmartAnt(getPosition());
    }
}
