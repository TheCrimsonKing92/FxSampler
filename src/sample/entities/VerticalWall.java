package sample.entities;

import javafx.scene.image.Image;
import sample.resources.ImageLoader;
import sample.util.Constants;
import sample.util.Point;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VerticalWall extends Sprite {
    private static Image image;
    static {
        image = ImageLoader.getImage("VerticalWall");
    }

    /*
    public static List<HorizontalWall> Walls(double x, double y, int walls) {
        return Walls(x, y, walls, -1);
    }

    public static List<HorizontalWall> Walls(double x, double y, int walls, int xOffset) {
        return IntStream.iterate(0, i -> i + 1)
                .limit(walls)
                .mapToDouble(index -> x + (index * (Constants.ENTITIES.WALLS.HORIZONTAL.WIDTH - xOffset)))
                .mapToObj(left -> new HorizontalWall(Point.of(left, y)))
                .collect(Collectors.toList());
    }*/

    public static List<VerticalWall> Walls(double x, double y, int walls) {
        return Walls(x, y, walls, 0);
    }

    public static List<VerticalWall> Walls(double x, double y, int walls, int yOffset) {
        return IntStream.iterate(0, i -> i + 1)
                        .limit(walls)
                        .mapToDouble(index -> y + (index * (Constants.ENTITIES.WALLS.VERTICAL.HEIGHT - yOffset)))
                        .mapToObj(next -> new VerticalWall(Point.of(x, next)))
                        .collect(Collectors.toList());
    }

    public VerticalWall(Point location) {
        this(image, location);
    }

    public VerticalWall(Image image, Point location) {
        super(image, location, Constants.ENTITIES.WALLS.VERTICAL.HEIGHT, Constants.ENTITIES.WALLS.VERTICAL.WIDTH);
    }
}
