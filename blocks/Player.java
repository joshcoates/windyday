package windyday.blocks;

import windyday.WindyDayGame;
import windyday.utils.ColorPalette;

import java.awt.Color;

/*
 * Josh Coates - Player
 */

public class Player extends Block {
    private static final int PLAYER_WIDTH = 12;
    private static final int PLAYER_HEIGHT = 12;
    private static final Color PLAYER_COLOR = ColorPalette.darkPurple;

    private static final int START_X_POS = WindyDayGame.X_FRAME_DIM / 2 - PLAYER_WIDTH;
    private static final int START_Y_POS = WindyDayGame.Y_FRAME_DIM / 2 - PLAYER_HEIGHT;

    private static final int INITIAL_X_VELOCITY = 0;
    private static final int INITIAL_Y_VELOCITY = 0;

    private boolean isAlive;

    public Player() {
        width = PLAYER_WIDTH;
        height = PLAYER_HEIGHT;
        blockColor = PLAYER_COLOR;
        isAlive = true;
        xPos = START_X_POS;
        yPos = START_Y_POS;
        xVelocity = INITIAL_X_VELOCITY;
        yVelocity = INITIAL_Y_VELOCITY;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
