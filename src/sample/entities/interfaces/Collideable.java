package sample.entities.interfaces;

import javafx.geometry.Rectangle2D;
import sample.util.Point;

public interface Collideable extends Locateable {
    default boolean collides(){ return true; }
    Rectangle2D getBoundary();
    double getHeight();
    double getWidth();
    boolean intersects(Collideable other);
    default boolean intersects(Point point) {
        Point location = getLocation();
        return location.getX() < point.getX() &&
                location.getY() < point.getY() &&
                (location.getX() + getWidth()) > point.getX() &&
                (location.getY() + getHeight()) > point.getY();
    }
}
