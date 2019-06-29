package sample.contexts;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.entities.Fire;
import sample.entities.Message;
import sample.entities.events.AddMessageEvent;
import sample.entities.events.RemoveEntityEvent;
import sample.entities.events.RemoveMessageEvent;
import sample.util.*;
import sample.entities.Player;

public class GameContext extends EntityCanvasContext {
    private Point gameStart = Point.of(0, 100);
    private boolean menuOpen = false;
    private boolean paused = false;
    private Message pauseMessage = null;
    private Point skyStart = Point.of(0, 0);

    public GameContext(Canvas canvas) {
        this(canvas, 0, 0);
    }

    public GameContext(Canvas canvas, double yOffset) {
        this(canvas, 0, yOffset);
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

    public void handle(PlayerMoveEvent event) {
        getPlayer().handle(event);
    }

    public void handle(PlayerMovedEvent event) {
        if (paused) {
            return;
        }

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

    private void closeMenu() {

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
        gc.fillOval(-5, -5, Constants.ENTITIES.SUN.WIDTH, Constants.ENTITIES.SUN.HEIGHT);
    }

    private void handleMenu() {
        if (paused) {
            return;
        }

        if (menuOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    private void handlePause() {
        if (paused) {
            unpause();
        } else {
            pause();
        }
    }

    private void openMenu() {

    }

    private void pause() {
        paused = true;
        pauseMessage = new Message(Constants.TEXT.PAUSED, Point.of(20, 50), Color.BLACK, null);
        add(pauseMessage);
    }

    private void unpause() {
        paused = false;
        remove(pauseMessage);
        pauseMessage = null;
    }
}
