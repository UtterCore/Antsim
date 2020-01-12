package Simulator;

import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

public class Wall extends GameEntity {

    public Wall(Position position) {
        super(position, new Dimension(40, 40), "./resources/wall.png");

        tag = "wall";

    }
}
