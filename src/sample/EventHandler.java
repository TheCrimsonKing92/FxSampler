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
    private static Set<Point> currentClicks = new HashSet<>();
    private static Set<KeyCode> currentImmediateKeys = new HashSet<>();
    private static Set<KeyCode> currentPlayerKeys = new HashSet<>();

    private GameContext keyboardContext;
    private List<ApplicationContext> mouseContexts;

    public EventHandler(GameContext keyboardContext, List<ApplicationContext> mouseContexts) {
        this.keyboardContext = keyboardContext;
        this.mouseContexts = mouseContexts;
    }

    public void addHandlers(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
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
                keyboardContext.handle(gameChange);
            } else {
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
                        move = null;
                        break;
                }
                if (move == null) {
                    return;
                } else {
                    keyboardContext.handle(move);
                }

            }
        });
        scene.setOnKeyReleased(keyEvent -> {

        });
        scene.setOnMouseClicked(mouseEvent -> {
            System.out.println("Clicked at " + mouseEvent.getX() + ", " + mouseEvent.getY());
        });
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (isImmediateInput(code)) {
                if (currentImmediateKeys.contains(code)) {
                    return;
                }

                currentImmediateKeys.add(code);
                lastPressed = code;
            } else if (isPlayerInput(code)) {
                currentPlayerKeys.add(code);
                lastPressed = code;
            } else {
                System.out.println("Unhandled KeyCode " + code);
            }
        });

        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            if (isImmediateInput(code)) {
                currentImmediateKeys.remove(code);
                lastReleased = code;
            } else if (isPlayerInput(code)) {
                currentPlayerKeys.remove(code);
                lastReleased = code;
            } else {
                System.out.println("Unhandled KeyCode " + code);
            }
        });

        scene.setOnMouseClicked(event -> {
            currentClicks.add(Point.of(event.getX(), event.getY()));
        });
    }

    public static boolean anyCurrentClicks() { return !currentClicks.isEmpty(); }

    public static boolean anyCurrentImmediateInputs() { return !currentImmediateKeys.isEmpty(); }

    public static boolean anyCurrentPlayerInput() { return !currentPlayerKeys.isEmpty(); }

    public static Set<Point> getCurrentClicks() { return currentClicks; }

    public static Set<KeyCode> getCurrentImmediateKeys() { return currentImmediateKeys; }

    public static Set<KeyCode> getCurrentPlayerKeys() { return currentPlayerKeys; }

    public static void removeClick(Point point) {
        currentClicks.remove(point);
    }

    private static boolean isImmediateInput(KeyCode key) {
        return immediateInputs.contains(key);
    }

    private static boolean isPlayerInput(KeyCode key) {
        return playerInputs.contains(key);
    }
}
