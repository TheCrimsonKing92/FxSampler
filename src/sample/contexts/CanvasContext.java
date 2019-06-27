package sample.contexts;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class CanvasContext extends ApplicationContext<Canvas> {
    private GraphicsContext graphicsContext;
    private double height;
    private double width;

    public CanvasContext(Canvas canvas) {
        this(canvas, 0, 0);
    }

    public CanvasContext(Canvas canvas, double xOffset, double yOffset) {
        super(canvas, xOffset, yOffset);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.height = canvas.getHeight();
        this.width = canvas.getWidth();
    }

    protected void bothRect(double x, double y, double width, double height) {
        GraphicsContext gc = getGraphicsContext();
        gc.fillRect(x, y, width, height);
        gc.strokeRect(x, y, width, height);
    }

    protected void bothRect(double x, double y, double width, double height, Color color) {
        GraphicsContext gc = getGraphicsContext();
        gc.setFill(color);
        gc.setStroke(color);
        gc.fillRect(x, y, width, height);
        gc.strokeRect(x, y, width, height);
    }

    private void clear() {
        getGraphicsContext().clearRect(0, 0, width, height);
    }

    public abstract void draw();

    @Override
    public final void render() {
        clear();
        draw();
    }

    protected GraphicsContext getGraphicsContext() { return graphicsContext; }

    protected double getHeight() { return height; }

    protected double getWidth() { return width; }
}