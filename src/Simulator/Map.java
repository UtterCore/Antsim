package Simulator;

import UtterEng.GameEntity;
import UtterEng.Position;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    private int width_blocks;
    private int height_blocks;

    private int block_width;

    private GameEntity[][] grid;

    public Map(int width_blocks, int height_blocks, int block_width) {
        this.width_blocks = width_blocks;
        this.height_blocks = height_blocks;
        this.block_width = block_width;

        grid = new GameEntity[height_blocks][width_blocks];
        setAllBlocks();
        for (int i = 0; i < width_blocks; i++) {
            setBlock(new Wall(null), i, 0);
            setBlock(new Wall(null), i, height_blocks - 3);
        }

        for (int i = 0; i < height_blocks; i++) {
            setBlock(new Wall(null), 0, i);
            setBlock(new Wall(null), width_blocks - 2, i);
        }
    }

    public GameEntity[][] getGrid() {
        return grid;
    }

    public GameEntity getUp(GameEntity entity) {
        if (getGridPosition(entity).getY() > 0) {
            return getBlock(getGridPosition(entity).getX(), getGridPosition(entity).getY() - 1);
        }
        return null;
    }

    public GameEntity getRight(GameEntity entity) {
        if (getGridPosition(entity).getX() < width_blocks) {
            return getBlock(getGridPosition(entity).getX() + 1, getGridPosition(entity).getY());
        }
        return null;
    }

    public GameEntity getDown(GameEntity entity) {
        if (getGridPosition(entity).getY() < height_blocks) {
            return getBlock(getGridPosition(entity).getX(), getGridPosition(entity).getY() + 1);
        }
        return null;
    }

    public GameEntity getLeft(GameEntity entity) {
        if (getGridPosition(entity).getX() > 0) {
            return getBlock(getGridPosition(entity).getX() - 1, getGridPosition(entity).getY());
        }
        return null;
    }


    private Position calculatePosition(int x, int y) {
        return new Position(x * block_width, y * block_width);
    }

    public void setBlock(GameEntity entity, int x, int y) {
        entity.setPosition(calculatePosition(x, y));
        grid[y][x] = entity;
    }

    private void setAllBlocks() {
        for (int i = 0; i < height_blocks; i++) {
            for (int j = 0; j < width_blocks; j++) {
                setBlock(new Earth(null), j, i);
            }
        }
    }

    public ArrayList<GameEntity> getBlocks() {
        ArrayList<GameEntity> entities = new ArrayList<>();
        for (int i = 0; i < height_blocks; i++) {
            for (int j = 0; j < width_blocks; j++) {
                if (grid[i][j].isShouldRemove()) {
                    setBlock(new EarthBg(null), j, i);
                }
                entities.add(grid[i][j]);
            }
        }
        return entities;
    }

    public Position getGridPosition(GameEntity entity) {
        for (int i = 0; i < height_blocks; i++) {
            for (int j = 0; j < width_blocks; j++) {
                if (grid[i][j].equals(entity)) {
                    return new Position(j, i);
                }
            }
        }
        return null;
    }

    public GameEntity getBlock(float gridX, float gridY) {
        return grid[(int)gridY][(int)gridX];
    }
    public Position getRealPosition(GameEntity entity) {
        for (int i = 0; i < height_blocks; i++) {
            for (int j = 0; j < width_blocks; j++) {
                if (entity.equals(grid[i][j]));
                return grid[i][j].getPosition();
            }
        }
        return null;
    }

}
