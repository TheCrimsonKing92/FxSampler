package sample.entities;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.entities.interfaces.Clickable;
import sample.util.Point;

public class MessagePane extends Message implements Clickable {
    private Pane canvas = new Pane();

    public MessagePane(String text, Point point) {
        super(text, point);
        canvas.setPrefSize(100, 100);
    }

    public MessagePane(String text, Point point, Font font) {
        super(text, point, font);
    }

    public MessagePane(String text, Point point, Color fill, Color stroke) {
        super(text, point, fill, stroke);
    }

    public MessagePane(String text, Point point, Color fill, Color stroke, Font font) {
        super(text, point, fill, stroke, font);
    }

    @Override
    public void onClick() {

    }
}
