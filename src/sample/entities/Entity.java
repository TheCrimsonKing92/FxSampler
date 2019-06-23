package sample.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import sample.entities.interfaces.Collideable;
import sample.entities.interfaces.Drawable;
import sample.entities.interfaces.Tickable;
import sample.util.Point;

public abstract class Entity implements Collideable, Drawable, Tickable {
    private Point location;
    private double height;
    private double width;

    public Entity(Point location, double height, double width) {
        this.location = location;
        this.height = height;
        this.width = width;
    }

    @Override
    public abstract void draw(GraphicsContext gc);

    public Rectangle2D getBoundary() {
        return new Rectangle2D(location.getX(), location.getY(), width, height);
    }

    public double getHeight() { return height; }

    public Point getLocation() { return location; }

    public double getWidth() { return width; }

    @Override
    public boolean intersects(Collideable that) {
        return that.getBoundary().intersects(this.getBoundary());
    }

    public boolean intersects(Point point) {
        Point location = getLocation();
        return location.getX() < point.getX() &&
                location.getY() < point.getY() &&
                (location.getX() + getWidth()) > point.getX() &&
                (location.getY() + getHeight()) > point.getY();
    }

    protected void setLocation(Point point) { this.location = point; }
}
