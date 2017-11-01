package windyday.blocks;

import windyday.*;
import windyday.utils.*;

/*
 * Josh Coates - Leaf extends Block.
 */

public class Leaf extends Block {

    private static final int LOWER_BOUND_Y_POS = (int) (0.2 * WindyDayGame.Y_FRAME_DIM);
    private static final int UPPER_BOUND_Y_POS = LOWER_BOUND_Y_POS + (int) (0.65 * WindyDayGame.Y_FRAME_DIM);

    public Leaf() {
        int sideLength = Block.MIN_WIDTH * Random.getInt(3);
        width = height = sideLength;
        blockColor = ColorPalette.darkGreen;
        xPos = 0;
        yPos = LOWER_BOUND_Y_POS + Random.getInt(UPPER_BOUND_Y_POS - LOWER_BOUND_Y_POS);
        xVelocity = Random.getInt(3) + 1;
        yVelocity = 0;
    }
}
