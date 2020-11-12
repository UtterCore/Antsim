package Simulator;

import UtterEng.Dimension;
import UtterEng.Position;

public class EarthBg extends Material {

    public EarthBg(Position position) {
        super(position, new Dimension(20, 20), "./resources/earthbg.png", 100);
        setIsTransparent(true);
        setIsBackground(true);

        removeEntityClass(EntityClass.Material);
    }
}
