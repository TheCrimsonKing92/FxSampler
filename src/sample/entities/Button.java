package sample.entities;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Pair;
import sample.entities.interfaces.Clickable;
import sample.entities.interfaces.DrawableText;
import sample.util.Point;

public class Button implements Clickable, DrawableText {
    private Font font = Font.font("Times New Roman", FontWeight.NORMAL, 10);
    private Point location;
    private Runnable toDo;
    private String value;

    public Button(String value) {
        this(Point.of(10, 10), value);
    }

    public Button(Point location) {
        this.location = location;
        this.value = "Text";
    }

    public Button(Point location, Runnable toDo) {
        this("Text", location, toDo);
    }

    public Button(Point location, String value) {
        this.location = location;
        this.value = value;
    }

    public Button(String value, Point location, Runnable toDo) {
        this.location = location;
        this.toDo = toDo;
        this.value = value;
    }

    public void draw(GraphicsContext gc) {
        gc.fillText(getValue(), getLocation().getX(), getLocation().getY());
        gc.strokeRect(getLocation().getX() - 2, getLocation().minusY(getDimensions().getValue() / 2).getY(), getDimensions().getValue() + 9, getDimensions().getKey() + 3);
    }

    @Override
    public void onClick(Point point) {
        if (intersects(point) && toDo != null) {
            toDo.run();
        }
    }

    @Override
    public Point getLocation() {
        return location;
    }

    public boolean intersects(Point point) {
        Point location = getLocation();
        Pair<Double, Double> dimensions = getDimensions();
        System.out.println("Dimensions: " + dimensions);
        return location.getX() < point.getX() &&
                location.minusY(dimensions.getKey()).getY() < point.getY() &&
                (location.plusX(dimensions.getValue()).getX()) > point.getX() &&
                (location.getY()) > point.getY();
    }

    // https://stackoverflow.com/a/13020490/1701316
    private Pair<Double, Double> getDimensions() {
        // If using CSS, need to add to a throwaway Scene
        Text measurer = new Text(getValue());
        measurer.setFont(font);
        Bounds bounds = measurer.getLayoutBounds();
        return new Pair<>(bounds.getHeight(), bounds.getWidth());
    }

    @Override
    public String getValue() {
        return value;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
