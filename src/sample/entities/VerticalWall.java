package sample.entities;

import javafx.scene.image.Image;
import sample.resources.ImageLoader;
import sample.util.Constants;
import sample.util.Point;

public class VerticalWall extends Sprite {
    private static Image image;
    static {
        image = ImageLoader.getImage("VerticalWall.png");
    }

    public VerticalWall(Point location) {
        this(image, location);
    }

    public VerticalWall(Image image, Point location) {
        super(image, location, Constants.ENTITIES.WALLS.VERTICAL.HEIGHT, Constants.ENTITIES.WALLS.VERTICAL.WIDTH);
    }
}
