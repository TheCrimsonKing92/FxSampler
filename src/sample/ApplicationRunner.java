package sample;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import sample.contexts.ApplicationContext;
import sample.contexts.GameContext;
import sample.util.EventHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

public class ApplicationRunner {
    private final static long startNanoTime = System.nanoTime();

    private static long currentMilli = 0;
    private static long currentNano = 0;
    private static long deltaMilli = 0;
    private static long previousMilli = 0;
    private static long previousNano = 0;
    private static long updateDelta = 0;
    private static long updateThreshold = BigDecimal.valueOf(1000).divide(BigDecimal.valueOf(60), RoundingMode.HALF_UP).longValue();

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

    public static double getUpdateDelta() { return updateDelta; }

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
        previousNano = currentNano;
        currentNano = newTime;
        previousMilli = currentMilli;
        currentMilli = (currentNano - startNanoTime) / 1_000_000;
        deltaMilli = currentMilli - previousMilli;
        updateDelta = updateDelta + deltaMilli;
    }

    private void updateContexts() {
        if (updateDelta < updateThreshold) {
            return;
        }

        double steps = updateDelta / updateThreshold;

        getContexts().forEach(context -> context.update(steps));
        updateDelta = 0;
    }
}
