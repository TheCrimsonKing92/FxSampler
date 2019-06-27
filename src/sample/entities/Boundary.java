package sample.entities;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Line;
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
        Line line = new Line(getLocation().getX(), getLocation().getY(), getEnd().getX(), getEnd().getY());
        Group root = Manager.getRoot();
        root.getChildren().add(line);
    }

    protected abstract Point calculateEnd(Point location, double height, double width);

    protected abstract double getLineWidth();

    protected Point getEnd() { return end; }
}
