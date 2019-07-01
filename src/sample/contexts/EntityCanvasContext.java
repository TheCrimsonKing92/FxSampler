package sample.contexts;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sample.entities.Entity;
import sample.entities.Message;
import sample.entities.Player;
import sample.entities.interfaces.Clickable;
import sample.entities.interfaces.Collideable;
import sample.entities.interfaces.Drawable;
import sample.util.DrawableManager;
import sample.util.EntityManager;
import sample.util.MessageManager;
import sample.util.Point;

import java.util.List;

public abstract class EntityCanvasContext extends CanvasContext {
    private DrawableManager drawableManager = new DrawableManager();
    private EntityManager entityManager = new EntityManager();
    private MessageManager messageManager = new MessageManager();

    public EntityCanvasContext(Canvas canvas) {
        super(canvas);
    }

    public EntityCanvasContext(Canvas canvas, double xOffset, double yOffset) {
        super(canvas, xOffset, yOffset);
    }

    @Override
    public void draw() {
        drawDrawables();
        drawEntities();
        drawMessages();
    }

    @Override
    public void handle(Point point) {
        System.out.println("Handling click at: " + point);
        Point transformed = transform(point);
        System.out.println("Transformed: " + transformed);
        if (!intersects(transformed)) {
            return;
        }

        getEntities().stream()
                     .filter(entity -> entity.intersects(transformed))
                     .filter(Clickable.class::isInstance)
                     .map(Clickable.class::cast)
                     .forEach(clickable -> clickable.onClick(transformed));
        getDrawables().stream()
                .filter(Clickable.class::isInstance)
                .map(Clickable.class::cast)
                .forEach(clickable -> clickable.onClick(transformed));
    }

    public boolean hasCollision(Collideable collideable) {
        return getEntities().stream()
                            .filter(entity -> entity != collideable)
                            .filter(Entity::collides)
                            .anyMatch(entity -> entity.intersects(collideable));
    }

    @Override
    public void update(double delta) {
        getEntities().stream()
                     .filter(Entity::canTick)
                     .forEach(entity -> entity.tick(delta));
        clearEntities();
    }

    protected <T extends Drawable> void add(T drawable) {
        drawableManager.add(drawable);
    }

    protected <T extends Entity> void add(T entity) {
        entityManager.add(entity);
    }

    protected <T extends Entity> void addAll(List<T> entities) {
        entityManager.addAll(entities);
    }

    protected <T extends Message> void add(T message) {
        messageManager.add(message);
    }

    protected <T extends Message> void addAllMessages(List<T> messages) {
        messageManager.addAll(messages);
    }

    protected void clearDrawables() {
        drawableManager.clear();
    }

    protected void clearEntities() {
        entityManager.clear();
    }

    protected void clearMessages() { messageManager.clear(); }

    protected void drawDrawables() {
        GraphicsContext gc = getGraphicsContext();
        getDrawables().forEach(drawable -> drawable.draw(gc));
    }

    protected void drawEntities() {
        GraphicsContext gc = getGraphicsContext();
        getEntities().forEach(entity -> entity.draw(gc));
    }

    protected void drawMessages() {
        GraphicsContext gc = getGraphicsContext();
        getMessages().forEach(message -> message.draw(gc));
    }

    protected List<Drawable> getDrawables() {
        return drawableManager.getCurrent();
    }

    protected List<Entity> getEntities() {
        return entityManager.getCurrent();
    }

    protected List<Message> getMessages() { return messageManager.getCurrent(); }

    protected Player getPlayer() {
        return entityManager.getPlayer();
    }

    protected boolean hasPlayer() {
        return entityManager.hasPlayer();
    }

    protected <T extends Drawable> void remove(T drawable) {
        drawableManager.remove(drawable);
    }

    protected <T extends Entity> void remove(T entity) {
        entityManager.remove(entity);
    }

    protected <T extends Message> void remove(T message) {
        messageManager.remove(message);
    }
}
