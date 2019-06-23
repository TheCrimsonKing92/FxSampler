package sample.entities;

import javafx.scene.image.Image;
import sample.util.Constants;
import sample.Manager;
import sample.util.Point;

public class Player extends Sprite {
    private static Image solemnMan;

    static {
        solemnMan = Manager.getImage("SolemnMan.png");
    }

    public Player() {
        this(Point.of(100, 100));
    }

    public Player(Point location) {
        this(solemnMan, location);
    }

    public Player(Image image, Point location) {
        super(image, location, Constants.ENTITIES.PLAYER.HEIGHT, Constants.ENTITIES.PLAYER.WIDTH, 4, 4);
    }
}
