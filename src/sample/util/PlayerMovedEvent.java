package sample.util;

public class PlayerMovedEvent extends EventWrapper<ContextEvent<Direction>> {
    public PlayerMovedEvent(Direction direction) {
        super(new ContextEvent<>(direction));
    }
}
