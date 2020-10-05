package Simulator;

import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

import java.util.ArrayList;

public class Effect extends GameEntity {

    private ArrayList<Effect> children;

    public Effect(Position position, Dimension dimension, String spritePath) {
        super(position, dimension, spritePath);

        children = new ArrayList<Effect>();
    }

    public ArrayList<Effect> getChildren() {
        return children;
    }
}
