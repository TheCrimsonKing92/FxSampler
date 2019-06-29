package sample.entities;

import javafx.scene.image.Image;
import sample.resources.ImageLoader;
import sample.util.*;

public class Player extends Sprite {
    private static Image solemnMan;

    static {
        solemnMan = ImageLoader.getImage("SolemnMan");
    }

    public Player() {
        this(Point.of(100, 100));
        setxVelocity(5);
        setyVelocity(3);
    }

    public Player(Point location) {
        this(solemnMan, location);
    }

    public Player(Image image, Point location) {
        super(image, location, Constants.ENTITIES.PLAYER.HEIGHT, Constants.ENTITIES.PLAYER.WIDTH, 4, 4);
    }

    public void handle(PlayerMoveEvent event) {
        MoveDetails body = event.getEvent().getBody();
        Direction direction = body.getDirection();

        if (body.started()) {
            handleMoveStart(direction);
        } else {
            handleMoveStop(direction);
        }
    }

    @Override
    public boolean canTick() {
        return true;
    }
}
