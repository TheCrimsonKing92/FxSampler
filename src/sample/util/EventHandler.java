package sample.util;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import sample.contexts.ApplicationContext;
import sample.contexts.GameContext;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EventHandler {
    private static Set<KeyCode> immediateInputs = Set.of(KeyCode.ENTER, KeyCode.ESCAPE, KeyCode.SPACE);
    private static Set<KeyCode> playerInputs = Set.of(
            KeyCode.UP,
            KeyCode.DOWN,
            KeyCode.LEFT,
            KeyCode.RIGHT,
            KeyCode.W,
            KeyCode.A,
            KeyCode.S,
            KeyCode.D
    );

    private static KeyCode lastPressed;
    private static KeyCode lastReleased;
    private Set<Point> currentClicks = new HashSet<>();
    private Set<KeyCode> currentImmediateKeys = new HashSet<>();
    private Set<KeyCode> currentPlayerKeys = new HashSet<>();

    private GameContext gameContext;
    private List<ApplicationContext> applicationContexts;

    public EventHandler(GameContext gameContext, List<ApplicationContext> applicationContexts) {
        this.gameContext = gameContext;
        this.applicationContexts = applicationContexts;
    }

    public void addHandlers(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
            KeyCode code = keyEvent.getCode();

            if (isPlayerInput(code)) {
                Optional.ofNullable(PlayerMoveEvent.startFromKey(code))
                        .ifPresent(gameContext::handle);
            }
        });
        scene.setOnKeyReleased(keyEvent -> {
            KeyCode code = keyEvent.getCode();

            if (isPlayerInput(code)) {
                Optional.ofNullable(PlayerMoveEvent.stopFromKey(code))
                        .ifPresent(gameContext::handle);
            }

            if (isImmediateInput(code)) {
                gameContext.handle(GameChangedEvent.fromKey(code));
            }
        });

        scene.setOnMouseClicked(event -> applicationContexts.forEach(ctx -> ctx.handle(Point.of(event.getX(), event.getY()))));
    }

    public boolean anyCurrentClicks() { return !currentClicks.isEmpty(); }

    public boolean anyCurrentImmediateInputs() { return !currentImmediateKeys.isEmpty(); }

    public boolean anyCurrentPlayerInput() { return !currentPlayerKeys.isEmpty(); }

    public Set<Point> getCurrentClicks() { return currentClicks; }

    public Set<KeyCode> getCurrentImmediateKeys() { return currentImmediateKeys; }

    public Set<KeyCode> getCurrentPlayerKeys() { return currentPlayerKeys; }

    public void removeClick(Point point) {
        currentClicks.remove(point);
    }

    private static boolean isImmediateInput(KeyCode key) {
        return immediateInputs.contains(key);
    }

    private static boolean isPlayerInput(KeyCode key) {
        return playerInputs.contains(key);
    }
}
