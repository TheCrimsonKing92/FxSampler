package sample.util;

import javafx.scene.input.KeyCode;

public class PlayerMoveEvent extends EventWrapper<ContextEvent<MoveDetails>> {
    public PlayerMoveEvent(MoveDetails details) { super(new ContextEvent<>(details)); }

    public static PlayerMoveEvent startFromKey(KeyCode code) {
        switch (code) {
            case W:
            case UP:
                return new PlayerMoveEvent(MoveDetails.start(Direction.UP));
            case A:
            case LEFT:
                return new PlayerMoveEvent(MoveDetails.start(Direction.LEFT));
            case S:
            case DOWN:
                return new PlayerMoveEvent(MoveDetails.start(Direction.DOWN));
            case D:
            case RIGHT:
                return new PlayerMoveEvent(MoveDetails.start(Direction.RIGHT));
            default:
                System.out.println("Unknown player move code");
                return null;
        }
    }

    public static PlayerMoveEvent stopFromKey(KeyCode code) {
        switch (code) {
            case W:
            case UP:
                return new PlayerMoveEvent(MoveDetails.stop(Direction.UP));
            case A:
            case LEFT:
                return new PlayerMoveEvent(MoveDetails.stop(Direction.LEFT));
            case S:
            case DOWN:
                return new PlayerMoveEvent(MoveDetails.stop(Direction.DOWN));
            case D:
            case RIGHT:
                return new PlayerMoveEvent(MoveDetails.stop(Direction.RIGHT));
            default:
                System.out.println("Unknown player move code");
                return null;
        }
    }
}
