package sample.entities;

import javafx.event.EventTarget;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
import sample.entities.events.RemoveMessageEvent;
import sample.entities.interfaces.Clickable;
import sample.util.Constants;
import sample.util.Point;

public class MessagePane extends Message implements Clickable {
    private int xOffset = 10;
    private int yOffset = 10;
    private boolean drawButton = false;
    private EventTarget eventTarget;

    public MessagePane(EventTarget eventTarget, String text, Point point) {
        super(text, point);
        this.eventTarget = eventTarget;
    }

    public MessagePane(EventTarget eventTarget, String text, Point point, Font font, boolean drawButton) {
        super(text, point, font);
        this.eventTarget = eventTarget;
        this.drawButton = drawButton;
    }

    public MessagePane(EventTarget eventTarget, String text, Point point, Font font, int yOffset) {
        super(text, point, font);
        this.eventTarget = eventTarget;
        this.yOffset = yOffset;
    }

    public MessagePane(EventTarget eventTarget, String text, Point point, Font font, boolean drawButton, int yOffset) {
        this(eventTarget, text, point, font, drawButton);
        this.yOffset = yOffset;
    }

    public MessagePane(EventTarget eventTarget, String text, Point point, Font font, boolean drawButton, int yOffset, int xOffset) {
        this(eventTarget, text, point, font, drawButton, yOffset);
        this.xOffset = xOffset;
    }

    @Override
    public void draw(GraphicsContext gc) {
        clear(gc);
        super.draw(gc);
        drawBorder(gc);
        if (drawButton) {
            drawButton(gc);
        }
    }

    @Override
    public void onClick(Point point) {
        if (buttonIntersects(point)) {
            RemoveMessageEvent.fire(eventTarget, this);
        }
    }

    private boolean buttonIntersects(Point point) {
        Point location = getButtonStart();
        return location.getX() < point.getX() &&
               location.getY() < point.getY() &&
               (location.getX() + Constants.BUTTON.WIDTH) > point.getX() &&
               (location.getY() + Constants.BUTTON.HEIGHT) > point.getY();
    }

    private void clear(GraphicsContext gc) {
        Pair<Double, Double> dimensions = getDimensions();
        Point start = getBorderStart();
        gc.strokeRect(start.getX(), start.getY(), dimensions.getValue(), dimensions.getKey());
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
        return Point.of(location.minusX(xOffset).getX(), location.minusY(dimensions.getKey()).plusY(yOffset).getY());
    }

    private Point getButtonStart() {
        Pair<Double, Double> dimensions = getDimensions();
        return getBorderStart().plusX(dimensions.getValue())
                               .minusX(Constants.BUTTON.WIDTH)
                .plusY(Constants.BUTTON.HEIGHT).plusY(yOffset)
                               .minusY(dimensions.getKey());
    }

    // https://stackoverflow.com/a/13020490/1701316
    private Pair<Double, Double> getDimensions() {
        // If using CSS, need to add to a throwaway Scene
        Text measurer = new Text(getText());
        measurer.setFont(getFont());
        Bounds bounds = measurer.getLayoutBounds();
        return new Pair<>(bounds.getHeight() * 1.1, bounds.getWidth() * 1.1);
    }
}
