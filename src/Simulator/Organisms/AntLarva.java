package Simulator.Organisms;

import Simulator.Dimension;
import Simulator.Position;

public class AntLarva extends Larvae {


    public AntLarva(Position position) {
        super(position, new Dimension(15, 25), "./antlarvae.png");

        tag = "ant_larva";
        setEnergy(30);
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
    }

    @Override
    public void update() {
        super.update();
        setVisibleObjects(useEye());
        wander();
        watchOut();
    }

    @Override
    public Organism evolve() {
        setIsDead(true);
        setEnergy(0);
        return new Ant(getPosition());
    }
}
