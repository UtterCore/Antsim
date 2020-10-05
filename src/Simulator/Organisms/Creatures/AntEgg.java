package Simulator.Organisms.Creatures;


import UtterEng.Dimension;
import UtterEng.Position;

public class AntEgg extends Egg {

    public AntEgg(Position position) {
        super(position, new Dimension(10, 20), "./resources/egg.png");

        tag = "ant_egg";
    }

    @Override
    public Organism hatch() {
        die();
        AntLarva larva = new AntLarva(getPosition());
        larva.setRotation(getRotation());

        return new AntLarva(getPosition());
    }
}
