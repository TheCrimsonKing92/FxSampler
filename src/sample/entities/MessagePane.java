package sample.entities;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
import sample.Manager;
import sample.entities.interfaces.Clickable;
import sample.util.Constants;
import sample.util.Point;

public class MessagePane extends Message implements Clickable {
    private int borderOffset = 10;
    private boolean drawButton = false;

    public MessagePane(String text, Point point) {
        super(text, point);
    }

    public MessagePane(String text, Point point, Font font) {
        super(text, point, font);
    }

    public MessagePane(String text, Point point, Font font, boolean drawButton) {
        super(text, point, font);
        this.drawButton = drawButton;
    }

    public MessagePane(String text, Point point, Font font, int offset) {
        super(text, point, font);
        borderOffset = offset;
    }

    public MessagePane(String text, Point point, Color fill, Color stroke) {
        super(text, point, fill, stroke);
    }

    public MessagePane(String text, Point point, Color fill, Color stroke, Font font) {
        super(text, point, fill, stroke, font);
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        drawBorder(gc);
        if (drawButton) {
            drawButton(gc);
        }
    }

    @Override
    public void onClick(Point point) {
        if (buttonIntersects(point)) {
            Manager.remove(this);
        }
    }

    private boolean buttonIntersects(Point point) {
        Point location = getButtonStart();
        return location.getX() < point.getX() &&
               location.getY() < point.getY() &&
               (location.getX() + Constants.BUTTON.WIDTH) > point.getX() &&
               (location.getY() + Constants.BUTTON.HEIGHT) > point.getY();
    }

    private void drawBorder(GraphicsContext gc) {
        Pair<Double, Double> dimensions = getDimensions();
        Point start = getBorderStart();
        gc.strokeRect(start.getX(), start.getY(), dimensions.getValue(), dimensions.getKey());
    }

    private void drawButton(GraphicsContext gc) {
        Point buttonStart = getButtonStart();
        gc.strokeRect(buttonStart.getX(), buttonStart.getY(), Constants.BUTTON.WIDTH, Constants.BUTTON.HEIGHT);
        drawX(gc);
    }

    private void drawX(GraphicsContext gc) {
        gc.setLineWidth(1);

        Point lineStart = getButtonStart().plusX(2).plusY(2);
        Point lineEnd = getButtonStart().plusX(Constants.BUTTON.WIDTH).minusX(2).plusY(Constants.BUTTON.HEIGHT).minusY(2);
        gc.strokeLine(lineStart.getX(), lineStart.getY(), lineEnd.getX(), lineEnd.getY());

        lineStart = getButtonStart().plusX(Constants.BUTTON.WIDTH).minusX(2).plusY(2);
        lineEnd = getButtonStart().plusX(2).plusY(Constants.BUTTON.HEIGHT).minusY(2);
        gc.strokeLine(lineStart.getX(), lineStart.getY(), lineEnd.getX(), lineEnd.getY());
    }

    private Point getBorderStart() {
        Pair<Double, Double> dimensions = getDimensions();
        Point location = getLocation();
        return Point.of(location.getX(), location.minusY(dimensions.getKey()).plusY(borderOffset).getY());
    }

    private Point getButtonStart() {
        Pair<Double, Double> dimensions = getDimensions();
        return getBorderStart().plusX(dimensions.getValue())
                               .minusX(Constants.BUTTON.WIDTH)
                               .plusY(Constants.BUTTON.HEIGHT).plusY(borderOffset)
                               .minusY(dimensions.getKey());
    }

    // https://stackoverflow.com/a/13020490/1701316
    private Pair<Double, Double> getDimensions() {
        // If using CSS, need to add to a throwaway Scene
        Text measurer = new Text(getText());
        measurer.setFont(getFont());
        Bounds bounds = measurer.getLayoutBounds();
        return new Pair<>(bounds.getHeight(), bounds.getWidth());
    }
}
