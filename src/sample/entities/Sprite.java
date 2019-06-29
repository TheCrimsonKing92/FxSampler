package sample.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.entities.interfaces.Moveable;
import sample.util.Direction;
import sample.util.MoveDetails;
import sample.util.PlayerMoveEvent;
import sample.util.Point;

public abstract class Sprite extends Entity implements Moveable {
    private Image image;
    private Point lastLocation;
    private boolean xMoving = false;
    private boolean xPositive = true;
    private double xVelocity = 5;
    private boolean yMoving = false;
    private boolean yPositive = true;
    private double yVelocity = 5;

    protected boolean isxMoving() { return xMoving; }
    protected boolean isyMoving() { return yMoving; }
    protected boolean isMoving() { return isxMoving() || isyMoving(); }

    protected void handleMoveStart(Direction direction) {
        if (direction == Direction.UP) {
            yPositive = true;
            yMoving = true;
        } else if (direction == Direction.RIGHT) {
            xPositive = true;
            xMoving = true;
        } else if (direction == Direction.DOWN) {
            yPositive = false;
            yMoving = true;
        } else if (direction == Direction.LEFT) {
            xPositive = false;
            xMoving = true;
        }
    }

    protected void handleMoveStop(Direction direction) {
        if (direction == Direction.UP || direction == Direction.DOWN) {
            yMoving = false;
        } else if (direction == Direction.RIGHT || direction == Direction.LEFT) {
            xMoving = false;
        }
    }

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

    public void startMove() {
        startXMove();
        startYMove();
    }

    public void startXMove() {
        xMoving = true;
    }

    public void startYMove() {
        yMoving = true;
    }

    public void stopMove() {
        stopXMove();
        stopYMove();
    }

    public void stopXMove() {
        xMoving = false;
    }

    public void stopYMove() {
        yMoving = false;
    }

    @Override
    public void tick(double steps) {
        double xMovement = xMoving ? (xPositive ? xVelocity : -1 * xVelocity) * steps : 0;
        double yMovement = yMoving ? (yPositive ? yVelocity : -1 * yVelocity) * steps : 0;

        move(xMovement, yMovement);
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
