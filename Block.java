package src;
import java.awt.Color;
import java.awt.Point;

/**
 * this block is the abstract superclass of the:
 * 1. head
 * 2. body
 * 3. food
 */
abstract class Block {

    private Point loc;
    private final Color color;

    public Block(Point loc, Color color) {
        this.loc = loc;
        this.color = color;
    }

    /* getters and setters */
    abstract void setLoc(Point loc);
    abstract void setColor(Color color);
    abstract Point getLoc();
    abstract Color getColor();
}