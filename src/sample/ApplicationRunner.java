package sample;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import sample.contexts.ApplicationContext;
import sample.contexts.GameContext;

import java.util.List;

public class ApplicationRunner {
    private final static long startNanoTime = System.nanoTime();

    private static double currentTime;
    private static double delta;
    private static double previousTime;

    private List<ApplicationContext> applicationContexts;
    private EventHandler eventHandler;
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
        eventHandler = new EventHandler(getGameContext(), getContexts());
        eventHandler.addHandlers(scene);

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

    private void renderContexts() {
        getContexts().forEach(ApplicationContext::render);
    }

    private static void setCurrentTime(long newTime) {
        previousTime = currentTime;
        currentTime = (newTime - startNanoTime) / 1_000_000_000;
        delta = currentTime - previousTime;
    }

    private void updateContexts() {
        double delta = getDelta();
        getContexts().forEach(context -> context.update(delta));
    }
}
