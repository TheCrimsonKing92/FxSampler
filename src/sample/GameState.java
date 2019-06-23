package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import sample.entities.Entity;
import sample.entities.Message;
import sample.entities.Player;
import sample.entities.interfaces.Clickable;
import sample.util.Constants;
import sample.util.Point;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

public class GameState {
    private final static long startNanoTime = System.nanoTime();

    private static double currentTime;
    private static double delta;
    private static boolean paused = false;
    private static Wrapper<Boolean> pausedRecently = new Wrapper<>(false);
    private static double previousTime;

    private GameState() {}

    public static double getDelta() { return delta; }

    public static void start(GraphicsContext gc) {
        new AnimationTimer() {
            public void handle(long newTime) {
                setCurrentTime(newTime);

                updateGame();
                renderGame(gc);
            }
        }.start();
    }

    private static void handleClick(Point point) {
        Manager.getEntities()
               .stream()
               .filter(Clickable.class::isInstance)
               .filter(entity -> entity.intersects(point))
               .map(Clickable.class::cast)
               .forEach(Clickable::onClick);

        EventHandler.removeClick(point);
    }

    private static void handleImmediateInput(KeyCode keyCode) {
        switch (keyCode) {
            case ENTER:
            case SPACE:
                pause();
                break;
            case ESCAPE:
                // menu
                break;
            default:
                System.out.println("Unknown immediate input " + keyCode);
                break;
        }
    }

    private static void handleImmediateInputs() {
        if (!EventHandler.anyCurrentImmediateInputs() && !EventHandler.anyCurrentClicks()) {
            return;
        }

        Set<KeyCode> gameInputs = EventHandler.getCurrentImmediateKeys();
        gameInputs.forEach(GameState::handleImmediateInput);
        pausedRecently.setValue(false);

        Set<Point> clicks = EventHandler.getCurrentClicks();
        clicks.forEach(GameState::handleClick);
    }

    private static boolean hasCollision(Player player) {
        return Manager.getEntities()
                      .stream()
                      .filter(entity -> entity != player)
                      .anyMatch(player::intersects);
    }

    private static void pause() {
        if (pausedRecently.getValue()) {
            return;
        } else {
            pausedRecently.setValue(true);
        }

        paused = !paused;
        togglePauseMessage();
    }

    private static void renderGame(GraphicsContext gc) {
        gc.clearRect(0, 0, Constants.CANVAS.WIDTH, Constants.CANVAS.HEIGHT);

        Manager.getMessages().forEach(message -> message.draw(gc));
        Manager.getEntities().forEach(entity -> entity.draw(gc));
    }

    private static void setCurrentTime(long newTime) {
        previousTime = currentTime;
        currentTime = (newTime - startNanoTime) / 1_000_000_000;
        delta = currentTime - previousTime;
    }

    private static void togglePauseMessage() {
        if (paused) {
            Manager.add(new Message("PAUSED", Point.of(20, 50), Color.BLACK, null));
        } else {
            Manager.remove("PAUSED");
        }
    }

    private static void updateGame() {
        handleImmediateInputs();

        if (paused) {
            return;
        }

        updatePlayer();
        List<Entity> entities = Manager.getEntities();
        for (Entity next : entities) {
            if (next.canTick()) {
                next.tick(delta);
            }
        }

        Manager.removeEntities();
    }

    private static void updatePlayer() {
        if (!EventHandler.anyCurrentPlayerInput()) {
            return;
        }

        Set<KeyCode> playerInputs = EventHandler.getCurrentPlayerKeys();

        Player player = Manager.getPlayer();
        playerInputs.forEach(input -> {
            switch (input) {
                case UP:
                case W:
                    player.moveY(5);
                    if (hasCollision(player)) {
                        player.revertLast();
                    }
                    break;
                case RIGHT:
                case D:
                    player.moveX(5);
                    if (hasCollision(player)) {
                        player.revertLast();
                    }
                    break;
                case DOWN:
                case S:
                    player.moveY(-5);
                    if (hasCollision(player)) {
                        player.revertLast();
                    }
                    break;
                case LEFT:
                case A:
                    player.moveX(-5);
                    if (hasCollision(player)) {
                        player.revertLast();
                    }
                    break;
                default:
                    System.out.println("Unknown player input type " + input);
                    break;
            }
        });
    }
}
