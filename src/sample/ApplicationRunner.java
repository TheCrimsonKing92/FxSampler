package sample;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sample.contexts.ApplicationContext;
import sample.contexts.GameContext;
import sample.entities.Entity;
import sample.entities.Message;
import sample.entities.MessagePane;
import sample.entities.Player;
import sample.entities.interfaces.Clickable;
import sample.util.Constants;
import sample.util.Point;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ApplicationRunner {
    private final static long startNanoTime = System.nanoTime();

    private static double currentTime;
    private static double delta;
    private static boolean paused = false;
    private static Wrapper<Boolean> pausedRecently = new Wrapper<>(false);
    private static double previousTime;

    private List<ApplicationContext> applicationContexts;
    private Scene scene;

    public ApplicationRunner(List<ApplicationContext> applicationContexts) {
        this.applicationContexts = applicationContexts;
        this.scene = generateScene();
    }

    private Scene generateScene() {
        VBox box = new VBox();
        ObservableList<Node> children = box.getChildren();
        getContexts().stream()
                     .map(ApplicationContext::getNode)
                     .map(Node.class::cast)
                     .forEach(children::add);
        Scene scene = new Scene(box);
        EventHandler handler = new EventHandler(getGameContext(), getContexts());
        handler.addHandlers(scene);

        return scene;
    }

    private GameContext getGameContext() {
        return getContexts().stream()
                            .filter(GameContext.class::isInstance)
                            .map(GameContext.class::cast)
                            .findFirst()
                            .orElse(null);
    }

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

    public Scene getScene() { return scene; }

    public void start() {
        new AnimationTimer() {
            public void handle(long newTime) {
                setCurrentTime(newTime);
                updateContexts();
                renderContexts();
            }
        }.start();
    }

    private List<ApplicationContext> getContexts() {
        return applicationContexts;
    }

    private static void handleClick(Point point) {
        Manager.getEntities()
               .stream()
               .filter(Clickable.class::isInstance)
               .filter(entity -> entity.intersects(point))
               .map(Clickable.class::cast)
               .forEach(clickable -> clickable.onClick(point));

        Manager.getMessages()
               .stream()
               .filter(MessagePane.class::isInstance)
               .map(Clickable.class::cast)
               .forEach(clickable -> clickable.onClick(point));

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
        gameInputs.forEach(ApplicationRunner::handleImmediateInput);
        pausedRecently.setValue(false);

        Set<Point> clicks = EventHandler.getCurrentClicks();
        clicks.forEach(ApplicationRunner::handleClick);
    }

    private static boolean hasCollision(Player player) {
        return Manager.getEntities()
                      .stream()
                      .filter(Entity::collides)
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

    private void renderContexts() {
        getContexts().forEach(ApplicationContext::render);
    }

    private static void setCurrentTime(long newTime) {
        previousTime = currentTime;
        currentTime = (newTime - startNanoTime) / 1_000_000_000;
        delta = currentTime - previousTime;
    }

    private static void togglePauseMessage() {
        if (paused) {
            Manager.add();
        } else {
            Manager.remove("PAUSED");
        }
    }

    private void updateContexts() {
        double delta = getDelta();
        getContexts().forEach(context -> context.update(delta));
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
        Manager.removeMessages();
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
