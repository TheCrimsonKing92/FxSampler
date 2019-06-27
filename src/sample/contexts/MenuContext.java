package sample.contexts;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sample.entities.HorizontalWall;
import sample.entities.Message;
import sample.entities.MessagePane;
import sample.entities.VerticalWall;
import sample.util.EntityManager;
import sample.util.Point;

import java.util.ArrayList;
import java.util.List;

public class MenuContext extends CanvasContext {
    private EntityManager entityManager = new EntityManager();
    private List<Message> messages = new ArrayList<>();
    public MenuContext(Canvas canvas) {
        super(canvas);
        entityManager.addAll(HorizontalWall.Walls(2, 0, 44));
        entityManager.addAll(VerticalWall.Walls(getWidth() - 6, 0, 30));
        entityManager.addAll(VerticalWall.Walls(1, 0, 4));
        entityManager.addAll(HorizontalWall.Walls(1, 84, 44));

        Font menuFont = Font.font("Times New Roman", FontWeight.NORMAL, 12);
        messages.add(new MessagePane("Menu", Point.of(10, 20), menuFont, false, 2));
        messages.add(new MessagePane("Options", Point.of(50, 20), menuFont, false, 2));
        messages.add(new MessagePane("Help", Point.of(110, 20), menuFont, false, 2));
    }

    public MenuContext(Canvas canvas, double xOffset, double yOffset) {
        super(canvas, xOffset, yOffset);
    }

    @Override
    public void draw() {
        GraphicsContext gc = getGraphicsContext();
        entityManager.getEntities().forEach(entity -> entity.draw(gc));
        messages.forEach(message -> message.draw(gc));
    }

    @Override
    public void update(double delta) {
        // No-op
    }
}