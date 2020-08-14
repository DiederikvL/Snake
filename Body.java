package src;

import java.awt.Color;
import java.awt.Point;

/**
 * food is nom
 */
public class Body extends Block {

    /* fields */
    private Point loc;
    private Color color;

    private Body prev;
    private Body next;

    /* constructor */
    public Body(Point loc, Color color) {
        super(loc, color);

        this.loc = loc;
        this.color = color;
    }

    /* getters and setters */
    public void setNext(Body next) {
        this.next = next;
    }

    public void setPrev(Body prev) {
        this.prev = prev;
    }

    public Body getNext() {
        return next;
    }

    public Body getPrev() {
        return prev;
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

    /* check if o collides */
    public boolean isCollision(Block o) {

        /* following is true when o and head collide */
        return (getLoc().x == o.getLoc().x && getLoc().y == o.getLoc().y);

    }

}