package sample.entities.interfaces;

import javafx.scene.canvas.GraphicsContext;
import sample.entities.interfaces.Locateable;

public interface Drawable extends Locateable {
    void draw(GraphicsContext gc);
}
