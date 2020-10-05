package Simulator.Organisms.Plants;
import Simulator.EntityClass;
import Simulator.Organisms.Creatures.Organism;
import UtterEng.Dimension;
import UtterEng.Position;

import java.util.ArrayList;
import java.util.Random;

public class Plant extends Organism {
    protected final static int GROW_TIMER_MAX = 1 * 30;
    protected final static int DROP_TIMER_MAX = 10 * 60;
    protected final static int WITHER_TIMER_MAX = 2 * 40 * 60;

    private int growTimer;
    private int dropTimer;
    private int witherTimer;

    private int minWidth;
    private int minHeight;

    private int maxWidth;
    private int maxHeight;

    private int maxEnergy;

    private Random random = new Random();

    private ArrayList<Fruit> fruitsToDrop;

    public Plant(Position position, Dimension dimension, String spritePath) {
        super(position, dimension, spritePath);

        maxWidth = dimension.getWidth();
        maxHeight = dimension.getHeight();

        fruitsToDrop = new ArrayList<>();

        //minWidth = maxWidth / 4;
        //minHeight = maxHeight / 4;

        minWidth = 10;
        minHeight = 10;

        maxEnergy = 100;

        dropTimer = DROP_TIMER_MAX;
        growTimer = GROW_TIMER_MAX;
        witherTimer = WITHER_TIMER_MAX;

        setIsTransparent(false);

        addEntityClass(EntityClass.Plant);
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getDropTimer() {
        return dropTimer;
    }

    public void setDropTimer(int dropTimer) {
        this.dropTimer = dropTimer;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    protected void dropFruit(Fruit fruit) {


        fruitsToDrop.add(fruit);
    }
    public ArrayList<Fruit> getFruitsToDrop() {
        return fruitsToDrop;
    }

    public void setFruitsToDrop(ArrayList<Fruit> fruitsToDrop) {
        this.fruitsToDrop = fruitsToDrop;
    }

    protected Position getRandomDropPoint() {

        Position dropPoint;
        Random random = new Random();

        float x;
        float y;

        if (random.nextBoolean()) {
           x = getPosition().getX() + (random.nextInt(maxWidth));
        }  else {
            x = getPosition().getX() - (random.nextInt(maxWidth));
        }

        if (random.nextBoolean()) {
            y = getPosition().getY() + (random.nextInt(maxHeight));
        }  else {
            y = getPosition().getY() - (random.nextInt(maxHeight));
        }

        dropPoint = new Position(x, y);

        return dropPoint;
    }

    public void grow() {
        //if (getEnergy() >= 100) {
          //  return;
        //}

        growTimer--;

        if (growTimer <= 0) {
            setEnergy(getEnergy() + 1);
            growTimer = GROW_TIMER_MAX;
        }
    }

    @Override
    public void update() {
        grow();


        if (witherTimer > 0) {
            if (random.nextBoolean()) {
                witherTimer--;
            }
        } else {
            setEnergy(0);
        }

        if (getDropTimer() > 0) {
            dropTimer--;
        }

        if (maxWidth * getEnergy() / 100 >= minWidth
        && maxHeight * getEnergy() / 100 >= minHeight) {

            if (getEnergy() <= maxEnergy) {
                setDimension(new Dimension(
                        maxWidth * getEnergy() / 100,
                        maxHeight * getEnergy() / 100));
            }
        } else {
            setDimension(new Dimension(minWidth,
                    minHeight));
        }
    }
}
