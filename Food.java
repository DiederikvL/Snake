package src;
import java.awt.Color;
import java.awt.Point;

/**
 * food is nom
 */
public class Food extends Block {

    /* fields */
    private Point loc;
    private Color color;

    public Food(Point loc, Color color) {
        super(loc, color);

        this.loc = loc;
        this.color = color;
    }

    /* getters and setters */
    @Override
    void setLoc(Point loc) {
        this.loc = loc;
    }

    @Override
    void setColor(Color color) {
        this.color = color;
    }

    @Override
    Point getLoc() {
        return loc;
    }

    @Override
    Color getColor() {
        return color;
    }

}