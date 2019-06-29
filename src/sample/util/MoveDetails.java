package sample.util;

public class MoveDetails {
    private boolean stopped;
    private Direction direction;

    public static MoveDetails start(Direction direction) { return new MoveDetails(direction, false); }

    public static MoveDetails stop(Direction direction) { return new MoveDetails(direction); }

    public MoveDetails(Direction direction) {
        this(direction, true);
    }

    public MoveDetails(Direction direction, boolean stopped) {
        this.direction = direction;
        this.stopped = stopped;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean started() {
        return !stopped;
    }

    public boolean stopped() {
        return stopped;
    }
}
