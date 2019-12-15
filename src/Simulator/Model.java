package Simulator;

import Simulator.Entity.GameEntity;
import Simulator.Organisms.*;
import Simulator.Organisms.Plants.Berry;
import Simulator.Organisms.Plants.BlueberryBush;
import Simulator.Organisms.Plants.*;

import java.util.ArrayList;
import java.util.Random;

public class Model {

    private static final int CONSUMABLE_TIMER_MAX = 4000;
    private int consumableTimer;

    //private ArrayList<Simulator.Entity.GameEntity> entities;
    private ArrayList<Background> backgrounds;
    private ArrayList<Creature> creatures;
    private ArrayList<GameEntity> items;

    public Model() {

        consumableTimer = CONSUMABLE_TIMER_MAX;
        //entities = new ArrayList<>();
        backgrounds = new ArrayList<>();
        creatures = new ArrayList<>();
        items = new ArrayList<>();

        addEntity(new Background("./resources/bg.png"));


        /*
        addEntity(new Berry(new Position(240, 200)));
        addEntity(new Berry(new Position(350, 100)));
        addEntity(new Berry(new Position(350, 300)));
        addEntity(new Berry(new Position(100, 400)));
        addEntity(new Berry(new Position(500, 350)));
        addEntity(new Berry(new Position(310, 230)));
        */
        BlueberryBush bbb = new BlueberryBush(new Position(300, 300));
        bbb.setEnergy(130);
        addEntity(bbb);

        BlueberryBush bbb2 = new BlueberryBush(new Position(100, 120));
        bbb2.setEnergy(110);
        addEntity(bbb2);

        BlueberryBush bbb3 = new BlueberryBush(new Position(500, 80));
        bbb3.setEnergy(120);
        addEntity(bbb3);

        addEntity(new AntEgg(new Position(100, 200)));
        addEntity(new AntEgg(new Position(240, 120)));
        addEntity(new AntEgg(new Position(280, 370)));

        Ant oldAntM = new Ant(new Position(565, 400));
        oldAntM.setAge(3);
        oldAntM.setMatingTimer(0);
        oldAntM.setEnergy(600);
        addEntity(oldAntM);

        Ant oldAntF = new Ant(new Position(565, 400));
        oldAntF.setAge(3);
        oldAntF.setMatingTimer(0);
        oldAntF.setEnergy(600);
        addEntity(oldAntF);

        addEntity(new Wall(new Position(520, 200)));
        addEntity(new Wall(new Position(565, 200)));

        //create borders
        for (int i = 0; i < 12; i++) {
            addEntity(new Wall(new Position(10, i * 40)));
        }

        for (int i = 0; i < 12; i++) {
            addEntity(new Wall(new Position(620, i * 40)));
        }

        for (int i = 0; i < 15; i++) {
            addEntity(new Wall(new Position(40 + (i*40), -10)));
        }

        for (int i = 0; i < 15; i++) {
            addEntity(new Wall(new Position(40 + (i*40), 470)));
        }
    }

    private void spawnConsumables() {
        Random random = new Random();

        consumableTimer--;

        if (consumableTimer <= 0) {


            if (random.nextBoolean()) {
                addEntity(new Berry(new Position(random.nextInt(500) + 40, random.nextInt(350) + 40)));
            }
            consumableTimer = CONSUMABLE_TIMER_MAX;
        }
    }
    public void displayInfo(int x, int y) {

        for (GameEntity entity : getDrawables()) {
            if (x >= entity.getPosition().getX() - 20 && x <= entity.getPosition().getX() + entity.getDimension().getWidth() + 20 &&
                    y >= entity.getPosition().getY() - 20 && y <= entity.getPosition().getY() + entity.getDimension().getHeight() + 20) {

                if (entity instanceof Background == false) {

                    if (entity instanceof Ant) {

                        //((Simulator.Organisms.Ant) entity).getLegs().setImmobile(!((Simulator.Organisms.Ant) entity).getLegs().isImmobile());
                        if (((Ant) entity).getIsDead()) {
                            System.out.println("Ant #" + ((Ant) entity).getId() + " (dead)");
                        } else {
                            System.out.println("Ant #" + ((Ant) entity).getId());
                        }
                        System.out.println("Age: " + ((Ant) entity).getAge());
                        System.out.println("Mating: " + ((Ant) entity).getMatingTimer());
                        System.out.println("Behavior: " + ((Ant) entity).getCurrentBehavior());
                        //System.out.println("Sought item: " + ((Simulator.Organisms.Ant) entity).soughtItem);
                    }
                    if (entity instanceof Organism) {
                        System.out.println("Energy: " + ((Organism) entity).getEnergy());
                    }
                    break;
                }
            }
        }
    }
    public void addEntity(GameEntity entity) {

        if (entity instanceof Background) {
            backgrounds.add((Background)entity);
        } else if (entity instanceof Creature) {
            creatures.add((Creature)entity);
        } else {
            items.add(entity);
        }
        //entities.add(entity);
    }

    public void update() {

        //spawnConsumables();
        for (int i = 0; i < creatures.size(); i++) {
            Creature creature = creatures.get(i);

            creature.update();
            if (creature instanceof Egg) {
                if (((Egg) creature).isHatched()) {
                    Egg egg = (Egg)creature;
                    addEntity(egg.hatch());
                }
            }

            if (creature instanceof Larvae) {
                if (((Larvae) creature).isEvolved()) {
                    Larvae larva = (Larvae)creature;
                    addEntity((Ant)larva.evolve());
                }
            }

            if (creature instanceof Ant) {

                if (((Ant) creature).getHasChild()) {
                    addEntity(new AntEgg(new Position(creature.getPosition().getX(), creature.getPosition().getY())));
                    //addEntity(new Ant(new Position(creature.getPosition().getX(), creature.getPosition().getY())));
                    ((Ant) creature).setHasChild(false);
                }
            }//

            if (creature instanceof Organism) {
                if (creature.getEnergy() <= 0) {
                    creatures.remove(creature);
                    continue;
                }
            }

        }

        for (int i = 0; i < items.size(); i++) {
            GameEntity entity = items.get(i);

            if (entity instanceof Organism) {
                ((Organism) entity).update();

                if (entity instanceof Plant) {
                    if (((Plant) entity).getFruitsToDrop().size() > 0) {
                        items.addAll(((Plant) entity).getFruitsToDrop());
                        ((Plant) entity).setFruitsToDrop(new ArrayList<Fruit>());
                    }
                }

                if (entity instanceof Fruit) {
                    if (((Fruit) entity).hasGrownInto() != null) {
                        Plant newPlant = ((Fruit) entity).hasGrownInto();
                        addEntity(newPlant);
                        ((Fruit) entity).giveEnergy(newPlant, -1);
                    }
                }

                if (((Organism) entity).getEnergy() <= 0) {
                    items.remove(entity);
                }
            }

        }
        World.entities = getDrawables();
    }

    public ArrayList<GameEntity> getDrawables() {

        ArrayList<GameEntity> drawables = new ArrayList<>();
        drawables.addAll(backgrounds);
        drawables.addAll(items);
        for(Organism creature : creatures) {
            if ((creature instanceof Egg)) {
                drawables.add(creature);
                continue;
            }
        }
        for(Organism creature : creatures) {
            if (!(creature instanceof Egg)) {
                drawables.add(creature);
                continue;
            }
        }

        return drawables;
    }
}
