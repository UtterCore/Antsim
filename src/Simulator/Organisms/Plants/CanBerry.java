package Simulator.Organisms.Plants;

import Simulator.EntityClass;
import Simulator.MessageLog;
import Simulator.Organisms.Creatures.Ant;
import Simulator.Organisms.Creatures.Creature;
import UtterEng.Dimension;
import UtterEng.Position;

public class CanBerry extends Fruit {

    public CanBerry(Position position) {
        super(position, new Dimension(20, 20), "./resources/canberry.png");
        tag = "canberry";
        setEnergy(20);
    }

    @Override
    public void eatTrigger(Creature creature) {
        //creature.die();
        //creature.clearFoodPreferences();
        creature.addFoodPreference(EntityClass.Meat);

        creature.getLegs().setSpeed(creature.getLegs().getSpeed() * 1.5f);
        MessageLog.getInstance().addMessage(creature.getName() + " ate cannibal berry!");
    }

    @Override
    public void decayTrigger() {
        
    }
}
