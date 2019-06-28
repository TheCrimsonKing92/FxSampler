package sample.contexts;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.entities.Fire;
import sample.entities.events.AddMessageEvent;
import sample.entities.events.RemoveEntityEvent;
import sample.entities.events.RemoveMessageEvent;
import sample.util.*;
import sample.entities.Player;

public class GameContext extends EntityCanvasContext {
    private Point gameStart = Point.of(0, 100);
    private boolean paused = false;
    private Point skyStart = Point.of(0, 0);

    public GameContext(Canvas canvas) {
        this(canvas, 0, 0);
    }

    public GameContext(Canvas canvas, double xOffset, double yOffset) {
        super(canvas, xOffset, yOffset);
        canvas.addEventHandler(AddMessageEvent.MESSAGE_ADDED, event -> add(event.getMessage()));
        canvas.addEventHandler(RemoveEntityEvent.ENTITY_REMOVED, event -> remove(event.getEntity()));
        canvas.addEventHandler(RemoveMessageEvent.MESSAGE_REMOVED, event -> remove(event.getMessage()));
        add(new Fire(canvas));
        add(new Player());
    }

    @Override
    public void draw() {
        GraphicsContext gc = getGraphicsContext();
        drawSky(gc);
        drawGame();

        super.draw();
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
        Player player = getPlayer();
        switch (direction) {
            case UP:
                player.moveY();
                break;
            case RIGHT:
                player.moveX();
                break;
            case LEFT:
                player.reverseX();
                break;
            case DOWN:
            default:
                player.reverseY();
                break;
        }

        if (hasCollision(player)) {
            player.revertLast();
        }
    }

    @Override
    public void update(double delta) {
        if (paused) {
            return;
        }

        super.update(delta);
    }

    private void drawGame() {
        drawGameBackground();
        drawEntities();
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
