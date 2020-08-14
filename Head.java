package src;
import java.awt.Color;
import java.awt.Point;

/**
 * food is nom
 */
public class Head extends Body {

    /* fields */
    private Point loc;
    private Color color;

    private Body next;

    /* constructor */
    public Head(Point loc, Color color) {
        super(loc, color);

        this.loc = loc;
        this.color = color;
    }

    /* getters and setters */
    public void setNext(Body next) {
        this.next = next;
    }

    public Body getNext() {
        return next;
    }

    /* overrides of abstract class */
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