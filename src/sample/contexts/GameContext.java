package sample.contexts;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.entities.Entity;
import sample.entities.Fire;
import sample.entities.Message;
import sample.util.*;
import sample.entities.Player;

public class GameContext extends CanvasContext {
    private EntityManager entityManager = new EntityManager();
    private Point gameStart = Point.of(0, 100);
    private boolean paused = false;
    private Point skyStart = Point.of(0, 0);

    public GameContext(Canvas canvas) {
        super(canvas);
        entityManager.add(new Fire());
        entityManager.add(new Player());
    }

    public GameContext(Canvas canvas, double xOffset, double yOffset) {
        super(canvas, xOffset, yOffset);
    }

    @Override
    public void draw() {
        GraphicsContext gc = getGraphicsContext();
        drawSky(gc);
        drawGame(gc);

        entityManager.getEntities().forEach(entity -> entity.draw(gc));
    }

    public void handle(GameChangedEvent event) {
        GameChangeType change = event.getEvent().getBody();
        switch (change) {
            case PAUSE:
                handlePause();
                break;
            case MENU:
            default:
                break;
        }
    }

    public void handle(PlayerMovedEvent event) {
        Direction direction = event.getEvent().getBody();
        switch (direction) {
            case UP:
                getPlayer().moveY()
        }
    }

    @Override
    public void update(double delta) {
        if (paused) {
            return;
        }

        entityManager.getEntities().stream().filter(Entity::canTick).forEach(entity -> entity.tick(delta));
        entityManager.removeEntities();
    }

    protected Player getPlayer() {
        return entityManager.getPlayer();
    }

    protected boolean hasPlayer() {
        return entityManager.hasPlayer();
    }

    private void drawEntities(GraphicsContext gc) {
        entityManager.getEntities().forEach(entity -> entity.draw(gc));
    }

    private void drawGame(GraphicsContext gc) {
        drawGameBackground();
        drawEntities(gc);
    }

    private void drawGameBackground() {
        bothRect(gameStart.getX(), gameStart.getY(), getWidth(), 500, Color.LAWNGREEN);
    }

    private void drawSky(GraphicsContext gc) {
        drawSkyBackground();
        drawSun(gc);
    }

    private void drawSkyBackground() {
        bothRect(skyStart.getX(), skyStart.getY(), getWidth(), 100, Color.SKYBLUE);
    }

    private void drawSun(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.setStroke(Color.YELLOW);
        gc.fillOval(-5, -5, 40, 40);
    }

    private void handlePause() {
        if (paused) {
            unpause();
        } else {
            pause();
        }
    }

    private void pause() {
        paused = true;
        // entityManager.add(new Message("PAUSED", Point.of(20, 50), Color.BLACK, null));
    }

    private void unpause() {
        paused = false;
        // entityManager.remove("PAUSED");
    }
}
