package sample;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import sample.util.Point;

import java.util.HashSet;
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

    public static void addHandlers(Scene scene) {
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
