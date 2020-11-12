package Simulator;

import Simulator.Organisms.Creatures.Organism;
import UtterEng.GameEntity;
import UtterEng.Position;

public class Gravity {

    private GameEntity parent;

    public Gravity(GameEntity parent) {
        this.parent = parent;
    }

    public void fall() {
        parent.setPosition(new Position(parent.getPosition().getX(), parent.getPosition().getY() + 1));
    }

    public void update() {

        if (parent instanceof Organism) {
            Organism organism = (Organism) parent;

            if (organism.getTouch().isOnGround()) {

                //  } else if (organism.isClimbing()) {

                //  }
            }
        }
    }
}
