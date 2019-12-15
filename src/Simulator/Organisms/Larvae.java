package Simulator.Organisms;

import Simulator.Dimension;
import Simulator.Position;

import java.util.Random;

public class Larvae extends Creature {


    private boolean isEvolved;
    private int evolveTimer;

    public Larvae(Position position, Dimension dimension, String spritePath) {
        super(position, dimension, spritePath);
    }

    public Organism evolve() {

        return null;
    }


    public boolean isEvolved() {
        return isEvolved;
    }

    public void setEvolved(boolean evolved) {
        isEvolved = evolved;
    }

    public int getEvolveTimer() {
        return evolveTimer;
    }

    public void setEvolveTimer(int evolveTimer) {
        this.evolveTimer = evolveTimer;
    }
}