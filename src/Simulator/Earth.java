package Simulator;

import UtterEng.Dimension;
import UtterEng.GameEntity;
import UtterEng.Position;

public class Earth extends Material {


    public Earth(Position position) {
       super(position, new Dimension(20, 20), "./resources/earth.png", 100);
        tag = "wall";
        addEntityClass(EntityClass.Earth);
        setIsTransparent(true);
    }
}
