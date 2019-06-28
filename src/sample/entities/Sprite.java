package sample.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.entities.interfaces.Moveable;
import sample.util.Point;

public abstract class Sprite extends Entity implements Moveable {
    private Image image;
    private Point lastLocation;
    private double xVelocity = 0.00;
    private double yVelocity = 0.00;

    public Sprite(Image image, Point location, double height, double width) {
        super(location, height, width);
        this.image = image;
        this.lastLocation = location;
    }

    public Sprite(Image image, Point location, double height, double width, double xVelocity, double yVelocity) {
        this(image, location, height, width);
        setxVelocity(xVelocity);
        setyVelocity(yVelocity);
    }


    @Override
    public final void draw(GraphicsContext gc) {
        Point location = getLocation();
        gc.drawImage(image, location.getX(), location.getY(), getWidth(), getHeight());
    }

    @Override
    public void moveX(double diff) {
        setLocation(getLocation().plusX(diff));
    }

    public void moveX() { moveX(xVelocity); }

    @Override
    public void moveY(double diff) {
        setLocation(getLocation().minusY(diff));
    }

    public void moveY() { moveY(yVelocity); }

    @Override
    public void move(double x, double y) {
        setLocation(getLocation().plusX(x).minusY(y));
    }

    public void reverseX() { setLocation(getLocation().minusX(xVelocity)); }

    public void reverseY() { setLocation(getLocation().plusY(yVelocity));}

    public void revertLast() { setLocation(lastLocation); }

    public void setxVelocity(double velocity) {
        xVelocity = velocity;
    }

    public void setyVelocity(double velocity) {
        yVelocity = velocity;
    }

    public void stop() {
        xVelocity = 0.00;
        yVelocity = 0.00;
    }

    public void update(double steps) {
        if (xVelocity > 0) {
            moveX(xVelocity * steps);
        }

        if (yVelocity >0) {
            moveY(yVelocity * steps);
        }
    }

    protected void setLocation(Point point) {
        lastLocation = getLocation();
        super.setLocation(point);
    }

    private void setLocation(double x, double y) {
        setLocation(Point.of(x, y));
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "height=" + getHeight() +
                ", image=" + image +
                ", lastLocation=" + lastLocation +
                ", location=" + getLocation() +
                ", width=" + getWidth() +
                ", xVelocity=" + xVelocity +
                ", yVelocity=" + yVelocity +
                '}';
    }
}
