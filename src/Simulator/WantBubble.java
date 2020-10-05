package Simulator;

import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

public class WantBubble extends Effect {

    WantBubble(GameEntity parent, String sprite) {
        super(new Position(parent.getPosition().getX(), parent.getPosition().getY() - 40), new Dimension(40, 40), "./resources/bubble.png");

        addEntityClass(EntityClass.Effect);

        setIsTransparent(true);
    }
}
