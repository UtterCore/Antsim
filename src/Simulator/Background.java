package Simulator;

import Simulator.Entity.GameEntity;

public class Background extends GameEntity {

    public Background(String stringPath) {
        super(new Position(0, 0), new Dimension(640, 480), stringPath);
        setIsTransparent(true);
        setIsBackground(true);
    }
}
