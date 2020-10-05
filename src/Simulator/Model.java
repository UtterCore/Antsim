package Simulator;

import Simulator.Organisms.Creatures.*;
import UtterEng.GameEntity;
import Simulator.Organisms.Plants.Berry;
import Simulator.Organisms.Plants.BlueberryBush;
import Simulator.Organisms.Plants.*;
import UtterEng.*;
import UtterEng.UModel;

import java.util.ArrayList;
import java.util.Random;

public class Model implements UModel {

    private static final int CONSUMABLE_TIMER_MAX = 4000;
    private int consumableTimer;

    //private ArrayList<UtterEng.GameEntity> entities;
    private ArrayList<Background> backgrounds;
    private ArrayList<Creature> creatures;
    private ArrayList<GameEntity> items;
    private ArrayList<GameEntity> effects;

    public long fps;

    public int frameCount;
    public Model() {

        consumableTimer = CONSUMABLE_TIMER_MAX;
        //entities = new ArrayList<>();
        backgrounds = new ArrayList<>();
        creatures = new ArrayList<>();
        items = new ArrayList<>();
        effects = new ArrayList<>();

        addEntity(new Background("./resources/bg.png"));


        for (int i = 0; i < 5; i++) {
            createSmartAnt();
        }

        addEntity(new SmartAntEgg(getRandomPos()));

        for (int i = 0;  i < 10; i++) {
            addEntity(new BlueberryBush(getRandomPos()));
        }

        addEntity(new CanBerry(getRandomPos()));

        addEntity(new Wall(new Position(520, 200)));
        addEntity(new Wall(new Position(565, 200)));

        //create borders
        for (int i = 0; i < 40; i++) {
            addEntity(new Wall(new Position(10, i * 40)));
        }

        for (int i = 0; i < 40; i++) {
            addEntity(new Wall(new Position(1580, i * 40)));
        }

        for (int i = 0; i < 45; i++) {
            addEntity(new Wall(new Position(40 + (i*40), -10)));
        }

        for (int i = 0; i < 45; i++) {
            addEntity(new Wall(new Position(40 + (i*40), 850)));
        }
    }

    private Position getRandomPos() {
        Random random = new Random();

        return new Position(random.nextInt(1500) + 40, random.nextInt(850) + 40);
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
    public void click(int x, int y) {

        for (GameEntity entity : getDrawables()) {
            if (x >= entity.getPosition().getX() && x <= entity.getPosition().getX() + entity.getDimension().getWidth() &&
                    y >= entity.getPosition().getY() && y <= entity.getPosition().getY() + entity.getDimension().getHeight()) {

                displayInfo(entity);
            }
        }
    }

    private void displayInfo(GameEntity entity) {
        if (!(entity instanceof Background)) {

            if (entity instanceof Ant) {

                //((Simulator.Organisms.Creatures.Ant) entity).getLegs().setImmobile(!((Simulator.Organisms.Creatures.Ant) entity).getLegs().isImmobile());
                if (((Ant) entity).getIsDead()) {
                    System.out.println("Ant #" + ((Ant) entity).getId() + " (dead)");
                } else {
                    System.out.println("Ant #" + ((Ant) entity).getId());
                }
                System.out.println("Age: " + ((Ant) entity).getAge());
                System.out.println("Mating: " + ((Ant) entity).getMatingTimer());
                System.out.println("Behavior: " + ((Ant) entity).getCurrentBehavior());
                //System.out.println("Sought item: " + ((Simulator.Organisms.Creatures.Ant) entity).soughtItem);
            }
            if (entity instanceof Organism) {
                System.out.println("Energy: " + ((Organism) entity).getEnergy());
            }
        }
    }

    public void allDance() {
        for (Creature creature : creatures) {
            if (creature instanceof Ant) {
                ((Ant) creature).dance();
            }
        }
    }
    public void createAnt() {
        addEntity(new Ant(getRandomPos()));
    }

    public void createSmartAnt() {
        addEntity(new SmartAnt(getRandomPos()));
    }

    public void createBush() {
        BlueberryBush bbb = new BlueberryBush(getRandomPos());
        bbb.setEnergy(130);
        addEntity(bbb);

    }

    public void createLina() {
        addEntity(new LinaAnt(getRandomPos()));
    }

    public void createWictor() {
        addEntity(new WictorAnt(getRandomPos()));
    }

    public void killAllAnts() {
        for (Creature creature : creatures) {
            creature.die();
        }
    }

    public void changeName(String currentName, String newName) {
        for (Creature creature : creatures) {
            if (creature.getName().equals(currentName)) {
                creature.setName(newName);
            }
        }
    }

    public void resetAll() {
        for (Creature creature : new ArrayList<>(creatures)) {
            creatures.remove(creature);
        }

        for (GameEntity item : new ArrayList<>(items)) {
            if (!(item instanceof Wall)) {
                items.remove(item);
            }
        }
    }

    public String getInfo() {
        String out = "";
        out += "Info: ";
        out += "Currently alive: " + creatures.size() + ", ";
        out += "Other items on map: " + items.size() + "\n";

        return out;
    }
    public synchronized void addEntity(GameEntity entity) {

        if (entity instanceof Background) {
            backgrounds.add((Background)entity);
        } else if (entity instanceof Creature) {
            creatures.add((Creature)entity);
        } else if (entity instanceof Effect) {
            effects.add(entity);
        } else {
            items.add(entity);
        }
        //entities.add(entity);
    }


    public void update() {

        long oldTime = System.currentTimeMillis();
        //spawnConsumables();


        for (int i = 0; i < effects.size(); i++) {
            GameEntity entity = effects.get(0);

            effects.remove(entity);
        }


                for (int i = 0; i < creatures.size(); i++) {
                    Creature creature = creatures.get(i);

                //    addEntity(new WantBubble(creature, "./resources/berry.png"));

                    creature.update();
                    if (creature instanceof Egg) {
                        Egg egg = (Egg) creature;
                        if (egg.isHatched()) {
                            addEntity(egg.hatch());
                        }
                    }

                    if (creature instanceof Larvae) {
                        Larvae larva = (Larvae) creature;
                        if (larva.isEvolved()) {
                            addEntity(larva.evolve());
                        }
                    }

                    if (creature.getHasChild()) {
                        addEntity(creature.giveBirth());
                    }

                    if (creature.getEnergy() <= 0) {
                        creatures.remove(creature);
                        continue;
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

        long newTime = System.currentTimeMillis();
        long msPerFrame = newTime - oldTime;

        if (msPerFrame >= 5) {
            fps = 1000 / msPerFrame;
        }
    }

    public ArrayList<GameEntity> getDrawables() {

        ArrayList<GameEntity> drawables = new ArrayList<>();
        drawables.addAll(backgrounds);
        drawables.addAll(items);
        drawables.addAll(creatures);
        drawables.addAll(effects);

        return drawables;
    }
}
