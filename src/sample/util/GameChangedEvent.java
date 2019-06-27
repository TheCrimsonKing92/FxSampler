package sample.util;

public class GameChangedEvent extends EventWrapper<ContextEvent<GameChangeType>> {
    public GameChangedEvent(GameChangeType type) {
        super(new ContextEvent<>(type));
    }
}
