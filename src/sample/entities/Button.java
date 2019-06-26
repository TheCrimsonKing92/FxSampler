package sample.entities;

import javafx.scene.canvas.GraphicsContext;
import sample.util.Point;

public class Button extends Entity {

    public Button(Point location, double height, double width) {
        super(location, height, width);
    }

    @Override
    public void draw(GraphicsContext gc) {
    }
}
