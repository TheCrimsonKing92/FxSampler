package sample.entities;

import javafx.scene.image.Image;
import sample.util.Point;

public abstract class TickableSprite extends Sprite {
    public TickableSprite(Image image, Point location, double height, double width) {
        super(image, location, height, width);
    }

    public TickableSprite(Image image, Point location, double height, double width, double xVelocity, double yVelocity) {
        super(image, location, height, width, xVelocity, yVelocity);
    }

    @Override
    public boolean canTick() {
        return true;
    }

    @Override
    public abstract void tick(double ticks);
}
