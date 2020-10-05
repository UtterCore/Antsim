package Simulator;

import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

public class Icon extends Effect {

    public Icon(GameEntity parent, Dimension dimension, String spritePath) {
        super(new Position(parent.getPosition().getX() + 8, parent.getPosition().getY() + 5), dimension, spritePath);
    }
}
