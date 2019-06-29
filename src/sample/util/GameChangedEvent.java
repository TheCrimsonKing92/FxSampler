package sample.util;

import javafx.scene.input.KeyCode;

public class GameChangedEvent extends EventWrapper<ContextEvent<GameChangeType>> {
    public GameChangedEvent(GameChangeType type) {
        super(new ContextEvent<>(type));
    }

    public static GameChangedEvent fromKey(KeyCode code) {
        switch (code) {
            case ENTER:
            case SPACE:
                return new GameChangedEvent(GameChangeType.PAUSE);
            case ESCAPE:
            default:
                return new GameChangedEvent(GameChangeType.MENU);
        }
    }
}
