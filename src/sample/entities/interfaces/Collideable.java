package sample.entities.interfaces;

import javafx.geometry.Rectangle2D;

public interface Collideable extends Locateable {
    Rectangle2D getBoundary();
    double getHeight();
    double getWidth();
    boolean intersects(Collideable other);
}
