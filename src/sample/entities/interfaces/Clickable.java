package sample.entities.interfaces;

import sample.util.Point;

public interface Clickable extends Locateable {
    void onClick(Point point);
}
