package sample.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import sample.entities.interfaces.DrawableText;
import sample.util.Point;

public class Message implements DrawableText {
    private Point location;
    private String text;
    private Color fill = Color.RED;
    private Color stroke = Color.BLACK;
    private Font font = Font.font("Times New Roman", FontWeight.BOLD, 48);

    public Message(String text, Point point) {
        this.location = point;
        this.text = text;
    }

    public Message(String text, Point point, double fontSize) {
        this(text, point);
        font = Font.font("Times New Roman", FontWeight.BOLD, fontSize);
    }

    public Message(String text, Point point, Font font) {
        this(text, point);
        setFont(font);
    }

    public Message(String text, Point point, Color fill, Color stroke) {
        this(text, point);
        this.fill = fill;
        this.stroke = stroke;
    }

    public Message(String text, Point point, Color fill, Color stroke, Font font) {
        this(text, point, fill, stroke);
        setFont(font);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFont(font);
        fill(gc);
        stroke(gc);
    }

    @Override
    public void fill(GraphicsContext gc) {
        if (fill != null) {
            gc.setFill(fill);
            Point location = getLocation();
            gc.fillText(text, location.getX(), location.getY());
        }
    }

    @Override
    public Point getLocation() { return location; }

    @Override
    public String getText() { return text; }

    @Override
    public void stroke(GraphicsContext gc) {
        if (stroke != null) {
            gc.setStroke(stroke);
            Point location = getLocation();
            gc.strokeText(text, location.getX(), location.getY());
        }
    }

    private void setFont(Font font) {
        if (font == null) {
            return;
        }

        this.font = font;
    }
}
