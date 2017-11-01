package windyday.blocks;

import windyday.*;
import windyday.utils.*;

import java.awt.Color;
import java.awt.Graphics;

/*
 * Josh Coates - Ground.
 */

public class Ground extends Block {

    public static final int DEFAULT_WIDTH = 160;
    public static final int MIN_HEIGHT = 50;
    public static final int GRASS_HEIGHT = 8;

    private Color grassColor;

    public Ground() {
        width = DEFAULT_WIDTH;
        height = MIN_HEIGHT + Random.getInt(MIN_HEIGHT);
        blockColor = ColorPalette.brown;
        grassColor = ColorPalette.green;
        xPos = WindyDayGame.X_FRAME_DIM;
        yPos = WindyDayGame.Y_FRAME_DIM - height;
        xVelocity = -1;
        yVelocity = 0;
    }

    public Ground(int startingX) {
        this();
        xPos = startingX;
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(grassColor);
        g.drawRect(xPos, yPos, width, GRASS_HEIGHT);
        g.fillRect(xPos, yPos, width, GRASS_HEIGHT);
    }
}
