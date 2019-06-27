package sample.entities;

import javafx.scene.image.Image;
import sample.util.Constants;
import sample.util.Point;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HorizontalWall extends Sprite {
    private static Image wall;

    static {
        wall = Manager.getImage("HorizontalWall");
    }

    public static List<HorizontalWall> Walls(double x, double y, int walls) {
        return Walls(x, y, walls, -1);
    }

    public static List<HorizontalWall> Walls(double x, double y, int walls, int xOffset) {
        return IntStream.iterate(0, i -> i + 1)
                        .limit(walls)
                        .mapToDouble(index -> x + (index * (Constants.ENTITIES.WALLS.HORIZONTAL.WIDTH - xOffset)))
                        .mapToObj(left -> new HorizontalWall(Point.of(left, y)))
                        .collect(Collectors.toList());
    }

    public HorizontalWall(Point location) {
        this(wall, location);
    }

    public HorizontalWall(Image image, Point location) {
        super(image, location, Constants.ENTITIES.WALLS.HORIZONTAL.HEIGHT, Constants.ENTITIES.WALLS.HORIZONTAL.WIDTH);
    }
}
