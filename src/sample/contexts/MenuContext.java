package sample.contexts;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sample.entities.Button;
import sample.entities.MessagePane;
import sample.entities.interfaces.Drawable;
import sample.util.Point;

public class MenuContext extends EntityCanvasContext {
    private double buttonX = 10;
    private Drawable button;
    public MenuContext(Canvas canvas) {
        super(canvas);
        /*
        addAll(HorizontalWall.Walls(2, 0, 44));
        addAll(VerticalWall.Walls(getWidth() - 6, 0, 30));
        addAll(VerticalWall.Walls(1, 0, 4));
        addAll(HorizontalWall.Walls(1, 84, 44));
        */

        Font menuFont = Font.font("Times New Roman", FontWeight.NORMAL, 12);
        // messages.add(new MessagePane(canvas, "Menu", Point.of(10, 20), menuFont, false, 3, 1));
        add(new MessagePane(canvas, "Options", Point.of(50, 20), menuFont, false, 3, 1));
        add(new MessagePane(canvas, "Help", Point.of(110, 20), menuFont, false, 3, 1));
        button = make();
        add(button);
    }

    public MenuContext(Canvas canvas, double xOffset, double yOffset) {
        super(canvas, xOffset, yOffset);
    }

    @Override
    public void draw() {
        GraphicsContext gc = getGraphicsContext();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, getWidth(), getHeight());
        gc.setLineWidth(1);
        super.draw();
    }

    public void toggleMenu() {
        if (buttonX == 10) {
            buttonX = 20;
            remove(button);
            button = make();
            add(button);
        } else {
            buttonX = 10;
            remove(button);
            button = make();
            add(button);
        }
    }

    @Override
    public void update(double delta) {
        // No-op
    }

    private Button make() {
        return new Button("Menu", Point.of(buttonX, 20), this::toggleMenu);
    }
}
