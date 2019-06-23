package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.entities.*;
import sample.resources.ImageLoader;
import sample.util.Constants;
import sample.util.Point;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Manager {
    private static Canvas canvas;
    private static GraphicsContext gc;
    private static Player player = new Player();
    private static Group root;
    private static Scene scene;
    private static boolean sceneStarted = false;

    // Synchronized access as opposed to ArrayList
    private static Vector<Entity> entities = new Vector<>();
    private static Vector<Entity> entitiesToRemove = new Vector<>();

    private static ConcurrentHashMap<String, Message> messageMap = new ConcurrentHashMap<>();
    private static Vector<Message> messages = new Vector<>();

    static {
        add(player);
        add(new VerticalWall(Point.of(75, 286)));
        add(new VerticalWall(Point.of(705, 286)));
        addAll(HorizontalWall.Walls(75, 300, 15, -1));
        addAll(HorizontalWall.Walls(435, 300, 15, -1));
        add(new HorizontalBoundary(Point.of(0, Constants.CANVAS.HEIGHT), 1, Constants.CANVAS.WIDTH));
        add(new HorizontalBoundary(Point.of(0, 0), 1, Constants.CANVAS.WIDTH));
        add(new VerticalBoundary(Point.of(0, 0), Constants.CANVAS.HEIGHT, 1));
        add(new VerticalBoundary(Point.of(Constants.CANVAS.WIDTH, 0), Constants.CANVAS.HEIGHT, 1));
        add(new Fire(Point.of(300, 200)));
    }

    public static boolean add(Entity entity) {
        if (entities.contains(entity)) {
            return false;
        }

        entities.add(entity);
        return true;
    }

    public static boolean add(Message message) {
        if (messages.contains(message)) {
            return false;
        }

        messages.add(message);
        messageMap.put(message.getText(), message);
        return true;
    }

    public static <T extends Entity> void addAll(List<T> toAdd) {
        entities.addAll(toAdd);
    }

    public static Canvas getCanvas() { return canvas; }

    public static List<Entity> getEntities() {
        return entities.stream()
                       .filter(entity -> !entitiesToRemove.contains(entity))
                       .collect(Collectors.toList());
    }

    public static GraphicsContext getGraphicsContext() { return gc; }

    public static Image getImage(String file) { return ImageLoader.getImage(file); }

    public static Vector<Message> getMessages() { return messages; }

    public static Player getPlayer() { return player; }

    public static Group getRoot() { return root; }

    public static Scene getScene() {
        if (!sceneStarted) {
            init();
        }

        return scene;
    }

    public static void remove(Entity entity) {
        if (entity == null) {
            return;
        }

        markForRemoval(entity);

        entities.removeIf(next -> next == entity);
    }

    public static void remove(String message) {
        if (!messageMap.containsKey(message)) {
            return;
        }

        Message toRemove = messageMap.get(message);
        messages.remove(toRemove);
        messageMap.remove(message);
    }

    public static void removeEntities() {
        entities.removeAll(entitiesToRemove);
        entitiesToRemove.clear();
    }

    private static void init() {
        root = new Group();
        scene = new Scene(root, Constants.CANVAS.WIDTH, Constants.CANVAS.HEIGHT);
        canvas = new Canvas(Constants.CANVAS.WIDTH, Constants.CANVAS.HEIGHT);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        GameState.start(gc);

        EventHandler.addHandlers(scene);
        sceneStarted = true;
    }

    private static void markForRemoval(Entity entity) {
        entitiesToRemove.add(entity);
    }
}
