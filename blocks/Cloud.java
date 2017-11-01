package windyday.blocks;

import windyday.utils.*;

/*
 * Josh Coates - Cloud.
 */

public class Cloud extends Block {
    private static final int MIN_CLOUD_WIDTH = 40;
    private static final int LOWER_BOUND_Y_POS = 80;

    public Cloud() {
        width = MIN_CLOUD_WIDTH * Random.getInt(3);
        height = width / 2;
        blockColor = ColorPalette.lightGrey;
        xPos = 0;
        yPos = Random.getInt(LOWER_BOUND_Y_POS);
        xVelocity = 1;
        yVelocity = 0;
    }

    public Cloud(int startingX) {
        this();
        xPos = startingX;
    }
}
