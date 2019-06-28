package sample;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import sample.contexts.ApplicationContext;
import sample.contexts.GameContext;
import sample.util.*;

import java.util.HashSet;
import java.util.List;
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
                PlayerMovedEvent move;
                switch (code) {
                    case W:
                    case UP:
                        move = new PlayerMovedEvent(Direction.UP);
                        break;
                    case A:
                    case LEFT:
                        move = new PlayerMovedEvent(Direction.LEFT);
                        break;
                    case S:
                    case DOWN:
                        move = new PlayerMovedEvent(Direction.DOWN);
                        break;
                    case D:
                    case RIGHT:
                        move = new PlayerMovedEvent(Direction.RIGHT);
                        break;
                    default:
                        System.out.println("Unknown player move code");
                        return;
                }

                gameContext.handle(move);
            }
        });
        scene.setOnKeyReleased(keyEvent -> {
            KeyCode code = keyEvent.getCode();

            if (isImmediateInput(code)) {
                GameChangedEvent gameChange;
                switch (code) {
                    case ENTER:
                    case SPACE:
                        gameChange = new GameChangedEvent(GameChangeType.PAUSE);
                        break;
                    case ESCAPE:
                    default:
                        gameChange = new GameChangedEvent(GameChangeType.MENU);
                        break;
                }
                gameContext.handle(gameChange);
            }
        });

        scene.setOnMouseClicked(event -> gameContext.handle(Point.of(event.getX(), event.getY())));
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
