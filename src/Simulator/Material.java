package Simulator;

import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

public abstract class Material extends GameEntity {

    private int maxHp;
    private int hp;

    public Material(Position position, Dimension dimension, String spritePath, int hp) {
        super(position, dimension, spritePath);
        this.maxHp = hp;
        this.hp = maxHp;
        addEntityClass(EntityClass.Material);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void breakMaterial() {
        this.setShouldRemove(true);
    }
}
