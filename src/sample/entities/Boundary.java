package sample.entities;

import javafx.scene.canvas.GraphicsContext;
import sample.util.Point;

public abstract class Boundary extends Entity {
    private Point end;

    public Boundary(Point location, double height, double width) {
        super(location, height, width);
        this.end = calculateEnd(getLocation(), getHeight(), getWidth());
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.strokeLine(getLocation().getX(), getLocation().getY(), getEnd().getX(), getEnd().getY());
    }

    protected abstract Point calculateEnd(Point location, double height, double width);

    protected abstract double getLineWidth();

    protected Point getEnd() { return end; }
}
