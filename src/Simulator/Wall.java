package Simulator;

import Simulator.Entity.GameEntity;

public class Wall extends GameEntity {

    public Wall(Position position) {
        super(position, new Dimension(40, 40), "./resources/wall.png");

        tag = "wall";

    }
}
