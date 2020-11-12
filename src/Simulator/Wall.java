package Simulator;

import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

public class Wall extends Material {

    public Wall(Position position) {
        super(position, new Dimension(40, 40), "./resources/wall.png", 200);

        tag = "wall";

    }
}
