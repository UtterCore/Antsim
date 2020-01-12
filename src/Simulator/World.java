package Simulator;

import UtterEng.GameEntity;

import java.util.ArrayList;

public class World {

    private static World world = new World( );
    public static ArrayList<GameEntity> entities;
    public static int ants;

    private World() {
        entities = new ArrayList<>();
    }

    /* Static 'instance' method */
    public static World getInstance( ) {
        return world;
    }

    public static void setEntities(ArrayList<GameEntity> entities) {
        //this.entities = entities;
    }

}
