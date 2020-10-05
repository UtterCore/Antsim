package Simulator;

import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

public class Background extends GameEntity {

    public Background(String stringPath) {
        super(new Position(0, 0), new Dimension(1600, 900), stringPath);
        setIsTransparent(true);
        setIsBackground(true);
    }
}
