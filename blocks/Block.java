package windyday.blocks;

import java.awt.Color;
import java.awt.Graphics;

/*
 * Josh Coates - Block Parent Class
 */

public class Block {

    public static final int MIN_WIDTH = 3;

    protected int width;
    protected int height;
    protected Color blockColor;
    protected int xPos;
    protected int yPos;
    protected int xVelocity;
    protected int yVelocity;

    protected Block() {
    }

    public void update(int timeStep) {
        xPos = (xPos + timeStep * xVelocity);
        yPos = (yPos + timeStep * yVelocity);
    }

    public void paint(Graphics g) {
        g.setColor(blockColor);
        g.drawRect(xPos, yPos, width, height);
        g.fillRect(xPos, yPos, width, height);
    }

// Getters & Setters

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public int getXVelocity() {
        return xVelocity;
    }

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public int getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }
}
