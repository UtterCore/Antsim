package Simulator;

import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

public class WantBubble extends Effect {

    WantBubble(GameEntity parent, String sprite) {
        super(new Position(parent.getPosition().getX(), parent.getPosition().getY() - 37), new Dimension(35, 35), "./resources/bubble.png");

        addEntityClass(EntityClass.Effect);

        getChildren().add(new Icon(this, new Dimension(15, 15), sprite));

        setIsTransparent(true);
    }
}
