package UtterEng;

import java.util.ArrayList;

public interface UModel {
    /**
     * Method for determining what happens when the user presses
     * somewhere on the game window.
     * @param x the x-coordinate of the mouse press
     * @param y the y-coordinate
     */
    void click(int x, int y);

    /**
     * A method for obtaining all game entities that should be drawn on
     * the screen
     * @return a list of game entities
     */
    ArrayList<GameEntity> getDrawables();

    /**
     * A method that will determine what happens on every update made
     * by the game process.
     * Update will be called as long as the process is active.
     */
    void update();
}
