package Simulator.Organisms.Creatures;

import UtterEng.Dimension;
import UtterEng.Position;

public class AntLarva extends Larvae {


    public AntLarva(Position position) {
        super(position, new Dimension(15, 25), "./resources/antlarvae.png");

        tag = "ant_larva";
        setEnergy(40);
        setEvolveTimer(5000);
        getLegs().setSpeed(0.1f);
    }

    @Override
    protected void updateCreatureStatus() {
        if (!isEvolved()) {
            setEvolveTimer(getEvolveTimer() - 1);
        }

        if (getEvolveTimer() <= 0) {
            setEvolved(true);
        }


        setVisibleObjects(useEye());

        wander();
        watchOut();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public Organism evolve() {
        die();
        setEnergy(0);
        return new Ant(getPosition());
    }
}
