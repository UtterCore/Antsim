package Simulator.Organisms;


import UtterEng.Dimension;
import UtterEng.Position;

public class AntEgg extends Egg {
    private int hatchTimer;

    public AntEgg(Position position) {
        super(position, new Dimension(10, 20), "./resources/egg.png");

        tag = "ant_egg";
        setEnergy(30);
        hatchTimer = 4000;
    }


    @Override
    public Organism hatch() {
        setIsDead(true);
        setEnergy(0);
        AntLarva larva = new AntLarva(getPosition());
        larva.setRotation(getRotation());

        return new AntLarva(getPosition());
    }
    @Override
    protected void updateCreatureStatus() {
        if (hatchTimer <= 0) {
            setHatched(true);
        } else {
            hatchTimer--;
        }
    }

    @Override
    protected void creatureBehavior() {

    }

    @Override
    public void update() {
        super.update();
    }
}
